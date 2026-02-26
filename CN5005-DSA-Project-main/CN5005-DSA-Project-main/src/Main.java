import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // μέρος Α & Β (BST & AVL)

        // δημιουργία του δέντρου
        AVLTree tree = new AVLTree();

        System.out.println("Δοκιμή Μέρους Πρώτου");

        // εισαγωγή δεδομένων
        // αντικαθιστούμε τους αριθμούς με τα τέσσερα τελευταία ψηφία του αμ ταξινομημένα αλφαβητικά με βάση το επώνυμο
        int[] studentIds = {
                2154,
                8902,
                2154,
                3341,
                1100,
                9999
        };

        System.out.println("Εισαγωγή κλειδιών (από ΑΜ φοιτητών)...");
        for (int id : studentIds) {
            System.out.println("Εισαγωγή: " + id);
            tree.insert(id);
        }

        // traversals
        System.out.println("\nTraversals Demo");

        // η inorder πρέπει να τα εμφανίσει ταξινομημένα
        tree.printInOrder();

        // η preorder δείχνει τη δομή (ρίζα -> παιδιά)
        tree.printPreOrder();

        // η postorder (παιδιά -> ρίζα)
        tree.printPostOrder();

        // διαγραφή
        System.out.println("\nDelete Demo");

        // διαγραφή duplicate
        // διαγράφουμε το 2154 που το βάλαμε 2 φορές.
        System.out.println("Δοκιμή 1: Διαγραφή του 2154 (Έχει count > 1).");
        tree.delete(2154);
        tree.printInOrder(); // ελέγχουμε ότι το 2154 υπάρχει ακόμα με count:1

        // πλήρης διαγραφή
        // διαγράφουμε το 9999 που έχει count 1.
        System.out.println("Δοκιμή 2: Διαγραφή του 9999 (Έχει count 1).");
        tree.delete(9999);
        tree.printInOrder(); // το 9999 δεν πρέπει να υπάρχει

        // δοκιμή changekey
        System.out.println("\nΔοκιμή Αλλαγής Κλειδιού (Change Key)");
        System.out.println("Αλλάζουμε το κλειδί 3341 σε 7777:");
        tree.changeKey(3341, 7777);
        tree.printInOrder();

    // μέρος C, D, E

    System.out.println("Μέρος C, D, E: Γενεαλογικό Δέντρο");
    runGenealogyPart();
    }

    private static void runGenealogyPart() {
        // φόρτωση csv
        CsvParser parser = new CsvParser();
        List<Person> peopleList = parser.loadPersons("persons.csv");

        if (peopleList.isEmpty()) {
            System.err.println("Το αρχείο δεν φορτώθηκε σωστά.");
            return;
        }
        System.out.println("Φορτώθηκαν " + peopleList.size() + " άτομα.");

        // δημιουργία maps
        Map<Integer, Person> idToPerson = new HashMap<>();
        Map<String, Integer> nameToId = new HashMap<>();

        for (Person p : peopleList) {
            idToPerson.put(p.getId(), p);
            nameToId.put(p.getName(), p.getId());
        }

        // εκτύπωση για επιβεβαίωση
        System.out.println("Λίστα Ατόμων");
        for (Person p : peopleList) {
            System.out.println(p.getName() + " [" + p.getGender() + "]");
        }

        // αρχικοποίηση RelationshipChecker
        RelationshipChecker checker = new RelationshipChecker(idToPerson, nameToId);

        // διαδραστικός έλεγχος σχεσεων
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nΈλεγχος Σχέσεων");
        System.out.print("Πληκτρολογήστε 'exit' για έξοδο.");

        while (true) {
            System.out.println("\nΔώστε το 1ο όνομα: ");
            String nameA = scanner.nextLine().trim();
            if (nameA.equalsIgnoreCase("exit")) break;

            System.out.println("Δώστε το 2ο όνομα: ");
            String nameB = scanner.nextLine().trim();
            if (nameB.equalsIgnoreCase("exit")) break;

            // κλήση της μεθόδου relation που φτιάξαμε
            String result = checker.relation(nameA, nameB);
            System.out.println("Αποτέλεσμα: " + result);
        }

        scanner.close();
        System.out.println("Τερματισμός προγράμματος.");
    }
}
