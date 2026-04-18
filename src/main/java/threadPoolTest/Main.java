package threadPoolTest;

import threadPoolTest.DiscardRejectHandle;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: Main
 * Package: threadPoolTest
 * Description:
 *
 * @Author 龙培
 * @Create 2025/5/21 10:00
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        MyThreadPool myThreadPool = new MyThreadPool(2, 4, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2), new DiscardRejectHandle());
        for (int i = 0; i < 8; i++) {
            final int fi = i;
            myThreadPool.execute(() -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(Thread.currentThread().getName() + "执行了" + fi);
                    }
            );
        }
    }
}
