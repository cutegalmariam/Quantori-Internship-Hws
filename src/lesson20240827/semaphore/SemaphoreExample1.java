package lesson20240827.semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreExample1 {


	public static void main(String[] args) throws InterruptedException {

		BlockingQueueWithSemaphore<String> queue = new BlockingQueueWithSemaphore<>();

		new Thread(() -> {
			System.out.println("Waiting for a message in the queue...");
			try {
				String message = queue.get();
				System.out.println("Received: " + message);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();


		Thread.sleep(5000);
		System.out.println("Ready...");
		queue.put("Message 1");
		Thread.sleep(1000);
		System.out.println("Steady...");
		queue.put("Message 2");
		Thread.sleep(1000);
		System.out.println("Go!");
		queue.put("Message 3");
	}


}
