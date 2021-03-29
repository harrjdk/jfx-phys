package dev.hornetshell;

import dev.hornetshell.director.GameDirector;
import dev.hornetshell.physics.PlayerEntity;
import dev.hornetshell.tasks.CleanerTask;
import dev.hornetshell.tasks.CollisionTask;
import dev.hornetshell.tasks.MoveTask;
import dev.hornetshell.tasks.SpinTask;
import dev.hornetshell.util.KeyboardHandler;
import dev.hornetshell.util.MouseHandler;
import dev.hornetshell.util.Shapes;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Timer;

/**
 * JavaFX App
 */
public class App extends Application {
	
	private static final long FRAME_RATE = 1000/60;
	
	private final Timer timer = new Timer();
	
	private final Group world = new Group();

	private boolean exit = false;

	/**
	 * Set up the physics engine
	 */
	private void setPhysics() {
		final CollisionTask collisionTask = new CollisionTask(world);
		final MoveTask moveTask = new MoveTask(world);
		final SpinTask spinTask = new SpinTask(world);
		final Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!exit) {
					try {
						Thread.sleep(FRAME_RATE);
					} catch (InterruptedException e) {
						// no-op
					}
					Platform.runLater(collisionTask);
					Platform.runLater(moveTask);
					Platform.runLater(spinTask);
				}
			}
		});

		thread.setDaemon(true);
		thread.start();
	}

	private void startGameDirector() {
		final GameDirector gameDirector = new GameDirector(world);
		final Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!exit) {
					try {
						Thread.sleep(20L * FRAME_RATE);
					} catch (InterruptedException e) {
						// no-op
					}
					Platform.runLater(gameDirector);
				}
			}
		});

		thread.setDaemon(true);
		thread.start();
	}

	private void startCleaner() {
		final CleanerTask cleanerTask = new CleanerTask(world);
		final Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!exit) {
					try {
						Thread.sleep(20L * FRAME_RATE);
					} catch (InterruptedException e) {
						// no-op
					}
					Platform.runLater(cleanerTask);
				}
			}
		});

		thread.setDaemon(true);
		thread.start();
	}
	
	private Camera buildCamera() {
		final PerspectiveCamera camera = new PerspectiveCamera();
		camera.setNearClip(0.1);
		camera.setFarClip(1000.0);
		camera.setTranslateZ(-640);
		camera.setTranslateX(320);
		camera.setTranslateY(240);
			
		return camera;
	}

	// Test method for shape generator
	private void makeRandomShape(int count) {
		final Random random = new Random(0);
		for(int i=0; i < count; i++) {
			final var shape = Shapes.makeBoxCluster();
	        shape.setTranslateX(random.nextInt(640));
	        shape.setTranslateY(random.nextInt(480));
	        shape.setTranslateZ(250);
	        world.getChildren().add(shape);
		}
	}

    @Override
    public void start(Stage stage) {
		stage.setHeight(480);
		stage.setWidth(640);
		stage.setMaximized(false);
		stage.setTitle("JavaFx Physics Demo -- Asteroids");
        var scene = new Scene(world, 640, 480, Color.BLACK);

        final Rotate crx = new Rotate(0, Rotate.X_AXIS);
        final Rotate cry = new Rotate(0, Rotate.Y_AXIS);
        final Camera camera = buildCamera();
        camera.getTransforms().addAll(crx, cry);
        scene.setCamera(camera);
        stage.setScene(scene);
        
        //makeRandomShape(20);

		final Rotate rz = new Rotate(0, Rotate.Z_AXIS);
        final PlayerEntity player = Shapes.makePlayerAvatar(rz);
        player.setAlive(false);
        player.setVisible(false);
        world.getChildren().add(player);
        
        KeyboardHandler.setKeyboardHandler(scene, world, player, rz);
		MouseHandler.setMouseHandler(scene, camera, world, player);

		setPhysics();
		startGameDirector();
		startCleaner();

        stage.show();
    }
    
    @Override
    public void stop() {
    	// stop timer
    	timer.cancel();
    	timer.purge();

    	// exit flag
		exit = true;
    	
    	// TODO: add clean up methods
    }

    public static void main(String[] args) {
        launch();
    }

}