package test;

import javax.swing.text.StyledEditorKit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * ClassName: huawei2
 * Package: test
 * Description:
 *
 * @Author 龙培
 * @Create 2026/4/18 14:46
 * @Version 1.0
 */
public class huawei2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        int n = Integer.parseInt(st.nextToken());
        List<List<Integer>> lists = new ArrayList<>();
        for(int i=0;i<=n;i++){
            lists.add(new ArrayList<>());
        }
        for(int i=0;i<n-1;i++){ //建立无序图
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            lists.get(u).add(v);
            lists.get(v).add(u);
        }
        String s = br.readLine().trim();
        int[] initval = Arrays.stream(s.split("\\s+")).mapToInt(Integer::parseInt).toArray();
        s = br.readLine().trim();
        int[] targetval = Arrays.stream(s.split("\\s+")).mapToInt(Integer::parseInt).toArray();
        List<List<Integer>> children = new ArrayList<>();
        for(int i=0;i<=n;i++){
            children.add(new ArrayList<>());
        }
        boolean[] visited = new boolean[n+1];
        Deque<Integer> deque = new LinkedList<>();
        deque.offerLast(1);
        int[] depth = new int[n+1];
        depth[1]=1;
        visited[1]=true;
        while(!deque.isEmpty()){
            int curnode = deque.pollLast();
            for(int child:lists.get(curnode)){
                if(!visited[child]){
                    deque.offerLast(child);
                    children.get(curnode).add(child);
                    visited[child]=true;
                    depth[child]=depth[curnode]+1;
                }
            }
        }
        deque.offerLast(1);
        int oddflipcount = 0;
        int evenflipcount = 0;
        int ans = 0;
        while(!deque.isEmpty()){
            int curnode = deque.pollLast();
            for(int child:children.get(curnode)){
                deque.offerLast(child);
                int curdepth = depth[curnode];
                int curval = initval[curnode] ^ (curdepth%2==1? oddflipcount%2 : evenflipcount%2);
                if(curval!=targetval[curnode]){
                    ans++;
                    if(curdepth%2==1){
                        oddflipcount++;
                    }
                    int a = 1;
                    else{
                        evenflipcount++;
                    }
                }
            }
        }
        System.out.println(ans);
    }
}
