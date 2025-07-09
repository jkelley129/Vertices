package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.commands.MoveLift;
import org.firstinspires.ftc.teamcode.commands.MoveScorer;
import org.ftcvertex.vertices.Command;
import org.ftcvertex.vertices.ParallelCommand;
import org.ftcvertex.vertices.SeriesCommand;

import java.util.function.Supplier;

public final class CommandLibrary {
    public enum Profile {
        SCORE(() -> new SeriesCommand(
                new MoveLift(Lift.Targets.HIGH),
                new MoveScorer(Scorer.Targets.SCORE)
        )),
        TRANSFER(() -> new ParallelCommand(
                new MoveLift(Lift.Targets.LOW),
                new MoveScorer(Scorer.Targets.TRANSFER)
        )),
        INTAKE(() -> new ParallelCommand(
                new MoveLift(Lift.Targets.GROUND),
                new MoveScorer(Scorer.Targets.TRANSFER)
        ));

        private final Supplier<Command> value;

        Profile(Supplier<Command> value) {
            this.value = value;
        }

        ///@return The specified command
        public Command get() {
            return value.get();
        }

    }
    private CommandLibrary() {}
}
