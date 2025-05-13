package Intermediate;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

class CustomThreadPool
{
    Queue<Runnable> q;
    Worker[]thread ;
    private volatile boolean isShutDown;
    public CustomThreadPool(int size)
    {
        q = new LinkedBlockingQueue<>();
        thread = new Worker[size];
        for(int i=0;i<size;i++)
        {
            thread[i] = new Worker("worker"+"_"+i);
            thread[i].start();
        }
    }
    public void submit(Runnable runnable)
    {
        if(isShutDown)
        {
            throw new IllegalStateException("thread pool is shutdown ");
        }
        else
        q.add(runnable);
    }
    public void shutDown()
    {
       isShutDown=true;
       for(Worker worker:thread)
       {
           worker.interrupt();
       }
    }
    private class Worker extends Thread
    {
        public Worker(String name)
        {
            super(name);
            System.out.println("worker thread with name "+name+" started");

        }
        public void run()
        {
            while(!isShutDown || q.size()>0)
            {
               try
               {
                   Runnable task = q.poll();
                   task.run();
               }
               catch(Exception ex)
               {
                    if(isShutDown)
                        break;
               }
            }
        }
    }
}

public class CustomThredPoolDemo {
    public static void main(String[] args) {
        CustomThreadPool customThreadPool = new CustomThreadPool(3);
        for (int i = 1; i <= 10; i++) {
            int taskId = i;
            customThreadPool.submit(() -> {
                System.out.println("Task " + taskId + " is running using "+Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        customThreadPool.shutDown();
        System.out.println("ThreadPool shutdown initiated ");
    }
}
