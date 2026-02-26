import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Unit Tests for CSV Parser (Part C.4)
 * Tests: Record count, ID mapping, null handling
 */
public class TestCsvParser {
    
    public static void main(String[] args) {
        System.out.println("=== CSV Parser Unit Tests ===\n");
        
        // Load CSV
        CsvParser parser = new CsvParser();
        List<Person> peopleList = parser.loadPersons("persons.csv");
        
        // Create maps
        Map<Integer, Person> idToPerson = new HashMap<>();
        Map<String, Integer> nameToId = new HashMap<>();
        for (Person p : peopleList) {
            idToPerson.put(p.getId(), p);
            nameToId.put(p.getName(), p.getId());
        }
        
        // Run tests
        int passed = 0;
        int failed = 0;
        
        // TEST 1: Check total record count
        if (test1_RecordCount(peopleList)) passed++; else failed++;
        
        // TEST 2: Check specific ID to name mapping
        if (test2_IdMapping(idToPerson)) passed++; else failed++;
        
        // TEST 3: Check null/empty fields (-1 handling)
        if (test3_NullFields(idToPerson)) passed++; else failed++;
        
        // TEST 4: Check name search works
        if (test4_NameSearch(nameToId, idToPerson)) passed++; else failed++;
        
        // Summary
        System.out.println("\n=== Test Summary ===");
        System.out.println("✅ Passed: " + passed);
        System.out.println("❌ Failed: " + failed);
        System.out.println("Total: " + (passed + failed));
    }
    
    /**
     * TEST 1: Ελέγχει ότι φορτώθηκαν ακριβώς 25 εγγραφές
     * (το persons.csv έχει 25 γραμμές + 1 header = 26 συνολικά)
     */
    private static boolean test1_RecordCount(List<Person> peopleList) {
        System.out.print("TEST 1: Record count (expected 25)... ");
        
        int expected = 25;
        int actual = peopleList.size();
        
        if (actual == expected) {
            System.out.println("✅ PASSED (loaded " + actual + " records)");
            return true;
        } else {
            System.out.println("❌ FAILED (expected " + expected + " but got " + actual + ")");
            return false;
        }
    }
    
    /**
     * TEST 2: Ελέγχει ότι συγκεκριμένα IDs αντιστοιχούν σε σωστά ονόματα
     */
    private static boolean test2_IdMapping(Map<Integer, Person> idToPerson) {
        System.out.print("TEST 2: ID to name mapping... ");
        
        // Ελέγχουμε 3 συγκεκριμένα άτομα από το CSV
        boolean pass1 = checkPerson(idToPerson, 1, "Αυγουστίνος Καποδίστριας");
        boolean pass2 = checkPerson(idToPerson, 3, "Ιωάννης Καποδίστριας");
        boolean pass3 = checkPerson(idToPerson, 10, "Σοφία Καποδίστρια");
        
        if (pass1 && pass2 && pass3) {
            System.out.println("✅ PASSED (all IDs match expected names)");
            return true;
        } else {
            System.out.println("❌ FAILED (some IDs don't match)");
            return false;
        }
    }
    
    // Helper method για TEST 2
    private static boolean checkPerson(Map<Integer, Person> map, int id, String expectedName) {
        if (!map.containsKey(id)) {
            System.out.println("\n  ❌ ID " + id + " not found!");
            return false;
        }
        String actualName = map.get(id).getName();
        if (!actualName.equals(expectedName)) {
            System.out.println("\n  ❌ ID " + id + ": expected '" + expectedName + "' but got '" + actualName + "'");
            return false;
        }
        return true;
    }
    
    /**
     * TEST 3: Ελέγχει ότι κενά πεδία (father_id, mother_id, spouse_id) 
     * αποθηκεύονται σωστά ως -1
     */
    private static boolean test3_NullFields(Map<Integer, Person> idToPerson) {
        System.out.print("TEST 3: Null/empty field handling... ");
        
        // Άτομο 1: Αυγουστίνος Καποδίστριας (δεν έχει γονείς στο CSV)
        Person p1 = idToPerson.get(1);
        if (p1.getFatherId() != -1 || p1.getMotherId() != -1) {
            System.out.println("❌ FAILED (ID 1 should have father=-1, mother=-1)");
            return false;
        }
        
        // Άτομο 3: Ιωάννης Καποδίστριας (έχει γονείς, δεν έχει spouse)
        Person p3 = idToPerson.get(3);
        if (p3.getSpouseId() != -1) {
            System.out.println("❌ FAILED (ID 3 should have spouse=-1)");
            return false;
        }
        
        // Άτομο 10: Σοφία Καποδίστρια (έχει γονείς, δεν έχει spouse)
        Person p10 = idToPerson.get(10);
        if (p10.getFatherId() == -1 || p10.getMotherId() == -1) {
            System.out.println("❌ FAILED (ID 10 should have valid parent IDs)");
            return false;
        }
        if (p10.getSpouseId() != -1) {
            System.out.println("❌ FAILED (ID 10 should have spouse=-1)");
            return false;
        }
        
        System.out.println("✅ PASSED (null fields stored as -1)");
        return true;
    }
    
    /**
     * TEST 4: Ελέγχει ότι η αναζήτηση με όνομα δουλεύει
     */
    private static boolean test4_NameSearch(Map<String, Integer> nameToId, 
                                           Map<Integer, Person> idToPerson) {
        System.out.print("TEST 4: Name search functionality... ");
        
        String searchName = "Ιωάννης Καποδίστριας";
        
        if (!nameToId.containsKey(searchName)) {
            System.out.println("❌ FAILED (name '" + searchName + "' not found in map)");
            return false;
        }
        
        int id = nameToId.get(searchName);
        Person p = idToPerson.get(id);
        
        if (p == null || !p.getName().equals(searchName)) {
            System.out.println("❌ FAILED (name search returned wrong person)");
            return false;
        }
        
        System.out.println("✅ PASSED (name search works correctly)");
        return true;
    }
}

