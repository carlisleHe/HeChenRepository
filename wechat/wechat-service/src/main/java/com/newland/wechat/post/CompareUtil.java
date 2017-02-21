package com.newland.wechat.post;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
/**
 * 
 * @Description: 比较工具类
 * @ClassName:CompareUtil 
 * @author:xuzz
 * @date:2014-8-25
 */
public class CompareUtil {
	/**
	 * 按照字母顺序对mapkey进行排序
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String calcStrByMapKeyCharOrder(Map<String,String> map){
		if(map instanceof TreeMap<?,?>){
			Comparator c = ((TreeMap<?,?>)map).comparator();
			if(c != null){
				if((c instanceof CharOrderComparator)){
					return calcStr(map);
				}
			}
		}
		Map<String,String> tmap = new TreeMap<String, String>(new CharOrderComparator());
		for(Entry<String,String> entry :map.entrySet()){
			tmap.put(entry.getKey(), entry.getValue());
		}
		return calcStr(tmap);
	}
	/**
	 * 按照字母顺序对mapkey进行排序
	 * @param map
	 * @return
	 */
	public static String calcStrByNameValuePairCharOrder(List<NameValuePair> list){
		Collections.sort(list, new NameValuePairCharOrderComparator());
		return calcStr(list);
	}
	/**
	 * 拼接map里的key,value，以KVKV的形式返回
	 * @param map
	 * @return
	 */
	public static String calcStr(Map<String,String> map){
		StringBuffer sb = new StringBuffer();
		for(Entry<String, String> entry :map.entrySet()){
			sb.append(entry.getKey()).append(entry.getValue()==null?"":entry.getValue());
		}
		return sb.toString();
	}
	/**
	 * 拼接NameValuePair里的key,value，以KVKV的形式返回
	 * @param map
	 * @return
	 */
	public static String calcStr(List<NameValuePair> list){
		Collections.sort(list, new NameValuePairCharOrderComparator());
		StringBuffer sb = new StringBuffer();
		for(int j =0;j<list.size();j++){
			NameValuePair nv = list.get(j);
			sb.append(nv.getName()).append("=").append(nv.getValue()==null?"":nv.getValue());
			if(j<list.size()-1){
				sb.append("&");
			}
		}
		return sb.toString();
	}
	/**
	 * 按字母顺序排序比较器
	 * @Description:
	 * @ClassName:CharOrderComparator 
	 * @author:xuzz
	 * @date:2014-8-25
	 */
	public static class CharOrderComparator implements Comparator<String>{

		@Override
		public int compare(String o1, String o2) {
			char[] c1 = o1.toCharArray();
			char[] c2 = o2.toCharArray();
			for(int i =0;i<Math.min(c1.length, c2.length);i++){
				if((int)c1[i]==(int)c2[i]){
					continue;
				}
				else{
					return ((int)c1[i]-(int)c2[i]);
				}
			}
			return c1.length-c2.length;
		}
	}
	/**
	 * 按字母顺序排序比较器
	 * @Description:
	 * @ClassName:CharOrderComparator 
	 * @author:xuzz
	 * @date:2014-8-25
	 */
	public static class NameValuePairCharOrderComparator implements Comparator<NameValuePair>{

		@Override
		public int compare(NameValuePair o1, NameValuePair o2) {
			char[] c1 = o1.getName().toCharArray();
			char[] c2 = o2.getName().toCharArray();
			for(int i =0;i<Math.min(c1.length, c2.length);i++){
				if((int)c1[i]==(int)c2[i]){
					continue;
				}
				else{
					return ((int)c1[i]-(int)c2[i]);
				}
			}
			return c1.length-c2.length;
		}
	}
	public static void main(String[] args) {
		Map<String,String> map = new TreeMap<String,String>(new CharOrderComparator());
		map.put("app_id", "qbf2707273ee654faaa16f2b67884ce05d");
		map.put("appl_from", "sh");
		map.put("outer_appl_seq", "qbf2707273ee654faaa16f2b67884ce05d111111");
		map.put("appl_sts", "0");
		map.put("appr_amt", "111.00");
		System.out.println(calcStrByMapKeyCharOrder(map));
	}
}
