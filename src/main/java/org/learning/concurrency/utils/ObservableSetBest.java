package org.learning.concurrency.utils;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class ObservableSetBest<E> extends ObservableSet<E> {

    public ObservableSetBest(Set<E> s) {
        super(s, new CopyOnWriteArrayList<>());
    }

    public void addObserver(SetObserver<E> observer) {
        observers.add(observer);
    }

    public boolean removeObserver(SetObserver<E> observer) {
        return observers.remove(observer);
    }

    @Override
    protected void notifyElementAdded(E element) {
        for (var observer : observers) {
            observer.added(this, element);
        }
    }
}
