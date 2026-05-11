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
    private int finishedCount = 0;

    public RelayForm() {
        raceManager = new RaceManager(this);
        setupUI();
    }

    private void setupUI() {
        setTitle("🏃 Staffetta Multi-Thread");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 450);
        setLayout(new BorderLayout(10, 10));

        // Pannello Centrale: Corsie e Info
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel tracks = new JPanel(new GridLayout(4, 1, 10, 10));
        JPanel info = new JPanel(new GridLayout(4, 1, 10, 10));
        
        tracks.setBorder(new EmptyBorder(20, 20, 20, 20));
        info.setBorder(new EmptyBorder(20, 10, 20, 20));
        info.setPreferredSize(new Dimension(250, 0));

        progressBars = new JProgressBar[4];
        statusLabels = new JLabel[4];

        for (int i = 0; i < 4; i++) {
            progressBars[i] = new JProgressBar(0, 100);
            tracks.add(progressBars[i]);

            statusLabels[i] = new JLabel("Corridore " + (i + 1) + ": Pronto");
            statusLabels[i].setFont(new Font("SansSerif", Font.BOLD, 14));
            info.add(statusLabels[i]);
        }

        mainPanel.add(tracks, BorderLayout.CENTER);
        mainPanel.add(info, BorderLayout.EAST);
        add(mainPanel, BorderLayout.CENTER);

        // Barra Comandi
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        speedMenu = new JComboBox<>(new String[]{"Slow", "Regular", "Fast"});
        speedMenu.setSelectedIndex(1);

        btnStart = new JButton("Avvia");
        btnPause = new JButton("Sospende");
        btnResume = new JButton("Riprende");
        btnStop = new JButton("Ferma");

        // Stato iniziale bottoni
        setButtonsState(true); 

        controls.add(new JLabel("Velocità:"));
        controls.add(speedMenu);
        controls.add(btnStart);
        controls.add(btnPause);
        controls.add(btnResume);
        controls.add(btnStop);
        add(controls, BorderLayout.SOUTH);

        // AZIONI BOTTONI
        btnStart.addActionListener(e -> startAction());
        
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

        setLocationRelativeTo(null);
    }

    private void setButtonsState(boolean isReady) {
        btnStart.setEnabled(isReady);
        speedMenu.setEnabled(isReady);
        
        // Se isReady è true, la gara è ferma, quindi i tasti di controllo sono spenti
        btnPause.setEnabled(!isReady);
        btnResume.setEnabled(false); // Sempre spento all'inizio
        btnStop.setEnabled(!isReady);
    }

    private void startAction() {
        finishedCount = 0;
        for(int i=0; i<4; i++) {
            progressBars[i].setValue(0);
            statusLabels[i].setText("Corridore " + (i+1) + ": In corsa...");
        }

        int delay = switch (speedMenu.getSelectedIndex()) {
            case 0 -> 120;
            case 2 -> 20;
            default -> 60;
        };

        // ABILITA I BOTTONI DI CONTROLLO IMMEDIATAMENTE
        setButtonsState(false); 
        
        raceManager.startRace(delay);
    }

    public void aggiornaBarra(int id, int valore) {
        SwingUtilities.invokeLater(() -> {
            progressBars[id].setValue(valore);
            statusLabels[id].setText("Corridore " + (id + 1) + ": " + valore + "%");
        });
    }

    public void segnalaArrivo(int id) {
        SwingUtilities.invokeLater(() -> {
            statusLabels[id].setText("Corridore " + (id + 1) + ": Fine");
            finishedCount++;
            if (finishedCount == 4) {
                JOptionPane.showMessageDialog(this, "Gara terminata!");
                resetUI();
            }
        });
    }

    public void resetUI() {
        SwingUtilities.invokeLater(() -> setButtonsState(true));
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
