package dev.hornetshell.util;

import dev.hornetshell.physics.Pellet;
import dev.hornetshell.physics.PlayerEntity;
import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;

import java.util.Arrays;

import static dev.hornetshell.util.Coordinates.cartToSphere;
import static dev.hornetshell.util.Coordinates.sphereToCart;

public class KeyboardHandler {
	
	private static final double SPEED = 5.0;

	private KeyboardHandler() {
		// no-op
	}
	
	public static void setKeyboardHandler(Scene scene, Group world, PlayerEntity player, Rotate rZ) {
		final Camera camera = scene.getCamera();
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				final double aZ = rZ.getAngle();
				final double rAz = degreesToRadians(aZ);
				final double[] pos = player.getPosition();
				final double[] vel = player.getVelocity();
				double angle;
				switch (event.getCode()) {
				case LEFT:
				case A:
					angle = (aZ - 5d) % 360;
					rZ.setAngle(angle);
					break;
				case RIGHT:
				case D:
					angle = (aZ + 5d) % 360;
					rZ.setAngle(angle);
					break;
				case S:
					vel[0] = vel[0] + (-2d * Math.sin(rAz));
					vel[1] = vel[1] + (2d * Math.cos(rAz));
					break;
				case W:
					vel[0] = vel[0] + (2d * Math.sin(rAz));
					vel[1] = vel[1] + (-2d * Math.cos(rAz));
					break;
				case Z:
					vel[0]=0d;
					vel[1]=0d;
					vel[2]=0d;
					break;
				case X:
					// TODO
					break;
				case SPACE:
					final long count = world.getChildren().stream().filter(node -> node instanceof Pellet).count();
					if (count < 3) {
						final double[] nVel = Arrays.copyOf(vel, 3);
						final double[] nPos = Arrays.copyOf(pos, 3);
						nVel[0] = vel[0] + (4d * Math.sin(rAz));
						nVel[1] = vel[1] + (-4d * Math.cos(rAz));
						final Pellet pellet = Shapes.createPellet(nPos, nVel);
						world.getChildren().add(pellet);
					}
					break;
				default: break;
				}
				if (Math.abs(vel[0]) > 8) {
					vel[0] = vel[0] > 0? 8: -8;
				}
				if (Math.abs(vel[1]) > 8) {
					vel[1] = vel[1] > 0? 8: -8;
				}
				player.setVelocityForce(vel);
			}
		});
	}

	private static double degreesToRadians(double degrees) {
		return (Math.PI/180d) * (degrees%360d);
	}
	

}
