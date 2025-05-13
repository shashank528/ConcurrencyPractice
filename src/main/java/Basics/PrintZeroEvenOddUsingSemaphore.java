package Basics;

import java.util.concurrent.Semaphore;

public class PrintZeroEvenOddUsingSemaphore {
    private static Semaphore zero = new Semaphore(1);
    private static Semaphore odd = new Semaphore(0);
    private static Semaphore even = new Semaphore(0);
    static int state=1;
    static int val=1;

    public static void printZero() throws InterruptedException {
        while(true)
        {
            zero.acquire();
            System.out.print(0);
            Thread.sleep(1000);
            if(state==1)
            {
                odd.release();
            }
            else
            {
                even.release();
            }
        }

    }
    public static void printEven() throws InterruptedException {
       while(true)
       {
           even.acquire();
           System.out.print(val);
           val=val+1;
           state=1;
           Thread.sleep(1000);
           zero.release();
       }
    }
    public static void printOdd() throws InterruptedException {
       while(true)
       {
           odd.acquire();
           System.out.print(val);
           val=val+1;
           state=2;
           Thread.sleep(1000);
           zero.release();
       }
    }
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            try {
                printZero();
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
        Thread t3 = new Thread(()->{
            try {
                printOdd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }
}
