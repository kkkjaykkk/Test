package test;

/**
 * ClassName: UserServiceImpl
 * Package: test
 * Description:
 *
 * @Author 龙培
 * @Create 2025/5/26 10:19
 * @Version 1.0
 */
public class UserServiceImpl implements UserService {
    @Override
    public void addUser(String name) {
        System.out.println("添加用户: " + name);
    }
}