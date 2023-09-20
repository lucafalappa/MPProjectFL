package it.unicam.cs.fl.mpproject.api.utils;

/**
 * Interfaccia d'utilita' per stampare predefiniti messaggi su terminale
 */
public interface MessagesProvider {

    static void continueCMDStarted() {System.out.println("\nCONTINUE HAS BEEN STARTED");}

    static void doForeverCMDStarted() {System.out.println("\nDO FOREVER HAS BEEN STARTED");}

    static void doneCMDStarted() {System.out.println("\nDONE HAS BEEN STARTED");}

    static void followCMDStarted() {System.out.println("\nFOLLOW HAS BEEN STARTED");}

    static void moveCMDStarted() {System.out.println("\nMOVE HAS BEEN STARTED");}

    static void moveRandomCMDStarted() {System.out.println("\nMOVE RANDOM HAS BEEN STARTED");}

    static void repeatCMDStarted() {System.out.println("\nREPEAT HAS BEEN STARTED");}

    static void signalCMDStarted() {System.out.println("\nSIGNAL HAS BEEN STARTED");}

    static void stopCMDStarted() {System.out.println("\nSTOP HAS BEEN STARTED");}

    static void unsignalCMDStarted() {System.out.println("\nUNSIGNAL HAS BEEN STARTED");}

    static void untilCMDStarted() {System.out.println("\nUNTIL HAS BEEN STARTED");}

    static void loadedShapes() {System.out.println("\nLOADED SHAPES");}

    static void loadedPrograms() {System.out.println("\nLOADED PROGRAMS");}

    static void loadedRobots() {System.out.println("\nLOADED ROBOTS");}

    static void loadedRandomRobots() {System.out.println("\nLOADED ROBOTS RANDOMLY");}

    static void resetAllExecuted() {System.out.println("\nRESET ALL HAS BEEN EXECUTED");}

    static void initializedGUI() {System.out.println("\nWELCOME TO ROBOT SWARM SIMULATION!");}

}
