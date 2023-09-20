package it.unicam.cs.fl.mpproject.api.cmds;

import it.unicam.cs.fl.mpproject.api.models.Direction;
import it.unicam.cs.fl.mpproject.api.models.Robot;
import it.unicam.cs.fl.mpproject.api.models.RobotLabel;
import it.unicam.cs.fl.mpproject.api.utils.ExceptionsLauncher;
import it.unicam.cs.fl.mpproject.api.utils.GeneralCalculator;
import it.unicam.cs.fl.mpproject.api.utils.MessagesProvider;

import java.util.List;

/**
 * Classe rappresentante il comando FOLLOW
 */
public class FollowCMD implements GeneralRobotCMD {

    /**
     * Lista dei robot da seguire
     */
    private final List<Robot> robotsWithLabel;

    /**
     * Etichetta dei robot da seguire
     */
    private final RobotLabel toFollow;

    /**
     * Distanza massima a cui si trovano i robot
     */
    private final double distance;

    /**
     * Velocita' a cui si muove il robot su cui viene
     * eseguito il comando
     */
    private final double speed;

    /**
     * Direzione percorsa dal robot
     */
    private Direction direction;

    public FollowCMD(List<Robot> robotList, RobotLabel toFollow, double distance, double speed) {
        this.robotsWithLabel = robotList;
        this.toFollow = toFollow;
        this.distance = distance;
        this.speed = speed;
        this.direction = null;
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
        MessagesProvider.followCMDStarted();
        if (!this.robotsWithLabel.isEmpty()) {
            this.direction = new Direction(GeneralCalculator.avgGridPos(filterRobotsWithLabel(robot)));
            robot = new MoveCMD(this.direction, this.speed).perform(robot);
        } else {
            robot = new MoveCMD(new Direction(GeneralCalculator.randomGridPos(this.distance)),
                    this.speed).perform(robot);
        }
        robot.setPerformingCMD(false);
        return robot;
    }

    @Override
    public GeneralRobotCMD cloneCMD() {
        return new FollowCMD(this.robotsWithLabel, this.toFollow, this.distance, this.speed);
    }

    @Override
    public boolean equalCMD(GeneralRobotCMD robotCMD) {
        if (robotCMD.getClass() == this.getClass()) {
            return this.robotsWithLabel.equals(((FollowCMD) robotCMD).robotsWithLabel)
                    && this.toFollow.equalLabel(((FollowCMD) robotCMD).toFollow)
                    && this.direction.getGridPos().equalGridPos(((FollowCMD) robotCMD).direction.getGridPos())
                    && this.speed == ((FollowCMD) robotCMD).speed
                    && this.distance == ((FollowCMD) robotCMD).distance;
        } else {
            return false;
        }
    }

    /**
     * Metodo privato per rimuovere dalla lista dei robot
     * quelli che si trovano ad una distanza maggiore da quella data
     * e che hanno label diversa dal robot in parametro
     * @return La lista filtrata de robot
     */
    private List<Robot> filterRobotsWithLabel(Robot robot) {
        return this.robotsWithLabel.stream().
                filter(r -> GeneralCalculator.calculateDistance(robot.getGridPos(),
                r.getGridPos()) <= this.distance
                        && r.getLabel().equalLabel(this.toFollow)).toList();
    }

}
