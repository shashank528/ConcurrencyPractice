package Basics;
class PingPong
{
    boolean flag=false;
    public synchronized  void printPing() throws InterruptedException {
       while(true)
       {
           if(flag)
           {
               wait();
           }
           System.out.println(Thread.currentThread().getName()+"ping");
           Thread.sleep(1000);
           flag=!flag;
           notify();
       }
    }
    public synchronized  void printPong() throws InterruptedException {
        while(true)
        {
            if(!flag)
            {
                wait();
            }
            System.out.println(Thread.currentThread().getName()+"pong");
            Thread.sleep(1000);
            flag=!flag;
            notify();
        }
    }
}
public class PingPongDemo {
    public static void main(String[] args) {
        PingPong p = new PingPong();
        Thread t1 = new Thread(()->{
            try {
                p.printPing();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(()->{
            try {
                p.printPong();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
    }
}
