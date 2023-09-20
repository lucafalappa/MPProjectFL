package it.unicam.cs.fl.mpproject.api.cmds;

import it.unicam.cs.fl.mpproject.api.models.Direction;
import it.unicam.cs.fl.mpproject.api.models.GridPos;
import it.unicam.cs.fl.mpproject.api.models.Robot;
import it.unicam.cs.fl.mpproject.api.utils.ExceptionsLauncher;
import it.unicam.cs.fl.mpproject.api.utils.GeneralCalculator;
import it.unicam.cs.fl.mpproject.api.utils.MessagesProvider;

/**
 * Classe rappresentante il comando MOVE_RANDOM
 */
public class MoveRandomCMD implements GeneralRobotCMD {

    /**
     * Direzione da seguire da parte del robot
     */
    private final Direction direction;

    /**
     * Velocita' da mantenere da parte del robot
     */
    private final double speed;

    public MoveRandomCMD(GridPos pos1, GridPos pos2, double speed) {
        this.direction = new Direction(GeneralCalculator.randomGridPos(pos1, pos2));
        this.speed = speed;
    }

    public MoveRandomCMD(Direction direction, double speed) {
        this.direction = direction;
        this.speed = speed;
    }

    @Override
    public boolean canGoOn() {
        return true;
    }

    @Override
    public int indexGoOn() {
        return -1;
    }

    @Override
    public Robot perform(Robot robot) {
        ExceptionsLauncher.launchNullRobot(robot);
        robot.setPerformingCMD(true);
        MessagesProvider.moveRandomCMDStarted();
        robotSettings(robot);
        robot.setPerformingCMD(false);
        return robot;
    }

    @Override
    public GeneralRobotCMD cloneCMD() {
        return new MoveRandomCMD(this.direction, this.speed);
    }

    @Override
    public boolean equalCMD(GeneralRobotCMD robotCMD) {
        if (robotCMD.getClass() == this.getClass()) {
            return this.direction.getGridPos().equalGridPos(((MoveRandomCMD) robotCMD).direction.getGridPos())
                    && this.speed == ((MoveRandomCMD) robotCMD).speed;
        } else {
            return false;
        }
    }

    /**
     * Metodo privato d'utilita' che imposta direzione e velocita'
     * del robot in parametro, in base ai valori assegnati al
     * comando stesso
     * @param robot Robot di cui impostare direzione e velocita'
     */
    private void robotSettings(Robot robot) {
        robot.setDirection(this.direction);
        robot.setSpeed(this.speed);
        robot.setGridPos(newXPos(robot), newYPos(robot));
    }

    /**
     * Metodo privato d'utilita' per il calcolo della nuova ascissa
     * per il robot
     */
    private double newXPos(Robot robot) {
        double oldX = robot.getGridPos().getXPos();
        return ((this.speed * this.direction.getGridPos().getXPos())
                + oldX) / GridPos.GUI_RESIZER;
    }

    /**
     * Metodo privato d'utilita' per il calcolo della nuova ordinata
     * per il robot
     */
    private double newYPos(Robot robot) {
        double oldY = robot.getGridPos().getYPos();
        return ((this.speed * this.direction.getGridPos().getYPos())
                + oldY) / GridPos.GUI_RESIZER;
    }

}
