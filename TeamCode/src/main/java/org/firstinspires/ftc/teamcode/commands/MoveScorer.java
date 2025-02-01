package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Scorer;
import org.ftcvertex.vertices.Command;

public class MoveScorer implements Command {
    private final Robot robot = Robot.getInstance();
    private final Scorer.Targets target;

    public MoveScorer(Scorer.Targets target) {
        this.target = target;
    }

    @Override
    public void init() {
        robot.scorer.setPosition(target.value);
    }

    @Override
    public void loop() {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
