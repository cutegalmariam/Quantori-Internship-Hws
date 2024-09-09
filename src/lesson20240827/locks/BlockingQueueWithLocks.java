package lesson20240827.locks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueWithLocks<T> {

    private final List<T> items = new ArrayList<>();
    private final int capacity;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();

    public BlockingQueueWithLocks(int capacity) {
        this.capacity = capacity;
    }

    public void put(T item) throws InterruptedException {
        lock.lock();

        try {
            while (items.size() == capacity) {
                notFull.await();
            }

            items.add(item);

            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T get() throws InterruptedException {
        lock.lock();

        try {
            while (items.isEmpty()) {
                notEmpty.await();
            }

            T item = items.remove(0);

            notFull.signal();

            return item;
        } finally {
            lock.unlock();
        }
    }
}
