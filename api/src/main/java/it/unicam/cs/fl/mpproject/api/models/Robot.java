package it.unicam.cs.fl.mpproject.api.models;

import it.unicam.cs.fl.mpproject.api.utils.ExceptionsLauncher;

/**
 * Classe che rappresenta un robot presente in griglia
 */
public class Robot {

    /**
     * Etichetta appertenente al robot
     */
    private RobotLabel label;

    /**
     * Posizione in griglia su cui si trova il robot
     */
    private final GridPos gridPos;

    /**
     * Direzione su cui si muove il robot
     */
    private Direction direction;

    /**
     * Velocita' alla quale il robot si muove
     */
    private double speed;

    /**
     * Figura associata, da rappresentare graficamente
     */
    private final RobotShape shape;

    /**
     * Valore boolean per stabilire se il robot
     * sta eseguendo un comando
     */
    private volatile boolean isPerformingCMD;

    public Robot(RobotLabel label, GridPos gridPos, Direction direction, double speed) {
        launchAll(label, gridPos, direction, speed);
        this.label = label;
        this.gridPos = gridPos;
        this.direction = direction;
        this.speed = speed;
        this.shape = new RobotShape(this.gridPos, 25.0);
        this.isPerformingCMD = false;
    }

    public Robot(RobotLabel label) {
        ExceptionsLauncher.launchNullLabel(label);
        this.label = label;
        this.gridPos = new GridPos();
        this.direction = new Direction();
        this.speed = 0.0;
        this.shape = new RobotShape(this.gridPos, 25.0);
        this.isPerformingCMD = false;
    }

    public RobotLabel getLabel() {
        return this.label;
    }

    public void setLabel(RobotLabel label) {
        ExceptionsLauncher.launchNullLabel(label);
        this.label = label;
    }

    public GridPos getGridPos() {
        return this.gridPos;
    }

    public boolean isPerformingCMD() {
        return this.isPerformingCMD;
    }

    public void setPerformingCMD(boolean performingCMD) {
        this.isPerformingCMD = performingCMD;
    }

    public void setGridPos(double x, double y) {
        this.gridPos.setGridPos(x, y);
        this.shape.setGridPos(x, y);
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        ExceptionsLauncher.launchNullDirection(direction);
        this.direction = direction;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        ExceptionsLauncher.launchWrongSpeed(speed);
        this.speed = speed;
    }

    public RobotShape getShape() {
        return this.shape;
    }

    /**
     * Metodo che riporta in stringa le caratteristiche
     * principali del robot
     * @return Messaggio di descrizione
     */
    @Override
    public String toString() {
        return "Robot{" +
                "label=" + label +
                ", gridPos=" + gridPos +
                ", direction=" + direction +
                ", speed=" + speed +
                '}';
    }

    /**
     * Metodo privato d'utilita' che lancia eccezioni sui parametri
     * @param label Etichetta che puo' lanciare eccezione
     * @param gridPos Posizione in griglia che puo' lanciare eccezione
     * @param direction Direzione che puo' lanciare eccezione
     */
    private void launchAll(RobotLabel label, GridPos gridPos, Direction direction, double speed) {
        ExceptionsLauncher.launchNullLabel(label);
        ExceptionsLauncher.launchNullGridPosition(gridPos);
        ExceptionsLauncher.launchNullDirection(direction);
        ExceptionsLauncher.launchWrongSpeed(speed);
    }

}
