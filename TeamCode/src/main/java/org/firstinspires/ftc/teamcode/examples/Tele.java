package org.firstinspires.ftc.teamcode.examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ButtonAction;
import org.firstinspires.ftc.teamcode.commands.MoveLift;
import org.firstinspires.ftc.teamcode.commands.MoveScorer;
import org.firstinspires.ftc.teamcode.commands.TelemetryCommand;
import static org.firstinspires.ftc.teamcode.subsystems.CommandLibrary.Profile;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Scorer;
import org.firstinspires.ftc.teamcode.subsystems.TelemetryProfiles;
import org.ftcvertex.vertices.CommandRunner;
import org.ftcvertex.vertices.CustomCommand;
import org.ftcvertex.vertices.SeriesCommand;
import org.ftcvertex.vertices.StoppableCommand;

@TeleOp(name = "Tele")
public class Tele extends LinearOpMode {
    private final Robot robot = Robot.getInstance();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(this);

        TelemetryCommand telemetryCommand = TelemetryProfiles
                .getProfile(TelemetryProfiles.Profile.TELEOP);

        CommandRunner commandRunner = new CommandRunner(telemetryCommand);

        StoppableCommand liftAndScorer = new StoppableCommand(
                Profile.INTAKE.get()
        );

        ButtonAction reset = new ButtonAction(
                liftAndScorer,
                commandRunner
        );

        ButtonAction stopReset = new ButtonAction(
                new CustomCommand(liftAndScorer::cancel),
                commandRunner
        );

        ButtonAction transfer = new ButtonAction(
                new SeriesCommand(
                        new MoveLift(Lift.Targets.LOW),
                        new MoveScorer(Scorer.Targets.TRANSFER)
                ),
                commandRunner
        );

        ButtonAction score = new ButtonAction(
                new SeriesCommand(
                        new MoveLift(Lift.Targets.HIGH),
                        new MoveScorer(Scorer.Targets.SCORE)
                ),
                commandRunner
        );

        waitForStart();

        commandRunner.start();

        while (opModeIsActive()) {
            reset.update(robot.gamepad1.a);
            transfer.update(robot.gamepad1.b);
            score.update(robot.gamepad1.dpad_down);
            stopReset.update(robot.gamepad1.dpad_up);

            if (robot.gamepad2.right_bumper) {
                telemetryCommand.setDisabled(true);
            }else if (robot.gamepad2.left_bumper) {
                telemetryCommand.setDisabled(false);
            }

            commandRunner.update();
            robot.update();
        }
    }
}
