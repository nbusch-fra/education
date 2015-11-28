package de.hs.emden.stud.busch;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author Nico Busch (nbusch@technik-emden.de)
 */
public class MatchGameTest {

    protected MatchGame game;
    protected InputStream io;

    @Before
    public void setUp() throws Exception {
        byte[] data = "ja".getBytes();

        io = new ByteArrayInputStream(data);
        this.game = new MatchGame(io);
    }

    @Test
    public void testPlayComputer() throws Exception {
        org.junit.Assert.assertEquals(this.game.playComputer(17), 1);
        org.junit.Assert.assertEquals(this.game.playComputer(16), 3);
        org.junit.Assert.assertEquals(this.game.playComputer(15), 2);
    }

    @Test
    public void testAskForUserDecisionTrue() throws Exception {
        org.junit.Assert.assertTrue(this.game.askForUserInput("ja", "nein", true));
    }

    @Test
    public void testAskForUserDecisionFalse() throws Exception {
        this.game = new MatchGame(new ByteArrayInputStream("nein".getBytes()));

        org.junit.Assert.assertFalse(this.game.askForUserInput("ja", "nein", true));
    }

    @Test
    public void testAskForUserDecisionDefault() throws Exception {
        this.game = new MatchGame(new ByteArrayInputStream("\r\n".getBytes()));

        org.junit.Assert.assertTrue(this.game.askForUserInput("ja", "nein", true));
    }

}