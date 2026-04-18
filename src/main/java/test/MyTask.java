package test;

/**
 * ClassName: Task
 * Package: test
 * Description:
 *
 * @Author 龙培
 * @Create 2025/4/30 13:56
 * @Version 1.0
 */

public class MyTask implements Runnable {
    private String name;
    public MyTask(String name) {
        this.name = name;
    }
    @Override
    public void run() {
        System.out.println("执行任务: " + name);
    }
}
