package dev.hornetshell.tasks;

import dev.hornetshell.physics.CanCollide;
import dev.hornetshell.physics.HasSpin;
import dev.hornetshell.physics.Moves;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class MoveTask extends TimerTask {

    private Group world;

    public MoveTask(Group world) {
        this.world = world;
    }

    @Override
    public void run() {
        // filter out actionable items
        final List<Moves> nodes = new ArrayList<>(world.getChildren().stream()
                .filter(node -> node instanceof Moves)
                .map(node -> (Moves) node)
                .collect(Collectors.toList()));

        // update their positions
        nodes.forEach(node -> {
            final double[] v = node.getVelocity();
            final double[] p = node.getPosition();
            p[0]+=v[0];
            p[1]+=v[1];
            p[2]+=v[2];
            node.setPosition(p);
            node.setVelocity(new double[3]);
        });
    }
}
