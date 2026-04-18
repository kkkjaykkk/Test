package test;

/**
 * ClassName: MySingleInstance
 * Package: test
 * Description:
 *
 * @Author 龙培
 * @Create 2025/5/11 16:51
 * @Version 1.0
 */
public class MySingleInstance {
    private static volatile MySingleInstance instance;

    private MySingleInstance getInstance() {
        if(instance == null){
            synchronized (MySingleInstance.class){
                if(instance == null){ //确保后面被阻塞的线程来再次创建的时候不会再创建实例
                    instance = new MySingleInstance();
                }
            }
        }
        return instance;
    }
}
