package it.unicam.cs.fl.mpproject.api;

import it.unicam.cs.fl.mpproject.api.cmds.*;
import it.unicam.cs.fl.mpproject.api.models.*;
import it.unicam.cs.fl.mpproject.api.utils.GeneralCalculator;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestGeneralRobotCMD {

    @Test
    void testGRCMDcloneCMD() {
        GeneralRobotCMD cmd = new SignalCMD("");
        assertNotEquals(cmd, cmd.cloneCMD());
    }

    @Test
    void testGRCMDmoveCMD() {
        GeneralRobotCMD cmd = new MoveCMD(new Direction(1.0, 1.0), 16.0);
        Robot robot = new Robot(new RobotLabel(""));
        cmd.perform(robot);
        assertEquals(16.0*GridPos.GUI_RESIZER, robot.getGridPos().getXPos());
        assertEquals(16.0*GridPos.GUI_RESIZER, robot.getGridPos().getYPos());
        assertTrue(cmd.canGoOn());
        assertEquals(-1, cmd.indexGoOn());
        cmd = new MoveCMD(new Direction(1.0, -1.0), 24.0);
        System.out.println(robot.getGridPos().getXPos());
        cmd.perform(robot);
        assertEquals((24.0*GridPos.GUI_RESIZER+16.0 * GridPos.GUI_RESIZER), robot.getGridPos().getXPos());
        assertEquals(((-24.0*GridPos.GUI_RESIZER)+(16.0 * GridPos.GUI_RESIZER)), robot.getGridPos().getYPos());
    }

    @Test
    void testGRCMDmoveRandomCMD() {
        Robot robot = new Robot(new RobotLabel(""));
        GeneralRobotCMD cmd = new MoveRandomCMD(new GridPos(0.0, 0.0), new GridPos(7.5, 7.5), 4.3);
        cmd.perform(robot);
        System.out.println(robot.getGridPos().getXPos());
        System.out.println(robot.getGridPos().getYPos());
        assertTrue(GeneralCalculator.isInTheRange(robot.getGridPos().getXPos() / GridPos.GUI_RESIZER, 0.0*4.3, 7.5*GridPos.GUI_RESIZER*4.3));
        assertTrue(GeneralCalculator.isInTheRange(robot.getGridPos().getYPos() / GridPos.GUI_RESIZER, 0.0*4.3, 7.5*GridPos.GUI_RESIZER*4.3));
    }

    @Test
    void testGRCMDsignalCMD() {
        Robot robot = new Robot(new RobotLabel(""));
        GeneralRobotCMD cmd = new SignalCMD("operaio");
        cmd.perform(robot);
        assertNotEquals("OPERAIO", robot.getLabel().getString());
        assertTrue(robot.getLabel().getString().equalsIgnoreCase("OPERAIO"));
    }

    @Test
    void testGRCMDunsignalCMD() {
        Robot robot = new Robot(new RobotLabel(""));
        GeneralRobotCMD cmd = new UnsignalCMD("operaio");
        robot.setLabel(new RobotLabel("operaio", true));
        cmd.perform(robot);
        assertFalse(robot.getLabel().isVisible());
        cmd = new UnsignalCMD("contadino");
        robot.setLabel(new RobotLabel("pastore", true));
        GeneralRobotCMD finalCmd = cmd;
        assertThrows(IllegalArgumentException.class, () -> finalCmd.perform(robot));
    }

    @Test
    void testGRCMDstopCMD() {
        Robot robot = new Robot(new RobotLabel(""));
        GeneralRobotCMD cmd = new MoveCMD(new Direction(1.0, 1.0), 35.0);
        cmd.perform(robot);
        assertEquals(87.5, robot.getGridPos().getXPos());
        assertEquals(87.5, robot.getGridPos().getYPos());
        cmd = new StopCMD();
        cmd.perform(robot);
        assertEquals(0.0, robot.getSpeed());
        double oldX = robot.getGridPos().getXPos();
        double oldY = robot.getGridPos().getYPos();
        cmd = new MoveCMD(robot.getDirection(), robot.getSpeed());
        cmd.perform(robot);
        assertEquals(oldX, robot.getGridPos().getXPos());
        assertEquals(oldY, robot.getGridPos().getYPos());
    }

    @Test
    void testGRCMDrepeatCMD() {
        Robot robot = new Robot(new RobotLabel(""));
        GeneralRobotCMD cmd = new RepeatCMD(1, 5);
        assertFalse(cmd.canGoOn());
        assertEquals(1, cmd.indexGoOn());
        cmd.perform(robot);
        assertEquals(1, cmd.indexGoOn());
        for (int i = 0; i < (5-1); i++) {
            cmd.perform(robot);
        }
        assertTrue(cmd.canGoOn());
        assertNotEquals(1, cmd.indexGoOn());
    }

    @Test
    void testGRCMDfollowCMD() {
        Robot robot = new Robot(new RobotLabel(""));
        List<Robot> robotList = new LinkedList<>();
        Robot r1 = new Robot(new RobotLabel(""), new GridPos(55.0, 55.0), new Direction(), 0.0);
        Robot r2 = new Robot(new RobotLabel(""), new GridPos(-55.0, -55.0), new Direction(), 0.0);
        robotList.add(robot);
        robotList.add(r1);
        robotList.add(r2);
        GeneralRobotCMD cmd = new FollowCMD(robotList, new RobotLabel("castoro"), 40.0, 16.0);
        r1.setLabel(new RobotLabel("castoro", true));
        r2.setLabel(new RobotLabel("castoro", true));
        cmd.perform(robot);
        assertFalse(robot.getGridPos().getXPos() >= 40.0);
        assertFalse(robot.getGridPos().getYPos() >= 40.0);
        assertTrue(cmd.canGoOn());
        assertEquals(-1, cmd.indexGoOn());
    }

    @Test
    void testGRCMDcontinueCMD() {
        GeneralRobotCMD cmd = new ContinueCMD(0, 10);
        assertFalse(cmd.canGoOn());
        assertEquals(0, cmd.indexGoOn());
        for (int i = 0; i < 10; i++) {
            cmd.perform(new Robot(new RobotLabel("")));
        }
        assertTrue(cmd.canGoOn());
        assertEquals(-1, cmd.indexGoOn());
    }

    @Test
    void testGRCMDdoneCMD() {
        GeneralRobotCMD cmd = new DoneCMD(1);
        assertFalse(cmd.canGoOn());
        assertEquals(1, cmd.indexGoOn());
        cmd.perform(new Robot(new RobotLabel("")));
        assertFalse(cmd.canGoOn());
        assertEquals(1, cmd.indexGoOn());
    }

    @Test
    void testGRCMDdoForeverCMD() {
        GeneralRobotCMD cmd = new DoForeverCMD(1);
        assertEquals(1, cmd.indexGoOn());
        cmd.perform(new Robot(new RobotLabel("")));
        assertFalse(cmd.canGoOn());
        assertEquals(1, cmd.indexGoOn());
    }

    @Test
    void testGRCMDuntilCMD() {
        Robot robot1 = new Robot(new RobotLabel(""));
        List<ShapeInterface> shapes = new LinkedList<>();
        ShapeInterface shape = new CircleShape(new GridPos(0.0,0.0), new RobotLabel("pippo"), 25.0);
        shapes.add(shape);
        GeneralRobotCMD cmd1 = new UntilCMD(new RobotLabel("pippo"), shapes, 1);
        cmd1.perform(robot1);
        assertEquals(0, cmd1.indexGoOn());

        Robot robot2 = new Robot(new RobotLabel(""), new GridPos(30.0,30.0), new Direction(), 0.0);
        List<ShapeInterface> shapes2 = new LinkedList<>();
        ShapeInterface shape2 = new CircleShape(new GridPos(0.0,0.0), new RobotLabel("pippo"), 25.0);
        shapes2.add(shape2);
        GeneralRobotCMD cmd2 = new UntilCMD(new RobotLabel("pippo"), shapes2, 1);
        cmd2.perform(robot2);
        assertEquals(1, cmd2.indexGoOn());

        Robot robot3 = new Robot(new RobotLabel(""));
        List<ShapeInterface> shapes3 = new LinkedList<>();
        ShapeInterface shape3 = new RectangleShape(new GridPos(0.0, 0.0), "pippo", 25.0, 25.0);
        shapes3.add(shape3);
        GeneralRobotCMD cmd3 = new UntilCMD(new RobotLabel("pippo"), shapes3, 1);
        cmd3.perform(robot3);
        assertEquals(0, cmd3.indexGoOn());

        Robot robot4 = new Robot(new RobotLabel(""), new GridPos(45.0,45.0), new Direction(), 0.0);
        List<ShapeInterface> shapes4 = new LinkedList<>();
        ShapeInterface shape4 = new RectangleShape(new GridPos(0.0, 0.0), "pippo", 25.0, 25.0);
        shapes4.add(shape4);
        GeneralRobotCMD cmd4 = new UntilCMD(new RobotLabel("pippo"), shapes4, 1);
        cmd4.perform(robot4);
        assertEquals(1, cmd4.indexGoOn());
    }

}
