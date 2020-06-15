package org.learning.concurrency.synchronization;

public class UnsynchronizedUpdate {

    public static void main(String[] args) throws InterruptedException {
        Counter c = new Counter();
        int REPEAT = 10_000_000;
        Runnable r = () -> {
            for (int i=0; i<REPEAT; i++) {
                c.increment();
            }
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(c.i);
    }

    public static class Counter {
        private int i = 0;
        // The i++ operation is not atomic. First the value of i is read, then incremented.
        // Being not synchronized, multiple threads calling will not work properly
        public int increment() {
            return i++;
        }
    }
}
