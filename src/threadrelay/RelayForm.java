/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package threadrelay;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
/**
 *
 * @author grottelli.gabriele
 */
public class RelayForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RelayForm.class.getName());
    
    private JProgressBar[] progressBars;
    private JLabel[] statusLabels;
    private JButton btnStart, btnPause, btnResume, btnStop;
    private JComboBox<String> speedMenu;
    private RaceManager raceManager;
    
    /**
     * Creates new form Relay
     */
    public RelayForm() {
        initComponents(); 
        setupGerefica();
        raceManager = new RaceManager(this);
    }

    private void setupGerefica() {
        setTitle("🏃 Staffetta Multi-Thread");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240));

        // --- TITOLO ---
        JLabel title = new JLabel("GARA A STAFFETTA", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // --- CENTRO: PROGRESS BARS ---
        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(10, 30, 10, 30));

        progressBars = new JProgressBar[4];
        statusLabels = new JLabel[4];

        for (int i = 0; i < 4; i++) {
            JPanel runnerPanel = new JPanel(new BorderLayout(5, 2));
            runnerPanel.setOpaque(false);
            
            statusLabels[i] = new JLabel("Runner " + (i + 1) + ": Pronto");
            statusLabels[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
            
            progressBars[i] = new JProgressBar(0, 100);
            progressBars[i].setStringPainted(true);
            progressBars[i].setPreferredSize(new Dimension(100, 30));
            
            runnerPanel.add(statusLabels[i], BorderLayout.NORTH);
            runnerPanel.add(progressBars[i], BorderLayout.CENTER);
            centerPanel.add(runnerPanel);
        }
        add(centerPanel, BorderLayout.CENTER);

        // --- SUD: CONTROLLI ---
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        southPanel.setBackground(Color.WHITE);

        String[] livelli = {"Tartaruga (Lento)", "Uomo (Normale)", "Fulmine (Veloce)"};
        speedMenu = new JComboBox<>(livelli);
        speedMenu.setSelectedIndex(1);

        btnStart = new JButton("INIZIA");
        btnPause = new JButton("PAUSA");
        btnResume = new JButton("RIPRENDI");
        btnStop = new JButton("STOP/RESET");

        // Configurazione iniziale stati (Abilitati/Disabilitati)
        btnStart.setEnabled(true);
        btnPause.setEnabled(false);
        btnResume.setEnabled(false);
        btnStop.setEnabled(false);

        southPanel.add(new JLabel("Velocità:"));
        southPanel.add(speedMenu);
        southPanel.add(btnStart);
        southPanel.add(btnPause);
        southPanel.add(btnResume);
        southPanel.add(btnStop);
        add(southPanel, BorderLayout.SOUTH);

        // --- GESTIONE CLICK (LISTENER) ---
        
        btnStart.addActionListener(e -> startRaceAction());

        btnPause.addActionListener(e -> {
            raceManager.pauseAll();
            btnPause.setEnabled(false);
            btnResume.setEnabled(true);
        });

        btnResume.addActionListener(e -> {
            raceManager.resumeAll();
            btnPause.setEnabled(true);
            btnResume.setEnabled(false);
        });

        btnStop.addActionListener(e -> {
            raceManager.stopAll();
            resetUI();
        });

        // Centra la finestra e rendila visibile
        setLocationRelativeTo(null);
    }

    private void startRaceAction() {
        resetUI(); // Pulisce tutto prima di partire
        
        int delay;
        switch (speedMenu.getSelectedIndex()) {
            case 0: delay = 150; break;
            case 2: delay = 20; break;
            default: delay = 60; break;
        }

        // Sblocca i pulsanti di controllo
        btnStart.setEnabled(false);
        speedMenu.setEnabled(false);
        btnPause.setEnabled(true);
        btnStop.setEnabled(true);

        raceManager.startRace(delay);
    }

    // Metodi chiamati dal RaceManager per aggiornare la grafica
    public void aggiornaBarra(int id, int valore) {
        SwingUtilities.invokeLater(() -> {
            progressBars[id].setValue(valore);
            statusLabels[id].setText("Runner " + (id + 1) + ": " + valore + "%");
        });
    }

    public void segnalaArrivo(int id) {
        SwingUtilities.invokeLater(() -> {
            statusLabels[id].setText("Runner " + (id + 1) + ": TRAGUARDO! 🏁");
            if (id == 3) {
                JOptionPane.showMessageDialog(this, "Gara terminata con successo!");
                resetUI();
            }
        });
    }

    public void resetUI() {
        SwingUtilities.invokeLater(() -> {
            btnStart.setEnabled(true);
            speedMenu.setEnabled(true);
            btnPause.setEnabled(false);
            btnResume.setEnabled(false);
            btnStop.setEnabled(false);
            for (int i = 0; i < 4; i++) {
                progressBars[i].setValue(0);
                statusLabels[i].setText("Runner " + (i + 1) + ": Pronto");
            }
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new RelayForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
