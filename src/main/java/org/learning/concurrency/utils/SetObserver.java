package org.learning.concurrency.utils;

@FunctionalInterface public interface SetObserver<E> {
    void added(ObservableSet<E> set, E element);
}
