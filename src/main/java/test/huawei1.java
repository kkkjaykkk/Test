package test;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class huawei1 {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int max_history = scanner.nextInt();
        Deque<String> deque = new LinkedList<>();
        Deque<String> stack = new LinkedList<>();
        scanner.nextLine();
        String cur = "Blank";
        for(int i=0;i<n;i++){
            String oprator = scanner.nextLine();
            String[] ops = oprator.split("\\s+");
            if(ops.length==2){ //代表是visit操作
                stack.clear(); //清空前进记录
                deque.offerLast(ops[1]);
                if(deque.size()>max_history){
                    deque.pollFirst();
                }
            }
            else if(oprator.equals("back")){
                if(deque.size()>=2){
                    stack.push(deque.pollLast());
                }
            }
            else if(oprator.equals("forward")){
                if(!stack.isEmpty()){
                    deque.offerLast(stack.pop());
                }
            }
            else{
                if(deque.isEmpty()){
                    System.out.println("Blank");
                }
                else{
                    System.out.println(deque.getLast());
                }
            }
        }
    }
}
