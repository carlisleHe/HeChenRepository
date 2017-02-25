package com.newland.wechat.proxy;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class KetamaDistributionTest {

	static Random ran = new Random();
	
	private static final Integer EXE_TIMES = 400000;
	
	private static final Integer NODE_COUNT = 4;
	
	private static final Integer VIRTUAL_NODE_COUNT = 500;
	
	public static void main(String[] args) {
		KetamaDistributionTest test = new KetamaDistributionTest();
		

		Map<WechatNode, Integer> nodeRecord = new HashMap<WechatNode, Integer>();
		
		List<WechatNode> allNodes = test.getNodes(NODE_COUNT);
		KetamaNodeLocator locator = new KetamaNodeLocator(HashAlgorithm.KETAMA_HASH, VIRTUAL_NODE_COUNT,KetamaNodeLocator.IteratorType.NEXT_NODE);
		
		locator.updateLocator(allNodes);
		
		List<String> allKeys = test.getAllStrings();
		for (String key : allKeys) {
			WechatNode node = locator.getPrimary(key);
			
			Integer times = nodeRecord.get(node);
			if (times == null) {
				nodeRecord.put(node, 1);
			} else {
				nodeRecord.put(node, times + 1);
			}
		}
		
		System.out.println("Nodes count : " + NODE_COUNT + ", Keys count : " + EXE_TIMES + ", Normal percent : " + (float) 100 / NODE_COUNT + "%");
		System.out.println("-------------------- boundary  ----------------------");
		for (Map.Entry<WechatNode, Integer> entry : nodeRecord.entrySet()) {
			System.out.println("Node name :" + entry.getKey().getNodeName() + " - Times : " + entry.getValue() + " - Percent : " + (float)entry.getValue() / EXE_TIMES * 100 + "%");
		}
		
	}
	
	
	/**
	 * Gets the mock node by the material parameter
	 * 
	 * @param nodeCount 
	 * 		the count of node wanted
	 * @return
	 * 		the node list
	 */
	private List<WechatNode> getNodes(int nodeCount) {
		List<WechatNode> nodes = new ArrayList<WechatNode>();
		for (int k = 1; k <= nodeCount; k++) {
			WechatNode node = new WechatNode("node" + k); 
			nodes.add(node);
		}
		return nodes;
	}
	
	/**
	 *	All the keys	
	 */
	private List<String> getAllStrings() {
		List<String> allStrings = new ArrayList<String>(EXE_TIMES);
		
		for (int i = 0; i < EXE_TIMES; i++) {
			allStrings.add(generateRandomString(ran.nextInt(50)));
		}
		
		return allStrings;
	}
	
	/**
	 * To generate the random string by the random algorithm
	 * <br>
	 * The char between 32 and 127 is normal char
	 * 
	 * @param length
	 * @return
	 */
	private String generateRandomString(int length) {
		StringBuffer sb = new StringBuffer(length);
		
		for (int i = 0; i < length; i++) {
			sb.append((char) (ran.nextInt(95) + 32));
		}
		
		return sb.toString();
	}
}
