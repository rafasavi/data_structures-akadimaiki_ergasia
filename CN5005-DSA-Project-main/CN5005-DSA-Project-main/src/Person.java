public class Person {

    private int id;
    private String name;
    private String gender;


    // αν δεν υπάρχει γονιός ή σύζηγος, θα έχουν την τιμή -1
    private int fatherId;
    private int motherId;
    private int spouseId;

    // constructor, δημιουργεί ένα αντικείμενο person
    public Person(int id, String name, String gender, int fatherId, int motherId, int spouseId) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.fatherId = fatherId;
        this.motherId = motherId;
        this.spouseId = spouseId;
    }

    // getters, για να παίρνουμε τα στοιχεία

    public int getId() { return id; }

    public String getName() { return name; }

    public String getGender() { return gender; }

    public int getFatherId() { return fatherId; }

    public int getMotherId() { return motherId; }

    public int getSpouseId() { return spouseId; }

    // helper method toString
    // μας βοηθάει να τυπώσουμε εύκολα τον άνθρωπο στη κονσόλα για έλεγχο
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", fatherId=" + fatherId +
                ", motherId=" + motherId +
                ", spouseId=" + spouseId +
                '}';
    }
}