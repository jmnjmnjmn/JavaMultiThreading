package JAVA_Solution;

import java.util.*;



// HashMap with get, put, get random, remove , all in O(1)
public class RandomHash {
	
	public static void main(String args[]){
		randomhash r = new randomhash();
		r.put(1, 1);
		r.put(2, 2);
		r.put(3, 3);
		r.put(4, 4);
		r.put(5, 5);
		
		System.out.println(r.getRandom());
		
		r.remove(1);
		System.out.println(r.get(2));
		System.out.println(r.get(3));
		System.out.println(r.get(4));
		System.out.println(r.get(5));
		
	}
	
	public static class randomhash {
	 Map<Integer,Integer> map = new HashMap<>();
	 List<Integer> list = new ArrayList<>();
	int lastKey = -1;
	
	public randomhash(){
		
	}
	
	public int getRandom(){
		Random ran = new Random();
		return list.get(ran.nextInt(list.size()));
	}
	
	public int get(int key){
		if(!map.containsKey(key)) return -1;
		int idx = map.get(key);
		return list.get(idx);
	}
	
	public void put(int key, int value){
		if(map.containsKey(key))
			list.set(map.get(key),value);
		map.put(key, list.size());
		list.add(value);
		lastKey = key;
	}
	
	public void remove(int key){
		if(!map.containsKey(key)) return;
		int last = list.get(list.size()-1);
		int idx = map.get(key);
		//replace delete (k,v) pair with last pair in List;
		list.set(idx,last);
		list.remove(list.size()-1);
		map.put(lastKey, idx);
		map.remove(key);
	}
	
}
}
