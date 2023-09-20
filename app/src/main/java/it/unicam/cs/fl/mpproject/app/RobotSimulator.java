package it.unicam.cs.fl.mpproject.app;

import it.unicam.cs.fl.mpproject.api.essentials.GeneralController;
import it.unicam.cs.fl.mpproject.api.models.*;
import it.unicam.cs.fl.mpproject.api.utils.FigureSize;
import it.unicam.cs.fl.mpproject.api.utils.GeneralCalculator;
import it.unicam.cs.fl.mpproject.api.utils.MessagesProvider;
import it.unicam.cs.fl.mpproject.utilities.FollowMeParserException;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RobotSimulator {

    @FXML
    public VBox box1 = new VBox();

    @FXML
    public VBox box2 = new VBox();

    @FXML
    public Group group = new Group();

    @FXML
    public Pane pane = new Pane();

    @FXML
    public Pane mainPane = new Pane();

    /**
     * Controller generale per la gestione del parsing ed esecuzione
     * dei comandi
     */
    private GeneralController controller;

    /**
     * Mappa contenente i robot e le figure circolari associate
     */
    private final Map<Robot, Circle> robotCircleMap = new HashMap<>();

    private final Map<Circle, Label> circleLabelMap = new HashMap<>();

    //region ROBOTSIMULATOR BUILDER

    public RobotSimulator() {
        this.controller = new GeneralController();
        initializeGUI();
        MessagesProvider.initializedGUI();
    }

    //endregion

    //region INITIALIZING GUI

    /**
     * Metodo che imposta: il clip per il pannello grafico su cui
     * vengono mostrati i robot e le figure, il traslatore delle
     * coordinate basato sulle dimensioni del pannello
     */
    public void initializeGUI() {
        clipOutsiders(this.pane);
    }

    //endregion

    //region DRAWING

    /**
     * Metodo che, per ogni shape contenuta nella lista del
     * controller generale, esegue su di essa il metodo
     * drawShapes(shapeInterface)
     */
    private void drawShapes() {
        this.controller.getShapeList()
                .forEach(x -> drawShapes(x));
    }

    /**
     * Metodo che crea nuove figure tramite due metodi, distinti in base
     * alla forma della figura da rappresentare
     * @param shapeInterface Il tipo di figura da rappresentare
     */
    private void drawShapes(ShapeInterface shapeInterface) {
        if (!shapeInterface.getDimensions().second().equals(Double.NaN)) {
            drawRectangle(shapeInterface);
        } else {
            drawCircle(shapeInterface);
        }
    }

    /**
     * Metodo per la creazione di figure circolari
     * @param shapeInterface La figura circolare da cui ottenere
     *                       le informazioni necessarie
     */
    private void drawCircle(ShapeInterface shapeInterface) {
        Circle c = new Circle(shapeInterface.getDimensions().first());
        GridPos screenPos = shapeInterface.getGridPos();
        c.setCenterX(screenPos.getXPos());
        c.setCenterY(screenPos.getYPos());
        addStandardPropertiesToCircle(c);
        Label label = createTXTfromCircle(c, shapeInterface.getLabel().getString());
        this.group.getChildren().addAll(c, label);
    }

    /**
     * Metodo per la creazione di figure rettangolari
     * @param shapeInterface La figura rettangolare da cui ottenere
     *                       le informazioni necessarie
     */
    private void drawRectangle(ShapeInterface shapeInterface) {
        FigureSize<Double, Double> position = getPosOfRectangle(shapeInterface);
        Rectangle r = new Rectangle(position.first(), position.second(),
                shapeInterface.getDimensions().first(), shapeInterface.getDimensions().second());
        addStandardPropertiesToRectangle(r);
        Label label = createTXTfromRectangle(r, shapeInterface.getLabel().getString());
        this.group.getChildren().addAll(r, label);
    }

    /**
     * Metodo che, basandosi sulla lista di robot del controller
     * generale, costruisce la stessa quantita' di figure e le
     * aggiunge al gruppo di figure mostrato sul pannello grafico
     */
    private void drawRobots() {
        this.controller.getRobotList().forEach(r -> {
            Circle c = new Circle(r.getShape().getGridPos().getXPos(), r.getShape().getGridPos().getYPos(), r.getShape().getRadius());
            Label l = createTXTfromCircle(c, r.getLabel().getString());
            l.setVisible(r.getLabel().isVisible());
            addStandardPropertiesToRobot(c);
            this.robotCircleMap.put(r, c);
            this.circleLabelMap.put(c, l);
            this.group.getChildren().addAll(c, l);
        });

    }

    //endregion

    //region GETTING

    /**
     * Metodo che calcola e restituisce la posizione del rettangolo in
     * parametro, basandola sulle dimensioni del pannello grafico
     * @param shapeInterface La figura di cui calcolare le nuove coordinate
     * @return Le nuove coordinate della figura in parametro
     */
    private FigureSize<Double, Double> getPosOfRectangle(ShapeInterface shapeInterface) {
        GridPos screenPos = shapeInterface.getGridPos();
        double x = screenPos.getXPos() - shapeInterface.getDimensions().first();
        double y = screenPos.getYPos() - shapeInterface.getDimensions().second();
        return FigureSize.generate(x, y);
    }

    //endregion

    //region CREATING

    /**
     * Metodo che crea una nuova istanza di FileChooser per la scelta
     * del file di cui si vuole effettuare il parsing
     */
    private FileChooser createFileChooser(String type) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(type, "*.txt"));
        System.out.println("\nCHOOSE REQUESTED FILE");
        return fileChooser;
    }

    /**
     * Metodo che crea la casella di testo in base alle dimensioni
     * e posizione del rettangolo in parametro
     *
     * @param r     Il rettangolo da cui raccogliere le informazioni per
     *              costruire la casella di testo
     * @param label La stringa da contenere nella casella
     * @return La nuova casella di testo con le caratteristiche
     * necessarie
     */
    private Label createTXTfromRectangle(Rectangle r, String label) {
        Label l = new Label(label);
        addStandardPropertiesToFile(l, r);
        return l;
    }

    /**
     * Metodo che crea la casella di testo in base alle dimensioni
     * e posizione del cerchio in parametro
     *
     * @param c     Il cerchio da cui raccogliere le informazioni per
     *              costruire la casella di testo
     * @param label La stringa da contenere nella casella
     * @return La nuova casella di testo con le caratteristiche
     * necessarie
     */
    private Label createTXTfromCircle(Circle c, String label) {
        Label l = new Label(label);
        addStandardPropertiesToFile(l, c);
        return l;
    }

    /**
     * Metodo che consente di generare un certo numero di robot,
     * posizionati all'interno dell'intervallo determinato dalle
     * due posizioni in griglia passate in parametro
     * @param totRobots Il numero di robot da generare randomicamente
     * @param minPos La minima posizione in griglia dei nuovi robot
     * @param maxPos La massima posizione in griglia dei nuovi robot
     */
    private void createRandomRobots(int totRobots, GridPos minPos, GridPos maxPos) {
        for (int i = 0; i < totRobots; i++) {
            this.controller.getRobotList().add(new Robot(new RobotLabel(""), GeneralCalculator.randomGridPos(minPos, maxPos),
                    new Direction(), 0.0));
        }
    }

    //endregion

    //region MAKING EVENTS

    /**
     * Metodo che fa partire l'esecuzione dei comandi sui robot
     * escludendo quelli che non hanno ancora terminato l'esecuzione
     * di un precedente comando
     * @param me L'evento interattivo che aziona l'esecuzione
     */
    public void clickPerform(ActionEvent me) {
        if (this.controller.hasEveryRobotFinished()) {
            this.controller.performNextForAll();
            updateAllCircles();
        }
    }

    /**
     * Metodo che esegue il reset del programma e azzera le strutture
     * dati, cosi' da poter ricominciare con l'esecuzione senza
     * chiudere l'applicazione
     * @param me L'evento interattivo che aziona il reset
     */
    private void clickResetAll(ActionEvent me) {
        this.robotCircleMap.clear();
        this.circleLabelMap.clear();
        this.controller = new GeneralController();
        this.group.getChildren().clear();
        MessagesProvider.resetAllExecuted();
    }

    private void clickLoadShapes(ActionEvent me) {
        initializeGUI();
        try {
            loadShapes(me);
        } catch (FollowMeParserException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void clickLoadRobotsAndPrograms(ActionEvent me) {
        loadRobots(me);
        try {
            loadPrograms(me);
        } catch (FollowMeParserException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    //endregion

    //region MAKING VISUAL EVENTS

    @FXML
    public void visualPerform(ActionEvent me) {
        clickPerform(me);
    }

    @FXML
    public void visualResetAll(ActionEvent me) {
        clickResetAll(me);
        this.box1.getChildren().get(0).setDisable(false);
        this.box1.getChildren().get(1).setDisable(true);
        this.box2.getChildren().forEach(x -> x.setDisable(true));
    }

    @FXML
    public void visualLoadShapes(ActionEvent me) {
        clickLoadShapes(me);
        this.box1.getChildren().get(0).setDisable(true);
        this.box1.getChildren().get(1).setDisable(false);
    }

    @FXML
    public void visualLoadRobotsAndPrograms(ActionEvent me) {
        clickLoadRobotsAndPrograms(me);
        this.box1.getChildren().forEach(x -> x.setDisable(true));
        this.box2.getChildren().forEach(x -> x.setDisable(false));
    }

    //endregion

    //region ADDING PROPERTIES

    /**
     * Metodo che applica proprieta' predefinite alla figura in parametro
     * @param c Il cerchio su cui applicare le proprieta'
     */
    private void addStandardPropertiesToCircle(Circle c) {
        c.setFill(Color.TRANSPARENT);
        c.setStroke(Color.BLACK);
        c.setStrokeWidth(1);
    }

    /**
     * Metodo che applica proprieta' predefinite alla figura in parametro
     * @param r Il rettangolo su cui applicare le proprieta'
     */
    private void addStandardPropertiesToRectangle(Rectangle r) {
        r.setFill(Color.TRANSPARENT);
        r.setStroke(Color.BLACK);
        r.setStrokeWidth(1);
    }

    /**
     * Metodo che applica proprieta' predefinite al robot
     * @param c Il cerchio appartenente al robot su cui
     *          applicare le proprieta'
     */
    private void addStandardPropertiesToRobot(Circle c) {
        c.setFill(Color.CYAN);
        c.setStroke(Color.BLACK);
        c.setStrokeWidth(1);
        c.setTranslateX(0);
        c.setTranslateY(0);
    }

    /**
     * Metodo che applica proprieta' predefinite alla casella di testo,
     * correlata alla figura circolare in parametro
     * @param t La casella di testo su cui applicare le proprieta'
     * @param c Il cerchio correlato alla casella di testo
     */
    private void addStandardPropertiesToFile(Label t, Circle c) {
        t.setFont(Font.font("Calibri Light", 14));
        t.setTextFill(Color.BLACK);
        t.setLayoutX(c.getCenterX() - t.getLayoutBounds().getWidth() / 2);
        t.setLayoutY(c.getCenterY() + t.getLayoutBounds().getHeight() / 4);
    }

    /**
     * Metodo che applica proprieta' predefinite alla casella di testo,
     * correlata alla figura rettangolare in parametro
     * @param t La casella di testo su cui applicare le proprieta'
     * @param r Il rettangolo correlato alla casella di testo
     */
    private void addStandardPropertiesToFile(Label t, Rectangle r) {
        t.setFont(Font.font("Calibri Light", 14));
        t.setTextFill(Color.BLACK);
        t.setLayoutX(r.getX());
        t.setLayoutY(r.getY() + r.getLayoutBounds().getHeight() / 4);
    }

    //endregion

    //region LOADING

    /**
     * Metodo d'utilita' che genera un certo numero di robot
     * in modo randomico in base alle dimensioni del pannello
     */
    private void loadRandomRobots() {
        createRandomRobots(10,
                new GridPos(-(this.pane.getPrefWidth()/4), -(this.pane.getPrefHeight()/4)),
                new GridPos((this.pane.getPrefWidth()/4), (this.pane.getPrefHeight()/4)));
    }

    /**
     * Metodo che aziona il parsing dei comandi da parte del
     * controller generale
     * @param me L'evento che aziona il parsing dei comandi
     * @throws FollowMeParserException
     * @throws IOException
     */
    private void loadPrograms(ActionEvent me) throws FollowMeParserException, IOException {
        this.controller.readCMDs(createFileChooser("programs").showOpenDialog(((Node)me.getSource())
                .getScene().getWindow()));
        MessagesProvider.loadedPrograms();
    }

    /**
     * Metodo che aziona il parsing delle figure da parte del
     * controller generale, e successivamente la creazione
     * delle figure stesse
     * @param me L'evento che aziona il parsing delle figure
     *           e la loro creazione
     * @throws FollowMeParserException
     * @throws IOException
     */
    private void loadShapes(ActionEvent me) throws FollowMeParserException, IOException {
        this.controller.readShapes(createFileChooser("shapes").showOpenDialog(((Node)me.getSource())
                .getScene().getWindow()));
        MessagesProvider.loadedShapes();
        drawShapes();
    }

    /**
     * Metodo che aziona il parsing dei robot, il loro
     * inserimento nella mappa contentente anche le figure
     * circolari costruite basandosi sui parametri dei robot
     * (contenuti nella classe FakeCircle), e la creazione
     * di essi
     * @param me L'evento che aziona il parsing dei robot e
     *           la loro creazione
     */
    private void loadRobots(ActionEvent me) {
        try {
            File selected = createFileChooser("robots").showOpenDialog(((Node)me.getSource())
                    .getScene().getWindow());
            if (selected != null) {
                this.controller.readRobots(selected);
                MessagesProvider.loadedRobots();
            } else {
                loadRandomRobots();
                MessagesProvider.loadedRandomRobots();
            }
            drawRobots();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    //endregion

    //region PRIVATE

    /**
     * Metodo d'utilita' che taglia le figure e i robot che si
     * collocano, in parte o totalmente, fuori dal pannello
     * grafico adibito a mostrarli
     * @param region La regione su cui applicare il Clip,
     *               quindi il pannello grafico
     */
    private void clipOutsiders(Region region) {
        final Rectangle clipper = new Rectangle(this.pane.getPrefWidth(), this.pane.getPrefHeight());
        clipper.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: black;");
        clipper.setStrokeWidth(1);
        region.setClip(clipper);
    }

    /**
     * Metodo che esegue l'aggiornamento delle posizioni di ogni singolo robot
     * verificando anche di aver cambiato posizione alla casella di testo
     * contenente la label
     */
    private void updateAllCircles() {
        this.robotCircleMap.forEach((r, c) -> {
            Label l = this.circleLabelMap.get(c);
            labelSettings(l, r);
            circleSettings(c, r);
            this.circleLabelMap.replace(c, l);
            performTransitions(r, c, l);
        });
    }

    /**
     * Metodo che esegue la transizione visiva sia del robot,
     * sia dell'etichetta associata
     * @param r Robot
     * @param c Figura circolare appartenente al robot
     * @param l Etichetta associata al robot
     */
    private void performTransitions(Robot r, Circle c, Label l) {
        TranslateTransition circleTransition = new TranslateTransition(Duration.ONE, c);
        circleTransition.setToX(r.getShape().getGridPos().getXPos());
        circleTransition.setToX(r.getShape().getGridPos().getYPos());
        circleTransition.play();
        TranslateTransition labelTransition = new TranslateTransition(Duration.ONE, l);
        labelTransition.setToX(circleTransition.getToX());
        labelTransition.setToY(circleTransition.getToY());
        labelTransition.play();
    }

    private void labelSettings(Label l, Robot r) {
        l.setText(r.getLabel().getString());
        l.setVisible(r.getLabel().isVisible());
    }

    private void circleSettings(Circle c, Robot r) {
        c.setCenterX(r.getShape().getGridPos().getXPos());
        c.setCenterY(r.getShape().getGridPos().getYPos());
    }

    //endregion

}