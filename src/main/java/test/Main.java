package test; //TODO 注意删除

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        String a = br.readLine();
        String b = br.readLine();

        int[] begin = Arrays.stream(a.split("\\s+")).mapToInt(Integer::parseInt).toArray();
        int[] bottom = Arrays.stream(b.split("\\s+")).mapToInt(Integer::valueOf).toArray();
        PriorityQueue<List<Integer>> pq = new PriorityQueue<>((x,y)->y.get(1)-x.get(1));
        int res = 0;



        for(int i=0;i<n;i++){
            List<Integer> list = new ArrayList<>();
            res+=begin[i];
            list.add(i);
            int diff = begin[i] - Math.max((begin[i]+1)/2,bottom[i]);
            list.add(diff);
            pq.offer(list);
        }
        for(int i=0;i<m;i++){
            List<Integer> list = pq.poll();
            // System.out.println(res+","+list.get(0)+","+list.get(1));
            res-=list.get(1);
            int index = list.get(0);
            begin[index] = Math.max((begin[index]+1)/2,bottom[index]);
            int diff = begin[index] - Math.max((begin[index]+1)/2,bottom[index]);
            list.remove(1);
            list.add(diff);

            pq.offer(list);

        }
        pw.println(res);
        pw.flush();
        pw.close();
        br.close();
    }
}


