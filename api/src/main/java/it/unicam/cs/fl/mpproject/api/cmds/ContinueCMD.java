package it.unicam.cs.fl.mpproject.api.cmds;

import it.unicam.cs.fl.mpproject.api.models.Robot;
import it.unicam.cs.fl.mpproject.api.utils.ExceptionsLauncher;
import it.unicam.cs.fl.mpproject.api.utils.MessagesProvider;

/**
 * Classe rappresentante il comando CONTINUE
 */
public class ContinueCMD implements GeneralRobotCMD {

    /**
     * Riga d'esecuzione in cui ci si trova
     */
    private final int line;

    /**
     * Secondi totali per cui eseguire l'istruzione
     */
    private final int totalSecs;

    /**
     * Secondi per cui e' stata gia' eseguita l'istruzione
     */
    private int doneSecs;

    public ContinueCMD(int line, int totalSecs) {
        this.line = line;
        this.totalSecs = totalSecs;
        this.doneSecs = 0;
    }

    @Override
    public boolean canGoOn() {
        return !(this.doneSecs < this.totalSecs);
    }

    @Override
    public int indexGoOn() {
        if (this.doneSecs < this.totalSecs) {
            return this.line;
        } else {
            return -1;
        }
    }

    @Override
    public Robot perform(Robot robot) {
        ExceptionsLauncher.launchNullRobot(robot);
        robot.setPerformingCMD(true);
        MessagesProvider.continueCMDStarted();
        robot = new MoveCMD(robot.getDirection(), robot.getSpeed()).perform(robot);
        this.doneSecs += 1;
        robot.setPerformingCMD(false);
        return robot;
    }

    @Override
    public GeneralRobotCMD cloneCMD() {
        return new ContinueCMD(this.line, this.totalSecs);
    }

    @Override
    public boolean equalCMD(GeneralRobotCMD robotCMD) {
        if (robotCMD.getClass() == this.getClass()) {
            return this.doneSecs == ((ContinueCMD) robotCMD).doneSecs
                    && this.totalSecs == ((ContinueCMD) robotCMD).totalSecs
                    && this.line == ((ContinueCMD) robotCMD).line;
        } else {
            return false;
        }
    }

}
