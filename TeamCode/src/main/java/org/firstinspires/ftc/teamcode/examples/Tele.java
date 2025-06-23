package org.firstinspires.ftc.teamcode.examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ButtonAction;
import org.firstinspires.ftc.teamcode.commands.MoveLift;
import org.firstinspires.ftc.teamcode.commands.MoveScorer;
import org.firstinspires.ftc.teamcode.commands.TelemetryCommand;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Scorer;
import org.ftcvertex.vertices.CommandRunner;
import org.ftcvertex.vertices.SeriesCommand;

@TeleOp(name = "Tele")
public class Tele extends LinearOpMode {
    private final Robot robot = Robot.getInstance();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(this);

        TelemetryCommand telemetryCommand = new TelemetryCommand()
                .addLine("Running")
                .addData("Lift Finished", robot.lift::isFinished)
                .clearEachLoop(true);
        telemetryCommand.addData("Disabled", telemetryCommand::isDisabled);

        CommandRunner commandRunner = new CommandRunner(telemetryCommand);

        ButtonAction reset = new ButtonAction(
                new SeriesCommand(
                        new MoveLift(Lift.Targets.GROUND),
                        new MoveScorer(Scorer.Targets.TRANSFER)
                ),
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
