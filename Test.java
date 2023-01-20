import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args){
        double time1 = System.currentTimeMillis();
        FibonacciHeap heep = new FibonacciHeap();
        Map<Integer, FibonacciHeap.HeapNode> mapConnect  = new HashMap<>();
          int m = (int)Math.pow(3,14)-1;
          m = 40;
//        for (int i = m-1; i > -2; i--){
//           mapConnect.put(i,heep.insert(i));
//        }
//        heep.deleteMin();
//        for(int i = (int)(Math.ceil(Math.log(m)/Math.log(2)));i>0;i--){
//             heep.decreaseKey(mapConnect.get((int)(m- Math.pow(2,i)) + 1), m+1);
//        }
//        heep.decreaseKey(mapConnect.get(m-2),m+1);
        for(int i = 0; i <= m;i++){
            heep.insert(i);
        }
        for (int i = 0;i < (m*3)/4;i++){
            heep.deleteMin();
        }
        double time2 = System.currentTimeMillis();
        System.out.println("toatal cats: " + FibonacciHeap.totalCuts + " total links: " + FibonacciHeap.totalLinks + " potential: " + heep.potential());
        System.out.println("time to execute: " + (time2 - time1));
    }
}
