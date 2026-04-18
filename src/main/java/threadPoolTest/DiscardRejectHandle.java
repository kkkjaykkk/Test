package threadPoolTest;


import java.util.concurrent.ThreadPoolExecutor;

/**
 * ClassName: DiscardRejectHandle
 * Package: threadPoolTest
 * Description:
 *
 * @Author 龙培
 * @Create 2025/5/21 10:36
 * @Version 1.0
 */
public class DiscardRejectHandle implements RejectHandle{
    @Override
    public void reject(Runnable command, MyThreadPool myThreadPool) {
        myThreadPool.blockingQueue.poll();
        myThreadPool.execute(command);
    }
}
