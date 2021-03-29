package dev.hornetshell.physics;

import dev.hornetshell.util.Shapes;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;

import java.util.Arrays;

/**
 * Represents the player
 */
public class PlayerEntity extends Group implements CanCollide, PlayerControlled, Moves {
    private double[] velocity = new double[3];
    private boolean isAlive;

    @Override
    public double[] getPosition() {
        return new double[] {
                getTranslateX(),
                getTranslateY(),
                getTranslateZ()
        };
    }

    @Override
    public void setPosition(double[] pos) {
        setTranslateX(pos[0]);
        setTranslateY(pos[1]);
        setTranslateZ(pos[2]);
    }

    @Override
    public double[] getVelocity() {
        return Arrays.copyOf(velocity, 3);
    }

    @Override
    public void setVelocity(double[] velocity) {
        // no-op
    }

    public void setVelocityForce(double[] velocity) {
        this.velocity = velocity;
    }

    @Override
    public double getBoundSphereRadius() {
        return 20d;
    }

    @Override
    public void setBoundSphereRadius(double radius) {
        // no-op
    }

    @Override
    public void notifyCollision(CanCollide collider) {
        if (collider instanceof Shapes.CollisionBox) {
            // player is dead
            this.isAlive = false;
            velocity = new double[3];
            this.setVisible(false);
        }
        // TODO: add logic for ignoring own projectiles
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }
}
