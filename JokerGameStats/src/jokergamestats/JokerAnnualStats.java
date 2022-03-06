/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jokergamestats;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import static java.lang.Integer.parseInt;
import java.text.DecimalFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * @author Vasilis Kerestetzis
 * @author Giorgos Kiopektzis
 * @author Fani Kontou
 * @author Giannis Sykaras
 */

public class JokerAnnualStats extends javax.swing.JFrame {
    static String year = null;

    
    /**
     * Creates new form JokerAnnualStats
     * @param year
     */
    public JokerAnnualStats(String year) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        jLabel5.setText(year);
        
        String month = null;
        int totaldraws = 0;
        double totalmoney = 0;
        String totalmoneySTR = null;
        
        /*Δημιουργώ τα διάφορα string array που χρειάζομαι για να καλύψω την απαίτηση
        * των δώδεκα μηνών του έτους
        */
        String[] DateStart = new String[12];
        String[] DateEnd = new String[12];
        String[] URL = new String[12];
        String[] SumDraws = new String[12];
        String[] SumMoney = new String[12];
        String[] SumJackpot = new String[12];
                
        /* τρέχω την επανάλληψη για τους 12 μήνες "γεμίζοντας" ταυτόχρονα
        τα string aray με τις αντίστοιχες τιμές
        */
        
        int i = 0;
        while (i <12){
            month = String.valueOf(i+1);
            month = (i<9 ? "-0":"-")+month;
            DateStart[i] = (year + month + "-01");
                                    
            switch (month){
                case "-04":
                case "-06":
                case "-09":
                case "-11":
                    DateEnd[i] = (year + month + "-30");
                    break;
                
                /*Ελέγχω τον μήνα Φεβροάριο για δίσεκτο έτος ή όχι,
                 *Ελέγχοντας αν το υπόλοιπο της διέρεσης με 4 είναι 0.
                 *Προφανώς θα υπήρχε και πιο άμεσος τρόπος ελέγχου...
                */
                case "-02":
                int yearint = parseInt(year);
                if (((yearint % 4 == 0)))
                    DateEnd[i] = (year + month + "-29");
                else
                    DateEnd[i] = (year + month + "-28");
                break;                    
                
                default:
                    DateEnd[i]= (year + month + "-31");
            }
            //Δημιουργώ ως String τα url που θα καλέσω και τα προσθέτω στο Array List
            URL[i] = ("https://api.opap.gr/draws/v3.0/5104/draw-date/" + DateStart[i] + "/" + DateEnd[i] + "/");

            //Δημιουργώ ένα αντικείμενο OkHttpClient 
            OkHttpClient client = new OkHttpClient();
        
            //Δημιουργώ ένα αντικείμενο Request με όρισμα το url του μήνα (i) που θα καλέσουμε 
            Request request = new Request.Builder().url(URL[i]).build();
            
            /*Ζητώ το url της συγκεκριμένης επανάλληψης και 
             *ελέγχω εάν φέρνει αποτελέσματα
             */
            try (okhttp3.Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {

                //Καταχωρώ σε ένα String το αποτέλεσμα
                String responseString = response.body().string();

                //Δημιουργώ ένα αντικείμενο GsonBuilder
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();

                //Πέρνώ τα αποτελέσματα σε JsonObject και δημιουργώ ένα JsonArray το αρχικό "content"
                JsonObject json = gson.fromJson(responseString, JsonObject.class);
                JsonArray content = json.get("content").getAsJsonArray();
                System.out.println(json);
                System.out.println("*****************************");
                
                //Αρχικοποιώ τους καταμετρητές κάθε μήνα
                int draws = 0;
                double money = 0;
                int jackpot = 0;

                //Διαπερνάω τα περιεχόμενα του "content"
                for (JsonElement jsonElement : content) {

                    //Δημιουργώ ένα JsonObject "prizeCategories" και δημιουργούμε ένα JsonArray το "prizeCategories"
                    JsonObject jsonprizeCategories = jsonElement.getAsJsonObject();
                    JsonArray prizeCategories = jsonprizeCategories.get("prizeCategories").getAsJsonArray();

                    //Διαπερνάω τα περιεχόμενα του "prizeCategories"
                    for (JsonElement prizeCategory : prizeCategories) {
                        //Δημιουργώ ένα JsonObject για κάθε κατηγορία (0,1,2,3,4,5,6,7)
                        JsonObject category = prizeCategory.getAsJsonObject();

                        //Εάν έχω νικητές υπολογίζω πόσα χρήματα διανεμήθηκαν
                        if (category.get("winners").getAsInt() > 0) {
                            money = money + category.get("distributed").getAsDouble();
                            
                        }
                        //Εάν έχω ΤΖΑΚ-ΠΟΤ αυξάνω τα ΤΖΑΚ-ΠΟΤ που έγιναν
                        if (category.get("jackpot").getAsDouble() > 0) {
                            jackpot++;
                        }

                    }
                    //Αυξάνω τα παιχνίδια που έγιναν
                    draws++;
                    totaldraws++;
                    
                    //Ενημερώνω τα ArrayList για τον συγκεκριμένο μήνα
                    SumDraws[i] =  String.valueOf(draws);
                    DecimalFormat df = new DecimalFormat("#,##0.00");
                    SumMoney[i] = String.valueOf(df.format(money)) + " EUR";
                    SumJackpot[i] = String.valueOf(jackpot);
                    totalmoneySTR = String.valueOf(df.format(totalmoney) + " EUR");
                }   
                totalmoney+=money;
                DecimalFormat df = new DecimalFormat("#,##0.00");
                totalmoneySTR = String.valueOf(df.format(totalmoney) + " EUR");
            } 
        } catch (Exception ex) {
            // Ενημερωτικό μύνημα
            JOptionPane.showMessageDialog(null, "Δεν βρήκαμε δεδομένα", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
        }
            
            i++;            
        }
                       
        DefaultTableModel model1 = new DefaultTableModel();
        //Ορίζω την επικεφαλίδα, εισάγουμε τους πίνακες numbers και times και ενημερώνω τον jTable1
        model1.setColumnIdentifiers(new String[]{"Ιανουάριος", "Φεβρουάριος", "Μάρτιος",
            "Απρίλιος", "Μάιος", "Ιούνιος", "Ιούλιος", "Αύγουστος", "Σεπτέμβριος", "Οκτώβριος", "Νοέμβριος", "Δεκέμβριος"});
        model1.addRow(SumDraws);
        model1.addRow(SumMoney);
        model1.addRow(SumJackpot);
        jTable1.setModel(model1);
        
        jLabel9.setText(String.valueOf(totaldraws));
        jLabel10.setText(totalmoneySTR);
            
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel4.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("ΠΡΟΒΟΛΗ ΔΕΔΟΜΕΝΩΝ ΤΖΟΚΕΡ ΑΝΑ ΜΗΝΑ ΓΙΑ ΤΟ ΕΤΟΣ: ");

        jLabel5.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("YEAR");

        jTable1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Ιανουάριος", "Φεβρουάριος", "Μάρτιος", "Απρίλιος", "Μάιος", "Ιούνιος", "Ιούλιος", "Αύγουστος", "Σεπτέμβριος", "Οκτώβριος", "Νοέμβριος", "Δεκέμβριος"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.setMinimumSize(new java.awt.Dimension(180, 50));
        jTable1.setPreferredSize(new java.awt.Dimension(900, 75));
        jTable1.setRowHeight(25);
        jTable1.setRowSelectionAllowed(false);
        jTable1.setSurrendersFocusOnKeystroke(true);
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Κληρώσεις");
        jLabel1.setPreferredSize(new java.awt.Dimension(50, 15));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Κέρδη:");
        jLabel2.setMaximumSize(new java.awt.Dimension(50, 15));
        jLabel2.setMinimumSize(new java.awt.Dimension(50, 15));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Τζακποτ:");

        jButton4.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        jButton4.setText("Επιστροφή στο Τζόκερ");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        jButton5.setText("Επιστροφή στην επιλογή έτους");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(240, 24, 24));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/tzoker.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Σύνολο Κληρώσεων Έτους:");
        jLabel7.setPreferredSize(new java.awt.Dimension(50, 15));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Σύνολο Κερδών Έτους:");
        jLabel8.setPreferredSize(new java.awt.Dimension(50, 15));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setPreferredSize(new java.awt.Dimension(50, 15));

