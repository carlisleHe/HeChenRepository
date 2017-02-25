package com.newland.alipay.proxy;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * hash 值和节点列表长度取模，作为下标，简单的数组查询
 * 
 * @author hongye
 * @since 2014-07-21
 */
public class ArrayModNodeLocator implements NodeLocator {
	
	private  HashAlgorithm hashAlg;

	private AlipayNode[] nodes;
	
	/*public ArrayModNodeLocator(){
		super();
	}*/
	  
	public ArrayModNodeLocator(HashAlgorithm alg) {
	    super();
	    hashAlg = alg;
	  }

	private ArrayModNodeLocator(AlipayNode[] n, HashAlgorithm alg) {
	    super();
	    nodes = n;
	    hashAlg = alg;
	  } 

	@Override
	public AlipayNode getPrimary(String k) {
		return nodes[getServerForKey(k)];
	}

	@Override
	public Iterator<AlipayNode> getSequence(String k) {
		 return new ArrayModIterator(getServerForKey(k));
	}

	@Override
	public Collection<AlipayNode> getAll() {
		 return Arrays.asList(nodes);
	}

	@Override
	public NodeLocator getReadonlyCopy() {
		AlipayNode[] n = new AlipayNode[nodes.length];
	    for (int i = 0; i < nodes.length; i++) {
	      n[i] = nodes[i];
	    }
	    return new ArrayModNodeLocator(n, hashAlg);
	}

	@Override
	public void updateLocator(List<AlipayNode> nodes) {
		 this.nodes = nodes.toArray(new AlipayNode[nodes.size()]);

	}
	
	private int getServerForKey(String key) {
	    int rv = (int) (hashAlg.hash(key) % nodes.length);
	    assert rv >= 0 : "Returned negative key for key " + key;
	    assert rv < nodes.length : "Invalid server number " + rv + " for key "
	        + key;
	    return rv;
	  }
	
	class ArrayModIterator implements Iterator<AlipayNode> {

	    private final int start;
	    private int next = 0;

	    public ArrayModIterator(int keyStart) {
	      start = keyStart;
	      next = start;
	      computeNext();
	      assert next >= 0 || nodes.length == 1 : "Starting sequence at " + start
	          + " of " + nodes.length + " next is " + next;
	    }

	    public boolean hasNext() {
	      return next >= 0;
	    }

	    private void computeNext() {
	      if (++next >= nodes.length) {
	        next = 0;
	      }
	      if (next == start) {
	        next = -1;
	      }
	    }

	    public AlipayNode next() {
	      try {
	        return nodes[next];
	      } finally {
	        computeNext();
	      }
	    }

	    public void remove() {
	      throw new UnsupportedOperationException("Can't remove a node");
	    }
	  }

	public HashAlgorithm getHashAlg() {
		return hashAlg;
	}

	public void setHashAlg(HashAlgorithm hashAlg) {
		this.hashAlg = hashAlg;
	}

	public AlipayNode[] getNodes() {
		return nodes;
	}

	public void setNodes(AlipayNode[] nodes) {
		this.nodes = nodes;
	}
	
	

}
