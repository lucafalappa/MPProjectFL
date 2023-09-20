package it.unicam.cs.fl.mpproject.api.parsers;

import it.unicam.cs.fl.mpproject.api.factory.CircleShapeFactory;
import it.unicam.cs.fl.mpproject.api.factory.RectangleShapeFactory;
import it.unicam.cs.fl.mpproject.api.factory.ShapeFactoryInterface;
import it.unicam.cs.fl.mpproject.api.models.ShapeInterface;
import it.unicam.cs.fl.mpproject.api.utils.ExceptionsLauncher;
import it.unicam.cs.fl.mpproject.utilities.ShapeData;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe utile per effettuare il parsing delle figure e inserirle
 * all'interno di una mappa che le contenga tutte
 */
public class ShapeParsingHandler {

    /**
     * Mappa contenente le varie figure da costruire
     */
    private final Map<String, ShapeFactoryInterface> shapeFactoryMap = new HashMap<>();

    public ShapeParsingHandler() {
        this.shapeFactoryMap.put("RECTANGLE", new RectangleShapeFactory());
        this.shapeFactoryMap.put("CIRCLE", new CircleShapeFactory());
    }

    /**
     * Metodo che costruisce una figura a partire da un'istanza di
     * ShapeData, dopo che venga controllata la presenza dell'etichetta
     * all'interno della mappa
     * @param shapeData Dati della figura
     * @return Una nuova figura costruita
     */
    public ShapeInterface parsingFromShapeData(ShapeData shapeData) throws IllegalArgumentException {
        ExceptionsLauncher.launchNullShapeData(shapeData);
        if (this.shapeFactoryMap.containsKey(shapeData.shape())) {
            return this.shapeFactoryMap.get(shapeData.shape()).buildShape(shapeData);
        } else {
            throw new IllegalArgumentException("\n" +
                    "THE PROGRAM DOESN'T INCLUDE THE SHAPE IN THE PARAMETER");
        }
    }

}
