
import java.util.LinkedList;

class PC {
    LinkedList<Integer> buffer = new LinkedList<>();
    int capacity = 2;
    int value = 0;

    public void produce() throws InterruptedException {
        while (true) {
            synchronized (this) {
                while (buffer.size() == capacity) {
                    wait();
                }

                buffer.add(value);
                System.out.println("Producer produced-" + value);
                value++;

                notify();
            }

            Thread.sleep(1000);
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            synchronized (this) {
                while (buffer.isEmpty()) {
                    wait();
                }

                int val = buffer.removeFirst();
                System.out.println("Consumer consumed-" + val);

                notify();
            }

            Thread.sleep(1000);
        }
    }
}

 class PC1 {
    public static void main(String[] args) {
        PC pc = new PC();

        Thread producer = new Thread(() -> {
            try {
                pc.produce();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}
