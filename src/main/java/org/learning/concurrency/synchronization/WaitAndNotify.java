package org.learning.concurrency.synchronization;

import java.util.LinkedList;

public class WaitAndNotify {

    // This examples uses the Object.wait() and Object.notifyAll() methods to implement a thread-safe waiting queue
    //
    public static void main(String[] args) throws InterruptedException {
        WaitingQueue<String> queue = new WaitingQueue<>();
        Thread t1 = new Thread(() -> queue.push("1"), "Push 1");
        Thread t2 = new Thread(() -> queue.push("2"), "Push 2");
        Thread t3 = new Thread(() -> queue.pop(), "Pop");

        t3.start();
        t1.start();
        t2.start();

        t3.join();
        t1.join();
        t2.join();
    }

    /**
     * A thread-safe Waiting Queue class. A Waiting Queue works like a normal queue except that when pop() is called
     * on the queue when it is empty, the caller thread goes in the wait state until another thread calls push()
     * on the queue to push an object.
     * @param <E> The type of object stored in the queue
     */
    public static class WaitingQueue<E> {

        LinkedList<E> q = new LinkedList<>();

        public synchronized void push(E o) {
            // synchronized is necessary for the thread to obtain the lock on the queue's monitor and to prevent
            // concurrent modification of the linked list
            // Both wait() and notify()/notifyAll() must always be used inside synchronized blocks
            System.out.println(String.format("Pushed %s", o));
            q.add(o);
            this.notifyAll();
        }

        public synchronized E pop() {
            while (q.isEmpty()) {
                try {
                    // wait() temporarily releases the lock on the queue and the thread waits for being notified by the
                    // queue (instance of this class). Since the lock is released, another thread can obtain the lock
                    // and call the push() method that will notify the waiting thread
                    this.wait();
                } catch (InterruptedException ignore) { }
            }
            E o = q.remove();
            System.out.println(String.format("Popped %s", o));
            return o;
        }
    }
}
