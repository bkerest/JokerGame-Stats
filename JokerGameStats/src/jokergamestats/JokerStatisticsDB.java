/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jokergamestats;

import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JFrame;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 * @author Vasilis Kerestetzis
 * @author Giorgos Kiopektzis
 * @author Fani Kontou
 * @author Giannis Sykaras
 */

public class JokerStatisticsDB extends javax.swing.JFrame {
    
    //Entity Manager & Controllers
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JokerGameStatsPU");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    DrawsJpaController djc = new DrawsJpaController(emf);
    PrizecategoriesJpaController pjc = new PrizecategoriesJpaController(emf);   

    /** Creates new form JokerStatisticsDB */
    public JokerStatisticsDB() {
        initComponents();        
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(984, 600));

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ΠΡΟΒΟΛΗ ΣΤΑΤΙΣΤΙΚΩΝ ΣΤΟΙΧΕΙΩΝ ΚΛΗΡΩΣΕΩΝ ΣΕ ΓΡΑΦΙΚΗ ΜΟΡΦΗ");

        jTable1.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "1ος Αριθμός", "2ος Αριθμός", "3ος Αριθμός", "4ος Αριθμός", "5ος Αριθμός"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        jTable2.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "1ος Αριθμός", "2ος Αριθμός", "3ος Αριθμός", "4ος Αριθμός", "5ος Αριθμός"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
            jTable2.getColumnModel().getColumn(2).setResizable(false);
            jTable2.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel2.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        jLabel2.setText("ΣΥΧΝΟΤΗΤΑ ΕΜΦΑΝΙΣΗΣ ΑΡΙΘΜΩΝ ΤΖΟΚΕΡ");

        jLabel3.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        jLabel3.setText("ΣΥΧΝΟΤΗΤΑ ΕΜΦΑΝΙΣΗΣ ΑΡΙΘΜΩΝ");

        jLabel4.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        jLabel4.setText("ΑΡΙΘΜΟΙ");

        jLabel5.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        jLabel5.setText("ΑΡΙΘΜΟΙ");

        jLabel6.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        jLabel6.setText("ΣΥΧΝΟΤΗΤΑ");

