package it.unicam.cs.fl.mpproject.api.essentials;

import it.unicam.cs.fl.mpproject.api.cmds.GeneralRobotCMD;
import it.unicam.cs.fl.mpproject.api.models.Robot;

public interface CMDsProgramInterface {

    /**
     * Metodo che esegue ogni comando della lista e lo applica
     * ad un robot in parametro
     * @param robot Il robot su cui applicare i comandi
     * @return Il robot in parametro che e' stato modificato
     */
    Robot performCMD(Robot robot);

    /**
     * Metodo che aggiunge un comando alla lista di comandi
     * da applicare successivamente ad un robot
     * @param robotCMD Il comando da aggiungere alla lista
     */
    void addCMD(GeneralRobotCMD robotCMD);

}
