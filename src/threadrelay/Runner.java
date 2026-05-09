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
    private int speed;
    private boolean running = true;
    private boolean paused = false;

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
                synchronized (this) {
                    while (paused) wait();
                }

                progress++;
                // Comunica il progresso al manager (Logica -> Manager)
                manager.notificaProgresso(id, progress);

                if (progress == 90 && nextRunner != null) {
                    nextRunner.start();
                }

                Thread.sleep(speed);
            }
            manager.notificaFine(id);
        } catch (InterruptedException e) {
            System.out.println("Runner " + id + " interrotto.");
        }
    }

    public synchronized void pauseRunner() { paused = true; }
    public synchronized void resumeRunner() { paused = false; notify(); }
    public void stopRunner() { running = false; }
}