package dev.hornetshell.physics;

/**
 * Nodes which can collide need to implement this.
 * This contains mostly physics related components such as
 * velocity and position, but also a way to notify nodes that 
 * they have collided with something.
 * @author devin
 *
 */
public interface CanCollide {
	
	double[] getPosition();
	
	void setPosition(double[] pos);
	
	double[] getVelocity();
	
	void setVelocity(double[] velocity);
	
	double getBoundSphereRadius();
	
	void setBoundSphereRadius(double radius);
	
	void notifyCollision(CanCollide collider);
}
