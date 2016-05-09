package TTL_LRU;

import java.util.ArrayList;
import java.util.HashMap;


public class LRU <K,T> {

    private class Node{
        Node next;
        Node prev;
        K key;
        T value;
        long lastAccessed;
        Node(K key, T value){
            this.key = key;
            this.value = value;
            this.lastAccessed = System.currentTimeMillis();
            this.next = null;
            this.prev = null;
        }
    }
    
    private long timeToLive;
    HashMap<K,Node> map;
    Node head = new Node(null,null);
    Node tail = new Node(null,null);
    
    private int c;
    // @param capacity, an integer
    public LRU(long timeToLive, final int interval, int capacity) {
        // write your code here
    	this.timeToLive = timeToLive;
        c = capacity;
        map = new HashMap<K,Node>();
        head.next = tail;
        tail.prev = head;
        
        if (timeToLive > 0 && interval>0) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(interval*1000);
                        } catch (InterruptedException ex) {
                        }
                        cleanup();
                    }
                }
            });
 
            t.setDaemon(true);
            t.start();
        }
        
    }

    // @return an integer
    public T get(K key) {
    	
        // write your code here
    	synchronized (this){
	        if(!map.containsKey(key)){
	            return null;
	        }
	        Node cur = map.get(key);
	        cur.lastAccessed = System.currentTimeMillis();
	        cur.prev.next = cur.next;
	        cur.next.prev = cur.prev; 
	        movetotail(cur);
	        return map.get(key).value;
    	}
    }

    // @param key, an integer
    // @param value, an integer
    // @return nothing
    public void put(K key, T value) {
        // write your code here
    	synchronized (this) {
	        if(get(key)!=null){
	            map.get(key).value = value;
	            return;
	        }
	        Node cur = new Node(key,value);
	        map.put(key, cur);
	        if(map.size()>c){
	            // start = head.next.value
	            map.remove(head.next.key);
	            head.next = head.next.next;
	            head.next.prev = head;
	        }
	        movetotail(cur);
    	 }
    }
    
    public void remove(K key) {
        // write your code here
    	synchronized (this){
    		Node cur = map.get(key);
    		map.remove(key);
    	    cur.prev.next = cur.next;
	        cur.next.prev = cur.prev; 
    	}
    }
    
    public void movetotail(Node cur) {
        cur.next = tail;
        cur.prev = tail.prev;
        tail.prev.next = cur;
        tail.prev = cur;
    }
    
    public int size(){
    	return map.size();
    }
    

	public void cleanup() {
    	 
        long now = System.currentTimeMillis();
        ArrayList<K> deleteKey = null;
 
        synchronized (this) {      
        	Node itr = head;
        	deleteKey = new ArrayList<>();

            while (itr.next!=null && itr.next.key!=null) {
            	System.out.println("asdfasdf"+(now-itr.next.lastAccessed));
                if ((now > (timeToLive + itr.next.lastAccessed))) {
                    deleteKey.add(itr.next.key);
                }
                itr = itr.next;
            }
        }
 
        for (K key : deleteKey) {
            synchronized (this) {
                remove(key);
            }
            Thread.yield();
        }
    }
}