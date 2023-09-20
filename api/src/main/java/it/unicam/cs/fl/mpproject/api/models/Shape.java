package it.unicam.cs.fl.mpproject.api.models;

/**
 * Classe astratta che determina le caratteristiche
 * principali di una figura, ossia posizione in griglia
 * e etichetta associata.
 */
public abstract class Shape implements ShapeInterface {

    /**
     * Posizione in griglia di una figura
     */
    private final GridPos gridPos;

    /**
     * Etichetta associata ad una figura
     */
    private final RobotLabel label;

    protected Shape(GridPos gridPos, String label) {
        this.gridPos = gridPos;
        this.label = new RobotLabel(label);
    }

    protected Shape(GridPos gridPos, RobotLabel label) {
        this.gridPos = gridPos;
        this.label = label;
    }

    @Override
    public GridPos getGridPos() {
        return gridPos;
    }

    @Override
    public RobotLabel getLabel() {
        return label;
    }

}
