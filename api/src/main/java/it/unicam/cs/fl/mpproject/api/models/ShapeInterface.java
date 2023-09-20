package it.unicam.cs.fl.mpproject.api.models;

import it.unicam.cs.fl.mpproject.api.utils.FigureSize;

/**
 * Interfaccia per definire una figura,
 * in questo caso circolare o rettangolare,
 * all'interno dello spazio di riferimento.
 */
public interface ShapeInterface {

    /**
     * Metodo che restituisce la posizione
     * della figura nella griglia.
     * @return Posizione in griglia
     */
    GridPos getGridPos();

    /**
     * Metodo che restituisce l'etichetta
     * (label) della figura
     * @return Etichetta della figura
     */
    RobotLabel getLabel();

    /**
     * Metodo che crea, e restituisce, una coppia
     * di numeri caratterizzanti le dimensioni
     * della figura
     * @return Dimensioni della figura
     */
    FigureSize<Double, Double> getDimensions();

}
