package org.learning.concurrency.synchronization;

import org.learning.concurrency.utils.ObservableSetBest;

import java.util.HashSet;

public class CallingClientCodeInSynchronizedMethodWorking {
    // In this example, the Observable Set invokes the method passed to addObserver for each element added to the set.
    // The lambda method does not have any side effects that modify something in the ObservableSet.
    // Hence, we see the numbers 0-99 being printed
    public static void main(String[] args) {
        ObservableSetBest<Integer> set = new ObservableSetBest<>(new HashSet<>());
        set.addObserver((s, e) -> {
            System.out.println(e);
        });

        for (int i=0; i<100; i++) {
            set.add(i);
        }
    }
}
