package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.ftcvertex.vertices.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TelemetryCommand implements Command {
    private final Robot robot = Robot.getInstance();

    private final List<TelemetryEntry> entries = new ArrayList<>();
    private boolean clearEachLoop = true;

    private volatile boolean disabled = false;

    public TelemetryCommand(){}

    public TelemetryCommand addData(String caption, Supplier<?> supplier) {
        entries.add(new DataEntry(caption,supplier));
        return this;
    }

    public TelemetryCommand addLine(String line) {
        entries.add(new LineEntry(line));
        return this;
    }

    public TelemetryCommand addDisabledTag(boolean add) {
        if (add) {
            entries.add(new DataEntry("Disabled", this::isDisabled));
        }
        return this;
    }

    public TelemetryCommand addDisabledTag() {
        return addDisabledTag(true);
    }

    public TelemetryCommand clearEachLoop(boolean clear) {
        this.clearEachLoop = clear;
        return this;
    }

    @Override
    public void init() {
        robot.telemetry.clear();
    }

    @Override
    public void loop() {
        if(!disabled) {
            if (clearEachLoop) {
                clear();
            }

            for (TelemetryEntry entry : entries) {
                entry.write(robot);
            }

            robot.telemetry.update();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void toggleDisabled() {
        setDisabled(!disabled);
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void clear(){
        robot.telemetry.clear();
    }

    public TelemetryCommand copy() {
        TelemetryCommand copy = new TelemetryCommand();
        for (TelemetryEntry entry : this.entries) {
            copy.entries.add(entry.copy());
        }
        copy.clearEachLoop = this.clearEachLoop;
        copy.disabled = this.disabled;
        return copy;
    }

    // Internal interface
    private interface TelemetryEntry {
        void write(Robot robot);
        TelemetryEntry copy();
    }

    private static class DataEntry implements TelemetryEntry {
        private final String caption;
        private final Supplier<?> supplier;

        DataEntry(String caption, Supplier<?> supplier) {
            this.caption = caption;
            this.supplier = supplier;
        }

        @Override
        public void write(Robot robot) {
            robot.telemetry.addData(caption, supplier::get);
        }

        @Override
        public TelemetryEntry copy() {
            return new DataEntry(caption, supplier);
        }
    }

    private static class LineEntry implements TelemetryEntry {
        private final String line;

        LineEntry(String line) {
            this.line = line;
        }

        @Override
        public void write(Robot robot) {
            robot.telemetry.addLine(line);
        }

        @Override
        public TelemetryEntry copy() {
            return new LineEntry(line);
        }
    }
}