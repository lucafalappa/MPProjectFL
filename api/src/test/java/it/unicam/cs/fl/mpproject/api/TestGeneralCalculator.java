package it.unicam.cs.fl.mpproject.api;

import it.unicam.cs.fl.mpproject.api.models.Direction;
import it.unicam.cs.fl.mpproject.api.models.GridPos;
import it.unicam.cs.fl.mpproject.api.models.Robot;
import it.unicam.cs.fl.mpproject.api.models.RobotLabel;
import it.unicam.cs.fl.mpproject.api.utils.GeneralCalculator;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestGeneralCalculator {

    @Test
    void testIsBetween() {
        assertEquals(GeneralCalculator.isInTheRange(5.0, 2.0,3.0), false);
        assertEquals(GeneralCalculator.isInTheRange(5.0, 2.0,6.0), true);
        assertEquals(GeneralCalculator.isInTheRange(5.0, 2.0,5.0), true);
        assertEquals(GeneralCalculator.isInTheRange(5.0, 2.0,4.0), false);
        assertEquals(GeneralCalculator.isInTheRange(Math.PI, 2.0,4.0), true);
        assertEquals(GeneralCalculator.isInTheRange(5.01, 2.0,5.0), false);

        assertThrows(IllegalArgumentException.class, () -> GeneralCalculator.isInTheRange(27.0, 29.0, 13.5));
        assertDoesNotThrow(() -> GeneralCalculator.isInTheRange(27.0, 13.5, 29.0));
    }

    @Test
    void testAvgGridPos() {
        Robot r1 = new Robot( new RobotLabel("circle"), new GridPos(-12.0, 4.0), new Direction(), 3.6);
        Robot r2 = new Robot( new RobotLabel("rectangle"), new GridPos(4.0, -4.0), new Direction(), 3.6);
        Robot r3 = new Robot( new RobotLabel("circle"), new GridPos(8.0, 0.0), new Direction(), 3.6);
        List<Robot> robotList = new LinkedList<>();
        robotList.add(r1);
        robotList.add(r2);
        robotList.add(r3);
        assertEquals(new GridPos(0.0, 0.0).getXPos(), GeneralCalculator.avgGridPos(robotList).getXPos());
        assertEquals(new GridPos(0.0, 0.0).getYPos(), GeneralCalculator.avgGridPos(robotList).getYPos());
    }

    @Test
    void testRandomGridPos() {
        GridPos g1 = GeneralCalculator.randomGridPos(new GridPos(15.0, 15.0), new GridPos(20.0, 20.0));
        assertEquals(GeneralCalculator.isInTheRange(g1.getXPos() / GridPos.GUI_RESIZER, 15.0, 20.0), true);
        assertEquals(GeneralCalculator.isInTheRange(g1.getYPos() / GridPos.GUI_RESIZER, 15.0, 20.0), true);

        GridPos g2 = GeneralCalculator.randomGridPos(12.5);
        assertEquals(GeneralCalculator.isInTheRange(g2.getXPos() / GridPos.GUI_RESIZER, -12.5, 12.5), true);
        assertEquals(GeneralCalculator.isInTheRange(g2.getYPos() / GridPos.GUI_RESIZER, -12.5, 12.5), true);
    }

}
