package dev.hornetshell.director;

import dev.hornetshell.physics.PlayerEntity;
import dev.hornetshell.util.Shapes;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.Iterator;
import java.util.Optional;
import java.util.Random;

/**
 * GameDirector is a class which exists to attempt to force a collision
 * between a boxcluster and the player
 */
public class GameDirector implements Runnable {
    private static final Random random = new Random(0);

    private final Group world;

    public GameDirector(Group world) {
        this.world = world;
    }

    @Override
    public void run() {
        // first find the player
        Optional<PlayerEntity> optPlayer = world.getChildren()
                .stream().filter(node -> node instanceof PlayerEntity)
                .findAny()
                .filter(Node::isVisible)
                .map(node -> (PlayerEntity) node)
                .filter(PlayerEntity::isAlive);
        // get a count of current boxclusters
        final long clusterCount = world.getChildren().stream().filter(node -> node instanceof Shapes.BoxGroup).count();
        // clear all shapes if the player is dead.
        if (optPlayer.isEmpty()) {
            world.getChildren().removeIf(next -> next instanceof Shapes.BoxGroup);
            return;
        }
        if (clusterCount > 50L) {
            return;
        }
        // if the player is visible and alive, start chucking rocks at it
        final PlayerEntity player = optPlayer.get();
        final double[] playerPosition = player.getPosition();
        for (int i = 0; i < random.nextInt(3); i++) {
            final var shape = Shapes.makeBoxCluster();
            // we want beyond the screen
            final double x = random.nextInt(1000) + (random.nextBoolean()?500:-1500);
            final double y = random.nextInt(1000) + (random.nextBoolean()?500:-1500);
            final double z = 250d;
            shape.setTranslateX(x);
            shape.setTranslateY(y);
            shape.setTranslateZ(z);
            // calculate velocity from this to the player
            final double[] velocity = new double[3];
            final double dX = Math.abs(x - playerPosition[0]);
            final double dY = Math.abs(y - playerPosition[1]);
            final double dZ = Math.abs(z - playerPosition[2]);
            // make random velocities from this with no more than 16 seconds at 60fps.
            final double cyclesToArrive = random.nextInt(720)+240;
            velocity[0] = dX/cyclesToArrive * (x>playerPosition[0]?-1:1);
            velocity[1] = dY/cyclesToArrive * (y>playerPosition[1]?-1:1);
            velocity[2] = dZ/cyclesToArrive * (z>playerPosition[2]?-1:1);
            shape.gameDirectorSetVelocity(velocity);
            world.getChildren().add(shape);
        }
    }
}
