package org.learning.concurrency.synchronization;

import org.learning.concurrency.utils.ObservableSet;
import org.learning.concurrency.utils.ObservableSetBad;
import org.learning.concurrency.utils.SetObserver;

import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallingClientCodeInSynchronizedMethodWithBackgroundThreadDeadlock {
    // In this example, the Observable Set invokes the methods passed to addObserver for each element added to the set.
    // The observing method has a side effect that it removes itself from the ObservableSet if the condition is met.
    // The method uses another thread to remove the element.
    // The notifications to the observers are done by iteration through the list of observers. The iteration of the
    // list as well as addObserver and removeObserver methods are synchronized. So when the new thread tries to
    // remove the observer, it gets blocked as iteration is in progress. The iteration can not proceed as the observer
    // must complete its execution. Hence, we get a deadlock
    public static void main(String[] args) {
        ObservableSetBad<Integer> set = new ObservableSetBad<>(new HashSet<>());
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
