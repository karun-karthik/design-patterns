package com.dsahub.patterns.compound;

public class SimpleThreadBeatGenerator implements BeatGenerator, Runnable {
    private volatile boolean running = false;
    private volatile int bpm = 90;
    private Runnable onBeat;
    private Thread thread;

    @Override
    public void initialize() {
        // no-op
    }

    @Override
    public void start() {
        if (running) return;
        running = true;
        thread = new Thread(this, "BeatGenerator");
        thread.start();
    }

    @Override
    public void stop() {
        running = false;
        if (thread != null) {
            thread.interrupt();
        }
    }

    @Override
    public void setBPM(int bpm) {
        if (bpm <= 0) bpm = 1;
        this.bpm = bpm;
    }

    @Override
    public void setBeatListener(Runnable onBeat) {
        this.onBeat = onBeat;
    }

    @Override
    public void run() {
        while (running) {
            try {
                if (onBeat != null) {
                    onBeat.run();
                }
                long delay = 60000L / (bpm <= 0 ? 1 : bpm);
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                // allow thread to exit
                break;
            }
        }
    }
}
