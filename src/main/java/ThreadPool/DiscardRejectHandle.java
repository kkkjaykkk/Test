package ThreadPool;

/**
 * ClassName: DiscardRejectHandle
 * Package: test
 * Description:
 *
 * @Author 龙培
 * @Create 2025/5/6 18:41
 * @Version 1.0
 */
public class DiscardRejectHandle implements RejectHandle{
    @Override
    public void reject(Runnable commend, MyThreadPool myThreadPool) {
        Runnable poll = myThreadPool.blockingQueue.poll();
        myThreadPool.execute(commend); //注意不能直接添加到阻塞队列里面，会有问题
    }
}
