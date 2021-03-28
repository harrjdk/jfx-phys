package dev.hornetshell;

import java.util.Random;
import java.util.Timer;

import dev.hornetshell.tasks.CollisionTask;
import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
	
	private static final long FRAME_RATE = 1000/60;
	
	private final Timer timer = new Timer();
	
	private final Group world = new Group();
	
	private void setPhysics() {
		timer.scheduleAtFixedRate(new CollisionTask(world), FRAME_RATE, FRAME_RATE);
	}
	
	private Camera buildCamera() {
		final PerspectiveCamera camera = new PerspectiveCamera();
		camera.setNearClip(0.1);
		camera.setFarClip(1000.0);
		camera.setTranslateZ(0);
		camera.setTranslateX(320);
		camera.setTranslateY(240);
			
		return camera;
	}
	
	private void makeRandomSpheres() {
		final Random random = new Random(0);
		for(int i=0; i < 10; i++) {
			final var sphere = new Group();
	        sphere.setTranslateX(random.nextInt(640));
	        sphere.setTranslateY(random.nextInt(480));
	        sphere.setTranslateZ(random.nextInt(500));
	        final var sphObj = new Sphere();
	        sphObj.setRadius(random.nextInt(50));
	        final var material = new PhongMaterial();
	        material.setDiffuseColor(Color.ALICEBLUE);
	        material.setSpecularColor(Color.DARKBLUE);
	        sphObj.setMaterial(material);
	        sphere.getChildren().add(sphObj);
	        world.getChildren().add(sphere);
		}
	}

    @Override
    public void start(Stage stage) {
        var scene = new Scene(world, 640, 480);
        final Rotate rx = new Rotate(0, Rotate.X_AXIS);
        final Rotate ry = new Rotate(0, Rotate.Y_AXIS);
        final Camera camera = buildCamera();
        camera.getTransforms().addAll(rx, ry);
        scene.setCamera(camera);
        stage.setScene(scene);
        
        makeRandomSpheres();
        
        KeyboardHandler.handleKeyboard(scene, world, rx, ry);
        stage.show();
    }
    
    @Override
    public void stop() {
    	// stop timer
    	timer.cancel();
    	timer.purge();
    	
    	// TODO: add clean up methods
    }

    public static void main(String[] args) {
        launch();
    }

}