package dev.hornetshell.physics;

import javafx.scene.transform.Rotate;

/**
 * Add spin to things. For now fixed rate spin.
 */
public interface HasSpin {
    double getDeltaRx();
    double getDeltaRy();
    double getDeltaRz();
    Rotate getRotationX();
    Rotate getRotationY();
    Rotate getRotationZ();
}
