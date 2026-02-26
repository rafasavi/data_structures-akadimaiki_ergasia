# data_structures-akadimaiki_ergasia
# CN5005-DSA-Project

- **Όνομα:** Σαββίδης Ραφαήλ

- **Ημερομηνία:** 17 Ιανουαρίου 2026

## Setup & Build
- Compile: `javac *.java` (Java 17+)
- Requires: persons.csv in same folder.

## Run AVL/BST Demo
`java Main`
- Shows inserts/duplicates, traversals (in/pre/post-order), delete (reduces count), changeKey with rotations.

## Run CLI Queries
`java RelationshipCLI relation "Name1" "Name2"`
- Examples: `java RelationshipCLI relation "Mpampas" "Gios"` (father-son)
- Loads persons.csv, checks relations (father/mother/sibling/cousin/spouse etc.).

## Manual Unit Tests
- CSV Parser: `java TestCsvParser` (checks records=25, IDs, nulls=-1, name search).
- Relationships: `java TestRelationshipChecker` (isFather/isMother etc. with mocks).

## Structure
- Trees: BinarySearchTree.java (base), AVLTree.java (balancing LL/RR/LR/RL, changeKey).
- Data: CsvParser.java, Person.java.
- Queries: RelationshipChecker.java (basic), ComplexRelationshipChecker.java (extended: half-sibs/in-laws/spouse).
- Node.java shared.

## Notes
- persons.csv: 25+ records, UTF-8, id,name,gender,fatherId,motherId,spouseId (-1 null).
- AVL: O(log n) ops, height-balanced.
- Tests manual (no Maven/JUnit).
