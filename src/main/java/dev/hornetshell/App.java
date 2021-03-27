package dev.hornetshell;

import java.util.Timer;

import dev.hornetshell.tasks.CollisionTask;
import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
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
		camera.setTranslateZ(-450);
		camera.setRotationAxis(Rotate.X_AXIS);
		camera.setRotate(33.0);
		
		
		return camera;
	}

    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();
        
        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        world.getChildren().add(new StackPane(label));
        var scene = new Scene(world, 640, 480);
        final Rotate rx = new Rotate(0, Rotate.X_AXIS);
        final Rotate ry = new Rotate(0, Rotate.Y_AXIS);
        final Camera camera = buildCamera();
        camera.getTransforms().addAll(rx, ry);
        scene.setCamera(camera);
        stage.setScene(scene);
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