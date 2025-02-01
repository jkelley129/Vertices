package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.ftcvertex.vertices.Command;

public class MoveLift implements Command {
    private final Robot robot = Robot.getInstance();

    private final Lift.Targets target;

    public MoveLift(Lift.Targets target) {
        this.target = target;
    }

    @Override
    public void init() {
        robot.lift.setTarget(target.value);
    }

    @Override
    public void loop() {}

    @Override
    public boolean isFinished() {
        return robot.lift.isFinished();
    }
}
