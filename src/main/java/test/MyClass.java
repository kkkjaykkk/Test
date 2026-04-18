package test;

/**
 * ClassName: MyClass
 * Package: test
 * Description:
 *
 * @Author 龙培
 * @Create 2025/5/7 23:03
 * @Version 1.0
 */
public class MyClass {
    public final int a = 1;
    private final int b = 2;
    public void method(){
        System.out.println(a);
        // System.out.println(b);
    }
}

////1. 字符串按频率降序 + 字典序
//map.keySet().stream()
//   .sorted((a,b) -> !map.get(a).equals(map.get(b))
//        ? map.get(b) - map.get(a)
//           : a.compareTo(b))
//        .collect(Collectors.toList());
////2. 数字数组 → 过滤偶数 → 降序 → 输出
//        Arrays.stream(arr)
//      .filter(x -> x % 2 == 0)
//        .boxed() //必须要装箱才能转化为Integer.Stream 否则是 Int.Stream
//      .sorted((a,b) -> b - a)
//        .forEach(x -> System.out.print(x+" "));
////3. 字符串列表 → 取长度 → 求和
//        list.stream()
//    .mapToInt(String::length)
//    .sum();
//
////4.字符串转为整形数组
//int[] bottom = Arrays.stream(b.split("\\s+"))
//        .mapToInt(Integer::parseInt)
//        .toArray();
