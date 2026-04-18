package lruCache;

import test.LRUCache;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

/**
 * ClassName: LRUCacheWithTTL
 * Package: lruCache
 * Description:
 *
 * @Author 龙培
 * @Create 2025/5/20 14:56
 * @Version 1.0
 */
public class LRUCacheWithTTL {
    class Node {
        int key, value;
        Node prev, next;
        long expireTime;

        public Node(int k, int v, long expireTime) {
            this.key = k;
            this.value = v;
            this.expireTime = expireTime;
        }
    }

    private final int capacity;
    private final Node dummy = new Node(0, 0, 0);
    private final ConcurrentHashMap<Integer, Node> keyToNode;
    private final ReentrantLock lock = new ReentrantLock(); //其实get和put方法都会对双向链表修改，因此不用读写锁
    public LRUCacheWithTTL(int capacity) { //初始化容量，哨兵结点，以及映射Map
        this.capacity = capacity;
        dummy.next = dummy;
        dummy.prev = dummy;
        keyToNode = new ConcurrentHashMap<>();
    }
    public int get(int key){
        lock.lock();
        try {
            Node node = getNode(key);
            return node ==null ?-1:node.value;
        }
        finally {
            lock.unlock();
        }
    }
    public void put(int key, int value, long expireTime, TimeUnit timeUnit) {
        lock.lock();
        try {
            Node node = getNode(key);
            if(node != null){
                node.value =value;
                node.expireTime = System.currentTimeMillis() + timeUnit.toMillis(expireTime);
                return;
            }
            node = new Node(key,value,System.currentTimeMillis() + timeUnit.toMillis(expireTime));
            keyToNode.put(key,node);
            pushFront(node);
            if(keyToNode.size()>capacity){
                Node workNode = dummy.next;
                while(workNode!=dummy){
                    if(workNode.expireTime < System.currentTimeMillis()){
                        remove(workNode);
                        keyToNode.remove(workNode.key);
                        return;
                    }
                    workNode = workNode.next;
                }
                Node last = dummy.prev;
                remove(last);
                keyToNode.remove(last.key);
            }
        }
        finally {
            lock.unlock();
        }
    }
    private Node getNode(int key){ //获取结点并放置到链表头部
        lock.lock();
        try {
            if(!keyToNode.containsKey(key)) return null;
            Node node = keyToNode.get(key);
            if(node.expireTime < System.currentTimeMillis()){ //惰性删除
                remove(node);
                keyToNode.remove(key);
                return null;
            }
            remove(node);
            pushFront(node);
            return node; //返回结点，并放置到链表头部（最近使用过
        }
        finally {
            lock.unlock();
        }
    }
    private void pushFront(Node x){ //将结点放置到链表头部
        x.prev = dummy;
        x.next = dummy.next;
        dummy.next.prev = x;
        dummy.next = x;
    }
    private void remove(Node x){//移除双向链表中的结点
        x.next.prev = x.prev;
        x.prev.next = x.next;
    }
    public static void main(String[] args) {
        LRUCacheWithTTL lruCache = new LRUCacheWithTTL(2);
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

