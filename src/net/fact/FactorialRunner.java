package net.fact;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class FactorialRunner {

    static class FactorialWorker implements Runnable {

        private final String name;
        private final AtomicBoolean stop = new AtomicBoolean(false);
        private final BlockingQueue<Long> queue;
        private final FactorialStore factorialStore;

        public FactorialWorker(String name, BlockingQueue<Long> queue, FactorialStore factorialStore) {
            this.name = name;
            this.queue = queue;
            this.factorialStore = factorialStore;
        }

        public void doStop() {
            stop.set(true);
        }

        @Override
        public void run() {
            System.out.println("Factorial worker name: " + name+ " is starting");
            while (!stop.get()) {
                try {
                    Long val = queue.poll(1, TimeUnit.SECONDS);
                    if (val != null) {
                        factorial(val.longValue());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;

            for (long i = n; i > 0; i--) {
                tempResult = tempResult.multiply(new BigInteger((Long.toString(i))));
            }
            factorialStore.storeValue(n, tempResult);
        }
    }

    static class FactorialDriver implements Runnable {

        private final AtomicBoolean stop = new AtomicBoolean(false);
        private final BlockingQueue<Long> queue;
        private final int count;
        private final Random r = new Random();

        public FactorialDriver(BlockingQueue<Long> queue, int count) {
            this.queue = queue;
            this.count = count;
        }

        public void doStop() {
            stop.set(true);
        }

        @Override
        public void run() {
            for (int i = 0; i < count; i++) {
                long factorialInput = r.nextInt(6000) + 1000;
                queue.add(factorialInput);

                if (stop.get()) break;
            }

            System.out.println("Factorial driver ID: " + Thread.currentThread().getId() + " is done with count: " + count);
        }
    }

    private final FactorialStore store = new FactorialStore();
    private final BlockingQueue<Long> queue = new LinkedBlockingQueue<>();
    private final List<FactorialWorker> workers = new ArrayList<>();
    private final int workerCount = 5;

    private void startWorkers() {
        for (int i = 0; i < workerCount; i++) {
            FactorialWorker worker = new FactorialWorker("worker"+i, queue, store);
            workers.add(worker);
            new Thread(worker).start();
        }
    }

    private void stopWorkers() {
        for (FactorialWorker worker : workers) {
            worker.doStop();
        }
    }

    public static void main(String[] args) {

        FactorialDriver driver = null;
        FactorialRunner runner = new FactorialRunner();
        runner.startWorkers();

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Enter choice");
            String input = in.nextLine();
            if (input.equalsIgnoreCase("quit")) {
                runner.stopWorkers();

                if (driver != null) {
                    driver.doStop();
                }
                break;
            }

            if (input.startsWith("iter")) {
                String[] array = input.split(" ");
                int iterCount = Integer.parseInt(array[1]);

                driver = new FactorialDriver(runner.queue, iterCount);
                new Thread(driver).start();

            } else if (input.startsWith("count")) {
                String[] array = input.split(" ");
                int factorialQuery = Integer.parseInt(array[1]);
                int frequency = runner.store.getExecutionCount(factorialQuery);
                System.out.println("Factorial for: " + factorialQuery + " has been calculated: " + frequency + " times");
            }
            else if (input.equalsIgnoreCase("inventory")) {
                runner.store.printInventory();
            }
        }
        in.close();
    }
}
