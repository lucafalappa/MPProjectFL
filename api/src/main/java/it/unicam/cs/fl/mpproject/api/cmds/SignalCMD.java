package it.unicam.cs.fl.mpproject.api.cmds;

import it.unicam.cs.fl.mpproject.api.models.Robot;
import it.unicam.cs.fl.mpproject.api.models.RobotLabel;
import it.unicam.cs.fl.mpproject.api.utils.ExceptionsLauncher;
import it.unicam.cs.fl.mpproject.api.utils.MessagesProvider;

/**
 * Classe rappresentante il comando SIGNAL
 */
public class SignalCMD implements GeneralRobotCMD {

    /**
     * Etichetta da segnalare
     */
    private final RobotLabel toSignal;

    public SignalCMD(RobotLabel toSignal) {
        this.toSignal = toSignal;
    }

    public SignalCMD(String label) {
        this.toSignal = new RobotLabel(label, true);
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
        MessagesProvider.signalCMDStarted();
        robot.setLabel(this.toSignal);
        robot.setPerformingCMD(false);
        return robot;
    }

    @Override
    public GeneralRobotCMD cloneCMD() {
        return new SignalCMD(this.toSignal);
    }

    @Override
    public boolean equalCMD(GeneralRobotCMD robotCMD) {
        if (robotCMD.getClass() == this.getClass()) {
            return this.toSignal.equalLabel(((SignalCMD) robotCMD).toSignal);
        } else {
            return false;
        }
    }

}
