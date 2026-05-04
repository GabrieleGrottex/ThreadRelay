/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package threadrelay;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
/**
 *
 * @author grottelli.gabriele
 */
public class RelayForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RelayForm.class.getName());
    
    private RaceManager manager;
    private JProgressBar[] progressBars;
    private JButton btnStart, btnPause, btnResume, btnStop;
    private JComboBox<String> speedMenu;
    
    private final Color BG_COLOR = new Color(245, 246, 250);
    private final Color ACCENT_COLOR = new Color(72, 126, 176);
    private final Color START_COLOR = new Color(76, 175, 80);
    private final Color STOP_COLOR = new Color(232, 65, 24);
    private final Color PAUSE_COLOR = new Color(251, 197, 49);
    
    /**
     * Creates new form Relay
     */
    
    public RelayForm() {
        manager = new RaceManager(this);
        initComponentsCustom();
    }

    private void initComponentsCustom() {
        setTitle("🏃 Thread Relay Race");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout(20, 20));

        JLabel titleLabel = new JLabel("Staffetta Multi-Thread", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBorder(new EmptyBorder(20, 0, 10, 0));
        titleLabel.setForeground(new Color(47, 54, 64));
        add(titleLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(new EmptyBorder(10, 30, 10, 30));
        
        progressBars = new JProgressBar[4];
        for (int i = 0; i < 4; i++) {
            JPanel runnerRow = new JPanel(new BorderLayout(10, 5));
            runnerRow.setBackground(BG_COLOR);
            
            JLabel runnerLabel = new JLabel("Runner " + (i + 1) + " 🏁");
            runnerLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
            
            progressBars[i] = new JProgressBar(0, 100);
            progressBars[i].setPreferredSize(new Dimension(400, 35));
            progressBars[i].setStringPainted(true);
            progressBars[i].setFont(new Font("Segoe UI", Font.BOLD, 12));
            progressBars[i].setForeground(ACCENT_COLOR);
            progressBars[i].setBackground(Color.WHITE);
            progressBars[i].setBorder(new LineBorder(new Color(220, 221, 225), 1, true));

            runnerRow.add(runnerLabel, BorderLayout.NORTH);
            runnerRow.add(progressBars[i], BorderLayout.CENTER);
            mainPanel.add(runnerRow);
        }
        add(mainPanel, BorderLayout.CENTER);

        JPanel controlContainer = new JPanel(new BorderLayout());
        controlContainer.setBackground(BG_COLOR);
        controlContainer.setBorder(new EmptyBorder(10, 20, 20, 20));

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        controlPanel.setBackground(Color.WHITE);
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(210, 210, 210), 1, true),
            new EmptyBorder(10, 10, 10, 10)
        ));

        String[] speeds = {"🐢 Lento", "🚶 Medio", "⚡ Veloce"};
        speedMenu = new JComboBox<>(speeds);
        speedMenu.setSelectedIndex(1);
        speedMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        btnStart = createStyledButton("Avvia ▶", START_COLOR);
        btnPause = createStyledButton("Pausa ⏸", PAUSE_COLOR);
        btnResume = createStyledButton("Riprendi ⏯", ACCENT_COLOR);
        btnStop = createStyledButton("Stop 🛑", STOP_COLOR);

        btnPause.setEnabled(false);
        btnResume.setEnabled(false);
        btnStop.setEnabled(false);

        controlPanel.add(new JLabel("Velocità:"));
        controlPanel.add(speedMenu);
        controlPanel.add(btnStart);
        controlPanel.add(btnPause);
        controlPanel.add(btnResume);
        controlPanel.add(btnStop);

        controlContainer.add(controlPanel, BorderLayout.CENTER);
        add(controlContainer, BorderLayout.SOUTH);

        btnStart.addActionListener(e -> startRace());
        btnPause.addActionListener(e -> { manager.pauseAll(); btnPause.setEnabled(false); btnResume.setEnabled(true); });
        btnResume.addActionListener(e -> { manager.resumeAll(); btnPause.setEnabled(true); btnResume.setEnabled(false); });
        btnStop.addActionListener(e -> { manager.stopAll(); resetUI(); });

        pack();
        setLocationRelativeTo(null);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 15, 8, 15));
        return btn;
    }

    private void startRace() {
        for (JProgressBar b : progressBars) {
            b.setValue(0);
            b.setString("Pronto");
        }
        
        int speed = switch (speedMenu.getSelectedIndex()) {
            case 0 -> 100;
            case 2 -> 20;
            default -> 50;
        };

        btnStart.setEnabled(false);
        speedMenu.setEnabled(false);
        btnPause.setEnabled(true);
        btnStop.setEnabled(true);

        manager.startRace(speed);
    }

    public void aggiornaBarra(int id, int valore) {
        SwingUtilities.invokeLater(() -> {
            progressBars[id].setValue(valore);
            progressBars[id].setString("Correndo... " + valore + "%");
        });
    }

    public void segnalaArrivo(int id) {
        SwingUtilities.invokeLater(() -> {
            progressBars[id].setString("ARRIVATO!");
            progressBars[id].setForeground(START_COLOR);
            if (id == 3) {
                resetUI();
                JOptionPane.showMessageDialog(this, "Gara completata con successo!", "Fine Gara", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void resetUI() {
        SwingUtilities.invokeLater(() -> {
            btnStart.setEnabled(true);
            speedMenu.setEnabled(true);
            btnPause.setEnabled(false);
            btnResume.setEnabled(false);
            btnStop.setEnabled(false);
            for(JProgressBar b : progressBars) b.setForeground(ACCENT_COLOR);
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
