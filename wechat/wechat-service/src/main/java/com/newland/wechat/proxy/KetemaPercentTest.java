package com.newland.wechat.proxy;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class KetemaPercentTest {

	static Random ran = new Random();
	
	/** key's count */
	private static final Integer EXE_TIMES = 400000;
	
	private static final Integer NODE_COUNT = 50;
	
	private static final Integer VIRTUAL_NODE_COUNT = 500;
	
	static List<String> allKeys = null;
	
	static {
		allKeys = getAllStrings();
	}
	
	public static void main(String[] args) {
		
		Map<String, List<AlipayNode>> map = generateRecord();
		
		List<AlipayNode> allNodes = getNodes(NODE_COUNT);
		System.out.println("Normal case : nodes count : " + allNodes.size());
		call(allNodes, map);
		
		allNodes = getNodes(NODE_COUNT + 10);
		System.out.println("Added case : nodes count : " + allNodes.size());
		call(allNodes, map);
		
		allNodes = getNodes(NODE_COUNT - 10);
		System.out.println("Reduced case : nodes count : " + allNodes.size());
		call(allNodes, map);
		
		int addCount = 0;
		int reduceCount = 0;
		for (Map.Entry<String, List<AlipayNode>> entry : map.entrySet()) {
			List<AlipayNode> list = entry.getValue();
			//System.err.println(list.size());
			if (list.size() == 3) {
				if (list.get(0).equals(list.get(1))) {
					addCount++;
				}
				
				if (list.get(0).equals(list.get(2))) {
					reduceCount++;
				}
			} else {
				System.out.println("It's wrong size of list, key is " + entry.getKey() + ", size is " + list.size());
			}
		}
		
		System.out.println(addCount + "   ---   " + reduceCount);
		
		System.out.println("Same percent in added case : " + (float) addCount * 100/ EXE_TIMES + "%");
		System.out.println("Same percent in reduced case : " + (float) reduceCount * 100/ EXE_TIMES + "%");
		
	}
	
	private static void call(List<AlipayNode> nodes, Map<String, List<AlipayNode>> map) {
		KetamaNodeLocator locator = new KetamaNodeLocator(HashAlgorithm.KETAMA_HASH, VIRTUAL_NODE_COUNT,KetamaNodeLocator.IteratorType.NEXT_NODE);
		
		locator.updateLocator(nodes);
		
		for (Map.Entry<String, List<AlipayNode>> entry : map.entrySet()) {
			AlipayNode node = locator.getPrimary(entry.getKey());
			
			if (node != null) {
				List<AlipayNode> list = entry.getValue();
				list.add(node);
			}
		}
	}
	
	private static Map<String, List<AlipayNode>> generateRecord() {
		Map<String, List<AlipayNode>> record = new HashMap<String, List<AlipayNode>>(EXE_TIMES);
		
		for (String key : allKeys) {
			List<AlipayNode> list = record.get(key);
			if (list == null) {
				list = new ArrayList<AlipayNode>();
				record.put(key, list);
			}
		}
		
		return record;
	}
	
	
	/**
	 * Gets the mock node by the material parameter
	 * 
	 * @param nodeCount 
	 * 		the count of node wanted
	 * @return
	 * 		the node list
	 */
	private static List<AlipayNode> getNodes(int nodeCount) {
		List<AlipayNode> nodes = new ArrayList<AlipayNode>();
		
		for (int k = 1; k <= nodeCount; k++) {
			AlipayNode node = new AlipayNode("node" + k);
			nodes.add(node);
		}
		
		return nodes;
	}
	
	/**
	 *	All the keys	
	 */
	private static List<String> getAllStrings() {
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
	private static String generateRandomString(int length) {
		StringBuffer sb = new StringBuffer(length);
		
		for (int i = 0; i < length; i++) {
			sb.append((char) (ran.nextInt(95) + 32));
		}
		
		return sb.toString();
	}
}
