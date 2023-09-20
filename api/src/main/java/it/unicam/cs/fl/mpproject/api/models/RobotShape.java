package it.unicam.cs.fl.mpproject.api.models;

/**
 * Classe che simula un oggetto di tipo Circle (javafx.scene.shape)
 * per quanto riguarda coordinate e raggio della figura
 */
public class RobotShape {

    /**
     * Posizione in griglia della figura circolare
     */
    private GridPos gridPos;

    /**
     * Raggio del cerchio
     */
    private final double radius;

    public RobotShape(GridPos gridPos, double radius) {
        this.gridPos = gridPos;
        this.radius = radius;
    }

    public GridPos getGridPos() {
        return this.gridPos;
    }

    public void setGridPos(double x, double y) {
        this.gridPos = new GridPos(x, y);
    }

    public double getRadius() {
        return radius;
    }

}
