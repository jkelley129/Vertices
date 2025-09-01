package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;

import org.ftcvertex.vertices.Command;

public class PathMovement implements Command {

    private final PathChain path;
    private final Follower follower;
    private boolean initialLoop = true;

    public PathMovement(PathChain path, Follower follower){
        this.path = path;
        this.follower = follower;
    }

    @Override
    public void init() {initialLoop = true;}

    @Override
    public void loop() {
        if(initialLoop) {
            follower.followPath(path);
            initialLoop = false;
        }
        follower.update();
    }

    @Override
    public boolean isFinished() {
        return !follower.isBusy();
    }
}
