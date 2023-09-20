package it.unicam.cs.fl.mpproject.api.models;

import it.unicam.cs.fl.mpproject.api.utils.ExceptionsLauncher;
import it.unicam.cs.fl.mpproject.api.utils.GeneralCalculator;

/**
 * Classe rappresentante una direzione da prendere per il
 * robot, costituita da un punto in griglia da seguire
 * (determinante il coefficiente angolare della retta
 * su cui si muove il robot)
 */
public class Direction {

    /**
     * Posizione (punto) in griglia
     */
    private final GridPos gridPos;

    public Direction(double xDir, double yDir) {
        acceptParameters(xDir, yDir);
        this.gridPos = new GridPos(xDir, yDir);
    }

    public Direction(GridPos gridPos) {
        ExceptionsLauncher.launchNullGridPosition(gridPos);
        this.gridPos = gridPos;
    }

    public Direction() {
        this.gridPos = new GridPos();
    }

    public GridPos getGridPos() {
        return this.gridPos;
    }

    /**
     * Metodo per modificare le coordinate della
     * direzione, controllando che entrambe rimangano
     * nell'intervallo [-1.0, +1.0]
     * @param x Ascissa del punto in griglia
     * @param y Ordinata del punto in griglia
     */
    public void setGridPos(double x, double y) {
        acceptParameters(x, y);
        this.gridPos.setGridPos(x, y);
    }

    /**
     * Metodo che controlla se le coordinate in parametro
     * siano all'interno dell'intervallo [-1.0, +1.0]
     *
     * @param x Ascissa del punto in griglia
     * @param y Ordinata del punto in griglia
     * @throws IllegalArgumentException Se c'e' almeno una
     * coordinata passata che non si trova nell'intervallo
     */
    private void acceptParameters(double x, double y) throws IllegalArgumentException {
        if (!(GeneralCalculator.isInTheRange(x, -1.0, 1.0)
                && GeneralCalculator.isInTheRange(y, -1.0, 1.0))) {
            ExceptionsLauncher.launchNotInRange();
        }
    }

}
