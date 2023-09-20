package it.unicam.cs.fl.mpproject.api.cmds;

import it.unicam.cs.fl.mpproject.api.models.Robot;
import it.unicam.cs.fl.mpproject.api.utils.ExceptionsLauncher;
import it.unicam.cs.fl.mpproject.api.utils.MessagesProvider;

/**
 * Classe rappresentante il comando STOP
 */
public class StopCMD implements GeneralRobotCMD {

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
        MessagesProvider.stopCMDStarted();
        robot = new MoveCMD(robot.getDirection(), 0.0).perform(robot);
        robot.setPerformingCMD(false);
        return robot;
    }

    @Override
    public GeneralRobotCMD cloneCMD() {
        return new StopCMD();
    }

    @Override
    public boolean equalCMD(GeneralRobotCMD robotCMD) {
        if (robotCMD.getClass() == this.getClass()) {
            return this.equals(robotCMD);
        } else {
            return false;
        }
    }

}
