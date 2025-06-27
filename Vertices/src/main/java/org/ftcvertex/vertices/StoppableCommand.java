package org.ftcvertex.vertices;

public class StoppableCommand implements Command{
    private final Command command;
    private boolean commandStopped = false;
    private boolean disabled = false;

    public StoppableCommand(Command command){
        this.command = command;
    }

    @Override
    public void init() {
        command.init();
    }

    @Override
    public void loop() {
        if(!disabled) {
            command.loop();
        }
    }

    public void stop(){
        commandStopped = true;
    }

    public void disable(){
        disabled = true;
    }
    public void enable(){
        disabled = false;
    }

    public boolean isDisabled(){
        return disabled;
    }

    @Override
    public boolean isFinished() {
        return command.isFinished() || commandStopped;
    }
}
