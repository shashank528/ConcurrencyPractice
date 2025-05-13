package Basics;
class SharedResource
{
    private volatile int  val;
    public SharedResource(int val)
    {
        this.val=val;
    }
    public synchronized void printOdd()
    {
        while(true)
        {
            if(val%2==0)
            {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+" "+val);
            val=val+1;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            notify();
        }
    }
    public synchronized void printEven()
    {
        while(true)
        {
            if(val%2!=0)
            {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+" "+val);
            val=val+1;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            notify();
        }

    }
}
public class PrintOddEvenDemo {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource(0);
        Thread t1 = new Thread(()->{
            sharedResource.printOdd();
        });
        Thread t2 = new Thread(()->{
            sharedResource.printEven();
        });
        t1.start();
        t2.start();

    }
}
