package it.unicam.cs.fl.mpproject.api.models;

import it.unicam.cs.fl.mpproject.api.utils.FigureSize;

/**
 * Classe che definisce una figura dalla forma circolare;
 * inoltre eredita le caratteristiche principali di una
 * figura generica
 */
public class CircleShape extends Shape {

    /**
     * Raggio della figura circolare
     */
    private final double radius;

    public CircleShape(GridPos gridPos, String label, double radius) {
        super(gridPos, label);
        this.radius = radius;
    }

    public CircleShape(GridPos gridPos, RobotLabel label, double radius) {
        super(gridPos, label);
        this.radius = radius;
    }

    @Override
    public FigureSize<Double, Double> getDimensions() {
        return FigureSize.generate(this.radius, Double.NaN);
    }

    /**
     * Metodo che restituisce un messaggio che descriva
     * le caratteristiche della figura circolare
     * @return Descrizione testuale della figura
     */
    @Override
    public String toString() {
        return "CircleShape{" +
                "radius=" + radius +
                '}';
    }

}
