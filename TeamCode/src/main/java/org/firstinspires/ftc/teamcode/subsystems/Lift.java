package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Lift {
    public enum Targets {
        GROUND(0),
        LOW(200),
        HIGH(600);

        public final int value;

        Targets(int value) {
            this.value = value;
        }
    }

    public static final int FINISH_BUFFER = 10;

    private final Robot robot = Robot.getInstance();

    private final DcMotorEx lift;

    private int target;

    public Lift() {
        lift = robot.hardwareMap.get(DcMotorEx.class, "lift");
    }

    public void update() {
        // control loop
    }


    public void setTarget(int target) {
        this.target = target;
    }

    public boolean isFinished() {
        return Math.abs(target - lift.getCurrentPosition()) < FINISH_BUFFER;
    }
}
