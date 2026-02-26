# Προκλήσεις

## Τι μάθαμε
-  AVL rotations (LL/RR/LR/RL) κατά την διάρκεια insert/delete/changeKey - υλοποιήθηκαν έλεγχοι παράγοντα ισορροπίας και 4 τύποι περιστροφών.
- Τα διπλοτυπα αντιμετωπίστηκαν μέσω μετρητή στο Node η διαγραφή μειώνει πρώτα τον μετρητή αν είναι >1.
- Γενεαλογία: Ανάλυση αρχείου CSV UTF-8 με τιμές -1 για ανύπαρκτους γονείς/συζύγους, χρήση HashMap για αναζήτηση ID/ονόματος σε χρόνο O(1).

## Σχεδιαστικές επιλογές
- Χειροκίνητα τεστ για απλότητα (main methods με passed / failed prints), αντί JUnit / Maven.
- Επέκταση των σχεσεων ComplexRelationshipChecker: half-siblings (shared parent), in-laws (spouse's parent / sibling), spouse.
- CLI ξεχωριστή για D.5,ενώ η Main χρησιμοποιήθηκε ως demo.

## Data Provenance (persons.csv)
- Format: UTF-8 CSV με header id,name,gender,fatherId,motherId,spouseId.
- Χρησιμοποιηθηκαν για την σύνδεση CW2: tree-based queries προσομοιώνουν πραγματικές εφαρμογές γενεαλογίας.

## Συνδεση με το CW2
- Βασίζεται στις δομές δεδομενων CW2, εφαρμοζοντας BST/AVL σε πραγματικό σύνολο δεδομένων για relation queries.
