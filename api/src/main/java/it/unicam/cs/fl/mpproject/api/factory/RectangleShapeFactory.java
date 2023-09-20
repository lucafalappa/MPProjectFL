package it.unicam.cs.fl.mpproject.api.factory;

import it.unicam.cs.fl.mpproject.api.models.GridPos;
import it.unicam.cs.fl.mpproject.api.models.RectangleShape;
import it.unicam.cs.fl.mpproject.api.models.ShapeInterface;
import it.unicam.cs.fl.mpproject.utilities.ShapeData;

/**
 * Classe utile a costruire una figura rettangolare
 */
public class RectangleShapeFactory implements ShapeFactoryInterface {

    @Override
    public ShapeInterface buildShape(ShapeData shapeData) {
        return new RectangleShape(new GridPos(shapeData.args()[0], shapeData.args()[1]),
                shapeData.label(), shapeData.args()[2], shapeData.args()[3]);
    }

}
