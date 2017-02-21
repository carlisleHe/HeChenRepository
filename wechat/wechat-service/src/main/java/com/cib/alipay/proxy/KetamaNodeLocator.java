package com.cib.alipay.proxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;



/**
 * ketama一致性哈希策略 实现
 * 
 * 这种实现可能不兼容的libketama作为散列是从节点位置的分离。
 * 目前不支持的加权节点。
 * @see <a href="http://www.last.fm/user/RJ/journal/2007/04/10/392555/">RJ's
 *      blog post</a>
 * @author hongye
 * @since 2014-07-21
 */
public final class KetamaNodeLocator implements NodeLocator {
	
	private static final  Logger logger = Logger.getLogger(KetamaNodeLocator.class);
	
	static final int NUM_REPS = 160;
	protected IteratorType iteratorType = IteratorType.ARRAY;
	private volatile TreeMap<Long, AlipayNode> ketamaNodes; //构建WechatNode对象map
	private volatile Collection<AlipayNode> allNodes; //物理节点
	private final HashAlgorithm hashAlg; //hash算法
	private  int numReps; //每个服务器节点生成的虚拟节点数
	
	public KetamaNodeLocator(HashAlgorithm alg,int numReps,final IteratorType it) {
		super();

		hashAlg = alg;
		this.numReps = numReps;

		this.iteratorType = it;
	}

	private KetamaNodeLocator(TreeMap<Long, AlipayNode> smn,Collection<AlipayNode> an, HashAlgorithm alg) {
		super();
		ketamaNodes = smn;
		allNodes = an;
		hashAlg = alg;
	}

	public Collection<AlipayNode> getAll() {
		return allNodes;
	}

	public AlipayNode getPrimary(final String k) {
		AlipayNode rv = getNodeForKey(getHash(k));
		//byte[] digest = hashAlg.computeMd5(k);
		//WechatNode rv=getNodeForKey(hashAlg.hash(digest, 0));
		assert rv != null : "Found no node for key " + k;
		return rv;
	}
	
	public long getHash(final String k){
		long hash = hashAlg.hash(k);
		if(logger.isDebugEnabled())
		{
			logger.info("取得：["+k+"]的hashcode为："+hash);
		}
		return hash;
	}

	long getMaxKey() {
		return getKetamaNodes().lastKey();
	}

	AlipayNode getNodeForKey(long hash) {
		final AlipayNode rv;
		//如果找到这个节点，直接取节点返回
		if (!ketamaNodes.containsKey(hash)) {
			////得到大于当前key的那个子Map，然后从中取出第一个key，就是大于且离它最近的那个key  
			//tailMap(key):返回treemap中大于或等于key的对象构建的map
			SortedMap<Long, AlipayNode> tailMap = getKetamaNodes().tailMap(hash);
			if (tailMap.isEmpty()) {
				hash = getKetamaNodes().firstKey();
			} else {
				hash = tailMap.firstKey();
			}
			
			 //在JDK1.6中，ceilingKey方法可以返回大于且离它最近的那个key  
			 //For JDK1.6 version  
			//          key = getKetamaNodes.ceilingKey(key);  
			//          if (key == null) {  
			//              key = getKetamaNodes.firstKey();  
			//   }  

		}
		rv = getKetamaNodes().get(hash);
		return rv;
	}


	public Iterator<AlipayNode> getSequence(String k) {
		
		 switch (iteratorType) {
	        case ARRAY:
	        	AlipayNode[] nodes = allNodes.toArray(new AlipayNode[allNodes.size()]);
	    		List<AlipayNode> list = Arrays.asList(nodes);
	    		int keyStart=list.indexOf(getPrimary(k));
	    		return new NodeIterator(keyStart,nodes);
	        case NEXT_NODE:
	        	// Seven searches gives us a 1 in 2^7 chance of hitting the
	    		// same dead node all of the time.
	    		return new KetamaIterator(k, 7, getKetamaNodes(), hashAlg);
	        default:
	          throw new IllegalStateException("Unhandled IteratorType: " + iteratorType);
	        }
		
		
	}

