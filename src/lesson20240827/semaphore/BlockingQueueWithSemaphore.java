package lesson20240827.semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class BlockingQueueWithSemaphore<T> {

	private final List<T> items = new ArrayList<>();
	private final Semaphore itemsSemaphore = new Semaphore(0);
	private final Semaphore mutexSemaphore = new Semaphore(1);


	public void put(T item) throws InterruptedException {
		mutexSemaphore.acquire();

		items.add(item);

		mutexSemaphore.release();

		itemsSemaphore.release();
	}

	public T get() throws InterruptedException {
		itemsSemaphore.acquire();

		mutexSemaphore.acquire();

		T item = items.remove(0);

		mutexSemaphore.release();

		return item;
	}
}
