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
    
    private Runner[] runners;
    private RelayForm form; 

    public RaceManager(RelayForm form) {
        this.form = form;
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

    public void notificaProgresso(int id, int valore) {
        form.aggiornaBarra(id, valore);
    }

    public void notificaFine(int id) {
        form.segnalaArrivo(id);
    }

    public void pauseAll() {
        if (runners != null) for (Runner r : runners) if (r != null) r.pauseRunner();
    }

    public void resumeAll() {
        if (runners != null) for (Runner r : runners) if (r != null) r.resumeRunner();
    }

    public void stopAll() {
        if (runners != null) for (Runner r : runners) if (r != null) r.stopRunner();
    }
}
