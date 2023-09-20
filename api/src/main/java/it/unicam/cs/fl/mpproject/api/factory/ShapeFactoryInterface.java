package it.unicam.cs.fl.mpproject.api.factory;

import it.unicam.cs.fl.mpproject.api.models.ShapeInterface;
import it.unicam.cs.fl.mpproject.utilities.ShapeData;

/**
 * Interfaccia volta a costruire una particolare figura
 * a partire dai dati ottenuti successivamente alla
 * fase di parsing
 */
public interface ShapeFactoryInterface {

    /**
     * Metodo che restituisce una figura, costruita tramite
     * i dati ottenuti dal parsing
     * @param shapeData Dati della figura
     * @return Una nuova figura
     */
    ShapeInterface buildShape(ShapeData shapeData);

}
