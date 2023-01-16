import java.util.Arrays;

public class Test {
    public static void main(String[] args){
        FibonacciHeap heep = new FibonacciHeap();
        for (int i = 4; i < 7; i++){
            heep.insert(i);
        }
        System.out.println(Arrays.toString(heep.countersRep()));
        heep.deleteMin();
        System.out.println(Arrays.toString(heep.countersRep()));

    }
}
