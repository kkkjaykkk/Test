package ThreadPool;

/**
 * ClassName: RejectHandle
 * Package: test
 * Description:
 *
 * @Author 龙培
 * @Create 2025/5/6 18:40
 * @Version 1.0
 */
public interface RejectHandle {
    public void reject(Runnable commend,MyThreadPool myThreadPool);
}
