package dev.hornetshell.physics;

import java.util.List;

/**
 * A group of collision-enabled nodes
 * @author devin
 *
 */
public interface CollisionGroup {
	List<CanCollide> getCollisionChildren();
}