	public NodeLocator getReadonlyCopy() {
		TreeMap<Long, AlipayNode> smn = new TreeMap<Long, AlipayNode>(
				getKetamaNodes());
		Collection<AlipayNode> an = new ArrayList<AlipayNode>(allNodes.size());

		// Rewrite the values a copy of the map.
		for (Map.Entry<Long, AlipayNode> me : smn.entrySet()) {
			me.setValue((AlipayNode)me.getValue());
		}

		// Copy the allNodes collection.
		for (AlipayNode n : allNodes) {
			an.add((AlipayNode)n);
		}

		return new KetamaNodeLocator(smn, an, hashAlg);
	}

	@Override
	public void updateLocator(List<AlipayNode> nodes) {
		allNodes = nodes;
		setKetamaNodes(nodes);
	}


	protected TreeMap<Long, AlipayNode> getKetamaNodes() {
		return ketamaNodes;
	}

	/**
	 * 设置ketamanodelocator与节点列表应该使用
	 * 
	 */
	protected void setKetamaNodes(List<AlipayNode> nodes) {
		if(logger.isDebugEnabled()){
			logger.info("服务器列表:");
			for (AlipayNode wn : nodes) {
				logger.info("服务器:"+wn.getNodeHost());
			}
		}
		
		TreeMap<Long, AlipayNode> newNodeMap = new TreeMap<Long, AlipayNode>();
		////对所有节点，生成numReps个虚拟结点 
		for (AlipayNode node : nodes) {
			// Ketama does some special work with md5 where it reuses chunks.
			if (hashAlg == HashAlgorithm.KETAMA_HASH) {
				
				for (int i = 0; i < numReps / 4; i++) {
					//为这组虚拟结点得到惟一名称
					byte[] digest = HashAlgorithm.computeMd5(node.getNodeHost()+"-"+i);
                    //Md5是一个16字节长度的数组，将16字节的数组每四个字节一组，分别对应一个虚拟结点，把虚拟结点四个划分一组的原因
					for (int h = 0; h < 4; h++) {
						//对于每四个字节，组成一个long值数值，做为这个虚拟节点的在环中的惟一key
						Long k = ((long) (digest[3 + h * 4] & 0xFF) << 24)
								| ((long) (digest[2 + h * 4] & 0xFF) << 16)
								| ((long) (digest[1 + h * 4] & 0xFF) << 8)
								| (digest[h * 4] & 0xFF);
						//long k = hashAlg.hash(digest, h);
						newNodeMap.put(k, node);
					}
				}
			} else {
				for (int i = 0; i < numReps; i++) {
					newNodeMap.put(hashAlg.hash(node.getNodeHost()+"-"+i),node);
				}
			}
		}
		if(logger.isDebugEnabled()){
			for (Map.Entry<Long, AlipayNode> entry : newNodeMap.entrySet()) {
				logger.info("服务器 key= " + entry.getKey() + " and value= " + entry.getValue().getNodeHost());
				  }
		}
		
		assert newNodeMap.size() == numReps * nodes.size();
		ketamaNodes = newNodeMap;
	}
	
	
	
	class KetamaIterator  implements Iterator<AlipayNode> {

		  private final String key;
		  private long hashVal;
		  private int remainingTries;
		  private int numTries = 0;
		  private final HashAlgorithm hashAlg;
		  private final TreeMap<Long, AlipayNode> ketamaNodes;

		  protected KetamaIterator(final String k, final int t,
		      TreeMap<Long, AlipayNode> ketamaNodes, final HashAlgorithm hashAlg) {
		    super();
		    this.ketamaNodes = ketamaNodes;
		    this.hashAlg = hashAlg;
		    hashVal = hashAlg.hash(k);
		    remainingTries = t+1;
		    key = k;
		    nextHash();
		  }

