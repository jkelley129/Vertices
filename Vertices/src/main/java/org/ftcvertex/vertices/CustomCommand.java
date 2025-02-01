package org.ftcvertex.vertices;

public class CustomCommand implements Command {
    private final Runnable toRun;

    public CustomCommand(Runnable toRun) {
        this.toRun = toRun;
    }

    public void init() {
        toRun.run();
    }

    public void loop() {
    }

    public boolean isFinished() {
        return true;
    }
}
