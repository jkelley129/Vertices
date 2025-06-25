package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.commands.TelemetryCommand;

import java.util.EnumMap;

public final class TelemetryProfiles{
    private final static Robot robot = Robot.getInstance();
    private final static EnumMap<Profile, TelemetryCommand> profiles = new EnumMap<>(Profile.class);

    public enum Profile{
        DEFAULT, LIFT_ONLY, SCORER_ONLY, TELEOP // Add more as needed
    }

    //Prevent class from being instantiated
    private TelemetryProfiles(){}

    static {
        profiles.put(Profile.DEFAULT, new TelemetryCommand()
                .addLine("Running")
                .addData("Lift Finished", robot.lift::isFinished)
                .addData("Scorer Position", robot.scorer::getPosition)
                .addDisabledTag(true)
        );


        profiles.put(Profile.LIFT_ONLY, new TelemetryCommand()
                .addData("Lift Finished", robot.lift::isFinished)
                .addDisabledTag(true)
        );


        profiles.put(Profile.SCORER_ONLY, new TelemetryCommand()
                .addData("Scorer Position", robot.scorer::getPosition)
                .addDisabledTag()
                .clearEachLoop(false)
        );


        profiles.put(Profile.TELEOP, new TelemetryCommand()
                .addLine("Running")
                .addData("Lift Finished", robot.lift::isFinished)
                .addData("Scorer Position", robot.scorer::getPosition)
                .addData("Left Stick Y",() -> robot.gamepad1.left_stick_y)
                .addData("Left Stick X",() -> robot.gamepad1.left_stick_x)
                .addData("Right Stick X",() -> robot.gamepad1.right_stick_x)
                .addData("Right Trigger",() -> robot.gamepad1.right_trigger)
                .clearEachLoop(true)
        );

        //Add more profiles here
    }


    ///@return A copy of the TelemetryCommand with the given key
    ///@param key The key of the TelemetryCommand profile to get
    ///@throws IllegalArgumentException If the profile with the given key is not defined
    public static TelemetryCommand getProfile(Profile key){
        TelemetryCommand command = profiles.get(key);
        if(command == null){
            throw new IllegalArgumentException("Profile " + key + " is not defined.");
        }

        // Copy the command so any changes to the command by the calling opMode
        // doesn't affect the original profile
        return command.copy();
    }
}