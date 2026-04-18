package ThreadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: Main
 * Package: test
 * Description:
 *
 * @Author 龙培
 * @Create 2025/5/6 17:25
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args){
        MyThreadPool myThreadPool = new MyThreadPool(2,4,1, TimeUnit.SECONDS,new ArrayBlockingQueue<>(2),new DiscardRejectHandle());
        for (int i = 0; i <8 ; i++) {
            final int fi = i;


            Runnable commend= ()->{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName()+"执行了任务"+fi);
            }; //这是创建的是一个runnable的匿名实现类
            myThreadPool.execute(commend);
        }
        System.out.println("主线程结束了！");
    }
}

