package test;

/**
 * ClassName: SingleTon
 * Package: test
 * Description:
 *
 * @Author 龙培
 * @Create 2026/3/30 14:50
 * @Version 1.0
 */
public class SingleTon {
    private static volatile SingleTon instance = null;
    public SingleTon getInstance(){
        if(instance == null){
            synchronized (SingleTon.class){
                if(instance == null){
                    instance = new SingleTon();
                }
            }
        }
        return instance;
    }
 }