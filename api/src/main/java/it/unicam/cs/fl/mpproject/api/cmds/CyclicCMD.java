package it.unicam.cs.fl.mpproject.api.cmds;

/**
 * Classe rappresentante l'insieme delle istruzioni iterative
 */
public abstract class CyclicCMD implements GeneralRobotCMD {

    /**
     * Indice della prima riga del ciclo, alla quale tornare
     * nel momento in cui una singola iterazione termina
     */
    protected final int firstLine;

    /**
     * Indice della prima riga successiva al comando DONE, alla quale
     * andare nel momento in cui si e' raggiunto il numero massimo
     * di iterazioni da eseguire
     */
    protected int lastLine;

    public CyclicCMD(int firstLine) {
        this.firstLine = firstLine;
    }

    public void setLastLine(int lastLine) {
        this.lastLine = lastLine;
    }

}
