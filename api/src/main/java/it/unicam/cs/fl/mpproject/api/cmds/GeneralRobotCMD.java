package it.unicam.cs.fl.mpproject.api.cmds;

import it.unicam.cs.fl.mpproject.api.models.Robot;

/**
 * Interfaccia rappresentante un comando eseguibile su ogni robot
 */
public interface GeneralRobotCMD {

    /**
     * Metodo che specifica se e' possibile proseguire con
     * il comando successivo
     *
     * @return true e' se possibile proseguire, false altrimenti
     */
    boolean canGoOn();

    /**
     * Flag numerico di supporto al metodo canGoOn()
     *
     * @return -1 se e' possibile proseguire, il numero
     * di riga a cui tornare altrimenti
     */
    int indexGoOn();

    /**
     * Metodo che esegue il particolare comando sul robot
     *
     * @param robot Il robot su cui va eseguito il comando
     * @return Robot che ha subito le modifiche
     */
    Robot perform(Robot robot);

    /**
     * Metodo per ottenere una copia del comando, con le
     * stesse caratteristiche del comando invocante il metodo
     * @return Una copia del comando
     */
    GeneralRobotCMD cloneCMD();

    /**
     * Metodo che stabilisce se due comandi possiedono
     * caratteristiche che coincidono
     * @param robotCMD Il comando da confrontare
     * @return True se i due comandi coincidono, false
     * altrimenti
     */
    boolean equalCMD(GeneralRobotCMD robotCMD);

}
