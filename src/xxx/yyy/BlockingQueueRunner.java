package xxx.yyy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.RandomStringUtils;

public class BlockingQueueRunner {
    private static final int MAX_QUEUE_SIZE = 64;
    private static final CountDownLatch start = new CountDownLatch(1);
    private final Queue<String> queue = new LinkedList<>();
    private int queueSize = 0;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmptyCondition = lock.newCondition();
    private final Condition notFullCondition = lock.newCondition();

    private static final AtomicLong globalCount = new AtomicLong(0L);

    public void put(final String val) {
        lock.lock();
        try {
            while (queueSize >= MAX_QUEUE_SIZE) {
                try {
                    notFullCondition.await();
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            queue.add(val);
            final boolean wasEmpty = (queueSize == 0);
            queueSize++;
            if (wasEmpty) {
                notEmptyCondition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    public String get(final int timeout) {
        long nanos = TimeUnit.MILLISECONDS.toNanos(timeout);
        lock.lock();
        try {
            while (queueSize == 0) {
                try {
                    nanos = notEmptyCondition.awaitNanos(nanos);
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if ((nanos <= 0) && (queueSize == 0)) {
                    return null;
                }
            }
            final boolean wasFull = (queueSize == MAX_QUEUE_SIZE);
            queueSize--;
            final String s = queue.remove();
            if (wasFull) {
                notFullCondition.signalAll();
            }
            return s;
        } finally {
            lock.unlock();
        }
    }

    private static class Writer implements Runnable {
        public Writer(final int count, final BlockingQueueRunner queue) {
            super();
            this.count = count;
            this.queue = queue;
        }
        private final int count;
        private final BlockingQueueRunner queue;
        @Override
        public void run() {
            try {
                start.await();
            } catch (final InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            for (int i = 0; i < count; i++) {
                final String s = RandomStringUtils.randomAlphanumeric(32);
                queue.put(s);
                System.out.println("Writer: " + Thread.currentThread().getId() + "   " + s);
            }
        }
    }

    private static class Reader implements Runnable {
        public Reader(final BlockingQueueRunner queue) {
            super();
            this.queue = queue;
        }
        private final BlockingQueueRunner queue;
        volatile boolean shutdown = false;
        volatile int readCount = 0;
        @Override
        public void run() {
            try {
                start.await();
            } catch (final InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            while (!shutdown) {
                final String s = queue.get(2000);
                if (s != null) {
                    System.out.println("Reader: " + Thread.currentThread().getId() + "   " + s);
                    readCount++;
                }
            }
            System.out.println("Reader: " + Thread.currentThread().getId() + "   " + readCount);
            globalCount.updateAndGet( n -> n + readCount);
        }
    }
    public static void main(final String[] args) throws InterruptedException {
        final BlockingQueueRunner queue = new BlockingQueueRunner();
        final int numWriters = 5;
        final int numReaders = 10;
        final int numCount = 30000;

        for (int i = 0; i < numWriters; i++) {
            new Thread(new Writer(numCount, queue)).start();
        }
        final List<Reader> readers = new ArrayList<>();
        for (int i = 0; i < numReaders; i++) {
            final Reader reader = new Reader(queue);
            readers.add(reader);
            new Thread(reader).start();
        }
        start.countDown();
        Thread.sleep(10000);
        for (final Reader reader : readers) {
            reader.shutdown = true;
        }
        Thread.sleep(2000);

        System.out.println("total count of messages: " + globalCount.longValue());
    }
}
