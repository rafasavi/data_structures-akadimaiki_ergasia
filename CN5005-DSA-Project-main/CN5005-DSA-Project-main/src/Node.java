public class Node {

    int key; 
    int count; 
    
    Node left;
    Node right;
    
    int height;

    // constructor (καλείται όταν δημιουργούμε νέο node)
    public Node(int key) {
        this.key = key;
        this.count = 1;  // ο αριθμός υπάρχει μια φορά αρχικά   
        this.left = null;     
        this.right = null;
        this.height = 1;  // το νέο node είναι leaf οπότε έχει ύψος 1
    }
}
