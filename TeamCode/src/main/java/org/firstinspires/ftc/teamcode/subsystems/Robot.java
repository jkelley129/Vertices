package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    private static volatile Robot instance;

    public HardwareMap hardwareMap;
    public MultipleTelemetry telemetry;

    public Lift lift;
    public Scorer scorer;

    public Gamepad gamepad1;
    public Gamepad gamepad2;

    public static Robot getInstance() {
        Robot cachedInstance = instance;

        if (cachedInstance == null) {
            synchronized (Robot.class) {
                cachedInstance = instance;

                if (cachedInstance == null) {
                    instance = new Robot();
                }
            }
        }

        return instance;
    }

    public void init(
        OpMode opMode
    ) {
        this.hardwareMap = opMode.hardwareMap;

        this.gamepad1 = opMode.gamepad1;
        this.gamepad2 = opMode.gamepad2;

        this.telemetry = new MultipleTelemetry(
            opMode.telemetry,
            FtcDashboard.getInstance().getTelemetry()
        );

        lift = new Lift();
        scorer = new Scorer();
    }

    public void update() {
        lift.update();

        telemetry.update();
    }
}