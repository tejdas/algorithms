package xxx.yyy;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class MyPriorityQueue {
    private static final int MAX_RECORDS = 128;

    static class StreamData implements Comparable<StreamData> {
        public StreamData(final long timeStamp, final int rpd) {
            super();
            this.timeStamp = timeStamp;
            this.rpd = rpd; //requests-per-duration
        }

        private final long timeStamp;
        private final int rpd;

        @Override
        public int compareTo(final StreamData that) {
            return Integer.compare(that.rpd,  this.rpd);
        }
    }

    /*
     * PriorityQueue sorted by rpd (max rpd @ head of queue)
     */
    private final Queue<StreamData> queue = new LinkedList<>();

    /*
     * Queue to store only recent MAX_RECORDS added
     */
    private final PriorityQueue<StreamData> pqueue = new PriorityQueue<>(MAX_RECORDS);

    private int queueSize = 0;

    /**
     * Add a StreamData record
     * @param timeStamp
     * @param rpd
     *
     * Adding to queue is O(1)
     * Adding to pqueue is O(logN) (where N is MAX_RECORDS)
     */
    public void addRecord(final long timeStamp, final int rpd) {
        final StreamData record = new StreamData(timeStamp, rpd);

        if (queueSize < MAX_RECORDS) {
            queue.add(record);
            queueSize++;
        } else {
            final StreamData removedRecord = queue.poll();
            queue.add(record);
            pqueue.remove(removedRecord);
            System.out.println("bumped out record: " + removedRecord.rpd);
        }
        pqueue.add(record);
    }


    /**
     * Get StreamData with max requests-per-duration
     * @return
     * O(1)
     */
    public StreamData getStreamDataWithMaxRPD() {
        return pqueue.peek();
    }

    public static void main(final String[] args) throws InterruptedException {
        final MyPriorityQueue myq = new MyPriorityQueue();

        myq.addRecord(System.currentTimeMillis(),  9);
        Thread.sleep(50);
        myq.addRecord(System.currentTimeMillis(),  7);
        Thread.sleep(50);
        myq.addRecord(System.currentTimeMillis(),  17);
        Thread.sleep(50);

        final StreamData sd0 = myq.getStreamDataWithMaxRPD();
        System.out.println("Retrieved max: " + sd0.rpd);

        myq.addRecord(System.currentTimeMillis(),  8);
        Thread.sleep(50);
        myq.addRecord(System.currentTimeMillis(),  5);
        Thread.sleep(50);
        myq.addRecord(System.currentTimeMillis(),  4);
        Thread.sleep(50);

        final StreamData sd = myq.getStreamDataWithMaxRPD();
        System.out.println("Retrieved max: " + sd.rpd);

        myq.addRecord(System.currentTimeMillis(),  10);
        Thread.sleep(50);

        myq.addRecord(System.currentTimeMillis(),  8);
        Thread.sleep(50);

        final StreamData sd2 = myq.getStreamDataWithMaxRPD();
        System.out.println("Retrieved max: " + sd2.rpd);
    }
}
