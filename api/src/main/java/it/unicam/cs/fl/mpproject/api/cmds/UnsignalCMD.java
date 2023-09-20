package it.unicam.cs.fl.mpproject.api.cmds;

import it.unicam.cs.fl.mpproject.api.models.Robot;
import it.unicam.cs.fl.mpproject.api.models.RobotLabel;
import it.unicam.cs.fl.mpproject.api.utils.ExceptionsLauncher;
import it.unicam.cs.fl.mpproject.api.utils.MessagesProvider;

/**
 * Classe rappresentante il comando UNSIGNAL
 */
public class UnsignalCMD implements GeneralRobotCMD {

    /**
     * Etichetta posseduta da alcuni robot, i quali,
     * successivamente al comando UNSIGNAL, smettono
     * di segnalarla
     */
    private final RobotLabel toUnsignal;

    public UnsignalCMD(RobotLabel toUnsignal) {
        assert toUnsignal.isVisible();
        this.toUnsignal = toUnsignal;
    }

    public UnsignalCMD(String label) {
        this.toUnsignal = new RobotLabel(label, true);
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
    public Robot perform(Robot robot) throws IllegalArgumentException {
        ExceptionsLauncher.launchNullRobot(robot);
        robot.setPerformingCMD(true);
        MessagesProvider.unsignalCMDStarted();
        if (!robot.getLabel().equalLabel(this.toUnsignal)) {
            ExceptionsLauncher.launchDifferentLabel();
        } else {
            robot.getLabel().setVisibility(false);
            this.toUnsignal.setVisibility(false);
        }
        robot.setPerformingCMD(false);
        return robot;
    }

    @Override
    public GeneralRobotCMD cloneCMD() {
        return new UnsignalCMD(this.toUnsignal);
    }

    @Override
    public boolean equalCMD(GeneralRobotCMD robotCMD) {
        if (robotCMD.getClass() == this.getClass()) {
            return this.toUnsignal.equalLabel(((UnsignalCMD) robotCMD).toUnsignal);
        } else {
            return false;
        }
    }

}
