package dev.hornetshell;

import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;

public class KeyboardHandler {
	
	private static final double SPEED = 5.0;

	private KeyboardHandler() {
		// no-op
	}
	
	public static void handleKeyboard(Scene scene, Node world, Rotate rx, Rotate ry) {
		final Camera camera = scene.getCamera();
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP: 
					rx.setAngle(rx.getAngle()+SPEED);
					break;
				case DOWN: 
					rx.setAngle(rx.getAngle()-SPEED);
					break;
				case LEFT: 
					ry.setAngle(ry.getAngle()-SPEED);
					break;
				case RIGHT: 
					ry.setAngle(ry.getAngle()+SPEED);
					break;
				case W:
					camera.setTranslateZ(camera.getTranslateZ()+SPEED);
					break;
				case S:
					camera.setTranslateZ(camera.getTranslateZ()-SPEED);
					break;
				case A:
					camera.setTranslateX(camera.getTranslateX()-SPEED);
					break;
				case D:
					camera.setTranslateX(camera.getTranslateX()+SPEED);
					break;
				default: break;
				}
			}
		});
	}

}
