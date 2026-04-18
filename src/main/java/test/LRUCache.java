package test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * ClassName: LRUCache
 * Package: test
 * Description:
 *
 * @Author 龙培
 * @Create 2025/5/19 22:46
 * @Version 1.0
 */
public class LRUCache {
    class Node{
        int key;
        int value;
        Node prev;
        Node next;
        long expireTime;
        public Node(int key, int value,long expireTime){
            this.key = key;
            this.value = value;
            this.expireTime = expireTime;
        }
    }
    private final int capacity;
    private final Node dummy = new Node(0,0,0);
    private final Map<Integer,Node> keyToNode;
    public LRUCache(int capacity){
        this.capacity = capacity;
        keyToNode = new HashMap<>();
        dummy.next = dummy;
        dummy.prev = dummy;
    }
    public int get(int key){
        Node node = getNode(key);
        return node != null? node.value : -1;
    }
    public void put(int key, int value, long expireTime, TimeUnit timeUnit){
        Node node = getNode(key);
        if(node!=null){
            node.value = value;
            node.expireTime = System.currentTimeMillis()+timeUnit.toMillis(expireTime); //设置过期时间
            return;
        }
        node = new Node(key,value,System.currentTimeMillis()+timeUnit.toMillis(expireTime)); //timeUnit.toMillis(expireTime)将expireTime转换成对应的毫秒数
        keyToNode.put(key,node);
        pushFront(node);
        if(keyToNode.size()>capacity){
            Node workNode = dummy.next;
            while(workNode!= dummy){
                if(workNode.expireTime<System.currentTimeMillis()){
                    remove(workNode);
                    keyToNode.remove(workNode.key);
                    return;
                }
                workNode = workNode.next;
            }
            Node last = dummy.prev; //如果没有过期的结点就删除最后一个结点
            remove(last);
            keyToNode.remove(last.key);
        }
    }
    public Node getNode(int key){ //根据key得到结点并放到双向链表的头部
        if(!keyToNode.containsKey(key)){ //不存在该结点
            return null;
        }
        Node node = keyToNode.get(key);
        if(node.expireTime<System.currentTimeMillis()){//如果过期了就删除该结点
            keyToNode.remove(key);
            remove(node);
            return null;
        }
        //将该结点放到双向链表的头部
        remove(node);
        pushFront(node);
        return node;
    }
    public void remove(Node x){
        x.prev.next = x.next;
        x.next.prev = x.prev;
    }
    public void pushFront(Node x){
        x.next = dummy.next;
        x.prev = dummy;
        dummy.next.prev = x;
        dummy.next = x;
    }
    public static void main(String[] args){
        LRUCache lruCache = new LRUCache(2);
        lruCache.put(1,1,100,TimeUnit.SECONDS);
        lruCache.put(2,2,1,TimeUnit.SECONDS);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lruCache.put(3,3,100,TimeUnit.SECONDS);
//        try {
//            Thread.sleep(5);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        System.out.println(lruCache.get(1));
        System.out.println(lruCache.get(2));
        System.out.println(lruCache.get(3));
    }
}
