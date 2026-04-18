package test;

/**
 * ClassName: MyRunnableThread
 * Package: test
 * Description:
 *
 * @Author 龙培
 * @Create 2025/4/29 22:13
 * @Version 1.0
 */
public class MyRunnableThread implements Runnable {
    @Override
    public void run() {
        System.out.println("我正在执行Runnable线程");
    }
}
