package test;

import java.util.concurrent.Callable;

/**
 * ClassName: MyCallableThread
 * Package: test
 * Description:
 *
 * @Author 龙培
 * @Create 2025/4/29 22:17
 * @Version 1.0
 */
public class MyCallableThread implements Callable {
    @Override
    public String call() throws Exception {
        System.out.println("我是Callable");
        return "1";
    }
}
