import java.util.HashMap;
import java.util.Map;


// unit tests για D2
public class D2TestSiblings {

    public static void main(String[] args) {
        System.out.println("TEST D.2: Child & Siblings\n");

        // δημιουργία δεδομένων
        Map<Integer, Person> idToPerson = new HashMap<>();
        Map<String, Integer> nameToId = new HashMap<>(); // δεν το χρησιμοποιούμε εδώ, αλλά χρειάζεται στον constructor

        // οικογένεια:
        // μπαμπάς (1) & μαμά (2)
        // γιος (3) -> παιδί των 1 & 2
        // κόρη (4) -> παιδί των 1 & 2
        // θείος (5) -> αδελφός του μπαμπά (γονείς 10, 11 - δεν μας νοιάζουν εδώ)
        
        Person dad = new Person(1, "Mpampas", "Male", -1, -1, 2);
        Person mom = new Person(2, "Mama", "Female", -1, -1, 1);
        
        // τα παιδιά έχουν τους ίδιους γονείς
        Person son = new Person(3, "Gios", "Male", 1, 2, -1);
        Person daughter = new Person(4, "Kori", "Female", 1, 2, -1);
        
        // ο θείος έχει άλλους γονείς (π.χ. 10, 11), άρα δεν είναι αδελφός των παιδιών
        Person uncle = new Person(5, "Theios", "Male", 10, 11, -1);

        idToPerson.put(1, dad);
        idToPerson.put(2, mom);
        idToPerson.put(3, son);
        idToPerson.put(4, daughter);
        idToPerson.put(5, uncle);

        RelationshipChecker checker = new RelationshipChecker(idToPerson, nameToId);

        int passed = 0;
        int failed = 0;

        // έλεγχος isChild
        System.out.print("Checking isChild... ");
        
        // ο 3 είναι παιδί του 1; (ναι)
        boolean c1 = checker.isChild(3, 1);
        // ο 3 είναι παιδί της 2; (ναι)
        boolean c2 = checker.isChild(3, 2);
        // ο 1 είναι παιδί του 3; (οχι - είναι πατέρας)
        boolean c3 = !checker.isChild(1, 3);
        
        if (c1 && c2 && c3) {
            System.out.println("PASS");
            passed++;
        } else {
            System.out.println("FAIL");
            failed++;
        }

        // έλεγχος isSibling
        System.out.print("Checking isSibling... ");
        
        // ο 3 και η 4 είναι αδέλφια; (ναι - κοινοί γονείς)
        boolean s1 = checker.isSibling(3, 4);
        
        // ο 3 και ο 5 είναι αδέλφια; (όχι - ο 5 είναι θείος)
        boolean s2 = !checker.isSibling(3, 5);
        
        // ο 3 είναι αδελφός του εαυτού του; (όχι - Ειδική απαίτηση PDF)
        boolean s3 = !checker.isSibling(3, 3);

        if (s1 && s2 && s3) {
            System.out.println("PASS");
            passed++;
        } else {
            System.out.println("FAIL");
            System.out.println("   - Gios & Kori siblings: " + s1);
            System.out.println("   - Gios & Uncle NOT siblings: " + s2);
            System.out.println("   - Gios sibling of self (Should be false): " + !s3); // s3 είναι true αν το check επέστρεψε false
            failed++;
        }

        // σύνοψη
        System.out.println("\nTotal Tests: " + (passed + failed));
        System.out.println("Passed: " + passed);
        if (failed > 0) System.out.println("Failed: " + failed);
    }
}