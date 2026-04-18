package test;

import java.lang.reflect.Proxy;

/**
 * ClassName: UserService
 * Package: test
 * Description:
 *
 * @Author 龙培
 * @Create 2025/5/26 10:19
 * @Version 1.0
 */
//    手写一个基于JDK动态代理的示例，要求：
//    定义UserService接口，包含addUser(String name)方法
//    实现真实类UserServiceImpl
//    通过Proxy类创建代理对象，在方法调用前后打印日志
//    期待候选人写出类似代码
public interface UserService {
    void addUser(String name);
}


