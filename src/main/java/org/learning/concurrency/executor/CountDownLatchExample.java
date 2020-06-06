package org.learning.concurrency.executor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CountDownLatchExample {

    // This example times concurrent execution of a single task
    // First it waits for all the threads to get ready for execution
    // Once ready, a time starts and all the threads start execution
    // Once the last thread finishes execution, the time stops
    public static void main(String[] args) throws InterruptedException {
        var exec = Executors.newCachedThreadPool();
        long duration = time(exec, 10, () -> {
            long test = 0;
            for (long i = 0; i < 10000000; i++) {
                test += Math.sin((double)i);
            }
        });

        exec.shutdown();
        System.out.println(String.format("Time taken: %.2f seconds", (double)duration / 1e9));
    }

    /**
     * Times concurrent execution of a single task
     * @param executor The executor to use
     * @param concurrency The number of concurrent tasks to run
     * @param action The task
     * @return Nanoseconds taken for all threads to finish execution
     * @throws InterruptedException
     */
    public static long time(Executor executor, int concurrency, Runnable action) throws InterruptedException {
        CountDownLatch ready = new CountDownLatch(concurrency);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                ready.countDown();
                try {
                    start.await();
                    action.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();
                }
            });
        }

        System.out.println("Waiting");

        ready.await();
        System.out.println("Ready");

        long startNanos = System.nanoTime();
        start.countDown();
        System.out.println("Go");

        done.await();
        System.out.println("Done");

        return System.nanoTime() - startNanos;
    }
}
