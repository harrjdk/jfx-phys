package dev.hornetshell.tasks;

import dev.hornetshell.physics.HasSpin;
import dev.hornetshell.physics.Moves;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class SpinTask extends TimerTask {

    private Group world;

    public SpinTask(Group world) {
        this.world = world;
    }

    @Override
    public void run() {
        // filter out actionable items
        final List<HasSpin> nodes = new ArrayList<>(world.getChildren().stream()
                .filter(node -> node instanceof HasSpin)
                .filter(node -> node.isVisible())
                .map(node -> (HasSpin) node)
                .collect(Collectors.toList()));

        // update their rotations
        nodes.forEach(node -> {
            final Rotate rX = node.getRotationX();
            final Rotate rY = node.getRotationY();
            final Rotate rZ = node.getRotationZ();
            if (rX != null) {
                rX.setAngle(rX.getAngle() + node.getDeltaRx());
            }
            if (rY != null) {
                rY.setAngle(rY.getAngle() + node.getDeltaRy());
            }
            if (rZ != null) {
                rZ.setAngle(rZ.getAngle() + node.getDeltaRz());
            }
        });


    }
}
