package org.learning.concurrency.synchronization;

import java.util.concurrent.TimeUnit;

public class ThreadSyncFailure {
    private static boolean stopRequested;

    // The program never terminates as the background thread never gets the update value of the variable
    // stopRequested.
    // Synchronization is needed
    public static void main(String[] args) throws InterruptedException {
        Thread bgThread = new Thread(() -> {
            int i = 0;
            while (!stopRequested) {
                i++;
            }
            System.out.println(i);
        });

        bgThread.start();

        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
    }
}
