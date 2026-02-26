import java.util.HashMap;
import java.util.Map;


// unit test για D4
public class D4TestCousin {

    public static void main(String[] args) {
        System.out.println("TEST D.4: First Cousins\n");

        // 1. δημιουργία δεδομένων
        Map<Integer, Person> idToPerson = new HashMap<>();
        Map<String, Integer> nameToId = new HashMap<>();

        // γενιά 1 (παππούδες)
        Person grandpa = new Person(1, "Pappous", "Male", -1, -1, 2);
        Person grandma = new Person(2, "Giagia", "Female", -1, -1, 1);
        
        // γενιά 2 (αδέλφια)
        // Δύο αδέλφια που έχουν τους ίδιους γονείς (1 & 2)
        Person brother1 = new Person(10, "Adelfos1", "Male", 1, 2, -1);
        Person brother2 = new Person(11, "Adelfos2", "Male", 1, 2, -1);
        
        // γενιά 3 (ξαδέρφια)
        // Ο cousin1 είναι παιδί του brother1
        Person cousin1 = new Person(20, "Ksaderfos1", "Male", 10, -1, -1); // Μητέρα άγνωστη (-1), Πατέρας ο 10
        
        // Ο cousin2 είναι παιδί του brother2
        Person cousin2 = new Person(21, "Ksaderfos2", "Female", 11, -1, -1); // Μητέρα άγνωστη (-1), Πατέρας ο 11
        
        // άσχετος / αδελφός
        // ένας αδελφός του cousin1 για αρνητικό έλεγχο (ίδιος πατέρας 10)
        Person siblingOfC1 = new Person(22, "AdelfosTouK1", "Male", 10, -1, -1);

        idToPerson.put(1, grandpa);
        idToPerson.put(2, grandma);
        idToPerson.put(10, brother1);
        idToPerson.put(11, brother2);
        idToPerson.put(20, cousin1);
        idToPerson.put(21, cousin2);
        idToPerson.put(22, siblingOfC1);

        RelationshipChecker checker = new RelationshipChecker(idToPerson, nameToId);

        int passed = 0;
        int failed = 0;

        // έλεγχος isFirstCousin
        System.out.print("Checking isFirstCousin... ");
        
        // θετικό σενάριο:
        // ο 20 (παιδί του 10) και ο 21 (παιδί του 11) είναι ξαδέρφια; 
        // (ναι, γιατί οι 10 και 11 είναι αδέλφια)
        boolean c1 = checker.isFirstCousin(20, 21);
        
        // αρνητικό σενάριο (αδέλφια):
        // ο 20 και ο 22 έχουν τον ίδιο πατέρα (10)
        // είναι αδέλφια η μέθοδος isFirstCousin δεν πρέπει να επιστρέφει true για αδέλφια)
        // αν η υλοποίησή σου στο RelationshipChecker ελέγχει απλά αν οι γονείς είναι αδέλφια,
        // τότε τα αδέλφια μπορεί να βγουν και ξαδέρφια
        // αν ο πατέρας είναι 10 και για τους δύο το isSibling(10, 10) επιστρέφει false.
        // Άρα σωστά θα βγει false.
        boolean c2 = !checker.isFirstCousin(20, 22);
        
        // αρνητικό σενάριο (γονέας-παιδί):
        // ο 10 δεν είναι ξάδερφος του 20
        boolean c3 = !checker.isFirstCousin(10, 20);
        
        // αρνητικό σενάριο (ίδιο άτομο):
        boolean c4 = !checker.isFirstCousin(20, 20);

        if (c1 && c2 && c3 && c4) {
            System.out.println("PASS");
            passed++;
        } else {
            System.out.println("FAIL");
            System.out.println("   - Cousin1 & Cousin2 match: " + c1);
            System.out.println("   - Siblings are NOT cousins: " + c2);
            System.out.println("   - Parent is NOT cousin: " + c3);
            failed++;
        }

        // σύνοψη
        System.out.println("\nTotal Tests: " + (passed + failed));
        System.out.println("Passed: " + passed);
        if (failed > 0) System.out.println("Failed: " + failed);
    }
}