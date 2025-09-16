package org.ftcvertex.vertices;

public class CustomCommand implements Command {
    private final Runnable toRun;
    private boolean initialLoop = true;

    public CustomCommand(Runnable toRun) {
        this.toRun = toRun;
    }

    public void init() {}

    public void loop() {
        if(initialLoop) toRun.run(); initialLoop = false;
    }

    public boolean isFinished() {
        return true;
    }
}
