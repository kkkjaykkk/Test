package threadPoolTest;
import com.sun.source.tree.NewArrayTree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

class MyThreadPool {
    public BlockingQueue<Runnable> blockingQueue;
    private List<Thread> coreThreadList = new ArrayList<>();
    private List<Thread> supportThreadList = new ArrayList<>();
    private int coreThreadSize;
    private int maxThreadSize;
    private int keepAliveTime;
    private TimeUnit timeUnit;
    private RejectHandle rejectHandle;
    public MyThreadPool(int coreThreadSize, int maxThreadSize, int keepAliveTime, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue, RejectHandle rejectHandle) {
        this.coreThreadSize = coreThreadSize;
        this.maxThreadSize = maxThreadSize;
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        this.blockingQueue = blockingQueue;
        this.rejectHandle = rejectHandle;
    }
    class CoreThread extends Thread {
        Runnable firstTask;
        public CoreThread(Runnable firstTask){
            this.firstTask = firstTask;
        }
        public void run(){
            firstTask.run();
            while(true){
                try {
                    blockingQueue.take().run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    class SupportThread extends Thread {
        Runnable firstTask;
        public SupportThread(Runnable firstTask){
            this.firstTask = firstTask;
        }
        public void run(){
            firstTask.run();
            while(true){
                try {
                    Runnable poll = blockingQueue.poll(keepAliveTime, timeUnit);
                    if(poll == null){
                        break;
                    }
                    poll.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(Thread.currentThread().getName() + " 线程被终止");
        }
    }
    public void execute(Runnable command){
        if(coreThreadSize > coreThreadList.size()){
            CoreThread coreThread = new CoreThread(command);
            coreThreadList.add(coreThread);
            coreThread.start();
        }
        if(blockingQueue.offer(command)){
            return;
        }
        if(coreThreadSize+supportThreadList.size()<maxThreadSize){
            SupportThread supportThread = new SupportThread(command);
            supportThreadList.add(supportThread);
            supportThread.start();
        }
        if(!blockingQueue.offer(command)){
            rejectHandle.reject(command,this);
        }
    }

}