package net.yura.domination.engine.core;

import java.util.Vector;
import junit.framework.TestCase;
import net.yura.domination.engine.ColorUtil;

/**
 * @author Matt Thornton
 */
public class PlayerTest extends TestCase {
    
    /**
     * Player instance to use for testing
     */
    private Player mPlayer;
    
    /**
     * PlayerTest constructor
     * @param testName String name of the test
     */
    public PlayerTest(String testName) {
        super(testName);
    }
    
    /**
     * Sets up test environment before each test
     * @throws Exception 
     */
    @Override
    protected void setUp() throws Exception {
        // Instaniate test Player instance
        mPlayer = new Player( Player.PLAYER_HUMAN,
                              "TestHuman",
                              ColorUtil.BLACK,
                              "TestAddress" );
        
        super.setUp();
    }
    
    /**
     * Tears down test environment after each test
     * @throws Exception 
     */
    @Override
    protected void tearDown() throws Exception {
        // Set test Player instance to null so next setup can reinstaniate it
        // to default test settings
        mPlayer = null;
        
        super.tearDown();
    }

    /**
     * Test of nextTurn method, of class Player.
     */
    public void testNextTurn() {
        System.out.println("Starting testNextTurn");
        
        mPlayer.nextTurn();
        fail("Not yet implemented");
    }

    /**
     * Test of addArmies method, of class Player.
     */
    public void testAddArmies() {
        System.out.println("addArmies");
        int n = 0;
        mPlayer.addArmies(n);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loseExtraArmy method, of class Player.
     */
    public void testLoseExtraArmy() {
        System.out.println("loseExtraArmy");
        int n = 0;
        mPlayer.loseExtraArmy(n);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of giveCard method, of class Player.
     */
    public void testGiveCard() {
        System.out.println("giveCard");
        Card card = null;
        mPlayer.giveCard(card);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of takeCard method, of class Player.
     */
    public void testTakeCard() {
        System.out.println("takeCard");
        Card expResult = null;
        Card result = mPlayer.takeCard();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of tradeInCards method, of class Player.
     */
    public void testTradeInCards() {
        System.out.println("tradeInCards");
        Card card1 = null;
        Card card2 = null;
        Card card3 = null;
        mPlayer.tradeInCards(card1, card2, card3);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

        /**
     * Test of newCountry method, of class Player.
     */
    public void testNewCountry() {
        System.out.println("newCountry");
        Country newCountry = null;
        mPlayer.newCountry(newCountry);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lostCountry method, of class Player.
     */
    public void testLostCountry() {
        System.out.println("lostCountry");
        Country lessCountry = null;
        mPlayer.lostCountry(lessCountry);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPlayersEliminated method, of class Player.
     */
    public void testAddPlayersEliminated() {
        System.out.println("addPlayersEliminated");
        Player mEliminatedPlayer = new Player( Player.PLAYER_HUMAN,
                                               "TestHuman2",
                                               ColorUtil.BLUE,
                                               "TestAddress2" );
        mPlayer.addPlayersEliminated( mEliminatedPlayer );
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayersEliminated method, of class Player.
     */
    public void testGetPlayersEliminated() {
        System.out.println("getPlayersEliminated");
        Vector expResult = null;
        Vector result = mPlayer.getPlayersEliminated();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isAlive method, of class Player.
     */
    public void testIsAlive() {
        System.out.println("isAlive");
        Player instance = null;
        boolean expResult = false;
        boolean result = instance.isAlive();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }    
}
