import java.util.Map;


   //RelationshipChecker μέρος D & E
  // ελέγχει τις σχέσεις μεταξύ ατόμων που φορτώθηκαν από το persons.csv

public class RelationshipChecker {

    private Map<Integer, Person> idToPerson;
    private Map<String, Integer> nameToId;

    // αναφορά στον ελεγκτή σύνθετων σχέσεων (composition)
    private ComplexRelationshipChecker complexChecker;

      // constructor, χρειάζεται τα δύο maps που δημιουργήθηκαν στην main.
     // αρχικοποιεί τον ComplexRelationshipChecker
    public RelationshipChecker(Map<Integer, Person> idToPerson,
                               Map<String, Integer> nameToId) {
        this.idToPerson = idToPerson;
        this.nameToId = nameToId;

        // αρχικοποίηση του complex checker εδώ.
        // περνάμε το map idToPerson ώστε να μπορεί να βρει τα στοιχεία.
        this.complexChecker = new ComplexRelationshipChecker(idToPerson);
    }

    // D1  Father / Mother

    public boolean isFather(int idA, int idB) {
        // έλεγχος ότι υπάρχουν και τα δύο IDs
        if (!idToPerson.containsKey(idA) || !idToPerson.containsKey(idB)) {
            return false;
        }
        Person personB = idToPerson.get(idB);
        return personB.getFatherId() == idA;
    }

    public boolean isMother(int idA, int idB) {
        if (!idToPerson.containsKey(idA) || !idToPerson.containsKey(idB)) {
            return false;
        }
        Person personB = idToPerson.get(idB);
        return personB.getMotherId() == idA;
    }

    // D2 - Child / Sibling

    public boolean isChild(int idA, int idB) {
        // A είναι παιδί του B αν ο B είναι πατέρας ή μητέρα του A
        return isFather(idB, idA) || isMother(idB, idA);
    }

    public boolean isSibling(int idA, int idB) {
        if (!idToPerson.containsKey(idA) || !idToPerson.containsKey(idB)) return false;
        if (idA == idB) return false;

        Person personA = idToPerson.get(idA);
        Person personB = idToPerson.get(idB);

        int fatherA = personA.getFatherId();
        int motherA = personA.getMotherId();
        int fatherB = personB.getFatherId();
        int motherB = personB.getMotherId();

        // αδέλφια = τουλάχιστον ένας κοινός γονέας
        boolean commonFather = (fatherA != -1) && (fatherA == fatherB);
        boolean commonMother = (motherA != -1) && (motherA == motherB);

        return commonFather || commonMother;
    }

    // D3 - Grandparent / Grandchild

    public boolean isGrandparent(int idA, int idB) {
        if (!idToPerson.containsKey(idA) || !idToPerson.containsKey(idB)) return false;

        Person personB = idToPerson.get(idB);
        int fatherB = personB.getFatherId();
        int motherB = personB.getMotherId();

        boolean isGrandparentThroughFather = false;
        if (fatherB != -1) {
            isGrandparentThroughFather = isFather(idA, fatherB) || isMother(idA, fatherB);
        }

        boolean isGrandparentThroughMother = false;
        if (motherB != -1) {
            isGrandparentThroughMother = isFather(idA, motherB) || isMother(idA, motherB);
        }

        return isGrandparentThroughFather || isGrandparentThroughMother;
    }

    public boolean isGrandchild(int idA, int idB) {
        return isGrandparent(idB, idA);
    }

    // D4 - First Cousins

    public boolean isFirstCousin(int idA, int idB) {
        if (!idToPerson.containsKey(idA) || !idToPerson.containsKey(idB)) return false;
        if (idA == idB) return false;

        Person personA = idToPerson.get(idA);
        Person personB = idToPerson.get(idB);

        int fatherA = personA.getFatherId();
        int motherA = personA.getMotherId();
        int fatherB = personB.getFatherId();
        int motherB = personB.getMotherId();

        boolean case1 = false, case2 = false, case3 = false, case4 = false;

        if (fatherA != -1 && fatherB != -1) case1 = isSibling(fatherA, fatherB);
        if (fatherA != -1 && motherB != -1) case2 = isSibling(fatherA, motherB);
        if (motherA != -1 && fatherB != -1) case3 = isSibling(motherA, fatherB);
        if (motherA != -1 && motherB != -1) case4 = isSibling(motherA, motherB);

        return case1 || case2 || case3 || case4;
    }

    // D5 & E - high-level relation() method


      // high-level μέθοδος που ελέγχει όλες τις σχέσεις.
     // ελέγχει και τις σύνθετες σχέσεις (μέρος E)
    public String relation(String nameA, String nameB) {
        // μετατροπή ονομάτων σε IDs
        if (!nameToId.containsKey(nameA)) {
            return "Σφάλμα: Το όνομα '" + nameA + "' δεν βρέθηκε στα δεδομένα.";
        }
        if (!nameToId.containsKey(nameB)) {
            return "Σφάλμα: Το όνομα '" + nameB + "' δεν βρέθηκε στα δεδομένα.";
        }

        int idA = nameToId.get(nameA);
        int idB = nameToId.get(nameB);

        // έλεγχος σχέσεων (με σειρά προτεραιότητας)

        // ίδιο άτομο
        if (idA == idB) {
            return nameA + " και " + nameB + " είναι το ίδιο άτομο.";
        }

        // σύζυγος (μέρος E - σύνθετες)
        if (complexChecker.isSpouse(idA, idB)) {
            return nameA + " είναι σύζυγος του/της " + nameB + ".";
        }

        // πατέρας/μητέρα
        if (isFather(idA, idB)) return nameA + " είναι πατέρας του/της " + nameB + ".";
        if (isMother(idA, idB)) return nameA + " είναι μητέρα του/της " + nameB + ".";

        // παιδί
        if (isChild(idA, idB)) return nameA + " είναι παιδί του/της " + nameB + ".";

        // ετεροθαλή αδέλφια (μέρος E - σύνθετες)
        // πρέπει να ελεγχθεί πριν το isSibling
        // επειδή η isSibling επιστρέφει true και για τα ετεροθαλή
        if (complexChecker.isHalfSibling(idA, idB)) {
            return nameA + " και " + nameB + " είναι ετεροθαλή αδέλφια.";
        }

        // 6. αδέλφια
        if (isSibling(idA, idB)) {
            return nameA + " και " + nameB + " είναι αδέλφια.";
        }

        // παππούς/γιαγιά
        if (isGrandparent(idA, idB)) return nameA + " είναι παππούς/γιαγιά του/της " + nameB + ".";

        // εγγόνι
        if (isGrandchild(idA, idB)) return nameA + " είναι εγγόνι του/της " + nameB + ".";

        // πρώτα ξαδέρφια
        if (isFirstCousin(idA, idB)) {
            return nameA + " και " + nameB + " είναι πρώτα ξαδέρφια.";
        }

        // καμία σχέση
        return nameA + " και " + nameB + " δεν σχετίζονται (ή η σχέση δεν υποστηρίζεται).";
    }
}