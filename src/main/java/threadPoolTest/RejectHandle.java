package threadPoolTest;



/**
 * ClassName: RejectHandle
 * Package: threadPoolTest
 * Description:
 *
 * @Author 龙培
 * @Create 2025/5/21 10:35
 * @Version 1.0
 */
public interface RejectHandle {
    public void reject(Runnable command, MyThreadPool myThreadPool);
}
