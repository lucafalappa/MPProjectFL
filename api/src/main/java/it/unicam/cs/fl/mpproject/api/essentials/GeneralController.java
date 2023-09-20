package it.unicam.cs.fl.mpproject.api.essentials;

import it.unicam.cs.fl.mpproject.api.models.*;
import it.unicam.cs.fl.mpproject.api.parsers.CMDParsingHandler;
import it.unicam.cs.fl.mpproject.api.parsers.ShapeParsingHandler;
import it.unicam.cs.fl.mpproject.api.utils.ExceptionsLauncher;
import it.unicam.cs.fl.mpproject.utilities.FollowMeParser;
import it.unicam.cs.fl.mpproject.utilities.FollowMeParserException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class GeneralController implements GeneralControllerInterface {

    /**
     * Lista di Shape ottenute tramite parsing di file
     */
    private final List<ShapeInterface> shapeList;

    /**
     * Lista di Robot ottenute tramite parsing di file
     */
    private final List<Robot> robotList;

    /**
     * Programma costituito dai vari comandi ottenuti
     * tramite parsing di file
     */
    private CMDsProgram programs;

    /**
     * Parser di file
     */
    private final FollowMeParser parser;

    public GeneralController(List<ShapeInterface> shapeList, List<Robot> robotList) {
        this.shapeList = shapeList;
        this.robotList = robotList;
        final CMDParsingHandler parserHandler = new CMDParsingHandler(this.robotList, this.shapeList);
        this.parser = new FollowMeParser(parserHandler);
    }

    public GeneralController() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public boolean hasEveryRobotFinished() {
        int counter = 0;
        for (Robot robot : this.robotList) {
            if (!robot.isPerformingCMD()) {
                counter += 1;
            }
        }
        return counter == this.robotList.size();
    }

    @Override
    public void performNextForAll() {
        int oldI = 0;
        for (Robot robot : this.robotList) {
            if (!robot.isPerformingCMD()) {
                Robot modifiedRobot = this.programs.performCMD(robot);
                this.robotList.set(oldI, modifiedRobot);
            }
            oldI++;
        }
    }

    @Override
    public void readCMDs(File file) throws IOException, FollowMeParserException {
        ExceptionsLauncher.launchNullFile(file);
        this.parser.parseRobotProgram(file);
        if (this.parser.getHandler().getClass() == CMDParsingHandler.class) {
            this.programs = ((CMDParsingHandler) this.parser.getHandler()).getFinalProgram();
        }
    }

    @Override
    public void readShapes(File shapesFile) throws IOException, FollowMeParserException {
        ExceptionsLauncher.launchNullFile(shapesFile);
        List<ShapeInterface> shapeList = this.parser.parseEnvironment(shapesFile).stream()
                .map(new ShapeParsingHandler()::parsingFromShapeData)
                .toList();
        this.shapeList.addAll(shapeList);
    }

    @Override
    public void readRobots(File robotsFile) throws IOException {
        ExceptionsLauncher.launchNullFile(robotsFile);
        List<String> robotList = Files.readAllLines(robotsFile.toPath());
        for (String line : robotList) {
            String[] strings = line.trim().toUpperCase().split(" ");
            this.robotList.add(new Robot(new RobotLabel(RobotLabel.DEFAULT_LABEL, false),
                    new GridPos(Double.parseDouble(strings[1]), Double.parseDouble(strings[2])),
                    new Direction(), 0.0));
        }
    }

    public List<ShapeInterface> getShapeList() {
        return this.shapeList;
    }

    public List<Robot> getRobotList() {
        return this.robotList;
    }

    public CMDsProgram getPrograms() {
        return this.programs;
    }

}
