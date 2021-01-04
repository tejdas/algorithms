package aaa.bbb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackgroundThreadNotifier {
    /**
     * Your application logic must be performed
     * in this worker thread.
     */
    private static final class Worker implements Runnable {
        CountDownLatch shutdownComplete = new CountDownLatch(1);
        private volatile boolean shutdown = false;

        /**
         * Called by main thread.
         * Sets shutdown to true, therefore asking for Worker
         * to shut itself down. Then, wait for the shutdownComplete.
         * @throws InterruptedException
         */
        public void doShutdown() throws InterruptedException {
            shutdown = true;
            shutdownComplete.await();
        }

        @Override
        public void run() {
            while (!shutdown) {
                System.out.println("WorkerA doing some work:" + new Date().toString());
                /*
                 * Put your application logic here.
                 * I have just put a sleep of 5 secs
                 */
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            /*
             * Perform cleanup logic, and signal
             * shutdown complete.
             */
            System.out.println("Performing SHUTDOWN");
            shutdownComplete.countDown();
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        /*
         * Create a thread pool and execute the Worker in the thread-pool
         */
        int numWorkers = 1;
        final ExecutorService threadPool = Executors.newFixedThreadPool(numWorkers);
        Worker worker = new Worker();
        threadPool.submit(worker);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Waiting for console output. Enter SHUTDOWN to be done");
            String command = in.readLine();
            if (command.equalsIgnoreCase("SHUTDOWN")) {
                /*
                 * Notify Worker to shut itself down.
                 */
                worker.doShutdown();
                break;
            } else {
                System.out.println("Cannot process command: " + command);
            }
        }

        /*
         * Cleanup thread-pool.
         */
        threadPool.shutdown();
    }
}
