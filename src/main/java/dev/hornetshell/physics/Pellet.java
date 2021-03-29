package dev.hornetshell.physics;

import dev.hornetshell.util.Shapes;
import javafx.scene.shape.Sphere;

import java.util.Arrays;

public class Pellet extends Sphere implements CanCollide, Moves {
    private double[] velocity = new double[3];

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
        System.out.println("pos: "+pos[0]+", "+pos[1]+", "+pos[2]);
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
        return getRadius();
    }

    @Override
    public void setBoundSphereRadius(double radius) {
        setRadius(radius);
    }

    @Override
    public void notifyCollision(CanCollide collider) {
        if (collider instanceof Shapes.CollisionBox) {
            this.setVisible(false);
        }
    }
}
