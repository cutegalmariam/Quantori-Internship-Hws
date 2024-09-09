package lesson20240820;

import java.util.LinkedList;
import java.util.List;

public class BlockingQueue<T> {

	private final List<T> items = new LinkedList<>();
	private final Object mutex = new Object();

	public void put(T item) {
		synchronized (mutex) {
			items.add(item);
			mutex.notify();
		}
	}

	public T get() {
		synchronized (mutex) {
			while (items.isEmpty()) {
				try {
					mutex.wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					throw new RuntimeException("Thread interrupted", e);
				}
			}
			return items.remove(0);
		}
	}
}
