package org.learning.concurrency.synchronization;

import org.learning.concurrency.utils.ObservableSet;
import org.learning.concurrency.utils.ObservableSetBad;
import org.learning.concurrency.utils.SetObserver;

import java.util.HashSet;

public class CallingClientCodeInSynchronizedMethodFailing {
    // In this example, the Observable Set invokes the method passed to addObserver for each element added to the set.
    // The observing method has a side effect that it removes itself from the ObservableSet if the condition is met.
    // The problem is that ObservableSet is invoking the method while iterating the array of observers, and the invoked
    // method attempts to update the array. The iteration itself is synchronized and, since the observable method is
    // invoked inside the iteration, it is not blocked. Hence we get a ConcurrentModification exception
    public static void main(String[] args) {
        ObservableSetBad<Integer> set = new ObservableSetBad<>(new HashSet<>());
        set.addObserver(new SetObserver<>() {
            @Override
            public void added(ObservableSet<Integer> set, Integer element) {
                System.out.println(element);
                if (element == 23) {
                    set.removeObserver(this);
                }
            }
        });

        for (int i = 0; i < 100; i++) {
            set.add(i);
        }
    }
}
