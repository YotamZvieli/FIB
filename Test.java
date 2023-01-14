public class Test {
    public static void main(String[] args){
        FibonacciHeap heep = new FibonacciHeap();
        for (int i = 0; i < 10; i++){
            heep.insert(i);
        }
        heep.deleteMin();
        heep.deleteMin();
        System.out.println(heep.findMin().key);

    }
}
