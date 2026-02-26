public class BinarySearchTree {

    Node root;

    // insert method
    public void insert(int key) {
        root = insertRec(root, key);
    }
    
    // recursive insertion sort
    private Node insertRec(Node current, int key) {
        // αν φτάσουμε σε κενό σημείο, δημιουργείται το node
        if (current == null) {
            return new Node(key);
        }
        
        // πλοήγηση στο δέντρο
        if (key < current.key) {
            current.left = insertRec(current.left, key);
        }
        else if (key > current.key) {
            current.right = insertRec(current.right, key);
        }
        else {
            // duplicate (το κλειδί υπάρχει ήδη)
            // δεν φτιάχνουμε νέο node, αυξάνετε το count
            current.count++;
        }

        return current;
    }

    // traversals
    // inorder (αριστερά-> ρίζα-> δεξιά)
    public void printInOrder() {
        System.out.print("In-Order: ");
        inOrderRec(root);
        System.out.println();
    }

    private void inOrderRec(Node current) {
        if (current != null) {
            inOrderRec(current.left);
            
          // τυπώνουμε μορφή (key, count) όπως ζητάει η εκφώνηση
          System.out.print("(" + current.key + ", count:" + current.count + ") ");
            
            inOrderRec(current.right);
        }
    }

    //  preorder (ρίζα-> αριστερά-> δεξιά)
    public void printPreOrder() {
        System.out.print("Pre-Order: ");
        preOrderRec(root);
        System.out.println();
    }

    private void preOrderRec(Node current) {
        if (current != null) {
            System.out.print("(" + current.key + ", count:" + current.count + ") ");
            preOrderRec(current.left);
            preOrderRec(current.right);
        }
    }

    // postorder (αριστερά-> δεξιά-> ρίζα)
    public void printPostOrder() {
        System.out.print("Post-Order: ");
        postOrderRec(root);
        System.out.println();
    }

    private void postOrderRec(Node current) {
        if (current != null) {
            postOrderRec(current.left);
            postOrderRec(current.right);
            
        System.out.print("(" + current.key + ", count:" + current.count + ") ");
        }
    }

    // delete method
    public void delete(int key) {
        root = deleteRec(root, key);
    }

    private Node deleteRec(Node current, int key) {
        // αν το δέντρο είναι κενό ή δεν βρέθηκε κλειδί
    if (current == null) return null;
    // αναζήτηση του node
    if (key < current.key) current.left = deleteRec(current.left, key);
    else if (key > current.key) current.right = deleteRec(current.right, key);
    else { // βρέθηκε το node
        // περίπτωση duplicate, αν υπάρχει πολλές φορές, μειώνουμε το count
        if (current.count > 1) { 
            current.count--; 
            return current; 
        }
        
        // περίπτωση count = 1, πρέπει να διαγραφεί το node
        // κόμβος με ένα ή κανένα παιδί
        if (current.left == null) return current.right;
        if (current.right == null) return current.left;
        
        // node με δύο παιδιά
        // χρησιμοποιούμε τον inorder predecessor (το μεγαλύτερο από το αριστερό υπόδεντρο)
        Node pred = getMaxValueNode(current.left);  // Αλλαγή!

        // αντιγραφή των δεδομένων του predecessor στο τωρινό node
        current.key = pred.key;
        current.count = pred.count;

        // θέτουμε το count του παλιού predecessor σε 1
        // έτσι η επόμενη κλήση deleterec το διαγράφει τελείως
        // δεν μειώνει απλά το count του
        pred.count = 1;  // Ή deleteRec(pred.parent, pred.key) ideally

        // διαγράφουμε τον παλιό predecessor από το αριστερό υπόδεντρο
        current.left = deleteRec(current.left, pred.key);
    }
    return current;
}

// helper method για την εύρεση του μέγιστου
private Node getMaxValueNode(Node node) {  // Predecessor
    while (node.right != null) node = node.right;
    return node;
}


}
