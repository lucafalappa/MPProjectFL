package it.unicam.cs.fl.mpproject.api.essentials;

import it.unicam.cs.fl.mpproject.api.cmds.GeneralRobotCMD;
import it.unicam.cs.fl.mpproject.api.models.Robot;
import it.unicam.cs.fl.mpproject.api.utils.ExceptionsLauncher;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe rappresentante un programma, costituito da piu'
 * comandi, da fornire ad uno o piu' robot
 */
public class CMDsProgram implements CMDsProgramInterface {

    /**
     * Lista contentente i comandi da fornire ad un robot
     */
    private final List<GeneralRobotCMD> robotCMDs;

    /**
     * Program counter
     */
    private int pc;

    public CMDsProgram() {
        this.robotCMDs = new ArrayList<>();
        this.pc = 0;
    }

    @Override
    public Robot performCMD(Robot robot) {
        ExceptionsLauncher.launchNullRobot(robot);
        if (! (this.pc == this.robotCMDs.size()) ) {
            robot.setPerformingCMD(true);
            robot = this.robotCMDs.get(this.pc).perform(robot);
            if (this.robotCMDs.get(this.pc).indexGoOn() == -1) {
                this.pc += 1;
            } else {
                this.pc = this.robotCMDs.get(this.pc).indexGoOn();
            }
            robot.setPerformingCMD(false);
        }
        return robot;
    }

    @Override
    public void addCMD(GeneralRobotCMD robotCMD) {
        ExceptionsLauncher.launchNullCMD(robotCMD);
        this.robotCMDs.add(robotCMD);
    }

    public List<GeneralRobotCMD> getRobotCMDs() {
        return this.robotCMDs;
    }

}
