package com.dsahub.patterns.compound;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BeatModel implements BeatModelInterface {

    private final List<BeatObserver> beatObservers = new CopyOnWriteArrayList<>();
    private final List<BPMObserver> bpmObservers = new CopyOnWriteArrayList<>();

    private int bpm = 90;
    private final BeatGenerator beatGenerator;

    public BeatModel(BeatGenerator generator) {
        this.beatGenerator = generator;
        this.beatGenerator.setBeatListener(new Runnable() {
            @Override
            public void run() {
                notifyBeatObservers();
            }
        });
    }

    @Override
    public void initialize() {
        beatGenerator.initialize();
    }

    @Override
    public void on() {
        beatGenerator.start();
        notifyBPMObservers();
    }

    @Override
    public void off() {
        beatGenerator.stop();
        notifyBPMObservers();
    }

    @Override
    public void setBPM(int bpm) {
        this.bpm = bpm;
        beatGenerator.setBPM(bpm);
        notifyBPMObservers();
    }

    @Override
    public int getBPM() {
        return bpm;
    }

    @Override
    public void registerBeatObserver(BeatObserver o) {
        beatObservers.add(o);
    }

    @Override
    public void removeBeatObserver(BeatObserver o) {
        beatObservers.remove(o);
    }

    @Override
    public void registerBPMObserver(BPMObserver o) {
        bpmObservers.add(o);
    }

    @Override
    public void removeBPMObserver(BPMObserver o) {
        bpmObservers.remove(o);
    }

    private void notifyBeatObservers() {
        for (BeatObserver o : beatObservers) {
            o.updateBeat();
        }
    }

    private void notifyBPMObservers() {
        for (BPMObserver o : bpmObservers) {
            o.updateBPM(bpm);
        }
    }
}
