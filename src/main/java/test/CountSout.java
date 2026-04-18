package test;

/**
 * ClassName: CountSout
 * Package: test
 * Description:
 *
 * @Author 龙培
 * @Create 2025/5/27 15:06
 * @Version 1.0
 */
public class CountSout {
    private static volatile int count = 0;
    public static void main(String[] args) {
        for(int i = 0; i < 3; i++) {
            final int fi = i;
            new Thread(()->{
                while(count<=30){
                    if(count%3==fi){
                        System.out.println(Thread.currentThread().getName()+" "+count);
                        count++;
                    }
                    else{
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}
