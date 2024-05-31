import java.util.*;

public class LRUcache {
    private int capacity;
    private ListNode head;
    private ListNode tail;
    private Map<String,ListNode> cache;
    LRUcache(int capacity) {
        this.capacity=capacity;
        this.head=new ListNode("head");
        this.tail=new ListNode("tail");
        head.next=tail;
        tail.prev=head;
        cache=new HashMap<>();
    }

    void put(String key, String data){
        if(cache.containsKey(data))
            return;
        if(cache.size()<capacity){
            ListNode newNode = insertDataInLL(data);
            cache.put(key, newNode);
        }
        else{
            removeLRU();
            cache.remove(key);
            ListNode newNode = insertDataInLL(data);
            cache.put(key, newNode);
        }
        System.out.println("Data added to cache");
        System.out.println(cache);
    }

    String get(String key){
        if(cache.containsKey(key)){
            System.out.println("Data from cache");
            removeLRU();
            ListNode newNode = insertDataInLL(key);
            cache.put(key, newNode);
            return cache.get(key).data;
        }
        System.out.println("No data in cache");
        return null;
    }

    ListNode insertDataInLL(String data){
        ListNode newNode = new ListNode(data);
        ListNode forward=head.next;
        head.next=newNode;
        newNode.next=forward;
        forward.prev=newNode;
        newNode.prev=head;

        return newNode;
    }

    void removeLRU(){
        ListNode prev=tail.prev.prev;
        ListNode lru=tail.prev;
        tail.prev=prev;
        prev.next=tail;
        lru.next=null;
        lru.prev=null;
    }
}


