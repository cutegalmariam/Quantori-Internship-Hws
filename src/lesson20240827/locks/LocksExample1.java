package lesson20240827.locks;

public class LocksExample1 {
    public static void main(String[] args) throws InterruptedException {

        BlockingQueueWithLocks<String> queue = new BlockingQueueWithLocks<>(5);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    String message = "Message " + i;
                    System.out.println("Producing: " + message);
                    queue.put(message);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    String message = queue.get();
                    System.out.println("Consumed: " + message);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}
