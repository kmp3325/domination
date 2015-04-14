package net.yura.domination.engine.core;

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
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addArmies method, of class Player.
     */
    public void testAddArmies() {
        System.out.println("addArmies");

        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loseExtraArmy method, of class Player.
     */
    public void testLoseExtraArmy() {
        System.out.println("loseExtraArmy");

        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of giveCard method, of class Player.
     */
    public void testGiveCard() {
        System.out.println("giveCard");

        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of takeCard method, of class Player.
     */
    public void testTakeCard() {
        System.out.println("takeCard");

        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of tradeInCards method, of class Player.
     */
    public void testTradeInCards() {
        System.out.println("tradeInCards");

        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

        /**
     * Test of newCountry method, of class Player.
     */
    public void testNewCountry() {
        System.out.println("newCountry");

        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lostCountry method, of class Player.
     */
    public void testLostCountry() {
        System.out.println("lostCountry");

        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPlayersEliminated method, of class Player.
     */
    public void testAddPlayersEliminated() {
        System.out.println("addPlayersEliminated");

        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayersEliminated method, of class Player.
     */
    public void testGetPlayersEliminated() {
        System.out.println("getPlayersEliminated");

        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isAlive method, of class Player.
     */
    public void testIsAlive() {
        System.out.println("isAlive");

        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }    
}
