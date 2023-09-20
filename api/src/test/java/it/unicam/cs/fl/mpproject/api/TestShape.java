package it.unicam.cs.fl.mpproject.api;

import it.unicam.cs.fl.mpproject.api.models.CircleShape;
import it.unicam.cs.fl.mpproject.api.models.GridPos;
import it.unicam.cs.fl.mpproject.api.models.RectangleShape;
import it.unicam.cs.fl.mpproject.api.models.ShapeInterface;
import it.unicam.cs.fl.mpproject.api.utils.FigureSize;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestShape {

    @Test
    void testRSgetDimensions() {
        double width = 4.5;
        double height = 12.0;
        ShapeInterface rectangleShape = new RectangleShape(new GridPos(0.0, 0.0), "rectangle", width, height);
        FigureSize<Double, Double> dimensions = rectangleShape.getDimensions();
        assertEquals(width, dimensions.first());
        assertEquals(height, dimensions.second());
    }

    @Test
    void testSgetCoordinatesAndSetCoordinates() {
        double width = 4.5;
        double height = 12.0;
        double newX = 5.6;
        double newY = -19.2;
        ShapeInterface rectangleShape = new RectangleShape(new GridPos(), "rectangle", width, height);
        assertEquals(0.0, rectangleShape.getGridPos().getXPos());
        assertEquals(0.0, rectangleShape.getGridPos().getYPos());
        rectangleShape.getGridPos().setGridPos(newX, newY);
        assertEquals(5.6 * GridPos.GUI_RESIZER, rectangleShape.getGridPos().getXPos());
        assertEquals(-19.2 * GridPos.GUI_RESIZER, rectangleShape.getGridPos().getYPos());
    }

    @Test
    void testCSgetDimensions() {
        double radius = 0.05;
        ShapeInterface circleShape = new CircleShape(new GridPos(), "circle", radius);
        FigureSize<Double, Double> dimensions = circleShape.getDimensions();
        assertEquals(radius, dimensions.first());
        assertNotEquals(-2.0, dimensions.second());
        assertNotEquals(12.46, dimensions.second());
        assertEquals(Double.NaN, dimensions.second());
    }

}
