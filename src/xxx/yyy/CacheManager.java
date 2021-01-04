package xxx.yyy;

import java.util.HashMap;
import java.util.Map;

class CacheData {
    public CacheData(final String val) {
        super();
        this.val = val;
    }

    public final String val;
};

class Cache {

    public Cache(final String url, final CacheData data) {
        super();
        this.url = url;
        this.data = data;
    }

    String url;

    CacheData data;
}

/**
 * The CacheManager manages an LRU cache. It maintains two data structures:
 *
 * 1. Map of CacheData, keyed by cacheURL
 *
 * 2. Linked-List of cacheURLs. When the cache is at MAX_CACHE_SIZE and a new
 * cache entry needs to be added, the head of the list is removed from the
 * cache. When a cache is accessed, it is always removed from the current
 * position in the list, and added to the end of the list.
 *
 */
public class CacheManager {

    static class DoublyLinkedListNode<T> {

        public DoublyLinkedListNode(final T data) {
            this.data = data;
        }

        T data;
        DoublyLinkedListNode<T> next;
        DoublyLinkedListNode<T> prev;
    }

    static class DLList<T> {

        public DLList() {
            super();
        }

        DoublyLinkedListNode<T> head = null;

        DoublyLinkedListNode<T> tail = null;

        public void enqueue(final DoublyLinkedListNode<T> llNode) {
            if (head == null) {
                head = llNode;
                tail = llNode;
            } else {
                tail.next = llNode;
                llNode.prev = tail;
                tail = llNode;
            }
        }

        public T dequeue() {
            if (head == null) {
                return null;
            }

            final T val = head.data;
            head = head.next;
            head.prev = null;
            return val;
        }

        public boolean isTail(final DoublyLinkedListNode<T> node) {
            return (node == tail);
        }

        public void remove(final DoublyLinkedListNode<T> node) {
            if (node == null) {
                throw new IllegalArgumentException();
            }

            if (head == tail) {
                if (head != node) {
                    throw new RuntimeException("node is not in DoubleLinkedList");
                }
                head = null;
                tail = null;
            } else if (node == head) {
                head = head.next;
                head.prev = null;
            } else if (node == tail) {
                tail = tail.prev;
                tail.next = null;
            } else {
                if (node.prev == null || node.next == null) {
                    throw new IllegalArgumentException();
                }

                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
        }
    }

    public static final int MAX_CACHE_SIZE = 5;

    private final Map<String, DoublyLinkedListNode<Cache>> cacheMap = new HashMap<>();

    private final DLList<Cache> cacheList = new DLList<>();

    /**
     * Accesses a cache entry.
     *
     * @param cacheUrl
     * @return
     */
    public synchronized CacheData getCachedData(final String cacheUrl) {
        final DoublyLinkedListNode<Cache> node = cacheMap.get(cacheUrl);
        if (node != null) {
            /*
             * Move the cache entry to the end of the list (if it is already not
             * at the end of the list).
             */
            if (!cacheList.isTail(node)) {
                cacheList.remove(node);
                cacheList.enqueue(node);
            }
            return node.data.data;
        }
        return null;
    }

    /**
     * Adds a new cache entry, or updates an existing cache entry.
     *
     * @param cacheUrl
     * @param updatedData
     */
    public synchronized void addOrUpdateCache(final String cacheUrl, final CacheData updatedData) {
        final DoublyLinkedListNode<Cache> node = cacheMap.get(cacheUrl);
        if (node == null) {
            final Cache cacheInfo = new Cache(cacheUrl, updatedData);

            if (cacheMap.size() == MAX_CACHE_SIZE) {
                /*
                 * The cache is full, thus remove the head of the list, (the one
                 * that was accessed the earliest)
                 */
                final Cache toRemove = cacheList.dequeue();
                cacheMap.remove(toRemove.url);
                System.out.println("Evicted: " + toRemove.url);
            }
            final DoublyLinkedListNode<Cache> newNode = new DoublyLinkedListNode<>(cacheInfo);
            cacheList.enqueue(newNode);
            cacheMap.put(cacheUrl, newNode);
        } else {
            node.data.data = updatedData;
        }
    }

    public static void main(final String[] args) {
        final CacheManager cm = new CacheManager();

        cm.addOrUpdateCache("abc.com", new CacheData("foo"));
        cm.addOrUpdateCache("abc2.com", new CacheData("foo2"));
        cm.addOrUpdateCache("abc3.com", new CacheData("foo3"));
        cm.addOrUpdateCache("abc4.com", new CacheData("foo4"));
        cm.addOrUpdateCache("abc5.com", new CacheData("foo5"));
        cm.addOrUpdateCache("abc6.com", new CacheData("foo6"));
        cm.addOrUpdateCache("abc7.com", new CacheData("foo7"));

        {
            final CacheData cd = cm.getCachedData("abc4.com");
            if (cd != null) {
                System.out.println(cd.val);
            } else {
                System.out.println("no data for abc4.com");
            }
        }

        {
            final CacheData cd = cm.getCachedData("abc.com");
            if (cd != null) {
                System.out.println(cd.val);
            } else {
                System.out.println("no data for abc.com");
            }
        }

        cm.addOrUpdateCache("abc8.com", new CacheData("foo8"));
        cm.addOrUpdateCache("abc9.com", new CacheData("foo9"));
        cm.addOrUpdateCache("abc10.com", new CacheData("foo10"));
        cm.addOrUpdateCache("abc11.com", new CacheData("foo11"));

        {
            final CacheData cd = cm.getCachedData("abc4.com");
            if (cd != null) {
                System.out.println(cd.val);
            } else {
                System.out.println("no data for abc4.com");
            }
        }

        cm.addOrUpdateCache("abc12.com", new CacheData("foo12"));
        cm.addOrUpdateCache("abc10.com", new CacheData("foo100"));

        {
            final CacheData cd = cm.getCachedData("abc10.com");
            if (cd != null) {
                System.out.println(cd.val);
            } else {
                System.out.println("no data for abc10.com");
            }
        }
    }
}
