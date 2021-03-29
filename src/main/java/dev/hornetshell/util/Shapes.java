package dev.hornetshell.util;

import dev.hornetshell.physics.*;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Shapes {
    private static final Random random = new Random(0);

    private Shapes() {
        // no-op
    }

    /**
     * Make an avatar for the player.
     * @param rz the z-axis rotation transform.
     * @return a right-facing pyramid.
     */
    public static PlayerEntity makePlayerAvatar(Rotate rz) {
        final PlayerEntity group = new PlayerEntity();
        final TriangleMesh pyramidMesh  = new TriangleMesh();
        pyramidMesh.getTexCoords().addAll(0,0);
        // essentially this is a 50px tall, 40px wide pyramid
        final float h = 50f;
        final float w = 40f;
        pyramidMesh.getPoints().addAll(
                0, 0, 0,
                0, h, -w/2f,
                -w/2f, h, 0,
                w/2f, h, 0,
                0, h, w/2f
        );
        pyramidMesh.getFaces().addAll(
                0,0,  2,0,  1,0,          // Front left face
                0,0,  1,0,  3,0,          // Front right face
                0,0,  3,0,  4,0,          // Back right face
                0,0,  4,0,  2,0,          // Back left face
                4,0,  1,0,  2,0,          // Bottom rear face
                4,0,  3,0,  1,0           // Bottom front face
        );
        // make mesh and color it
        final MeshView pyramid = new MeshView(pyramidMesh);
        pyramid.setDrawMode(DrawMode.FILL);
        final PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.GREENYELLOW);
        material.setSpecularColor(Color.BLUEVIOLET);
        pyramid.setMaterial(material);
        // rotate the pyramid
        pyramid.getTransforms().add(rz);
        group.getChildren().addAll(pyramid);
        return group;
    }

    public static Pellet createPellet(double[] pos, double[] vel) {
        final Pellet pellet = new Pellet();
        pellet.setRadius(5);
        final PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.RED);
        material.setSpecularColor(Color.ORANGE);
        pellet.setMaterial(material);
        pellet.setDrawMode(DrawMode.FILL);
        pellet.setPosition(pos);
        pellet.setVelocityForce(vel);
        pellet.setVisible(true);
        return pellet;
    }

    /**
     * Make a chunky box cluster.
     * @return a group containing the cluster.
     */
    public static BoxGroup makeBoxCluster() {
        final BoxGroup group = new BoxGroup();
        // take a random number between 1 and 10
        final int boxCount = random.nextInt(10)+1;
        for (int i = 0; i < boxCount; i++) {
            final Box box = new CollisionBox();
            // give the box random dimensions
            // between 10 and 40 pixels
            box.setDepth(random.nextInt(31)+10);
            box.setHeight(random.nextInt(31)+10);
            box.setWidth(random.nextInt(31)+10);
            // give the box a random rotation to make the cluster "chunky"
            final Rotate rx = new Rotate(random.nextDouble()*360d, Rotate.X_AXIS);
            final Rotate ry = new Rotate(random.nextDouble()*360d, Rotate.Y_AXIS);
            final Rotate rz = new Rotate(random.nextDouble()*360d, Rotate.Z_AXIS);
            box.getTransforms().addAll(rx, ry, rz);
            // color the box
            box.setDrawMode(DrawMode.FILL);
            final PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(Color.ANTIQUEWHITE);
            material.setSpecularColor(Color.BLUEVIOLET);
            box.setMaterial(material);
            // cull stuff that isn't visible
            box.setCullFace(CullFace.BACK);
            // add the box to the group
            group.getChildren().add(box);
        }
        return group;
    }

    public static class BoxGroup extends Group implements CollisionGroup, Moves, HasSpin {

        private double[] velocity = new double[3];
        private final Rotate rX = new Rotate(0, Rotate.X_AXIS);
        private final Rotate rY = new Rotate(0, Rotate.Y_AXIS);
        private final Rotate rZ = new Rotate(0, Rotate.Z_AXIS);

        public BoxGroup() {
            super();
            this.getTransforms().addAll(rX, rY, rZ);
        }

        @Override
        public List<CanCollide> getCollisionChildren() {
            return getChildren().stream()
                    .filter(node -> node instanceof CanCollide)
                    .map(node -> (CanCollide) node)
                    .collect(Collectors.toList());
        }

        @Override
        public double getDeltaRx() {
            return 1d;
        }

        @Override
        public double getDeltaRy() {
            return 1d;
        }

        @Override
        public double getDeltaRz() {
            return 1d;
        }

        @Override
        public Rotate getRotationX() {
            return rX;
        }

        @Override
        public Rotate getRotationY() {
            return rY;
        }

        @Override
        public Rotate getRotationZ() {
            return rZ;
        }

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
            // once they start they keep going
        }

        public void gameDirectorSetVelocity(double[] velocity) {
            this.velocity = velocity;
        }
    }

    public static class CollisionBox extends Box implements CanCollide {

        @Override
        public double[] getPosition() {
            final Parent group = getParent();
            return new double[] {
                    group.getTranslateX(),
                    group.getTranslateY(),
                    group.getTranslateZ()
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
            // these don't move on their own
            return new double[] {
                    0,0,0
            };
        }

        @Override
        public void setVelocity(double[] velocity) {
            // no-op
        }

        @Override
        public double getBoundSphereRadius() {
            // get average of their height, width, depth
            return (getHeight() + getWidth() + getDepth())/3d;
        }

        @Override
        public void setBoundSphereRadius(double radius) {
            // no-op
        }

        @Override
        public void notifyCollision(CanCollide collider) {
            if (collider instanceof Pellet) {
                getParent().setVisible(false);
            }
        }
    }
}
