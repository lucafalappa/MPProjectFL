package it.unicam.cs.fl.mpproject.api.models;

import it.unicam.cs.fl.mpproject.api.utils.FigureSize;

/**
 * Classe che definisce una figura dalla forma rettangolare;
 * inoltre eredita le caratteristiche principali di una
 * figura generica
 */
public class RectangleShape extends Shape {

    /**
     * Larghezza della figura rettangolare
     */
    private final double width;

    /**
     * Altezza della figura rettangolare
     */
    private final double height;

    public RectangleShape(GridPos gridPos, String label, double width, double height) {
        super(gridPos, label);
        this.width = width;
        this.height = height;
    }

    @Override
    public FigureSize<Double, Double> getDimensions() {
        return FigureSize.generate(this.width, this.height);
    }

    /**
     * Metodo che restituisce un messaggio che descriva
     * le caratteristiche della figura rettangolare
     * @return Descrizione testuale della figura
     */
    @Override
    public String toString() {
        return "RectangleShape{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }

}
