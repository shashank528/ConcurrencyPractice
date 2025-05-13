package Basics;

import java.util.concurrent.atomic.AtomicInteger;

class Sharedresources
{
    AtomicInteger x= new AtomicInteger(1);
    int state=0;
    public synchronized void printZero() throws InterruptedException {
        while(true)
        {
            while(state!=0) {
                wait();
            }
            System.out.print(0);
            if(x.get()%2==0)
            {
                state=2;
            }
            else
            {
                state=1;
            }
            notifyAll();
            Thread.sleep(1000);
        }

    }
    public synchronized void printEven() throws InterruptedException {
       while(true)
       {
           while(state!=2)
           {
              wait();
           }
           if(x.get()%2==0)
           {
               System.out.print(x.getAndIncrement());
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               state=0;
               notifyAll();
           }


       }
    }
    public synchronized void printOdd() throws InterruptedException {
        while(true)
        {
            while(state!=1)
            {
                wait();
            }
            if(x.get()%2!=0)
            {
                System.out.print(x.getAndIncrement());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                state=0;
                notifyAll();
            }
        }
    }
}
public class PrintZeroEvenOddDemo {

    public static void main(String[] args) {
        Sharedresources sharedresource = new Sharedresources();
        Thread t1 = new Thread(()->{
            try {
                sharedresource.printZero();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(()->{
            try {
                sharedresource.printEven();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(()->{
            try {
                sharedresource.printOdd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }
}
