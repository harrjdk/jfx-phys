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
				final double x = camera.getTranslateX();
				final double y = camera.getTranslateY();
				final double z = camera.getTranslateZ();
				final double ya = ry.getAngle();
				final double xa = rx.getAngle();
				double[] sph;
				double[] p1;
				switch (event.getCode()) {
				case UP: 
					System.out.println("xa: "+xa);
					rx.setAngle(xa+SPEED);
					break;
				case DOWN:
					System.out.println("xa: "+xa);
					rx.setAngle(xa-SPEED);
					break;
				case LEFT: 
					System.out.println("ya: "+ya);
					ry.setAngle(ya-SPEED);
					break;
				case RIGHT: 
					System.out.println("ya: "+ya);
					ry.setAngle(ya+SPEED);
					break;
				case W:
					sph = cartToSphere(new double[] {x, y, z});
					sph[0]-=SPEED;
					p1 = sphereToCart(sph);
					camera.setTranslateX(p1[0]);
					camera.setTranslateY(p1[1]);
					camera.setTranslateZ(p1[2]);
					break;
				case S:
					sph = cartToSphere(new double[] {x, y, z});
					sph[0]+=SPEED;
					p1 = sphereToCart(sph);
					camera.setTranslateX(p1[0]);
					camera.setTranslateY(p1[1]);
					camera.setTranslateZ(p1[2]);
					break;
				case A:
					// turn left
					sph = cartToSphere(new double[] {x, y, z});
					sph[1]+=Math.PI/10d;
					p1 = sphereToCart(sph);
					camera.setTranslateX(p1[0]);
					camera.setTranslateY(p1[1]);
					camera.setTranslateZ(p1[2]);
					break;
				case D:
					// turn right
					sph = cartToSphere(new double[] {x, y, z});
					sph[1]-=Math.PI/10d;
					p1 = sphereToCart(sph);
					camera.setTranslateX(p1[0]);
					camera.setTranslateY(p1[1]);
					camera.setTranslateZ(p1[2]);
					break;
				case Z:
					rx.setAngle(0);
					ry.setAngle(0);
					break;
				default: break;
				}
			}
		});
	}
	
	private static double[] cartToSphere(double[] xyz) {
		final double p = Math.sqrt(Math.pow(xyz[0], 2) + Math.pow(xyz[1], 2) + Math.pow(xyz[2], 2));
		final double theta = Math.atan2(xyz[1], xyz[0]);
		final double phi = Math.acos(xyz[2]/p);
		System.out.println("Sphere: "+p+", "+theta+", "+phi);
		return new double[] {p, theta!=theta?0:theta, phi!=phi?0:phi};
	}
	
	private static double[] sphereToCart(double[] ptp) {
		final double x = ptp[0] * Math.sin(ptp[2]) * Math.cos(ptp[1]);
		final double y = ptp[0] * Math.sin(ptp[2]) * Math.sin(ptp[1]);
		final double z = ptp[0] * Math.cos(ptp[2]);
		System.out.println("Cart:"+x+", "+y+", "+z);
		return new double[] {x, y, z};
	}
	

}
