/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 *
 * @author user
 */
public class JokerJFrame2_2 extends javax.swing.JFrame {

    static String date;

    /**
     * Creates new form JokerJFrame2_2
     */
    public JokerJFrame2_2(String datein) throws ParseException {
        initComponents();

        //Καταχωρώ τον μήνα και τον χρόνο
        String month = datein.substring(datein.length() - 2);
        String year = datein.substring(0, 4);

        //Προσθέτω και την αρχική ημέρα του μήνα
        date = datein + "-01";

        //Υπολογίζω την τελευταία ημέρα του μήνα
        LocalDate lastDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        lastDate = lastDate.withDayOfMonth(
                lastDate.getMonth().length(lastDate.isLeapYear()));
        System.out.println(date);
        System.out.println(lastDate);

        //Εμφανίζω στο GUI τον μήνα
        switch (month) {
            case "01":
                month = "Ιανουάριο " + year;
                break;
            case "02":
                month = "Φεβρουάριο " + year;
                break;
            case "03":
                month = "Μάρτιο " + year;
                break;
            case "04":
                month = "Απρίλιο " + year;
                break;
            case "05":
                month = "Μαϊο " + year;
                break;
            case "06":
                month = "Ιούνιο " + year;
                break;
            case "07":
                month = "Ιούλιο " + year;
                break;
            case "08":
                month = "Αύγουστο " + year;
                break;
            case "09":
                month = "Σεπτέμβριο " + year;
                break;
            case "10":
                month = "Οκτώμβριο " + year;
                break;
            case "11":
                month = "Νοέμβριο " + year;
                break;
            case "12":
                month = "Δεκέμβριο " + year;
                break;
        }
        jLabel2.setText(month);

        //Δημιουργώ ως String το url που θα καλέσω
        String urlToCall = "https://api.opap.gr/draws/v3.0/5104/draw-date/" + date + "/" + lastDate + "/";

        //Δημιουργούμε ένα αντικείμενο OkHttpClient 
        OkHttpClient client = new OkHttpClient();
        //Δημιουργούμε ένα αντικείμενο Request με όρισμα το url που θα καλέσουμε 
        Request request = new Request.Builder().url(urlToCall).build();

        //Ξεκινάμε και ζητάμε το url και ελέγχουμε εάν μας φέρνει αποτελέσματα
        try (okhttp3.Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {

                //Καταχωρούμε σε ένα String το αποτέλεσμα
                String responseString = response.body().string();

                //Δημιουργούμε ένα αντικείμενο GsonBuilder
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();

                //Πέρνουμε τα αποτελέσματα σε JsonObject και δημιουργούμε ένα JsonArray το αρχικό "content"
                JsonObject json = gson.fromJson(responseString, JsonObject.class);
                JsonArray content = json.get("content").getAsJsonArray();
                System.out.println(json);
                System.out.println("*****************************");

                //Πόσα παιχνίδια έγιναν
                int games = 0;
                //Πόσα χρήματα διανεμήθηκαν
                double money = 0;
                // Πόσα ΤΖΑΚ-ΠΟΤ έγιναν
                int jackpot = 0;

                //Διαπερνάω τα περιεχόμενα του "content"
                for (JsonElement jsonElement : content) {

                    //Δημιουργώ ένα JsonObject "prizeCategories" και δημιουργούμε ένα JsonArray το "prizeCategories"
                    JsonObject jsonprizeCategories = jsonElement.getAsJsonObject();
                    JsonArray prizeCategories = jsonprizeCategories.get("prizeCategories").getAsJsonArray();

                    //Διαπερνάω τα περιεχόμενα του "prizeCategories"
                    for (JsonElement prizeCategorie : prizeCategories) {
                        //Δημιουργώ ένα JsonObject για κάθε κατηγορία (0,1,2,3,4,5,6,7)
                        JsonObject categorie = prizeCategorie.getAsJsonObject();
                        System.out.println(categorie.get("distributed"));

                        //Εάν έχω νικητές υπολογίζω πόσα χρήματα διανεμήθηκαν
                        if (categorie.get("winners").getAsInt() > 0) {
                            money = money + categorie.get("distributed").getAsDouble();
                        }
                        //Εάν έχω ΤΖΑΚ-ΠΟΤ αυξάνω τα ΤΖΑΚ-ΠΟΤ που έγιναν
                        if (categorie.get("jackpot").getAsDouble() > 0) {
                            jackpot++;
                        }

                    }
                    //Αυξάνω τα παιχνίδια έγιναν
                    games++;
                }
                //Ενημερώνω τις τιμές Πόσα παιχνίδια έγιναν, Πόσα χρήματα διανεμήθηκαν, Πόσα ΤΖΑΚ-ΠΟΤ έγιναν
                jTextField1.setText(String.valueOf(games));
                //2 δεκαδικά και τελείες
                DecimalFormat df = new DecimalFormat("#,##0.00");
                jTextField2.setText(String.valueOf(df.format(money)));
                jTextField3.setText(String.valueOf(jackpot));
            }
        } catch (Exception ex) {
            // Ενημερωτικό μύνημα
            JOptionPane.showMessageDialog(null, "Δεν βρήκαμε δεδομένα", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("2.2 Συγκεντρωτικά δεδομένα για τον μήνα");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("date");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("1. Παιχνίδια που έγιναν");

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("2. Χρήματα που διανεμήθηκαν");

        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("3. ΤΖΑΚ - ΠΟΤ που έγιναν");

        jTextField3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(jTextField1)
                            .addComponent(jTextField3))))
                .addContainerGap(240, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(178, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JokerJFrame2_2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JokerJFrame2_2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JokerJFrame2_2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JokerJFrame2_2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new JokerJFrame2_2(date).setVisible(true);
                } catch (ParseException ex) {
                    System.out.println(ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
