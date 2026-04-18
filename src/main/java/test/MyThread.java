package test;

/**
 * ClassName: MyThread
 * Package: test
 * Description:
 *
 * @Author 龙培
 * @Create 2025/4/29 22:11
 * @Version 1.0
 */
public class MyThread extends Thread{
    @Override
    public void run() {
        System.out.println("hello world");
        System.out.println("我是Thread");
    }


}
