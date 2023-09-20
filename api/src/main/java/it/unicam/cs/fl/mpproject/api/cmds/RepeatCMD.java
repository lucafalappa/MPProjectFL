package it.unicam.cs.fl.mpproject.api.cmds;

import it.unicam.cs.fl.mpproject.api.models.Robot;
import it.unicam.cs.fl.mpproject.api.utils.ExceptionsLauncher;
import it.unicam.cs.fl.mpproject.api.utils.MessagesProvider;

/**
 * Classe rappresentante il commando REPEAT
 */
public class RepeatCMD extends CyclicCMD {

    /**
     * Numero massimo di iterazioni da eseguire
     */
    private final int totalIters;

    /**
     * Numero di iterazioni gia' eseguite
     */
    private int doneIters;

    public RepeatCMD(int firstLine, int totalIters) {
        super(firstLine);
        this.totalIters = totalIters;
        this.doneIters = 0;
    }

    @Override
    public boolean canGoOn() {
        return !(this.doneIters < this.totalIters);
    }

    @Override
    public int indexGoOn() {
        return this.doneIters < this.totalIters ? this.firstLine : this.lastLine;
    }

    @Override
    public Robot perform(Robot robot) {
        ExceptionsLauncher.launchNullRobot(robot);
        robot.setPerformingCMD(true);
        MessagesProvider.repeatCMDStarted();
        robot = new MoveCMD(robot.getDirection(), robot.getSpeed()).perform(robot);
        this.doneIters += 1;
        robot.setPerformingCMD(false);
        return robot;
    }

    @Override
    public GeneralRobotCMD cloneCMD() {
        RepeatCMD duplicate = new RepeatCMD(this.firstLine, this.totalIters);
        duplicate.setLastLine(this.lastLine);
        return duplicate;
    }

    @Override
    public boolean equalCMD(GeneralRobotCMD robotCMD) {
        if (robotCMD.getClass() == this.getClass()) {
            return this.doneIters == ((RepeatCMD) robotCMD).doneIters
                    && this.totalIters == ((RepeatCMD) robotCMD).totalIters
                    && this.firstLine == ((RepeatCMD) robotCMD).firstLine
                    && this.lastLine == ((RepeatCMD) robotCMD).lastLine;
        } else {
            return false;
        }
    }

}
