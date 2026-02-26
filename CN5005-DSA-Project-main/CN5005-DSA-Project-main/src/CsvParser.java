import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {
    // διαβάζει το αρχείο csv και επιστρέφει λίστα με αντικείμενα Person

    public List<Person> loadPersons(String filePath) {
        List<Person> peopleList = new ArrayList<>();
        String line = "";

        // χρησιμοποιούμε try-with-resources για να κλείσει αυτόματα το αρχείο
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            // διαβάζουμε την πρώτη γραμμή και την αγνοούμε
            // αν το αρχείο είναι κενό, σταματάμε
            if ((line = br.readLine()) == null) {
                return peopleList;
            }
                // loop για τις υπόλοιπες γραμμές
                // όσο η γραμμή δεν είναι null, συνεχίζουμε
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",", -1);


                    // έλεγχος ασφαλείας, πρέπει να έχουμε έξι στήλες
                    if (values.length >= 6) {
                        try {
                            // καθαρίζουμε τα δεδομένα από τυχόν κενά διαστήματα
                            String idStr = values[0].trim();
                            String name = values[1].trim();
                            String gender = values[2].trim();
                            String fatherIdStr = values[3].trim();
                            String motherIdStr = values[4].trim();
                            String spouseIdStr = values[5].trim();

                            // μετατροπή των String σε int
                            int id = parseIntOrMinusOne(idStr);
                            int fatherId = parseIntOrMinusOne(fatherIdStr);
                            int motherId = parseIntOrMinusOne(motherIdStr);
                            int spouseId = parseIntOrMinusOne(spouseIdStr);

                            // δημιουργία του αντικειμένου person
                            Person p = new Person(id, name, gender, fatherId, motherId, spouseId);

                            // προσθήκη στη λίστα
                            peopleList.add(p);
                        } catch (NumberFormatException e) {
                            System.err.println("Σφάλμα στη γραμμή (λάθος αριθμός): " + line);
                        }
                    }
                }
            } catch(IOException e){
                e.printStackTrace();
            }
            return peopleList;
        }


        // helper method, μετατρέπει String σε int
// αν το String είναι κενό ή null, επιστρέφει -1
        private int parseIntOrMinusOne (String value){
            if (value == null || value.isEmpty()) {
                return -1;
            }
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                // αν για κάποιο λόγο δεν είναι αριθμός, επιστρέφουμε -1
                return -1;
            }
        }
    }



