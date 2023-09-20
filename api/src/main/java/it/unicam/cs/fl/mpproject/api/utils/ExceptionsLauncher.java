package it.unicam.cs.fl.mpproject.api.utils;

import it.unicam.cs.fl.mpproject.api.cmds.GeneralRobotCMD;
import it.unicam.cs.fl.mpproject.api.models.Direction;
import it.unicam.cs.fl.mpproject.api.models.GridPos;
import it.unicam.cs.fl.mpproject.api.models.Robot;
import it.unicam.cs.fl.mpproject.api.models.RobotLabel;
import it.unicam.cs.fl.mpproject.utilities.ShapeData;

import java.io.File;

/**
 * Interfaccia d'utilita' per il lancio di eccezioni nelle classi
 * dei vari pacchetti
 */
public interface ExceptionsLauncher {

    static void launchNullRobot(Robot robot) {
        if (robot == null) {
            throw new NullPointerException("\n" +
                    "A NULL ROBOT IS PRESENT IN THE PARAMETER");
        }
    }

    static void launchNullCMD(GeneralRobotCMD robotCMD) {
        if (robotCMD == null) {
            throw new NullPointerException("\n" +
                    "A NULL CMD IS PRESENT IN THE PARAMETER");
        }
    }

    static void launchNullFile(File file) {
        if (file == null) {
            throw new NullPointerException("\n" +
                    "A NULL FILE IS PRESENT IN THE PARAMETER");
        }
    }

    static void launchNullShapeData(ShapeData shapeData) {
        if (shapeData == null) {
            throw new NullPointerException("\n" +
                    "A NULL SHAPEDATA IS PRESENT IN THE PARAMETER");
        }
    }

    static void launchNullGridPosition(GridPos gridPos) {
        if (gridPos == null) {
            throw new NullPointerException("\n" +
                    "A NULL GRID POSITION IS PRESENT IN THE PARAMETER");
        }
    }

    static void launchNullLabel(RobotLabel label) {
        if (label == null) {
            throw new NullPointerException("\n" +
                    "A NULL LABEL IS PRESENT IN THE PARAMETER");
        }
    }

    static void launchNullDirection(Direction direction) {
        if (direction == null) {
            throw new NullPointerException("\n" +
                    "A NULL DIRECTION IS PRESENT IN THE PARAMETER");
        }
    }

    static void launchWrongSpeed(double speed) {
        if (speed < 0.0) {
            throw new IllegalArgumentException("\n" +
                    "A WRONG SPEED IS PRESENT IN THE PARAMETER");
        }
    }

    static void launchDifferentLabel() {
        throw new IllegalArgumentException("\n" +
                "ROBOT IN PARAMETER HASN'T THE SAME LABEL TO UNSIGNAL");
    }

    static void launchNotInRange() {
        throw new IllegalArgumentException("\n" +
                "COORDINATES IN THE PARAMETER AREN'T IN THE RANGE [-1, 1]");
    }

}
