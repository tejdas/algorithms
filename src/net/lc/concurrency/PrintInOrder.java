package net.lc.concurrency;

import java.util.concurrent.Semaphore;

/**
 * 1114
 * MT
 * Semaphore
 */
public class PrintInOrder {
    private final Semaphore semC = new Semaphore(0);
    private final Semaphore semB = new Semaphore(0);
    private final Semaphore semA = new Semaphore(1);

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        semB.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {

        // printSecond.run() outputs "second". Do not change or remove this line.
        semB.acquire();
        printSecond.run();
        semC.release();
    }

    public void third(Runnable printThird) throws InterruptedException {

        // printThird.run() outputs "third". Do not change or remove this line.
        semC.acquire();
        printThird.run();
    }
}
