package it.unicam.cs.fl.mpproject.api.cmds;

import it.unicam.cs.fl.mpproject.api.models.GridPos;
import it.unicam.cs.fl.mpproject.api.models.Robot;
import it.unicam.cs.fl.mpproject.api.models.RobotLabel;
import it.unicam.cs.fl.mpproject.api.models.ShapeInterface;
import it.unicam.cs.fl.mpproject.api.utils.ExceptionsLauncher;
import it.unicam.cs.fl.mpproject.api.utils.GeneralCalculator;
import it.unicam.cs.fl.mpproject.api.utils.MessagesProvider;

import java.util.List;

/**
 * Classe rappresentante il comando UNTIL
 */
public class UntilCMD extends CyclicCMD {

    /**
     * Etichetta che, se segnalata da qualche robot,
     * fa terminare il comando
     */
    private final RobotLabel toBeFound;

    /**
     * Lista delle figure presenti in griglia alle
     * quali potrebbe essere associata l'etichetta
     */
    private final List<ShapeInterface> shapeList;

    /**
     * Valore booleano utile per segnalare se si possa
     * uscire dal comando o no
     */
    private boolean shouldQuit;

    public UntilCMD(RobotLabel toBeFound, List<ShapeInterface> shapeList, int firstRow) {
        super(firstRow);
        this.toBeFound = toBeFound;
        this.shapeList = shapeList;
        this.shouldQuit = false;
    }

    @Override
    public boolean canGoOn() {
        return this.shouldQuit;
    }

    @Override
    public int indexGoOn() {
        return this.shouldQuit ? this.lastLine : this.firstLine;
    }

    @Override
    public Robot perform(Robot robot) {
        ExceptionsLauncher.launchNullRobot(robot);
        robot.setPerformingCMD(true);
        MessagesProvider.untilCMDStarted();
        ShapeInterface shape = getFirstShapeFilteredByLabel();
        if (shape != null) {
            this.shouldQuit = shape.getDimensions().second().isNaN()
                    ? isInCircleRange(robot, shape) : isInRectangleRange(robot, shape);
        }
        robot = new MoveCMD(robot.getDirection(), robot.getSpeed()).perform(robot);
        robot.setPerformingCMD(false);
        return robot;
    }

    @Override
    public GeneralRobotCMD cloneCMD() {
        UntilCMD duplicate = new UntilCMD(this.toBeFound, this.shapeList, this.firstLine);
        duplicate.setLastLine(this.lastLine);
        return duplicate;
    }

    @Override
    public boolean equalCMD(GeneralRobotCMD robotCMD) {
        if (robotCMD.getClass() == this.getClass()) {
            return this.firstLine == ((UntilCMD) robotCMD).firstLine
                    && this.lastLine == ((UntilCMD) robotCMD).lastLine
                    && this.shouldQuit == ((UntilCMD) robotCMD).shouldQuit
                    && this.toBeFound.equalLabel(((UntilCMD) robotCMD).toBeFound)
                    && this.shapeList.equals(((UntilCMD) robotCMD).shapeList);
        } else {
            return false;
        }
    }

    /**
     * Metodo che fornisce la prima figura avente
     * la label uguale a quella da cercare
     * @return Shape avente label identica a quella cercata
     */
    private ShapeInterface getFirstShapeFilteredByLabel() {
        return this.shapeList.stream().
                filter(x -> x.getLabel().equalLabel(this.toBeFound)).findFirst().orElse(null);
    }

    /**
     * Metodo che stabilisce se il robot in parametro si trova
     * a contatto della figura rettangolare in parametro
     * @param robot Robot con l'etichetta richiesta
     * @param shape Figura rettangolare con l'etichetta richiesta
     * @return True se le due entita' sono a contatto tra loro,
     * false altrimenti
     */
    private boolean isInRectangleRange(Robot robot, ShapeInterface shape) {
        double width = shape.getDimensions().first();
        double height = shape.getDimensions().second();
        GridPos topRight = new GridPos(shape.getGridPos().getXPos() + (width / 2),
                shape.getGridPos().getYPos() + (height / 2));
        GridPos bottomLeft = new GridPos(shape.getGridPos().getXPos() - (width / 2),
                shape.getGridPos().getYPos() - (height / 2));
        return inARectangle(robot, bottomLeft, topRight);
    }

    /**
     * Metodo che stabilisce se il robot in parametro si trova
     * a contatto della figura circolare in parametro
     * @param robot Robot con l'etichetta richiesta
     * @param shape Figura circolare con l'etichetta richiesta
     * @return True se le due entita' sono a contatto tra loro,
     * false altrimenti
     */
    private boolean isInCircleRange(Robot robot, ShapeInterface shape) {
        double radius = shape.getDimensions().first();
        double distance = Math.sqrt(Math.pow(robot.getShape().getGridPos().getXPos() - shape.getGridPos().getXPos(), 2)
                + Math.pow(robot.getShape().getGridPos().getYPos() - shape.getGridPos().getYPos(), 2));
        return distance <= radius;
    }

    /**
     * Metodo d'utilita' che stabilisce se una parte del robot in
     * parametro si trova all'interno della figura rettangolare
     * le cui coordinate sono quelle specificate in parametro
     * @param robot Robot con l'etichetta richiesta
     * @param bottomLeft Estremo inferiore sinistro della figura
     * @param topRight Estremo superiore destro della figura
     * @return True se c'e' contatto tra il robot e la figura,
     * false altrimenti
     */
    private boolean inARectangle(Robot robot, GridPos bottomLeft, GridPos topRight) {
        return GeneralCalculator.isInTheRange
                (robot.getShape().getGridPos().getXPos(),
                        bottomLeft.getXPos(),
                        topRight.getXPos())
                &&
                GeneralCalculator.isInTheRange
                        (robot.getShape().getGridPos().getYPos(),
                                bottomLeft.getYPos(),
                                topRight.getYPos());
    }

}
