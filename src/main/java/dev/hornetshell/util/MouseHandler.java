package dev.hornetshell.util;

import dev.hornetshell.physics.PlayerControlled;
import dev.hornetshell.physics.PlayerEntity;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import static dev.hornetshell.util.Coordinates.cartToSphere;
import static dev.hornetshell.util.Coordinates.sphereToCart;

public class MouseHandler {

    private MouseHandler() {
        // no-op
    }

    public static void setMouseHandler(final Scene scene, final Camera camera, final Group world, final Group player) {
        scene.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                final double dY = event.getDeltaY();
                final double x = camera.getTranslateX();
                final double y = camera.getTranslateY();
                final double z = camera.getTranslateZ();
                double[] sph;
                double[] p1;
                if (dY < 0) {
                    camera.setTranslateZ(z - 20d);
                } else {
                    camera.setTranslateZ(z + 20d);
                }
            }
        });
        // on click, add the player to the world and remove this handler.
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                final double x = event.getX();
                final double y = event.getY();
                // determine if the player is alive
                final PlayerEntity player = world.getChildren()
                        .stream().filter(node -> node instanceof PlayerEntity)
                        .findAny()
                        .map(node -> (PlayerEntity) node)
                        .get();
                if (!player.isAlive()) {
                    player.setTranslateX(x);
                    player.setTranslateY(y);
                    player.setTranslateZ(250);
                    player.setAlive(true);
                    player.setVisible(true);
                }
            }
        });
    }
}
