package it.unicam.cs.fl.mpproject.api.cmds;

import it.unicam.cs.fl.mpproject.api.models.Robot;
import it.unicam.cs.fl.mpproject.api.utils.ExceptionsLauncher;
import it.unicam.cs.fl.mpproject.api.utils.MessagesProvider;

/**
 * Classe che rappresenta il comando DO_FOREVER
 */
public class DoForeverCMD extends CyclicCMD {

    public DoForeverCMD(int firstLine) {
        super(firstLine);
    }

    @Override
    public boolean canGoOn() {
        return this.firstLine == -1;
    }

    @Override
    public int indexGoOn() {
        return this.firstLine;
    }

    @Override
    public Robot perform(Robot robot) {
        ExceptionsLauncher.launchNullRobot(robot);
        robot.setPerformingCMD(true);
        MessagesProvider.doForeverCMDStarted();
        robot = new MoveCMD(robot.getDirection(), robot.getSpeed()).perform(robot);
        robot.setPerformingCMD(false);
        return robot;
    }

    @Override
    public GeneralRobotCMD cloneCMD() {
        DoForeverCMD duplicate = new DoForeverCMD(this.firstLine);
        duplicate.setLastLine(this.lastLine);
        return duplicate;
    }

    @Override
    public boolean equalCMD(GeneralRobotCMD robotCMD) {
        if (robotCMD.getClass() == this.getClass()) {
            return this.firstLine == ((DoForeverCMD) robotCMD).firstLine
                    && this.lastLine == ((DoForeverCMD) robotCMD).lastLine;
        } else {
            return false;
        }
    }

}
