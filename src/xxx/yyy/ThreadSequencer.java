package xxx.yyy;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class ThreadSequencer {
    private static Semaphore semC = new Semaphore(0);
    private static Semaphore semB = new Semaphore(0);
    private static Semaphore semA = new Semaphore(1);

    private static CountDownLatch startSignal = new CountDownLatch(1);
    private static CountDownLatch doneSignal = new CountDownLatch(3);
    private static class A implements Runnable {
        @Override
        public void run() {
            try {
                startSignal.await();
                for (int i = 0; i < 5; i++) {
                    semA.acquire();
                    System.out.println("Hi from A");
                    Thread.sleep(1000);
                    semB.release();
                }
                doneSignal.countDown();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private static class B implements Runnable {
        @Override
        public void run() {
            try {
                startSignal.await();
                for (int i = 0; i < 5; i++) {
                    semB.acquire();
                    System.out.println("Hi from B");
                    Thread.sleep(1000);
                    semC.release();
                }
                doneSignal.countDown();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private static class C implements Runnable {
        @Override
        public void run() {
            try {
                startSignal.await();
                for (int i = 0; i < 5; i++) {
                    semC.acquire();
                    System.out.println("Hi from C");
                    Thread.sleep(1000);
                    semA.release();
                }
                doneSignal.countDown();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new Thread(new A()).start();
        new Thread(new B()).start();
        new Thread(new C()).start();
        startSignal.countDown();
        doneSignal.await();

        Thread.sleep(2000);
    }

}
