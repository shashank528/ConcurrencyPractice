package Basics;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class PrintEvenOddUsingSemaphore {
    private static Semaphore odd = new Semaphore(1);
    private static Semaphore even = new Semaphore(0);
    private static AtomicInteger x = new AtomicInteger(1);
    public static void printEven() throws InterruptedException {
        while(true)
        {
            if(x.get()%2==0)
            {
                even.acquire();
                System.out.println(Thread.currentThread().getName()+" "+x.get());
                x.getAndIncrement();
                odd.release();
                Thread.sleep(1000);
            }
        }

    }
    public static void printOdd() throws InterruptedException {
        while(true)
        {
            if(x.get()%2!=0) {
                odd.acquire();
                System.out.println(Thread.currentThread().getName()+" "+x.get());
                x.getAndIncrement();
                even.release();
                Thread.sleep(1000);

            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            try {
                printOdd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(()->{
            try {
                printEven();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
    }
}