        jLabel10.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setPreferredSize(new java.awt.Dimension(50, 15));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1182, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(515, 515, 515))
            .addGroup(layout.createSequentialGroup()
                .addGap(417, 417, 417)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton4))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // Μεταφερομαστε στην αρχικη σελιδα του JOKER
        JokerMainScreen jokerMainScreen = new JokerMainScreen();
        jokerMainScreen.setVisible(true);
        //Κλεισιμο παράθυρου
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // Μεταφερομαστε στην αρχικη σελιδα Επιλογής έτους
        JokerSelectYear jokerSelectYear = new JokerSelectYear();
        jokerSelectYear.setVisible(true);
        //Κλεισιμο παράθυρου
        dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(JokerAnnualStats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JokerAnnualStats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JokerAnnualStats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JokerAnnualStats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new JokerAnnualStats(year).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton jButton4;
    javax.swing.JButton jButton5;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel10;
    javax.swing.JLabel jLabel2;
    javax.swing.JLabel jLabel3;
    javax.swing.JLabel jLabel4;
    javax.swing.JLabel jLabel5;
    javax.swing.JLabel jLabel6;
    javax.swing.JLabel jLabel7;
    javax.swing.JLabel jLabel8;
    javax.swing.JLabel jLabel9;
    javax.swing.JScrollPane jScrollPane1;
    javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
