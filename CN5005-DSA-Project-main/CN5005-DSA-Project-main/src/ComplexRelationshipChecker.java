
import java.util.Map;

public class ComplexRelationshipChecker {

    private Map<Integer, Person> idToPerson;

    // constructor: χρειαζόμαστε μόνο το map με τα IDs για να βρίσκουμε τα πρόσωπα
    public ComplexRelationshipChecker(Map<Integer, Person> idToPerson) {
        this.idToPerson = idToPerson;
    }

    // spouse check
    // ελέγχει αν ο A είναι σύζυγος του B
    public boolean isSpouse(int idA, int idB) {
        // έλεγχος εγκυρότητας
        if (!idToPerson.containsKey(idA) || !idToPerson.containsKey(idB)) {
            return false;
        }

        Person pA = idToPerson.get(idA);
        
        // ελέγχουμε αν το spouseId του A είναι ίσο με το id του B
        return pA.getSpouseId() == idB;
    }

    // half siblings check
    // ετεροθαλή = έχουν έναν κοινό γονέα
    public boolean isHalfSibling(int idA, int idB) {
        // βασικοί έλεγχοι (να υπάρχουν τα άτομα και να μην είναι το ίδιο άτομο)
        if (!idToPerson.containsKey(idA) || !idToPerson.containsKey(idB) || idA == idB) {
            return false;
        }

        Person pA = idToPerson.get(idA);
        Person pB = idToPerson.get(idB);

        // παίρνουμε τους γονείς του A
        int fatherA = pA.getFatherId();
        int motherA = pA.getMotherId();

        // παίρνουμε τους γονείς του B
        int fatherB = pB.getFatherId();
        int motherB = pB.getMotherId();

        // έλεγχος: κοινός πατέρας (και να είναι γνωστός, δηλαδη != -1)
        boolean sameFather = (fatherA != -1) && (fatherA == fatherB);

        // έλεγχος: κοινή μητέρα (και να είναι γνωστή, δηλαδη != -1)
        boolean sameMother = (motherA != -1) && (motherA == motherB);

        // λογική XOR:
        // Θέλουμε να ισχύει είτε μόνο ο κοινός πατέρας, είτε μονό η κοινή μητέρα.
        // Αν ισχύουν και τα δύο (sameFather && sameMother), τότε είναι κανονικά αδέλφια (όχι ετεροθαλή).
        
        if (sameFather && !sameMother) {
            return true; // κοινός πατέρας, διαφορετική μητέρα
        }

        if (!sameFather && sameMother) {
            return true; // διαφορετικός πατέρας, κοινή μητέρα
        }

        return false;
    }
}