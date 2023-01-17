import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args){

        FibonacciHeap heep = new FibonacciHeap();
        Map<Integer, FibonacciHeap.HeapNode> mapConnect  = new HashMap<>();
        int m = 0;
        for (int i = m-1; i > -2; i--){
           mapConnect.put(i,heep.insert(i));
        }
        heep.deleteMin();
        for(int i = (int)(Math.ceil(Math.log(m)));i>0;i--){
             heep.decreaseKey(mapConnect.get((int)(m- Math.pow(2,i) + 1)), m+1);
        }

    }
}
