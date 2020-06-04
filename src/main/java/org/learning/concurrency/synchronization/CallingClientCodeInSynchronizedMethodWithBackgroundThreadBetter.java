package org.learning.concurrency.synchronization;

import org.learning.concurrency.utils.ObservableSet;
import org.learning.concurrency.utils.ObservableSetBetter;
import org.learning.concurrency.utils.SetObserver;

import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallingClientCodeInSynchronizedMethodWithBackgroundThreadBetter {
    // The code is the same as CallingClientCodeInSynchronizedMethodWithBackgroundThreadDeadlock
    // except that we now use the ObservableSetBetter class.
    // This classes makes a snapshots of the observers list with synchronization and iterates over the snapshot
    // When the observer attempts to remove itself during the iteration, the observer is removed from the original list
    // Thus, there is no deadlock
    // This is a better use of synchronization. We use synchronize the minimum required code
    public static void main(String[] args) {
        ObservableSetBetter<Integer> set = new ObservableSetBetter<>(new HashSet<>());
        set.addObserver(new SetObserver<>() {
            @Override
            public void added(ObservableSet<Integer> set, Integer element) {
                System.out.println(element);
                if (element == 23) {
                    ExecutorService exec = Executors.newSingleThreadExecutor();
                    try {
                        exec.submit(() -> set.removeObserver(this)).get();
                    } catch (ExecutionException | InterruptedException ex) {
                        throw new AssertionError(ex);
                    } finally {
                        exec.shutdown();
                    }
                }
            }
        });

        for (int i=0; i<100; i++) {
            set.add(i);
        }
    }
}
