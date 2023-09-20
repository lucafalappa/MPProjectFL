package it.unicam.cs.fl.mpproject.api.cmds;

import it.unicam.cs.fl.mpproject.api.models.Direction;
import it.unicam.cs.fl.mpproject.api.models.GridPos;
import it.unicam.cs.fl.mpproject.api.models.Robot;
import it.unicam.cs.fl.mpproject.api.utils.ExceptionsLauncher;
import it.unicam.cs.fl.mpproject.api.utils.MessagesProvider;

/**
 * Classe rappresentante il comando MOVE
 */
public class MoveCMD implements GeneralRobotCMD {

    /**
     * Direzione da seguire da parte del robot
     */
    private final Direction direction;

    /**
     * Velocita' da mantenere da parte del robot
     */
    private final double speed;

    public MoveCMD(Direction direction, double speed) {
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
        MessagesProvider.moveCMDStarted();
        robotSettings(robot);
        robot.setPerformingCMD(false);
        return robot;
    }

    @Override
    public GeneralRobotCMD cloneCMD() {
        return new MoveCMD(this.direction, this.speed);
    }

    @Override
    public boolean equalCMD(GeneralRobotCMD robotCMD) {
        if (robotCMD.getClass() == this.getClass()) {
            return this.direction.getGridPos().equalGridPos(((MoveCMD) robotCMD).direction.getGridPos())
                    && this.speed == ((MoveCMD) robotCMD).speed;
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
