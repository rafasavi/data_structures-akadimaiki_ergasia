
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * RelationshipCLI - Διεπαφή Γραμμής Εντολών
 * 
 * Χρήση από το τερματικό:
 *   java RelationshipCLI relation "Όνομα1" "Όνομα2"
 * 
 * Παραδείγματα:
 *   java RelationshipCLI relation "Ιωάννης Καποδίστριας" "Αυγουστίνος Καποδίστριας"
 *   java RelationshipCLI relation "Δημήτρης Καποδίστριας" "Ανδρέας Λυμπερόπουλος"
 */
public class RelationshipCLI {
    
    public static void main(String[] args) {
        // φόρτωση δεδομένων από το CSV
        CsvParser parser = new CsvParser();
        List<Person> peopleList = parser.loadPersons("persons.csv");
        
        if (peopleList.isEmpty()) {
            System.err.println("ΣΦΑΛΜΑ: Δεν ήταν δυνατή η φόρτωση του persons.csv");
            System.err.println("Βεβαιωθείτε ότι το αρχείο υπάρχει στον τρέχοντα φάκελο.");
            System.exit(1);
        }
        
        // δημιουργία maps (αντιστοιχίσεων)
        Map<Integer, Person> idToPerson = new HashMap<>();
        Map<String, Integer> nameToId = new HashMap<>();
        
        for (Person p : peopleList) {
            idToPerson.put(p.getId(), p);
            nameToId.put(p.getName(), p.getId());
        }
        
        // δημιουργία ελεγκτή συγγενικών σχέσεων
        RelationshipChecker checker = new RelationshipChecker(idToPerson, nameToId);
        
        // ανάλυση ορισμάτων γραμμής εντολών
        if (args.length < 3) {
            printUsage();
            System.exit(1);
        }
        
        String command = args[0];
        String nameA = args[1];
        String nameB = args[2];
        
        // ελεγχος εγκυρότητας εντολής
        if (!command.equals("relation")) {
            System.err.println("ΣΦΑΛΜΑ: Άγνωστη εντολή '" + command + "'");
            printUsage();
            System.exit(1);
        }
        
        // εκτέλεση ερωτήματος σχέσης
        String result = checker.relation(nameA, nameB);
        System.out.println(result);
    }
    
       // εμφανίζει οδηγίες χρήσης
    private static void printUsage() {
        System.out.println();
        System.out.println("Έλεγχος Συγγενικών Σχέσεων - CLI");
        System.out.println();
        System.out.println("Χρήση (αν είστε έξω από το φάκελο src):");
        System.out.println("  java src.RelationshipCLI relation \"Όνομα1\" \"Όνομα2\"");
        System.out.println();
        System.out.println("Παραδείγματα:");
        System.out.println("  java src.RelationshipCLI relation \"Ιωάννης Καποδίστριας\" \"Αυγουστίνος Καποδίστριας\"");
        System.out.println();
    }
}
