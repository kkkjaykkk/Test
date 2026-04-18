package lruCache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadSafeLRUCache {
    class Node {
        int key;
        int value;
        Node prev;
        Node next;
        long expireTime;

        public Node(int key, int value, long expireTime) {
            this.key = key;
            this.value = value;
            this.expireTime = expireTime;
        }
    }

    private final int capacity;
    private final Node dummy = new Node(0, 0, 0);
    private final ConcurrentHashMap<Integer, Node> keyToNode; //利用ConcurrentHashMap实现线程安全，不需要显式的加锁
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    public ThreadSafeLRUCache(int capacity) {
        this.capacity = capacity;
        keyToNode = new ConcurrentHashMap<>();
        dummy.next = dummy;
        dummy.prev = dummy;
    }

    public int get(int key) {
        readLock.lock(); //没涉及到修改双向链表
        try {
            Node node = getNode(key);
            return node != null ? node.value : -1;
        } finally {
            readLock.unlock();
        }
    }

    public void put(int key, int value, long expireTime, TimeUnit timeUnit) {
        writeLock.lock(); //但凡涉及到修改双向链表的代码都要加写锁
        try {
            Node node = getNode(key);
            if (node != null) {
                node.value = value;
                node.expireTime = System.currentTimeMillis() + timeUnit.toMillis(expireTime);
                return;
            }
            node = new Node(key, value, System.currentTimeMillis() + timeUnit.toMillis(expireTime));
            keyToNode.put(key, node);
            pushFront(node);
            if (keyToNode.size() > capacity) {
                Node workNode = dummy.next;
                while (workNode != dummy) {
                    if (workNode.expireTime < System.currentTimeMillis()) {
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
        } finally {
            writeLock.unlock();
        }
    }

    private Node getNode(int key) {
        writeLock.lock(); //但凡涉及到修改双向链表的代码都要加写锁
        try {
            if (!keyToNode.containsKey(key)) {
                return null;
            }
            Node node = keyToNode.get(key);
            if (node.expireTime < System.currentTimeMillis()) {
                keyToNode.remove(key);
                remove(node);
                return null;
            }
            remove(node);
            pushFront(node);
            return node;
        } finally {
            writeLock.unlock();
        }
    }

    private void remove(Node x) {
        x.prev.next = x.next;
        x.next.prev = x.prev;
    }

    private void pushFront(Node x) {
        x.next = dummy.next;
        x.prev = dummy;
        dummy.next.prev = x;
        dummy.next = x;
    }
}