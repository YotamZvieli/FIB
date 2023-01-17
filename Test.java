import java.util.Arrays;

public class Test {
    public static void main(String[] args){
        FibonacciHeap heep = new FibonacciHeap();
        for (int i = 0; i < 5; i++){
            heep.insert(i);
        }
        System.out.println(Arrays.toString(heep.countersRep()));
        heep.deleteMin();
        System.out.println(heep.first);
        System.out.println(heep.min);
        System.out.println(Arrays.toString(heep.countersRep()));

    }
}
