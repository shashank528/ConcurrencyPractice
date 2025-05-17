    package Advanced;

    import java.util.concurrent.*;

    public class BarrierSynchronizationDemo {

        static class Barier
        {
             Semaphore mutex = new Semaphore(1);
             Semaphore lock = new Semaphore(0);
            int count;
            final int limit;

            public Barier(int limit,int count) {
                this.limit = limit;
                this.count=count;
            }
            public void await() throws InterruptedException {
                mutex.acquire();
                count--;
                if(count==0)
                {
                    System.out.println("release all thread "+Thread.currentThread().getName());
                    lock.release(limit-1);
                    count=limit;
                    System.out.println("count ="+count);
                    mutex.release();
                }
                else
                {
                    mutex.release();
                    lock.acquire();
                    //order is very important here in case we want all thread to wait at lock.aquire() this is a classic case of deadlock

                }
            }
        }

        public static void main(String[] args) {
            Barier barier = new Barier(5,5);
            ExecutorService executor = Executors.newFixedThreadPool(5, new ThreadFactory() {
                int counter=1;
                @Override
                public Thread newThread(Runnable r) {
                    Thread t= new Thread(r,"Shashank_"+counter);
                    counter++;
                     return t;
                }
            });
           for(int i=1;i<=5;i++)
           {
               executor.submit(()->{
                   System.out.println("starting first execution "+Thread.currentThread().getName());
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   System.out.println("finished first execution "+Thread.currentThread().getName());
                   try {
                       barier.await();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   System.out.println("starting second execution "+Thread.currentThread().getName());
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   System.out.println("finished second execution "+Thread.currentThread().getName());
                   try {
                       barier.await();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               });
           }
           executor.shutdown();
        }
    }
