package dev.hornetshell.physics;

/**
 * Indicates a player controls this.
 */
public interface PlayerControlled {
    boolean isAlive();
    void setAlive(boolean alive);
}
