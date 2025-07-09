package org.firstinspires.ftc.teamcode.examples;

import static org.firstinspires.ftc.teamcode.subsystems.CommandLibrary.Profile;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.commands.MoveLift;
import org.firstinspires.ftc.teamcode.commands.MoveScorer;
import org.firstinspires.ftc.teamcode.commands.TelemetryCommand;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Scorer;
import org.firstinspires.ftc.teamcode.subsystems.TelemetryProfiles;
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

        TelemetryCommand telemetryCommand = TelemetryProfiles
                .getProfile(TelemetryProfiles.Profile.DEFAULT);

        CommandRunner commandRunner = new CommandRunner(
                new ParallelCommand(
                        new SeriesCommand(
                                Profile.TRANSFER.get(),
                                new SleepCommand(400),
                                Profile.SCORE.get(),
                                new ParallelCommand(
                                        new MoveLift(Lift.Targets.HIGH),
                                        new MoveScorer(Scorer.Targets.TRANSFER)
                                )
                        ),
                        telemetryCommand
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
