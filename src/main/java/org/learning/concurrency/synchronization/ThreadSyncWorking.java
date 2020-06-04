package org.learning.concurrency.synchronization;

import java.util.concurrent.TimeUnit;

public class ThreadSyncWorking {
    private static boolean stopRequested;

    // Synchronization is need when multiple threads need to access shared data. Synchronization is not only needed
    // for mutually exclusive access but also to make sure that each thread sees the value of the data after the most
    // recent write
    public static void main(String[] args) throws InterruptedException {
        Thread bgThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested()) {
                i++;
            }
            System.out.println(i);
        });

        bgThread.start();

        TimeUnit.SECONDS.sleep(1);
        requestStop();
    }

    private static synchronized void requestStop() {
        stopRequested = true;
    }

    private static synchronized boolean stopRequested() {
        return stopRequested;
    }
}
