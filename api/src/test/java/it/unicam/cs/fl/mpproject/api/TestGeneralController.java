package it.unicam.cs.fl.mpproject.api;

import it.unicam.cs.fl.mpproject.api.cmds.GeneralRobotCMD;
import it.unicam.cs.fl.mpproject.api.cmds.MoveCMD;
import it.unicam.cs.fl.mpproject.api.cmds.SignalCMD;
import it.unicam.cs.fl.mpproject.api.essentials.GeneralController;
import it.unicam.cs.fl.mpproject.api.models.CircleShape;
import it.unicam.cs.fl.mpproject.api.models.Direction;
import it.unicam.cs.fl.mpproject.api.models.GridPos;
import it.unicam.cs.fl.mpproject.api.models.RectangleShape;
import it.unicam.cs.fl.mpproject.utilities.FollowMeParserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestGeneralController {


    private GeneralController controller;

    @BeforeEach
    void initializeController() {
        this.controller = new GeneralController();
    }

    @Test
    void testGCreadCMDsToList() throws IOException, FollowMeParserException {
        File file1 = temporaryFile("MOVE_BACKWARD 7, MOVE_FORWARD 3");
        assertThrows(FollowMeParserException.class, () -> this.controller.readCMDs(file1));
        assertNull(this.controller.getPrograms());

        File file2 = temporaryFile("MOVE -1.0 -1.0 4.0", "SIGNAL contadino");
        this.controller.readRobots(temporaryFile("-1.0 -1.0 4.0", "2.0 10.0 10.0"));
        this.controller.readCMDs(file2);
        List<GeneralRobotCMD> robotCMDs = this.controller.getPrograms().getRobotCMDs();
        assertEquals(2, robotCMDs.size());
        assertTrue(new MoveCMD(new Direction(-1.0, -1.0), 4.0).equalCMD(robotCMDs.get(0)));
        assertTrue(new SignalCMD("contadino").equalCMD(robotCMDs.get(1)));
    }

    @Test
    void testGCreadShapes() throws IOException, FollowMeParserException {
        File file3 = temporaryFile("contadino CIRCLE 2.0 5.0 4.0", "contadino RECTANGLE 4.0 1.0 3.0 2.0");
        this.controller.readShapes(file3);
        assertEquals(2, this.controller.getShapeList().size());
        assertInstanceOf(CircleShape.class, this.controller.getShapeList().get(0));
        assertInstanceOf(RectangleShape.class, this.controller.getShapeList().get(1));
    }

    @Test
    void testGCreadRobots() throws IOException {
        File file4 = temporaryFile("5 2.0 1.0", "4 7.0 9.0");
        this.controller.readRobots(file4);
        assertEquals(2, this.controller.getRobotList().size());
        assertTrue(new GridPos(2.0, 1.0).equalGridPos(this.controller.getRobotList().get(0).getGridPos()));
        assertTrue(new GridPos(7.0, 9.0).equalGridPos(this.controller.getRobotList().get(1).getGridPos()));
    }

    private File temporaryFile(String... strings) throws IOException {
        Path temp = Files.createTempFile(null, null);
        Files.write(temp, String.join("\n", strings).getBytes());
        return temp.toFile();
    }

}
