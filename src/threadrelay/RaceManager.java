/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threadrelay;

import javax.swing.*;
/**
 *
 * @author grottelli.gabriele
 */

public class RaceManager {

    private JProgressBar[] bars;
    private JLabel[] labels;

    private Runner[] runners;

    public RaceManager(JProgressBar[] bars, JLabel[] labels) {
        this.bars = bars;
        this.labels = labels;
    }

    public void startRace(int speed) {
        runners = new Runner[4];

        for (int i = 0; i < 4; i++) {
            runners[i] = new Runner(i, speed, this);
        }

        for (int i = 0; i < 3; i++) {
            runners[i].setNextRunner(runners[i + 1]);
        }

        runners[0].start(); 
    }

    public void updateRunner(int id, int value) {
        SwingUtilities.invokeLater(() -> {
            bars[id].setValue(value);
            labels[id].setText("Corridore " + (id + 1) + ": " + value);
        });
    }

    public void finishRunner(int id) {
        SwingUtilities.invokeLater(() -> {
            labels[id].setText("Corridore " + (id + 1) + ": Fine");
        });
    }

    public void pauseAll() {
        for (Runner r : runners) r.pauseRunner();
    }

    public void resumeAll() {
        for (Runner r : runners) r.resumeRunner();
    }

    public void stopAll() {
        for (Runner r : runners) r.stopRunner();
    }
}
