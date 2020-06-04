package org.learning.concurrency.synchronization;

import org.learning.concurrency.utils.ObservableSet;
import org.learning.concurrency.utils.ObservableSetBest;
import org.learning.concurrency.utils.SetObserver;

import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallingClientCodeInSynchronizedMethodWithBackgroundThreadBest {
    // The code is the same as CallingClientCodeInSynchronizedMethodWithBackgroundThreadBetter
    // Now we use ObservableSetBest, which uses a CopyOnWriteArrayList that makes a new copy of the list whenever
    // it is updated. This removes the need for explicit synchronization
    public static void main(String[] args) {
        ObservableSetBest<Integer> set = new ObservableSetBest<>(new HashSet<>());
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

        for (int i = 0; i < 100; i++) {
            set.add(i);
        }
    }
}
