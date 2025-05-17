package Advanced;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

class BoundedQueue<T>
{
    Queue<T> q;
    int capacity;
    private Semaphore enq ;
    private Semaphore deq ;
    public BoundedQueue(int capacity)
    {
        q = new ArrayBlockingQueue<>(capacity);
        this.capacity=capacity;
        enq = new Semaphore(capacity);
        deq = new Semaphore(0);
    }
    public void enqueue(T data) throws InterruptedException {
        enq.acquire();
        System.out.println("added eleme t in queue " +data+"  "+Thread.currentThread().getName());
        q.add(data);
        Thread.sleep(2000);
        deq.release();

    }
    public T deque() throws InterruptedException {
        deq.acquire();
        T x = q.remove();
        System.out.println("x ="+x+" "+Thread.currentThread().getName());
        Thread.sleep(2000);
        enq.release();
        return x;
    }
    public int size()
    {
        return q.size();
    }
}
public class BoundeBlaockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BoundedQueue<Integer> bq = new BoundedQueue<>(3);
        for (int i = 1; i <= 5; i++) {
            final int val = i;
            Thread producer = new Thread(() -> {
                try {
                    bq.enqueue(val);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Producer-" + i);
            producer.start();
        }

        Thread.sleep(3000); // Let producers fill up

        // Consumer threads
        for (int i = 1; i <= 2; i++) {
            Thread consumer = new Thread(() -> {
                try {
                    bq.deque();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Consumer-" + i);
            consumer.start();
        }
    }
}
