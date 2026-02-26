public class AVLTree extends BinarySearchTree {

    // helper methods
    private int height(Node node) {
        return node == null ? 0 : node.height;
    }
    
    private int getBalance(Node node) {
        return height(node.left) - height(node.right);
    }
    
    // περιστροφές
    // δεξιά περιστροφή (left-left)
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        
        // εκτέλεση περιστροφής
        x.right = y; 
        y.left = T2;

        // ενημέρωση υψών
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // επιστροφή νέας ρίζας
        return x;
    }
    
    // αριστερή περιστροφή (right-right)
    private Node leftRotate(Node x) {
        Node y = x.right; 
        Node T2 = y.left;
        
        // εκτέλεση περιστροφής
        y.left = x;
        x.right = T2;

        // ενημέρωση υψών
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // επιστροφή νέας ρίζας
        return y;
    }
    
    // διπλή περιστροφή (left-right)
    private Node leftRightRotate(Node x) { 
        x.left = leftRotate(x.left); 
        return rightRotate(x); 
    }
    // διπλή περιστροφή (right-left)  
    private Node rightLeftRotate(Node x) { 
        x.right = rightRotate(x.right); 
        return leftRotate(x); 
    }
    
    // insert
    @Override
    public void insert(int key) {
        root = insertRecAVL(root,key);
    }
    private Node insertRecAVL(Node node, int key) {
        // κανονική εισαγωγή BST
        if (node == null) return new Node(key);

        if (key < node.key) {
            node.left = insertRecAVL(node.left, key);
        } else if (key > node.key) {
            node.right = insertRecAVL(node.right, key);
        } else { 
            // αν το κλειδί υπάρχει ήδη, αυξάνουμε το count
            node.count++; 
            return node; 
        }
        
        // ενημέρωση ύψους του τρέχοντος κόμβου
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        
        // υπολογισμός συντελεστή ισορροπίας (balance factor)
        int balance = getBalance(node);

        // αν ο κόμβος δεν είναι ισορροπημένος, υπάρχουν 4 περιπτώσεις
        // περίπτωση left left
        if (balance > 1 && key < node.left.key) 
            return rightRotate(node);

        // περίπτωση left right
        if (balance > 1 && key > node.left.key) 
            return leftRightRotate(node);

        // περίπτωση right right
        if (balance < -1 && key > node.right.key) 
            return leftRotate(node);

        // περίπτωση right left
        if (balance < -1 && key < node.right.key) 
            return rightLeftRotate(node); // RL
        
        return node;
    }
    
    
    // delete
    @Override
    public void delete(int key) {
        root = deleteRecAVL(root, key);
    }

    private Node deleteRecAVL(Node node, int key) {
        // κανονική διαγραφή BST
        if (node == null) return null;

        if (key < node.key) {
            node.left = deleteRecAVL(node.left, key);
        } else if (key > node.key) {
            node.right = deleteRecAVL(node.right, key);
        } else {
            // βρέθηκε ο κόμβος προς διαγραφή
            // duplicate
            if (node.count > 1) {
                node.count--;
                return node; // δεν αλλάζει η δομή, μειώνεται το count
            }

            // κόμβος με ένα ή κανένα παιδί
            if (node.left == null) return node.right;
            else if (node.right == null) return node.left;

            // κόμβος με δύο παιδιά
            // βρίσκουμε τον inorder predecessor (μέγιστο στο αριστερό υπόδεντρο)
            Node temp = getMaxValueNode(node.left);

            // αντιγράφουμε τα δεδομένα του predecessor στον τωρινό κόμβο
            node.key = temp.key;
            node.count = temp.count;

            // θέτουμε το count του predecessor σε 1, για να διαγραφεί πλήρως
            temp.count = 1;

            // διαγράφουμε τον παλιό predecessor
            node.left = deleteRecAVL(node.left, temp.key);
        }

        // αν το δέντρο είχε μόνο έναν κόμβο που διαγράφηκε
        if (node == null) return null;

        // ενημέρωση ύψους
        node.height = Math.max(height(node.left), height(node.right)) + 1;

        // έλεγχος ισορροπίας
        int balance = getBalance(node);

        // εξισορρόπιση του δέντρου

        // left left
        if (balance > 1 && getBalance(node.left) >= 0)
            return rightRotate(node);

        // left right
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // right right
        if (balance < -1 && getBalance(node.right) <= 0)
            return leftRotate(node);

        // right left
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    
    }

    // helper method για εύρεση μέγιστου κόμβου
    // στο BST είναι private
    private Node getMaxValueNode(Node node) {
        Node current = node;
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }

    // change key
    
    public void changeKey(int oldKey, int newKey) {
        // βρίσκουμε αν υπάρχει παλιό κλειδί
        Node node = findNode(root, oldKey);
        if (node != null) {
            int cnt = node.count;
            
            // διαγράφουμε το παλιό κλειδί
            // κάνουμε loop για να διαγραφούν όλα τα αντίτυπα
            for(int i = 0; i < cnt; i++) {
                delete(oldKey); // χρησιμοποιεί την delete του AVL
            }

            // εισάγουμε το νέο κλειδί
            for(int i = 0; i < cnt; i++) {
                insert(newKey); // χρησιμοποιεί την insert του AVL
            }

        } else {
            System.out.println("Το κλειδί " + oldKey + " δεν βρέθηκε");
        }
    }
    
    // helper method για εύρεση κόμβου
    private Node findNode(Node node, int key) {
        if (node == null || node.key == key) return node;
        if (key < node.key) return findNode(node.left, key);
        return findNode(node.right, key);
    }
}
