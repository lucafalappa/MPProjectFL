package it.unicam.cs.fl.mpproject.api.parsers;

import it.unicam.cs.fl.mpproject.api.cmds.*;
import it.unicam.cs.fl.mpproject.api.essentials.CMDsProgram;
import it.unicam.cs.fl.mpproject.api.models.*;
import it.unicam.cs.fl.mpproject.api.utils.GeneralCalculator;
import it.unicam.cs.fl.mpproject.utilities.FollowMeParserHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Classe utile per effettuare il parsing dei comandi e inserirli
 * all'interno di un'istanza della classe CMDsProgram
 * che li contenga tutti
 */
public class CMDParsingHandler implements FollowMeParserHandler {

    private final List<Robot> robotList;

    private final List<ShapeInterface> shapeList;

    private final CMDsProgram program;

    private Stack<Integer> instructionNumbers;

    private Map<Integer, CyclicCMD> rowAndInstruction;

    private int cmdCounter;

    public CMDParsingHandler(List<Robot> robotList, List<ShapeInterface> shapeList) {
        this.robotList = robotList;
        this.shapeList = shapeList;
        this.program = new CMDsProgram();
    }

    /**
     * This method is the method that is invoked ad the beginning of the parse procedure.
     */
    @Override
    public void parsingStarted() {
        this.instructionNumbers = new Stack<>();
        this.rowAndInstruction = new HashMap<>();
        this.cmdCounter = 0;
    }

    /**
     * This method is the method that is invoked ad the end of the parse procedure.
     */
    @Override
    public void parsingDone() {
        this.instructionNumbers.clear();
        this.rowAndInstruction.clear();
        this.cmdCounter = 0;
    }

    /**
     * Method invoked when a command "MOVE" is parsed.
     *
     * @param args argomenti del comando.
     */
    @Override
    public void moveCommand(double[] args) {
        if (GeneralCalculator.isInTheRange(args[0], -1.0, 1.0)
                && GeneralCalculator.isInTheRange(args[1], -1.0, 1.0)) {
            this.program.addCMD(new MoveCMD(new Direction(args[0], args[1]), args[2]));
            this.cmdCounter += 1;
        }
    }

    /**
     * Method invoked when a command "MOVE RANDOM" is parsed.
     *
     * @param args argomenti del comando.
     */
    @Override
    public void moveRandomCommand(double[] args) {
        this.program.addCMD(new MoveRandomCMD(new GridPos(args[0], args[2]), new GridPos(args[1], args[3]), args[4]));
        this.cmdCounter += 1;
    }

    /**
     * Method invoked when a command "SIGNAL" is parsed.
     *
     * @param label label to signal
     */
    @Override
    public void signalCommand(String label) {
        this.program.addCMD(new SignalCMD(new RobotLabel(label, true)));
        this.cmdCounter += 1;

    }

    /**
     * Method invoked when a command "UNSIGNAL" is parsed.
     *
     * @param label label to unsignal
     */
    @Override
    public void unsignalCommand(String label) {
        this.program.addCMD(new UnsignalCMD(new RobotLabel(label, true)));
        this.cmdCounter += 1;
    }

    /**
     * Method invoked when a command "FOLLOW" is parsed.
     *
     * @param label label to follow
     * @param args  command arguments
     */
    @Override
    public void followCommand(String label, double[] args) {
        this.program.addCMD(new FollowCMD(this.robotList, new RobotLabel(label), args[0], args[1]));
        this.cmdCounter += 1;
    }

    /**
     * Method invoked when a command "STOP" is parsed.
     */
    @Override
    public void stopCommand() {
        this.program.addCMD(new StopCMD());
        this.cmdCounter += 1;
    }

    /**
     * Method invoked when a command "WAIT" is parsed.
     *
     * @param s number of seconds;
     */
    @Override
    public void continueCommand(int s) {
        this.program.addCMD(new ContinueCMD(s, this.cmdCounter));
        this.cmdCounter += 1;
    }

    /**
     * Method invoked when a command "REPEAT" is parsed.
     *
     * @param n number of iterations.
     */
    @Override
    public void repeatCommandStart(int n) {
        addCyclicCMD(new RepeatCMD(n, this.cmdCounter + 1));
    }

    /**
     * Method invoked when a command "UNTIL" is parsed.
     *
     * @param label name of a label
     */
    @Override
    public void untilCommandStart(String label) {
        addCyclicCMD(new UntilCMD(new RobotLabel(label), this.shapeList, this.cmdCounter + 1));
    }

    /**
     * Method invoked when a command "DO FOREVER" is parsed.
     */
    @Override
    public void doForeverStart() {
        addCyclicCMD(new DoForeverCMD(this.cmdCounter + 1));
    }

    /**
     * Method invoked when a command "DONE" is parsed.
     */
    @Override
    public void doneCommand() {
        int actualRow = this.instructionNumbers.pop();
        this.rowAndInstruction.get(actualRow).setLastLine(this.cmdCounter + 1);
        this.program.addCMD(new DoneCMD(actualRow));
        this.cmdCounter += 1;
    }

    public CMDsProgram getFinalProgram() {
        return this.program;
    }

    private void addCyclicCMD(CyclicCMD cyclicCMD) {
        this.rowAndInstruction.put(this.cmdCounter, cyclicCMD);
        this.instructionNumbers.add(this.cmdCounter);
        this.program.addCMD(cyclicCMD);
        this.cmdCounter += 1;
    }

}
