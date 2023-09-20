package it.unicam.cs.fl.mpproject.api.factory;

import it.unicam.cs.fl.mpproject.api.models.CircleShape;
import it.unicam.cs.fl.mpproject.api.models.GridPos;
import it.unicam.cs.fl.mpproject.api.models.RobotLabel;
import it.unicam.cs.fl.mpproject.api.models.ShapeInterface;
import it.unicam.cs.fl.mpproject.utilities.ShapeData;

/**
 * Classe utile a costruire una figura circolare
 */
public class CircleShapeFactory implements ShapeFactoryInterface {

    @Override
    public ShapeInterface buildShape(ShapeData shapeData) {
        return new CircleShape(new GridPos(shapeData.args()[0], shapeData.args()[1]),
                new RobotLabel(shapeData.label()), shapeData.args()[2]);
    }

}
