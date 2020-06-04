package org.learning.concurrency.synchronization;

import java.util.concurrent.TimeUnit;

public class ThreadSyncWorkingWithVolatile {
    private static volatile boolean stopRequested;

    // Making the stopRequested variable volatile makes the background thread always read the most recent value
    // We can use volatile here since the synchronization required is only for communicating the value of the variable
    // and not for providing mutually exclusive access to the variable (which would be the case if multiple threads
    // were trying to modify the value of the variable).
    // Volatile doesn't have the overhead of using synchronized access
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
