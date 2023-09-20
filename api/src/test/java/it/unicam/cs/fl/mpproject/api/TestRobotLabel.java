package it.unicam.cs.fl.mpproject.api;

import it.unicam.cs.fl.mpproject.api.models.CircleShape;
import it.unicam.cs.fl.mpproject.api.models.GridPos;
import it.unicam.cs.fl.mpproject.api.models.RobotLabel;
import it.unicam.cs.fl.mpproject.api.models.ShapeInterface;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRobotLabel {

    @Test
    void testLequalObject() {
        ShapeInterface shape = new CircleShape(new GridPos(), new RobotLabel("circle"), 12.0);
        Object o = new RobotLabel("CIRCLE");
        assertFalse(shape.getLabel().equalObject(o));
        o = new RobotLabel("circle");
        assertTrue(shape.getLabel().equalObject(o));
    }

    @Test
    void testLequalLabel() {
        ShapeInterface shape = new CircleShape(new GridPos(), new RobotLabel("circle"), 12.0);
        RobotLabel l = new RobotLabel("CIRCLE", true);
        assertFalse(shape.getLabel().equalLabel(l));
        l.setVisibility(false);
        assertTrue(shape.getLabel().equalLabel(l));
    }

}
