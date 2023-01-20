/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap
{
    HeapNode min;
    HeapNode first;
    int marked;
    int rootsNum;
    int size;
    static int totalLinks;
    static int totalCuts;
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
        this.size += 1;
        this.rootsNum += 1;
    	HeapNode newNode = new HeapNode(key);
        if(this.isEmpty()){
            this.min = newNode;
            this.first = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
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
        return newNode;
    }

    public HeapNode insert(int key,HeapNode originalCopyNode)
    {
        this.size += 1;
        this.rootsNum += 1;
        HeapNode newNode = new HeapNode(key,originalCopyNode);
        if(this.isEmpty()){
            this.min = newNode;
            this.first = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
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
        return newNode;
    }

   /**
    * public void deleteMin()
    *
    * Deletes the node containing the minimum key.
    *
    */
    public void deleteMin() //w.c - O(n) amortized O(logn)
    {
    HeapNode tmpMinNode = this.min;
    HeapNode prevNode = this.min.prev;
    HeapNode nextNode = this.min.next;
    if (this.rootsNum == 1 && this.min.child == null){
        rootsNum = 0;
        this.first = this.min = null;
        size = 0;
        this.marked = 0;
        return;
    }
    if(this.rootsNum == 1){
        this.first = tmpMinNode.child;
        this.size -= 1;
        HeapNode current = this.first;
        HeapNode miniNode = new HeapNode(Integer.MAX_VALUE);
        int cnt = 0;
        boolean bool = true;
        while (current != this.first || bool){ //set parent and find new minimum
            current.parent = null;
            if(current.key < miniNode.key){
                miniNode = current;
            }
            current = current.next;
            cnt += 1;
            bool = false;
        }

        this.min = miniNode;
        current.parent = null;
        this.rootsNum = cnt;
        this.considulation();
        return;
    }
    if (tmpMinNode.child == null){
        nextNode.prev = prevNode;
        prevNode.next = nextNode;

    }
    else {
        HeapNode child = this.min.child;
        prevNode.next = child;
        child.prev.next = nextNode;
        nextNode.prev = child.prev;
        child.prev = prevNode;
    }
    if(this.first == this.min){
        if (this.first.child != null){
            this.first = this.min.child;
        }
        else {
            this.first = this.first.next;
        }
    }
    HeapNode curr = this.first;
    boolean bool = true;
    while ((curr != this.first) || bool){ //make all roots not marked
        if(curr.mark) {
            curr.mark = false;
            this.marked -= 1;
        }
        curr.parent = null;
        curr = curr.next;
        bool = false;
    }
    this.considulation();
    this.size -= 1;
    }
    /**
     * private HeapNode connect(HeapNode node1, HeapNode node2)
     *
     * connect two trees with the same rank and return the smallest node after connect.
     *
     */

    private HeapNode connect(HeapNode node1, HeapNode node2){
        totalLinks += 1;
        if(node1.child == null && node2.child == null){
            if(node1.key < node2.key){
                node1.child = node2;
                node2.parent = node1;
                node1.rank = 1;
                node2.next = node2;
                node2.prev = node2;
                return node1;
            }
            else {
                node2.child = node1;
                node1.parent = node2;
                node2.rank = 1;
                node1.next = node1;
                node1.prev = node1;
                return node2;
            }
        }
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
        if(root.child.next == root.child){
            root.child.prev = nodeToConnect;
            nodeToConnect.next = root.child;
            nodeToConnect.prev = root.child;
            root.child.next = nodeToConnect;
            root.child = nodeToConnect;
            root.child.parent = root;

        }
        else {
            HeapNode tmpChild = root.child;
            root.child = nodeToConnect;
            root.child.prev = tmpChild.prev;
            tmpChild.prev.next = root.child;
            tmpChild.prev = root.child;
            root.child.next = tmpChild;
            nodeToConnect.parent = root;
        }
        root.rank += 1;
        return root;
    }

    public static int getTotalLinks() {
        return totalLinks;
    }

    public static void setTotalLinks(int totalLinks) {
        FibonacciHeap.totalLinks = totalLinks;
    }

    public static int getTotalCuts() {
        return totalCuts;
    }

    public static void setTotalCuts(int totalCuts) {
        FibonacciHeap.totalCuts = totalCuts;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getRootsNum() {
        return rootsNum;
    }

    public void setRootsNum(int rootsNum) {
        this.rootsNum = rootsNum;
    }

    public HeapNode getMin() {
        return min;
    }

    public void setMin(HeapNode min) {
        this.min = min;
    }

    public int getMarked() {
        return marked;
    }

    public void setMarked(int marked) {
        this.marked = marked;
    }

    public HeapNode getFirst() {
        return first;
    }

    public void setFirst(HeapNode first) {
        this.first = first;
    }

    private void considulation (){ // W.C - O(n) amort - O(logn)
        int size  = (int) (Math.log10(this.size)/(Math.log10(0.5+Math.sqrt(1.25))));
        HeapNode[] nodeBox = new HeapNode[size + 10];
        HeapNode curr = this.first;
        boolean petch = true;
        while (curr != this.first || petch){
            HeapNode tmp = curr.next;
            curr.next = null; // unplugged curr from siblings
            curr.prev = null;
            int tmpRank = curr.rank;
            HeapNode tmpCurr = curr;
            petch = false;
            while (nodeBox[tmpRank] != null) {
                tmpCurr = connect(tmpCurr, nodeBox[tmpRank]);
                nodeBox[tmpRank] = null;
                tmpRank = tmpCurr.rank;
            }
            nodeBox[tmpRank] = tmpCurr; // the cell is empty
            curr = tmp;
        }
        int p = 0;
        for (int i =0; i < nodeBox.length; i ++){
            if(nodeBox[i] != null){
                this.first = nodeBox[i];
                this.first.next = this.first;
                this.first.prev = this.first;
                p = i;
                break;
            }
        }
        HeapNode lastConnected = this.first;
        HeapNode nextToConnect = null;
        for (int i = p + 1; i < nodeBox.length; i ++) {
            if(nodeBox[i] != null){
                nextToConnect = nodeBox[i];
                nextToConnect.prev = lastConnected;
                nextToConnect.next = this.first;
                this.first.prev = nextToConnect;
                lastConnected.next = nextToConnect;
                lastConnected = nextToConnect;
            }
        }
        HeapNode minNode = new HeapNode(Integer.MAX_VALUE);
        this.rootsNum = 0;
        for (int j = 0; j < nodeBox.length;j++){
            if (nodeBox[j]  != null && nodeBox[j].key < minNode.key){
                minNode = nodeBox[j];
            }
            if (nodeBox[j]  != null){
                if (nodeBox[j].mark){
                    nodeBox[j].mark = false;
                    this.marked -= 1;
                }
                this.rootsNum += 1;
            }
        }
        this.min = minNode;
        }


   /**
    * public HeapNode findMin()
    *
    * Returns the node of the heap whose key is minimal, or null if the heap is empty.
    *
    */
    public HeapNode findMin() // O(1)
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
            this.rootsNum = heap2.rootsNum;
            this.marked = heap2.marked;
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
            this.marked += heap2.marked;
            this.rootsNum += heap2.rootsNum;
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
        boolean bool = true;
        while ((curr !=this.first)||bool){
            if (curr.rank > maxRank){
                maxRank = curr.rank;
            }
            curr = curr.next;
            bool = false;
        }
    	int[] arr = new int[maxRank+1];

        curr = this.first;
        bool = true;
        while ((curr != this.first) || bool) {
            int rank = curr.rank;
            arr[rank] += 1;
            bool = false;
            curr = curr.next;
        }

        return arr;
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
        if(x.isMark()){
            x.mark = false;
            this.marked -= 1;
        }
        decreaseKey(x, Integer.MAX_VALUE);
        deleteMin();
    }

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
    * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta) // W.C - O(n) amort - O(logn)
    {
        if ((x.key - delta) > x.key){
            x.key = Integer.MIN_VALUE;
        }
        else {
            x.key -= delta;
        }
        if (this.min.key > x.key){
            this.min = x;
        }

        if (x.parent != null){ // x is not root
            if (x.key < x.parent.key){
                if(x.isMark()){
                    x.mark = false;
                    this.marked -= 1;
                }
                cascadingCut(x);
            }
        }

    }

    public void cutNode(HeapNode node){ //O(1)
        node.parent.rank -= 1;
        this.rootsNum += 1;
        totalCuts += 1;
        if(node.mark){
            node.mark = false;
            this.marked -= 1;
        }
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
    public void cascadingCut(HeapNode node){//w.c O(logn) amort O(1)
        if (node.parent.isMark()){
            while (node.parent != null && node.parent.isMark()){
                HeapNode tmpParent = node.parent;
                cutNode(node);
                node = tmpParent;
            }
            if(!ifRoot(node.parent)) {
                node.parent.mark = true;
                this.marked += 1;
            }
            if (node.mark){
                cutNode(node);
            }
        }
        else {
            if(!ifRoot(node.parent)) {
                node.parent.mark = true;
                this.marked += 1;
                cutNode(node);
            }
            else {
                cutNode(node);
            }
        }

    }
    private boolean ifRoot(HeapNode node){
        return node.parent == null;
    }
   /**
    * public int nonMarked() 
    *
    * This function returns the current number of non-marked items in the heap
    */
    public int nonMarked() 
    {    
        return (this.size - this.marked);
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
        return this.rootsNum + 2*this.marked;
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
    	return totalLinks;
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
    	return totalCuts; // should be replaced by student code
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
        if(H.isEmpty()){
            return new int[0];
        }
        FibonacciHeap helpHeap = new FibonacciHeap();
        int[] arr = new int[k];
        HeapNode Hmin = H.findMin();
        helpHeap.insert(Hmin.key,Hmin);
        HeapNode curr = Hmin;
        HeapNode pointer = null;
        for (int i =0; i < k; i++){
            arr[i] = helpHeap.findMin().key;
            pointer = helpHeap.findMin().originPointer;
            helpHeap.deleteMin();
            if(pointer.child != null) {
                curr = pointer.child;
                boolean bool = true;
                while (curr != pointer.child || bool) {
                    helpHeap.insert(curr.key,curr);
                    curr = curr.next;
                    bool = false;
                }
            }
        }
        return arr;
    }
    
   /**
    * public class HeapNode
    * 
    * If you wish to implement classes other than FibonacciHeap
    * (for example HeapNode), do it in this file, not in another file. 
    *  
    */
    public static class HeapNode{
       public int getRank() {
           return rank;
       }

       public void setRank(int rank) {
           this.rank = rank;
       }

       public HeapNode getPrev() {
           return prev;
       }

       public void setPrev(HeapNode prev) {
           this.prev = prev;
       }

       public HeapNode getParent() {
           return parent;
       }

       public void setParent(HeapNode parent) {
           this.parent = parent;
       }

       public HeapNode getOriginPointer() {
           return originPointer;
       }

       public void setOriginPointer(HeapNode originPointer) {
           this.originPointer = originPointer;
       }

       public HeapNode getNext() {
           return next;
       }

       public void setNext(HeapNode next) {
           this.next = next;
       }

       public int getKey() {
           return key;
       }

       public void setKey(int key) {
           this.key = key;
       }

       public String getInfo() {
           return info;
       }

       public void setInfo(String info) {
           this.info = info;
       }

       public HeapNode getChild() {
           return child;
       }

       public void setChild(HeapNode child) {
           this.child = child;
       }

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
        private HeapNode originPointer;


       public HeapNode(int key) {
           this.key = key;
           this.next = this;
           this.prev = this;
       }

        public HeapNode(int key,HeapNode originNode){
           this.key = key;
           this.originPointer = originNode;
            this.next = this;
            this.prev = this;
        }

       public boolean getMarked() {
           return mark;
       }
   }
}
