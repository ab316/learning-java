package org.learning.concurrency.utils;

import java.util.ArrayList;
import java.util.Set;

public class ObservableSetBad<E> extends ObservableSet<E> {

    public ObservableSetBad(Set<E> s) {
        super(s, new ArrayList<>());
    }

    @Override
    public void addObserver(SetObserver<E> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    @Override
    public boolean removeObserver(SetObserver<E> observer) {
        synchronized (observers) {
            return observers.remove(observer);
        }
    }

    @Override
    protected void notifyElementAdded(E element) {
        synchronized (observers) {
            for (var observer : observers) {
                observer.added(this, element);
            }
        }
    }
}
