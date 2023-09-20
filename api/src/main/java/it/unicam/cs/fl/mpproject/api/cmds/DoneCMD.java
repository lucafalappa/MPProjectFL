package it.unicam.cs.fl.mpproject.api.cmds;

import it.unicam.cs.fl.mpproject.api.models.Robot;
import it.unicam.cs.fl.mpproject.api.utils.ExceptionsLauncher;
import it.unicam.cs.fl.mpproject.api.utils.MessagesProvider;

/**
 * Classe rappresentante il comando DONE
 */
public class DoneCMD implements GeneralRobotCMD {

    /**
     * Indice della riga in cui si trova il comando
     * associato a questo comando DONE
     */
    private final int associatedLine;

    public DoneCMD(int associatedLine) {
        this.associatedLine = associatedLine;
    }

    @Override
    public boolean canGoOn() {
        return this.associatedLine == -1;
    }

    @Override
    public int indexGoOn() {
        return this.associatedLine;
    }

    @Override
    public Robot perform(Robot robot) {
        ExceptionsLauncher.launchNullRobot(robot);
        robot.setPerformingCMD(true);
        MessagesProvider.doneCMDStarted();
        robot = new MoveCMD(robot.getDirection(), robot.getSpeed()).perform(robot);
        robot.setPerformingCMD(false);
        return robot;
    }

    @Override
    public GeneralRobotCMD cloneCMD() {
        return new DoneCMD(this.associatedLine);
    }

    @Override
    public boolean equalCMD(GeneralRobotCMD robotCMD) {
        if (robotCMD.getClass() == this.getClass()) {
            return this.associatedLine == ((DoneCMD) robotCMD).associatedLine;
        } else {
            return false;
        }
    }

}
