package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Scorer {
    public enum Targets {
        TRANSFER(.5), SCORE(.8);

        public final double value;

        Targets(double value) {
            this.value = value;
        }
    }

    private final Robot robot = Robot.getInstance();

    private final Servo scorer;

    public Scorer() {
        scorer = robot.hardwareMap.get(
            Servo.class,
            "scorer"
        );
    }

    public void setPosition(double target) {
        scorer.setPosition(target);
    }
}