		  private void nextHash() {
		    // this.calculateHash(Integer.toString(tries)+key).hashCode();
		    long tmpKey = hashAlg.hash((numTries++) + key);
		    // This echos the implementation of Long.hashCode()
		    hashVal += (int) (tmpKey ^ (tmpKey >>> 32));
		    hashVal &= 0xffffffffL; /* truncate to 32-bits */
		    remainingTries--;
		  }

		  public boolean hasNext() {
		    return remainingTries > 0;
		  }

		  public AlipayNode next() {
		    try {
		      return getNodeForKey(hashVal);
		    } finally {
		      nextHash();
		    }
		  }

		  public void remove() {
		    throw new UnsupportedOperationException("remove not supported");
		  }

		  private AlipayNode getNodeForKey(long hash) {
		    final AlipayNode rv;
		    if (!ketamaNodes.containsKey(hash)) {
		      // Java 1.6 adds a ceilingKey method, but I'm still stuck in 1.5
		      // in a lot of places, so I'm doing this myself.
		      SortedMap<Long, AlipayNode> tailMap = ketamaNodes.tailMap(hash);
		      if (tailMap.isEmpty()) {
		        hash = ketamaNodes.firstKey();
		      } else {
		        hash = tailMap.firstKey();
		      }
		    }
		    rv = ketamaNodes.get(hash);
		    return rv;
		  }
		}
	
	/**
	   * Type of Iterator to use.
	   */
	  public static enum IteratorType {
	    
	    ARRAY,

	    NEXT_NODE
	    
	  }
	
	
	public static void main(String[] args) {
		
		List<AlipayNode> wechatNodeList = new ArrayList<AlipayNode>();
		
		AlipayNode node1 = new AlipayNode();
		node1.setNodeContext("weixin");
		node1.setNodeHost("168.3.27.56");
		node1.setNodePort("8002");
		node1.setNodeMACAddress("0021971D084F");
		node1.setNodeName("scgWeixinServer");
		
		AlipayNode node2 = new AlipayNode();
		node2.setNodeContext("weibank/weixin");
		node2.setNodeHost("168.3.26.16");
		node2.setNodePort("8001");
		node2.setNodeMACAddress("0021971BM84F");
		node2.setNodeName("LzWeixinServer");

		
		AlipayNode node3 = new AlipayNode();
		node3.setNodeContext("weibank/weixin");
		node3.setNodeHost("168.3.27.86");
		node3.setNodePort("8001");
		node3.setNodeMACAddress("00219FFD084F");
		node3.setNodeName("LzWeixinServer");
		
		
		wechatNodeList.add(node1);
		wechatNodeList.add(node2);
		wechatNodeList.add(node3);
		KetamaNodeLocator knl = new KetamaNodeLocator( HashAlgorithm.KETAMA_HASH, 500,KetamaNodeLocator.IteratorType.NEXT_NODE);
		
		knl.updateLocator(wechatNodeList);
		AlipayNode n = knl.getPrimary("orVFSuGPZ7V-X5uQ3jU5NQfDsax0");//2859918084  //2860687341 86   2864925613 16  2860687341 56
/*		ketamaNodes  key= 3311226 and value= 168.3.26.16
				ketamaNodes  key= 4284183424 and value= 168.3.26.16

				-----------16----------------------

				ketamaNodes  key= 3311226 and value= 168.3.27.56
				ketamaNodes  key= 4293124247 and value= 168.3.26.16

				---------------------86------------------------

				ketamaNodes  key= 10247348 and value= 168.3.27.56
				ketamaNodes  key= 4287390698 and value= 168.3.27.56

				-----------------------56--------------------------*/
		
		System.out.println(n.getNodeHost()+":"+n.hashCode());
		for (Iterator<AlipayNode> i = knl.getSequence("orVFSuGPZ7V-X5uQ3jU5NQfDsax0"); i.hasNext();) {
			AlipayNode nn = i.next();
			System.out.println("取得下一个服务器："+nn.getNodeHost());
			
		}
		
	}
}
