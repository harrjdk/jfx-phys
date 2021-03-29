package dev.hornetshell.tasks;

import dev.hornetshell.physics.PlayerEntity;
import javafx.scene.Group;

public class CleanerTask implements Runnable {

    public static final double OUT_OF_BOUNDS = 1500d;
    private final Group world;

    public CleanerTask(Group world) {
        this.world = world;
    }

    @Override
    public void run() {
        world.getChildren().removeIf(node -> (!(node instanceof PlayerEntity) && !node.isVisible()));
        world.getChildren()
                .removeIf(node -> !(node instanceof PlayerEntity) && (node.getTranslateX() < -OUT_OF_BOUNDS
                        || node.getTranslateX() > OUT_OF_BOUNDS || node.getTranslateY() < -OUT_OF_BOUNDS
                        || node.getTranslateY() > OUT_OF_BOUNDS));
    }
}
