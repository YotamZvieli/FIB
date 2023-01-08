/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap
{
    HeapNode min;
    HeapNode first;

    int size;
   /**
    * public boolean isEmpty()
    *
    * Returns true if and only if the heap is empty.
    *   
    */
    public boolean isEmpty()
    {
        return min == null;
    }
		
   /**
    * public HeapNode insert(int key)
    *
    * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
    * The added key is assumed not to already belong to the heap.  
    * 
    * Returns the newly created node.
    */
    public HeapNode insert(int key)
    {    
    	HeapNode newNode = new HeapNode(key);
        if(this.isEmpty()){
            this.min = newNode;
            return newNode;
        }
        else {
            if(this.min.key > key){
                this.min = newNode;
            }
            newNode.next = this.first;
            HeapNode prevOfFirst = this.first.prev;
            this.first.prev = newNode;
            newNode.prev = prevOfFirst;
            prevOfFirst.next = newNode;
            this.first = newNode;
        }
        this.size += 1;
        return new HeapNode(key); // should be replaced by student code
    }

   /**
    * public void deleteMin()
    *
    * Deletes the node containing the minimum key.
    *
    */
    public void deleteMin()
    {
    HeapNode tmpMinNode = this.min;
    if (tmpMinNode.child == null){
        // bridge min node
    }
    else {
        // make sons as roots
    }
    // make all root non-marked
    // considollation - add root to array and connect when
        // find new min

    }
    /**
     * private HeapNode connect(HeapNode node1, HeapNode node2)
     *
     * connect two trees with the same rank and return the smallest node after connect.
     *
     */

    private HeapNode connect(HeapNode node1, HeapNode node2){
        HeapNode root;
        HeapNode nodeToConnect;
        if (node1.key < node2.key){
            root = node1;
            nodeToConnect = node2;
        }
        else{
            root = node2;
            nodeToConnect = node1;
            }
        HeapNode tmpChild = root.child;
        root.child = nodeToConnect;
        tmpChild.prev = root.child;
        root.child.next = tmpChild;
        tmpChild.prev.next = root.child;
        nodeToConnect.parent = root;
        return root;
    }



   /**
    * public HeapNode findMin()
    *
    * Returns the node of the heap whose key is minimal, or null if the heap is empty.
    *
    */
    public HeapNode findMin()
    {
        return min;
    }
    
   /**
    * public void meld (FibonacciHeap heap2)
    *
    * Melds heap2 with the current heap.
    *
    */
    public void meld (FibonacciHeap heap2)
    {
        if(heap2.isEmpty()){
            return;
        } else if (this.isEmpty()) {
            this.min = heap2.min;
            this.first = heap2.first;
            this.size = heap2.size();
        }
        else { //both heaps are not empty
            HeapNode lastOne = this.first.prev;
            HeapNode lastTwo = heap2.first.prev;
            lastOne.next = heap2.first;
            heap2.first.prev = lastOne;
            this.first.prev = lastTwo;
            lastTwo.next = this.first;
            if(heap2.min.key < this.min.key){
                this.min = heap2.min;
            }
            this.size += heap2.size();
        }
    }

   /**
    * public int size()
    *
    * Returns the number of elements in the heap.
    *   
    */
    public int size()
    {
    	return this.size;
    }
    	
    /**
    * public int[] countersRep()
    *
    * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
    * (Note: The size of of the array depends on the maximum order of a tree.)  
    * 
    */
    public int[] countersRep()
    {
        HeapNode curr = this.first;
        if (this.isEmpty()){
            return new int[0];
        }
        int maxRank = 0;
        while (curr.next != ( this.first)){
            if (curr.rank > maxRank){
                maxRank = curr.rank;
            }
            curr = curr.next;
        }
    	int[] arr = new int[maxRank+1];

        curr = this.first;
        while (curr.next != ( this.first)) {
            int rank = curr.rank;
            arr[rank] += 1;
        }

        return arr; //	 to be replaced by student code
    }
	
   /**
    * public void delete(HeapNode x)
    *
    * Deletes the node x from the heap.
	* It is assumed that x indeed belongs to the heap.
    *
    */
    public void delete(HeapNode x) 
    {
        decreaseKey(x, Integer.MAX_VALUE);
        deleteMin();
    }

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
    * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta)
    {
        x.key -= delta;
        if (this.min.key > x.key){
            this.min = x;
        }

        if (x.parent != null){ // x is not root
            if (x.key < x.parent.key){
                cascadingCut(x);
            }
        }

    }

    public void cutNode(HeapNode node){
        HeapNode tmpNext = node.next;
        HeapNode tmpPrev = node.prev;
        HeapNode tmpParent = node.parent;
        if (node == node.next){ // node has no sibiling
            tmpParent.child = null;
        }
        else {
            if (tmpParent.child.key == node.key) {
                tmpParent.child = tmpNext;
            }
            tmpPrev.next = tmpNext;
            tmpNext.prev = tmpPrev;
        }
        node.parent = null;
        node.next = this.first;
        node.prev = this.first.prev;
        this.first.prev.next = node;
        this.first.prev = node;
        this.first = node;
    }
    public void cascadingCut(HeapNode node){
        if (node.parent.isMark()){
            while (node.parent.isMark()){
                HeapNode tmpParent = node.parent;
                cutNode(node);
                node = tmpParent;
            }
            node.parent.mark = true;
        }
        else {
            node.parent.mark = true;
            cutNode(node);
        }

    }
   /**
    * public int nonMarked() 
    *
    * This function returns the current number of non-marked items in the heap
    */
    public int nonMarked() 
    {    
        return -232; // should be replaced by student code
    }

   /**
    * public int potential() 
    *
    * This function returns the current potential of the heap, which is:
    * Potential = #trees + 2*#marked
    * 
    * In words: The potential equals to the number of trees in the heap
    * plus twice the number of marked nodes in the heap. 
    */
    public int potential() 
    {    
        return -234; // should be replaced by student code
    }

   /**
    * public static int totalLinks() 
    *
    * This static function returns the total number of link operations made during the
    * run-time of the program. A link operation is the operation which gets as input two
    * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
    * tree which has larger value in its root under the other tree.
    */
    public static int totalLinks()
    {    
    	return -345; // should be replaced by student code
    }

   /**
    * public static int totalCuts() 
    *
    * This static function returns the total number of cut operations made during the
    * run-time of the program. A cut operation is the operation which disconnects a subtree
    * from its parent (during decreaseKey/delete methods). 
    */
    public static int totalCuts()
    {    
    	return -456; // should be replaced by student code
    }

     /**
    * public static int[] kMin(FibonacciHeap H, int k) 
    *
    * This static function returns the k smallest elements in a Fibonacci heap that contains a single tree.
    * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
    *  
    * ###CRITICAL### : you are NOT allowed to change H. 
    */
    public static int[] kMin(FibonacciHeap H, int k)
    {
        return new int[100];
    }
    
   /**
    * public class HeapNode
    * 
    * If you wish to implement classes other than FibonacciHeap
    * (for example HeapNode), do it in this file, not in another file. 
    *  
    */
    public static class HeapNode{

        private String info;
    	public int key;


       private int rank;
        private boolean mark;

       public boolean isMark() {
           if (this.parent == null){ //if this is root
               this.mark = false;
           }
           return mark;
       }

       public void setMark(boolean mark) {
           this.mark = mark;
       }

       private HeapNode child;
        private HeapNode next;
        private HeapNode prev;
        private HeapNode parent;


       public HeapNode(int key) {}
       public HeapNode(int key, String info, int rank, boolean mark, HeapNode child, HeapNode next, HeapNode prev, HeapNode parent) {
    		this.info = info;
            this.key = key;
            this.rank = rank;
            this.mark = mark;
            this.child = child;
            this.next = next;
            this.prev = prev;
            this.parent = parent;
    	}

    	public int getKey() {
    		return this.key;
    	}
    	public void setKey(int key) {
    		this.key = key;
    	}

       public HeapNode getPrev() {
           return prev;
       }

       public void setPrev(HeapNode prev) {
           this.prev = prev;
       }

       public HeapNode getNext() {
           return next;
       }

       public void setNext(HeapNode next) {
           this.next = next;
       }

       public HeapNode getChild() {
           return child;
       }

       public void setChild(HeapNode child) {
           this.child = child;
       }

       public int getRank() {
           return rank;
       }

       public void setRank(int rank) {
           this.rank = rank;
       }
       public HeapNode getParent() {
           return parent;
       }

       public void setParent(HeapNode parent) {
           this.parent = parent;
       }

       public void setInfo(String info) {
           this.info = info;
       }

       public String getInfo() {
           return info;
       }

   }
}
