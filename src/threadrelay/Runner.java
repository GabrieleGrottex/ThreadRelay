/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threadrelay;

/**
 *
 * @author grottelli.gabriele
 */

public class Runner extends Thread {
    private int id;
    private int progress = 0;
    private boolean running = true;
    private boolean paused = false;
    private int speed;

    private Runner nextRunner;
    private RaceManager manager;

    public Runner(int id, int speed, RaceManager manager) {
        this.id = id;
        this.speed = speed;
        this.manager = manager;
    }

    public void setNextRunner(Runner next) {
        this.nextRunner = next;
    }

    @Override
    public void run() {
        try {
            while (progress < 100 && running) {
                // Controllo Pausa
                synchronized (this) {
                    while (paused) wait();
                }

                progress++;
                manager.updateRunner(id, progress);

                // Passaggio testimone al 90%
                if (progress == 90 && nextRunner != null) {
                    nextRunner.start();
                }

                Thread.sleep(speed);
            }
            
            if (running && progress >= 100) {
                manager.finishRunner(id);
            }

        } catch (InterruptedException e) {
            // Gestisce lo stop forzato
        }
    }

    public synchronized void pauseRunner() {
        paused = true;
    }

    public synchronized void resumeRunner() {
        paused = false;
        notify();
    }

    public void stopRunner() {
        running = false;
        this.interrupt(); 
    }
}