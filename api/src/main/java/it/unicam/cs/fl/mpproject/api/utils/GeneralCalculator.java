package it.unicam.cs.fl.mpproject.api.utils;

import it.unicam.cs.fl.mpproject.api.models.GridPos;
import it.unicam.cs.fl.mpproject.api.models.Robot;

import java.util.List;
import java.util.Random;

/**
 * Interfaccia d'utilita' che offre metodi per il calcolo
 * di alcune funzioni necessarie al funzionamento del programma
 */
public interface GeneralCalculator {

    /**
     * Metodo che restituisce una nuova posizione in griglia
     * le cui coordinate sono ottenute dalla media delle
     * coordinate di una lista di robot
     * @param robotList La lista dei robot da cui ottenere le
     * coordinate
     * @return Una nuova posizione in griglia
     */
    static GridPos avgGridPos(List<Robot> robotList) {
        double x = 0.0;
        double y = 0.0;
        for (Robot r : robotList) {
            x += r.getGridPos().getXPos();
            y += r.getGridPos().getYPos();
        }
        return new GridPos((x / robotList.size()), (y / robotList.size()));
    }

    /**
     * Metodo per il calcolo della distanza tra i due punti
     * in griglia in parametro
     * @param from Punto di partenza
     * @param to Punto d'arrivo
     * @return Distanza tra i due punti
     */
    static double calculateDistance(GridPos from, GridPos to) {
        double deltaX = Math.abs(to.getXPos() - from.getXPos());
        double deltaY = Math.abs(to.getYPos() - from.getYPos());
        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }

    /**
     * Metodo che restituisce una nuova posizione in griglia,
     * con coordinate ottenute randomicamente a partire da quelle
     * dei punti in parametro
     * @param pos1 Prima posizione in griglia
     * @param pos2 Seconda posizione in griglia
     * @return Una nuova posizione in griglia
     */
    static GridPos randomGridPos(GridPos pos1, GridPos pos2) {
        Random random = new Random();
        double x = random.nextDouble(Math.min(pos1.getXPos() / GridPos.GUI_RESIZER, pos2.getXPos() / GridPos.GUI_RESIZER),
                Math.max(pos1.getXPos() / GridPos.GUI_RESIZER, pos2.getXPos() / GridPos.GUI_RESIZER));
        double y = random.nextDouble(Math.min(pos1.getYPos() / GridPos.GUI_RESIZER, pos2.getYPos() / GridPos.GUI_RESIZER),
                Math.max(pos1.getYPos() / GridPos.GUI_RESIZER, pos2.getYPos() / GridPos.GUI_RESIZER));
        return new GridPos(x, y);
    }

    /**
     * Metodo che restituisce una nuova posizione in griglia,
     * con coordinate ottenute randomicamente in un intervallo
     * del tipo [-dist, +dist]
     * @param dist La distanza massima da considerare
     * @return Una nuova posizione in griglia
     */
    static GridPos randomGridPos(double dist) {
        Random random = new Random();
        double aDist = Math.abs(dist);
        return new GridPos(random.nextDouble(-aDist, aDist),
                random.nextDouble(-aDist, aDist));
    }

    /**
     * Metodo che verifica se il valore passato come primo parametro
     * si trovi nell'intervallo costituito dai due successivi parametri
     * @param value Valore da confrontare
     * @param min Valore minimo dell'intervallo
     * @param max Valore massimo dell'intervallo
     * @return true se il valore si trova nell'intervallo, false altrimenti
     */
    static boolean isInTheRange(double value, double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("\nMIN VALUE IS GREATER THAN MAX VALUE");
        }
        else {
            return value >= min && value <= max;
        }
    }

}
