package it.unicam.cs.fl.mpproject.api;

import it.unicam.cs.fl.mpproject.api.models.Direction;
import it.unicam.cs.fl.mpproject.api.models.GridPos;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDirection {

    @Test
    void testDsetGridPos() {
        double wrongX = -2.0;
        double wrongY = -1.1;
        assertThrows(IllegalArgumentException.class, () -> new Direction(wrongX, wrongY));
        double x = -0.7;
        double y = 1.0;
        Direction d = new Direction();
        assertThrows(IllegalArgumentException.class, () -> d.setGridPos(wrongX, wrongY));
        d.setGridPos(x, y);
        assertEquals(x * GridPos.GUI_RESIZER, d.getGridPos().getXPos());
        assertEquals(y * GridPos.GUI_RESIZER, d.getGridPos().getYPos());
    }

}
