package org.VoituLex.model;

public class ContextState {

    private boolean stopped;
    private long time;

    public boolean isStopped() {
        return stopped;
    }
    public void begin(){
        this.stopped = false;
    }
    public void stop(){
        this.stopped = true;
    }
}
