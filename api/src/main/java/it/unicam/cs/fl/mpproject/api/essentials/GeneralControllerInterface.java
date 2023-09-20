package it.unicam.cs.fl.mpproject.api.essentials;

import it.unicam.cs.fl.mpproject.utilities.FollowMeParserException;

import java.io.File;
import java.io.IOException;

public interface GeneralControllerInterface {

    /**
     * Metodo che verifica se ogni robot ha eseguito il proprio comando
     * @return True se ogni robot ha eseguito il proprio comando,
     * false altrimenti
     */
    boolean hasEveryRobotFinished();

    /**
     * Metodo che consente ai robot di eseguire il proprio comando
     * successivo, assegnatogli dal programma
     */
    void performNextForAll();

    /**
     * Metodo che, a partire dal file in parametro, crea i vari
     * programmi per i robot
     * @param file Il file da cui ottenere le informazioni volute
     * @throws IOException
     * @throws FollowMeParserException
     */
    void readCMDs(File file) throws IOException, FollowMeParserException;

    /**
     * Metodo che, a partire dal file in parametro, crea le varie figure
     * @param shapesFile Il file da cui ottenere le informazioni volute
     * @throws IOException
     * @throws FollowMeParserException
     */
    void readShapes(File shapesFile) throws IOException, FollowMeParserException;

    /**
     * Metodo che, a partire dal file in parametro, crea i vari robot
     * @param robotsFile Il file da cui ottenere le informazioni volute
     * @throws IOException
     */
    void readRobots(File robotsFile) throws IOException;

}