        jLabel7.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        jLabel7.setText("ΣΥΧΝΟΤΗΤΑ");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "5+1", "5", "4+1", "4", "3+1", "3", "2+1", "1+1"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setResizable(false);
            jTable3.getColumnModel().getColumn(1).setResizable(false);
            jTable3.getColumnModel().getColumn(2).setResizable(false);
            jTable3.getColumnModel().getColumn(3).setResizable(false);
            jTable3.getColumnModel().getColumn(4).setResizable(false);
            jTable3.getColumnModel().getColumn(5).setResizable(false);
            jTable3.getColumnModel().getColumn(6).setResizable(false);
            jTable3.getColumnModel().getColumn(7).setResizable(false);
        }

        jLabel8.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        jLabel8.setText("ΜΕΣΟΣ ΟΡΟΣ ΚΕΡΔΩΝ ΑΝΑ ΚΑΤΗΓΟΡΙΑ");

        jButton1.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        jButton1.setText("Προβολή");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        jButton7.setText("Επιστροφή");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(240, 24, 24));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/tzoker.png"))); // NOI18N

        jButton2.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        jButton2.setText("Διαχείριση δεδομένων ΤΖΟΚΕΡ");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        jButton3.setText("Προβολή Γραφήματος");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        jButton4.setText("Προβολή Γραφήματος");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(197, 197, 197))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1)
                                    .addComponent(jButton7))
                                .addGap(156, 156, 156)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)))
                            .addComponent(jButton2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(jLabel2))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(214, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(429, 429, 429)
                        .addComponent(jLabel8))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(444, 444, 444)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel3))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addGap(57, 57, 57)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel6)
                            .addGap(6, 6, 6))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(18, 41, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)))
                .addGap(3, 3, 3)
                .addComponent(jButton4)
                .addGap(30, 30, 30)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // Μεταφερομαστε στην αρχικη σελιδα του JOKER
        JokerStatistic jokerStatistic = new JokerStatistic();
        jokerStatistic.setVisible(true);
        //Κλεισιμο παράθυρου
        dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        /*Δημιοργώ το Query όπου η SQL θα κάνει την "δουλειά"
         *του αθροίσματος
        */
        // Συχνότητας εμφάνισης αριθμών (πέντε πιο συχνά εμφανιζόμενοι αριθμοί)
        Query query = em.createNativeQuery("SELECT NUMBERS.N, SUM(NUMBERS.C) AS COUNT_TOTAL\n"
                + "FROM\n"
                + "(\n"
                + "SELECT WINNINGNO1 AS N, COUNT(WINNINGNO1) AS C FROM DRAWS GROUP BY WINNINGNO1\n"
                + "UNION ALL\n"
                + "SELECT WINNINGNO2 AS N, COUNT(WINNINGNO2) AS C FROM DRAWS GROUP BY WINNINGNO2\n"
                + "UNION ALL\n"
                + "SELECT WINNINGNO3 AS N, COUNT(WINNINGNO3) AS C FROM DRAWS GROUP BY WINNINGNO3\n"
                + "UNION ALL\n"
                + "SELECT WINNINGNO4 AS N, COUNT(WINNINGNO4) AS C FROM DRAWS GROUP BY WINNINGNO4\n"
                + "UNION ALL\n"
                + "SELECT WINNINGNO5 AS N, COUNT(WINNINGNO5) AS C FROM DRAWS GROUP BY WINNINGNO5\n"
                + ") NUMBERS\n"
                + "GROUP BY N\n"
                + "ORDER BY COUNT_TOTAL DESC FETCH FIRST 5 ROWS ONLY");
        
        //Μετατρέπω το αποτέλεσμα σε Object[]
        List<Object[]> list = query.getResultList();
        
        //Δημιουργώ πίνακας για τους αριθμούς και την συχνότητα εμφάνισης
        String[] numbers = new String[5];
        Integer[] times = new Integer[5];
        
        //Διαπερνάω τα Object[] και καταχωρώ στους πίνακες numbers και times την πληροφορία
        int i = 0;//counter
        for (Object[] obj : list) {
            numbers[i] = (String) obj[0];
            times[i] = (int) obj[1];
            i++;
        }
        
        //Δημιουργώ model για τον πίνακα
        DefaultTableModel model = new DefaultTableModel();

        //Ορίζω τις επικεφαλίδες στον πίνακα και προσθέτω τους Αριθμούς και τη Συχνότητα
        model.setColumnIdentifiers(new String[]{"1oς Αριθμός", "2oς Αριθμός", "3oς Αριθμός", "4oς Αριθμός", "5oς Αριθμός"});
        model.addRow(numbers);
        model.addRow(times);
        jTable1.setModel(model);
               
        //Συχνότητα εμφάνισης αριθμών joker (πέντε πιο συχνά εμφανιζόμενοι αριθμοί)
        Query query2 = em.createNativeQuery("SELECT WINNINGBONUS, COUNT(WINNINGBONUS) AS C FROM DRAWS GROUP BY WINNINGBONUS ORDER BY C DESC FETCH FIRST 5 ROWS ONLY");
        List<Object[]> list2 = query2.getResultList();
        
        //Πίνακας για τους αριθμούς και την συχνότητα εμφάνισης
        String[] numbers2 = new String[5];
        Integer[] times2 = new Integer[5];
        
        //Διαπερνάω τα Object[] και καταχωρώ στους πίνακες numbers και times την πληροφορία
        i = 0;
        for (Object[] obj : list2) {
            numbers2[i] = (String) obj[0];
            times2[i] = (int) obj[1];
            i++;
        }

        //Δημιουργώ model για τον πίνακα
        DefaultTableModel model2 = new DefaultTableModel();

        //Ορίζουμε την επικεφαλίδα, εισάγουμε τους πίνακες numbers και times και ενημερώνουμε τον jTable2
        model2.setColumnIdentifiers(new String[]{"1oς Αριθμός", "2oς Αριθμός", "3oς Αριθμός", "4oς Αριθμός", "5oς Αριθμός"});
        model2.addRow(numbers2);
        model2.addRow(times2);
        jTable2.setModel(model2);

        final DefaultPieDataset pieNumbers = new DefaultPieDataset();
        final DefaultPieDataset pieJoker = new DefaultPieDataset();
        
        for (int k = 0; k < 5 ; k++){
            pieNumbers.setValue(numbers[k], times[k]);
            pieJoker.setValue(numbers2[k], times2[k]);
        }   
      
        //Μέσος όρος κερδών ανά κατηγορία
        
        //Καταχωρώ τα Prizecategories σε μία λίστα χρησημοποιώντας τον controller
        List<Prizecategories> categories = pjc.findPrizecategoriesEntities();
        
        //Αρχικοποιώ τον πίνακα με τα κέρδη ανά κατηγορία
        float[] divident = {0, 0, 0, 0, 0, 0, 0, 0};
        i = 0;
        int divider = 0;//Σύνολο Αποθηκευμένων Κληρώσεων
        //Διαπερνάω τα Prizecategories και καταχωρώ στον πίνακα τα αθροίσματα κάθε κατηγορίας
        
        for (Prizecategories categoryid : categories) {
            divident[i] = divident[i] + categoryid.getDivedent();
            i++;
            if (categoryid.getCategoryid() == 7) {
                i = 0;
                divider++;
            }
        }

        //Δημιουργώ το κατάλληλο Format
        DecimalFormat df = new DecimalFormat("#,##0.00");
        
        //Υπολογισμός Μέσου όρου κερδών ανά κατηγορία
        String[] dividentsString = new String[8];
        for (int j = 0; j < divident.length; j++) {
            dividentsString[j] = df.format(divident[j]/divider);
        }

        //Δημιουργούμε model για τον πίνακα
        DefaultTableModel model3 = new DefaultTableModel();

        //Ορίζουμε την επικεφαλίδα, εισάγουμε τον πίνακα dividentsString και ενημερώνουμε τον jTable3
        model3.setColumnIdentifiers(new String[]{"5+1", "5", "4+1", "4", "3+1", "3", "2+1", "1+1"});
        model3.addRow(dividentsString);
        jTable3.setModel(model3);
        //System.out.println(Arrays.toString(dividents));
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Μεταφερόμαστε στη σελίδα "Διαχείριση δεδομένων ΤΖΟΚΕΡ"
        JokerDBControl jokerDBControl = new JokerDBControl();
        jokerDBControl.setVisible(true);
        //Κλεισιμο παράθυρου
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        /*
        Διαβάζω τους αριθμούς μου από τον πίνακα 1 και τος μετατρέπω
        πίσω σε lists
        */
        String[] numbers = new String[5];
        Integer[] times = new Integer[5];
        
        try {        
        for (int i=0; i < 5; i++){
            numbers[i]=jTable1.getModel().getValueAt(0, i).toString();
            times[i]=Integer.valueOf(jTable1.getModel().getValueAt(1, i).toString());
        }
        
        /*
        Δημιουργώ ένα PieDataset απαραίτητο για το γράφημά μου.
        */
        
        DefaultPieDataset pieNumbers = new DefaultPieDataset();
        for (int k = 0; k < 5 ; k++){
            pieNumbers.setValue(numbers[k], times[k]);
        } 
        
        /*
        Δημιουργώ το γράφημα! Κώδικας από: https://youtu.be/MkrtvyxPpNg
        */
        
        JFreeChart NumbersChart = ChartFactory.createPieChart("Συχνότητα Εμφάνισης Αριθμών", pieNumbers, true, true, true);
        PiePlot p = (PiePlot) NumbersChart.getPlot();
        ChartFrame frame = new ChartFrame("Συχνότητα Εμφάνισης Αριθμών",NumbersChart);
        frame.setVisible(true);
        frame.setSize(450, 500);
        } catch (Exception ex) {
                System.out.println(ex);
                // Ενημερωτικό μύνημα
                JOptionPane.showMessageDialog(null, "Παρακαλώ πατήστε το κουμπί Προβολή στατιστικών ", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
                }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        /*
        Διαβάζω τους αριθμούς μου από τον πίνακα 1 και τος μετατρέπω
        πίσω σε lists
        */
        String[] numbers = new String[5];
        Integer[] times = new Integer[5];
        
        try {        
        for (int i=0; i < 5; i++){
            numbers[i]=jTable2.getModel().getValueAt(0, i).toString();
            times[i]=Integer.valueOf(jTable2.getModel().getValueAt(1, i).toString());
        }
        
        /*
        Δημιουργώ ένα PieDataset απαραίτητο για το γράφημά μου.
        */
        
        DefaultPieDataset pieNumbers = new DefaultPieDataset();
        for (int k = 0; k < 5 ; k++){
            pieNumbers.setValue(numbers[k], times[k]);
        } 
        
        /*
        Δημιουργώ το γράφημα! Κώδικας από: https://youtu.be/MkrtvyxPpNg
        */
        
        JFreeChart NumbersChart = ChartFactory.createPieChart("Συχνότητα Εμφάνισης Joker", pieNumbers, true, true, true);
        PiePlot p = (PiePlot) NumbersChart.getPlot();
        ChartFrame frame = new ChartFrame("Συχνότητα Εμφάνισης Joker",NumbersChart);
        frame.setVisible(true);
        frame.setSize(450, 500);
        } catch (Exception ex) {
                System.out.println(ex);
                // Ενημερωτικό μύνημα
                JOptionPane.showMessageDialog(null, "Παρακαλώ πατήστε το κουμπί Προβολή στατιστικών ", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
                }    
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(JokerStatisticsDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JokerStatisticsDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JokerStatisticsDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JokerStatisticsDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JokerStatisticsDB().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    // End of variables declaration//GEN-END:variables

}
