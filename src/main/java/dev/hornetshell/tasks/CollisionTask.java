package dev.hornetshell.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;

import dev.hornetshell.physics.CanCollide;
import dev.hornetshell.physics.CollisionGroup;
import javafx.scene.Group;

/**
 * Timer task for collision logic
 * Ideally this would run at 1000/60 millisecond periods
 * @author devin
 *
 */
public class CollisionTask extends TimerTask {
	
	// world should be your root group for objects.
	// collision-friendly objects will need to implement
	// CanCollidate
	private Group world;
	
	public CollisionTask(Group world) {
		this.world = world;
	}

	@Override
	public void run() {
		// filter out actionable items
		final List<CanCollide> nodes = new ArrayList<>(world.getChildren().stream()
			.filter(node -> node instanceof CanCollide)
			.map(node -> (CanCollide) node)
			.collect(Collectors.toList()));
		
		// add an collision groups to the list
		nodes.addAll(
				world.getChildren().stream()
					.filter(node -> node instanceof CollisionGroup)
					.map(node -> (CollisionGroup) node)
					.flatMap(node -> node.getCollisionChildren().stream())
					.collect(Collectors.toList())
				);
		
		// we now have all collision groups and can proceed
		nodes.forEach(node1 -> {
			final double[] position1 = node1.getPosition();
			final double radius1 = node1.getBoundSphereRadius();
			
			// check for each node for simple sphere collision
			nodes.forEach(node2 -> {
				if (node2 != node1) {
					final double[] position2 = node2.getPosition();
					final double radius2 = node2.getBoundSphereRadius();
					if (doCollide(position1, radius1, position2, radius2)) {
						node2.notifyCollision(node1);
					}
				}
			});
		});
		
	}
	
	private static boolean doCollide(double[] p1, double r1, double[] p2, double r2) {
		final double d = Math.sqrt(
				Math.pow(p2[0]-p1[0], 2) + Math.pow(p2[1]-p1[1], 2) + Math.pow(p2[2]-p1[2], 2)
				);
		final double sumR = r2 + r1;
		
		return d < sumR;
	}

}
