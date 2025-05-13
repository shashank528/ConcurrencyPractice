package Basics;

import org.w3c.dom.UserDataHandler;

import java.util.LinkedList;
import java.util.Queue;

class SharedBuffer
{
    Queue<Integer> q= new LinkedList<>();
    public synchronized void add(int x) throws InterruptedException {
        while (q.size() > 0) {
            //System.out.println(Thread.currentThread().getName() + " goes to waiting state");
            wait();
        }
          System.out.println(Thread.currentThread().getName()+" adding "+x+" to queue");
            q.add(x);
            Thread.sleep(1000);
            notify();

    }
    public synchronized void  get() throws InterruptedException {
        while (q.size() == 0) {
          //  System.out.println(Thread.currentThread().getName() + " goes to waiting state");
            wait();
        }
        System.out.println(Thread.currentThread().getName()+" "+q.remove());
        Thread.sleep(1000);
        notify();
    }
    public int size()
    {
        return q.size();
    }
}
class Producer
{
    SharedBuffer sharedBuffer;
    public Producer(SharedBuffer sharedBuffer)
    {
        this.sharedBuffer=sharedBuffer;
    }
    public  void produce() throws InterruptedException {
       for(int i=1;i<=15;i++)
       {
               sharedBuffer.add(i);
       }

    }
}
class Consumer
{
    SharedBuffer sharedBuffer;
    public Consumer(SharedBuffer sharedBuffer)
    {
        this.sharedBuffer=sharedBuffer;
    }
    public   void consume() throws InterruptedException {
        for(int i=1;i<=15;i++)
        {
            sharedBuffer.get();
        }

    }
}
public class ProducerCoonsumerDemo {
    public static void main(String[] args) {
        SharedBuffer sharedBuffer = new SharedBuffer();
        Producer producer = new Producer(sharedBuffer);
        Consumer consumer = new Consumer(sharedBuffer);
        Thread t1 = new Thread(()->{
            try {
                producer.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(()->{
            try {
                consumer.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
    }

}
