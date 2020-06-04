package org.learning.concurrency.utils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class ObservableSet<E> extends ForwardingSet<E> {
    protected final List<SetObserver<E>> observers;

    public ObservableSet(Set<E> s, List<SetObserver<E>> observers) {
        super(s);
        this.observers = observers;
    }

    public abstract void addObserver(SetObserver<E> observer);

    public abstract boolean removeObserver(SetObserver<E> observer);

    protected abstract void notifyElementAdded(E element);

    @Override
    public boolean add(E e) {
        boolean added = super.add(e);
        if (added) {
            notifyElementAdded(e);
        }
        return added;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        for (var element : c) {
            result |= add(element);
        }
        return result;
    }
}
