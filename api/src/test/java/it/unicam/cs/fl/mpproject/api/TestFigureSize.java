package it.unicam.cs.fl.mpproject.api;

import it.unicam.cs.fl.mpproject.api.utils.FigureSize;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestFigureSize {

    @Test
    void testFSgenerate() {
        double f = 2.5;
        double s = 14.9;
        assertThrows(NullPointerException.class, () -> FigureSize.generate(null, 3.4));
        assertThrows(NullPointerException.class, () -> FigureSize.generate(3.4, null));
        FigureSize<Double, Double> figureSize = new FigureSize<>(f, s);
        assertEquals(figureSize.first(), FigureSize.generate(f, s).first());
        assertEquals(figureSize.second(), FigureSize.generate(f, s).second());
    }

}
