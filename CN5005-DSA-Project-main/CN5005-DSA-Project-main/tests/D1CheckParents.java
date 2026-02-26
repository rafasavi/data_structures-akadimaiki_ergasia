import java.util.HashMap;
import java.util.Map;


// unit tests για D1
public class D1CheckParents {

    public static void main(String[] args) {
        System.out.println("TEST D.1: Parents (Father/Mother)\n");

        // 1. δημιουργία ψεύτικων δεδομένων (mock data)
        Map<Integer, Person> idToPerson = new HashMap<>();
        Map<String, Integer> nameToId = new HashMap<>();

        // δημιουργούμε μια απλή οικογένεια: μπαμπάς, μαμά, γιος
        // person(id, name, gender, fatherId, motherId, spouseId)
        
        // μπαμπάς (ID: 1)
        Person dad = new Person(1, "Mpampas", "Male", -1, -1, 2);
        idToPerson.put(1, dad);
        
        // μαμά (ID: 2)
        Person mom = new Person(2, "Mama", "Female", -1, -1, 1);
        idToPerson.put(2, mom);
        
        // γιος (ID: 3) - γονείς ο 1 και η 2
        Person son = new Person(3, "Gios", "Male", 1, 2, -1);
        idToPerson.put(3, son);
        
        // άσχετος (ID: 4)
        Person stranger = new Person(4, "Xenos", "Male", -1, -1, -1);
        idToPerson.put(4, stranger);

        // αρχικοποίηση checker
        RelationshipChecker checker = new RelationshipChecker(idToPerson, nameToId);

        // εκτέλεση ελέγχων
        int passed = 0;
        int failed = 0;

        // έλεγχος isFather
        System.out.print("Checking isFather... ");
        
        // θετικό σενάριο: Ο 1 είναι πατέρας του 3
        boolean check1 = checker.isFather(1, 3); 
        // αρνητικό σενάριο: Ο 3 δεν είναι πατέρας του 1
        boolean check2 = !checker.isFather(3, 1);
        // αρνητικό σενάριο: Ο 4 (Άσχετος) δεν είναι πατέρας του 3
        boolean check3 = !checker.isFather(4, 3);
        
        if (check1 && check2 && check3) {
            System.out.println("PASS");
            passed++;
        } else {
            System.out.println("FAIL");
            System.out.println("   - Mpampas is father of Gios: " + check1);
            System.out.println("   - Gios is NOT father of Mpampas: " + check2);
            failed++;
        }

        // ελεγχος isMother
        System.out.print("Checking isMother... ");
        
        // θετικό σενάριο: Η 2 είναι μητέρα του 3
        boolean check4 = checker.isMother(2, 3);
        // αρνητικό σενάριο: Ο 1 (Μπαμπάς) δεν είναι μητέρα του 3
        boolean check5 = !checker.isMother(1, 3);
        
        if (check4 && check5) {
            System.out.println("PASS");
            passed++;
        } else {
            System.out.println("FAIL");
            failed++;
        }
        
        // σύνοψη
        System.out.println("\nTotal Tests: " + (passed + failed));
        System.out.println("Passed: " + passed);
        if (failed > 0) System.out.println("Failed: " + failed);
    }
}