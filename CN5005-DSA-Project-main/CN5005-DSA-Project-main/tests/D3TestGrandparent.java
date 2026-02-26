import java.util.HashMap;
import java.util.Map;

// unit tests για D3: Grandparent & Grandchild
public class D3TestGrandparent {

    public static void main(String[] args) {
        System.out.println("TEST D.3: Grandparent & Grandchild\n");

        // δημιουργία δεδομένων
        Map<Integer, Person> idToPerson = new HashMap<>();
        Map<String, Integer> nameToId = new HashMap<>();

        /*
            Οικογένεια:

            Παππούς (1) --- Γιαγιά (2)
                     |
                   Πατέρας (3) --- Μητέρα (4)
                                |
                              Παιδί (5)

            Θείος (6) παιδί του παππού, αλλά όχι γονέας του παιδιού
        */

        Person grandpa = new Person(1, "Pappous", "Male", -1, -1, 2);
        Person grandma = new Person(2, "Giagia", "Female", -1, -1, 1);

        Person father = new Person(3, "Pateras", "Male", 1, 2, 4);
        Person mother = new Person(4, "Mitera", "Female", -1, -1, 3);

        Person child = new Person(5, "Paidi", "Male", 3, 4, -1);

        Person uncle = new Person(6, "Theios", "Male", 1, 2, -1); // παιδί παππού, αλλά όχι γονέας παιδιού

        idToPerson.put(1, grandpa);
        idToPerson.put(2, grandma);
        idToPerson.put(3, father);
        idToPerson.put(4, mother);
        idToPerson.put(5, child);
        idToPerson.put(6, uncle);

        RelationshipChecker checker = new RelationshipChecker(idToPerson, nameToId);

        int passed = 0;
        int failed = 0;

        // Tests isGrandparent
        System.out.print("Checking isGrandparent... ");

        boolean g1 = checker.isGrandparent(1, 5); // παππούς → παιδί (true)
        boolean g2 = checker.isGrandparent(2, 5); // γιαγιά → παιδί (true)
        boolean g3 = !checker.isGrandparent(6, 5); // θείος → παιδί (false)
        boolean g4 = !checker.isGrandparent(5, 1); // παιδί → παππού (false)
        boolean g5 = !checker.isGrandparent(1, 1); // ίδιο άτομο (false)

        if (g1 && g2 && g3 && g4 && g5) {
            System.out.println("PASS");
            passed++;
        } else {
            System.out.println("FAIL");
            System.out.println("   grandpa->child: " + g1);
            System.out.println("   grandma->child: " + g2);
            System.out.println("   uncle->child (should be false): " + g3);
            System.out.println("   child->grandpa (should be false): " + g4);
            System.out.println("   same person (should be false): " + g5);
            failed++;
        }

        // Tests isGrandchild
        System.out.print("Checking isGrandchild... ");

        boolean c1 = checker.isGrandchild(5, 1); // παιδί → παππού (true)
        boolean c2 = checker.isGrandchild(5, 2); // παιδί → γιαγιά (true)
        boolean c3 = !checker.isGrandchild(5, 6); // παιδί → θείος (false)
        boolean c4 = !checker.isGrandchild(1, 5); // παππούς → παιδί (false)
        boolean c5 = !checker.isGrandchild(5, 5); // ίδιο άτομο (false)

        if (c1 && c2 && c3 && c4 && c5) {
            System.out.println("PASS");
            passed++;
        } else {
            System.out.println("FAIL");
            System.out.println("   child->grandpa: " + c1);
            System.out.println("   child->grandma: " + c2);
            System.out.println("   child->uncle (should be false): " + c3);
            System.out.println("   grandpa->child (should be false): " + c4);
            System.out.println("   same person (should be false): " + c5);
            failed++;
        }

        // Σύνοψη
        System.out.println("\nTotal Tests: " + (passed + failed));
        System.out.println("Passed: " + passed);
        if (failed > 0) System.out.println("Failed: " + failed);
    }
}