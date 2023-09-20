package it.unicam.cs.fl.mpproject.api.models;

import it.unicam.cs.fl.mpproject.api.utils.ExceptionsLauncher;

/**
 *  Classe per rappresentare un punto sulla griglia
 */
public class GridPos {

    /**
     * Ascissa del punto
     */
    private double xPos;

    /**
     * Ordinata del punto
     */
    private double yPos;

    /**
     * Modificatore di dimensioni per l'interfaccia grafica
     */
    public static final double GUI_RESIZER = 2.5;

    public GridPos(double x, double y) {
        this.xPos = x * GUI_RESIZER;
        this.yPos = y * GUI_RESIZER;
    }

    public GridPos() {
        this.xPos = 0.0;
        this.yPos = 0.0;
    }

    public double getXPos() {
        return this.xPos;
    }

    public double getYPos() {
        return this.yPos;
    }

    public void setGridPos(double x, double y) {
        this.xPos = x * GUI_RESIZER;
        this.yPos = y * GUI_RESIZER;
    }

    /**
     * Metodo che stabilisce se il punto passato in parametro
     * equivale al punto che invoca il confronto
     * @param pos Posizione in griglia da confrontare
     * @return True se le coordinate dei due punti coincidono,
     * false altrimenti
     */
    public boolean equalGridPos(GridPos pos) {
        ExceptionsLauncher.launchNullGridPosition(pos);
        return this.xPos == pos.getXPos()
                && this.yPos == pos.getYPos();
    }

}
