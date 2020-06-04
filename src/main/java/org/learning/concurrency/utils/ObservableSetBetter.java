package org.learning.concurrency.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class ObservableSetBetter<E> extends ObservableSet<E> {

    public ObservableSetBetter(Set<E> s) {
        super(s, new CopyOnWriteArrayList<>());
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
        List<SetObserver<E>> snapshot = null;
        synchronized (observers) {
            snapshot = new ArrayList<>(observers);
        }

        for (var observer : snapshot) {
            observer.added(this, element);
        }
    }
}
