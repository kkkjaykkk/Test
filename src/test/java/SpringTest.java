import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.MethodWrapper;
import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;
import org.springframework.scheduling.config.Task;
import test.*;

import java.io.*;
import java.lang.reflect.*;
import java.nio.channels.SelectableChannel;
import java.util.concurrent.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ClassName: SpringTest
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author 龙培
 * @Create 2025/4/29 22:05
 * @Version 1.0
 */
@SpringBootTest
public class SpringTest {
    @Test
    public void testInheritableThreadLocal(){
        ThreadLocal<String> t1 = new ThreadLocal<>();
        t1.set("龙培");
        new Thread(()->{
            System.out.println(t1.get());
        }).start();
    }
    @Test
    public void testThead(){
        MyThread t1 = new MyThread();
        t1.start();
    }
    @Test
    public void testRunnableThread(){
        MyRunnableThread t1 = new MyRunnableThread();
        Thread t = new Thread(t1);
        t.start();
    }
    @Test
    public void testCallable(){
        MyCallableThread t1 = new MyCallableThread();
        FutureTask<String> task = new FutureTask<String>(t1);
        Thread t = new Thread(task);
        t.start();
        try {
            System.out.println(task.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testThreadPool() {
        ExecutorService executor = new ThreadPoolExecutor(
                8,
                16,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(16),
                new ThreadPoolExecutor.AbortPolicy());
        for(int i = 0;i<10;i++){
            try {
                Runnable task = new MyTask("Task"+i);
                executor.execute(task);
            } catch (Exception e) {
                System.out.println("Task" + i + ": " + e.getMessage());
            }
        }
    }
    @Test
    public void testIO(){
        Scanner scanner = new Scanner(System.in);
        int age = scanner.nextInt();
        System.out.println(age);
        String s = scanner.nextLine();
        System.out.println(s);
        scanner.close();
    }
    @Test
    public void testDeque(){
        Deque<Integer> stack = new LinkedList<>();
        Deque<Integer> queue = new LinkedList<>();
        for(int i = 0;i<5;i++){
            stack.push(i);
            queue.offer(i);
        }
        stack.pop();
        System.out.println(stack);
        queue.poll();
        System.out.println(queue);
     }
     @Test
     public void testStringBuilder(){
         StringBuilder sb = new StringBuilder();
         sb.append("龙培");
         sb.append("LOVE");
         sb.append("王翊菲");
         System.out.println(sb);
     }
     @Test
     public void testStringConvert(){
         String s = "123";
         int i = Integer.parseInt(s);
         Double d = Double.parseDouble(s);
         System.out.println(i);
         System.out.println(d);
     }
     @Test
     public void testReflect() throws Exception{
//         Constructor<MyClass> constructor = MyClass.class.getConstructor();
//         MyClass myClass = constructor.newInstance();
         MyClass myClass = MyClass.class.newInstance();
         Field b = MyClass.class.getDeclaredField("b");
         b.setAccessible(true);
         b.set(myClass,10);
         Object o = b.get(myClass);
         System.out.println(o);
         System.out.println(myClass.a);
         Method method = MyClass.class.getMethod("method");
         method.invoke(myClass);
     }
     @Test
     public void testUpperCase(){
         Scanner scan = new Scanner(System.in);
         String str = scan.nextLine();
         char max = Character.toUpperCase(str.charAt(0));
         for(int i = 1;i<str.length();i++){
             if(Character.toUpperCase(str.charAt(i))>max){
                 max = Character.toUpperCase(str.charAt(i));
             }
         }
         StringBuilder sb = new StringBuilder();
         for(int i = 0;i<str.length();i++){
             sb.append(str.charAt(i));
             if(Character.toUpperCase(str.charAt(i))==max){
                 sb.append("(max)");
             }
         }
         System.out.println(sb);
    }
    @Test
    public void testStream(){
        int sum = Arrays.asList(1, 2, 3).stream().filter(x -> x > 1).mapToInt(Integer::intValue).sum();
    }
    private volatile int count =1;
    CountDownLatch countDownLatch = new CountDownLatch(3);
    @Test
    public void testCountSout() throws InterruptedException {
        for(int i = 0; i < 3; i++) {
            final int fi = i;
            Thread thread = new Thread(() -> {
                System.out.println("线程" + fi + "开始执行");
                while (count <= 30) {
                    if (count % 3 == fi) {
                        System.out.println(Thread.currentThread().getName() + " " + count);
                        count++;
                    } else {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                countDownLatch.countDown();
            });
            thread.start();
        }
        System.out.println("主线程完成");
        countDownLatch.await();
    }
    @Test
    public void testAtomic(){
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println(i);
    }





    @Test
    public void test22(){
        int[] arr = new int[10];
        for(int i = 0;i<10;i++){
            arr[i] = i+1;
        }
        //编写冒泡排序算法
        for(int i = 0;i<arr.length-1;i++){
            for(int j = 0;j<arr.length-1-i;j++){
                if(arr[j]>arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));


    }
    
    
    
    @Test
    public void testCompletableFutrue(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5, 1000, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), new ThreadPoolExecutor.CallerRunsPolicy());
        List<CompletableFuture<String>> futureList = new ArrayList<>();
        executor.getActiveCount();
        for(int i=0;i<10;i++){
            CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName()+"");
                return "nbsbb";
            },executor);
            futureList.add(stringCompletableFuture);
        }
        CompletableFuture.runAsync(() -> {
            System.out.println("runAsync");
        }, executor);

        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();
        System.out.println("主线程完成");
    }


    private volatile int count1 = 0;
    CountDownLatch countDownLatch1 = new CountDownLatch(3);
    @Test
    public void testThreadSout(){
        final long time = System.currentTimeMillis();
        //AI禁止提醒
        for(int i=0;i<3;i++){
            final int fi = i;
            new Thread(()->{
                while(count1<30){
                    if(System.currentTimeMillis() - time > 3000){
                        break;
                    }
                    if(fi==count1%3){
                        System.out.println(Thread.currentThread().getName()+" "+count1);
                        count1++;
                    }
                    else{
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                countDownLatch1.countDown();
            }).start();
        }
        try {
            countDownLatch1.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("主线程完成");
    }


    @Test
    public void test2222(){
        long l = 11;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        String string  = new String("11");
    }

    @Test
    public void testStreams(){
        String[] strs = new String[]{"123","12","1111"};
        int sum = Arrays.stream(strs).mapToInt(String::length).sum();
        List<String> list = Arrays.asList(strs);
        int sum1 = list.stream().mapToInt(String::length).sum();
        list.stream().map(String::length).collect(Collectors.toList());
        System.out.println(sum);
        System.out.println(sum1);

        Map<String,Integer> map = new HashMap<>();
        map.put("abc",1);
        map.put("ab",2);
        map.put("bc",2);
        map.put("c",3);
        map.put("ac",4);
        //出现频率按降序，否则按字典序升序
        List<String> collect = map.keySet().stream().sorted((a, b) -> map.get(a) == map.get(b) ? a.compareTo(b) : map.get(b) - map.get(a)).collect(Collectors.toList());

        int[] ints = new int[]{1,2,3,4,5,6};
        int[] array = Arrays.stream(ints).filter(a -> a % 2 == 0).toArray();
        for(int i:array){
            System.out.println(i);
        }
        for(String s: collect){
            System.out.println(s);
        }

    }
    @Test
    public void testIOs() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(System.out));
        StringTokenizer stringTokenizer = new StringTokenizer(in.readLine().trim());
        int a = Integer.parseInt(stringTokenizer.nextToken());
        String s = new String(stringTokenizer.nextToken());
        printWriter.println(a+","+s);
        printWriter.flush();
        System.out.println(a+","+s);
        printWriter.close();
    }
}

