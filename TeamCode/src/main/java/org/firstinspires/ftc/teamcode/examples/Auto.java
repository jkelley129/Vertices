package org.firstinspires.ftc.teamcode.examples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.commands.MoveLift;
import org.firstinspires.ftc.teamcode.commands.MoveScorer;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Scorer;
import org.ftcvertex.vertices.CommandRunner;
import org.ftcvertex.vertices.ParallelCommand;
import org.ftcvertex.vertices.SeriesCommand;
import org.ftcvertex.vertices.SleepCommand;

@Autonomous(name = "Auto")
public class Auto extends LinearOpMode {
    private final Robot robot = Robot.getInstance();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(this);

        CommandRunner commandRunner = new CommandRunner(
            new SeriesCommand(
                new MoveLift(Lift.Targets.LOW),
                new MoveScorer(Scorer.Targets.TRANSFER),
                new SleepCommand(400),
                new ParallelCommand(
                    new MoveLift(Lift.Targets.HIGH),
                    new MoveScorer(Scorer.Targets.SCORE)
                )
            )
        );

        waitForStart();

        commandRunner.start();

        while (opModeIsActive()) {
            if (commandRunner.isFinished()) {
                break;
            }

            commandRunner.update();
            robot.update();
        }
    }
}
