package it.unicam.cs.fl.mpproject.api;

import it.unicam.cs.fl.mpproject.api.models.CircleShape;
import it.unicam.cs.fl.mpproject.api.models.RectangleShape;
import it.unicam.cs.fl.mpproject.api.models.ShapeInterface;
import it.unicam.cs.fl.mpproject.api.parsers.ShapeParsingHandler;
import it.unicam.cs.fl.mpproject.utilities.ShapeData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestShapeParsingHandler {

    private ShapeParsingHandler parsingHandler;

    @BeforeEach
    void setUpParsingHandler() {
        this.parsingHandler = new ShapeParsingHandler();
    }

    @Test
    void testShapeParsing() {
        ShapeData finalShapeData1 = new ShapeData("contadino", "SQUARE", new double[]{3.0, 3.0, 3.0});
        assertThrows(IllegalArgumentException.class, () -> this.parsingHandler.parsingFromShapeData(finalShapeData1));
        ShapeData finalShapeData2 = new ShapeData("contadino", "RECTANGLE", new double[]{3.0, 3.0, 3.0, 3.0});
        assertDoesNotThrow(() -> this.parsingHandler.parsingFromShapeData(finalShapeData2));

        ShapeData shapeData1 = new ShapeData("cameriere", "RECTANGLE", new double[]{4.25, 4.25, 4.25, 4.25});
        ShapeInterface shape1 = this.parsingHandler.parsingFromShapeData(shapeData1);
        assertEquals(shape1.getLabel().getString(), shapeData1.label());
        assertEquals(RectangleShape.class, shape1.getClass());
        ShapeData shapeData2 = new ShapeData("cameriere", "CIRCLE", new double[]{2.7, 2.7, 2.7});
        ShapeInterface shape2 = this.parsingHandler.parsingFromShapeData(shapeData2);
        assertEquals(shape2.getLabel().getString(), shapeData1.label());
        assertEquals(CircleShape.class, shape2.getClass());
    }

}
