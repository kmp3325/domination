package net.yura.domination.engine.core;

import java.util.List;
import java.util.Vector;
import junit.framework.TestCase;
import net.yura.domination.engine.ColorUtil;
import static org.mockito.Mockito.*;

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
        System.out.println("testNextTurn");
        
        // Test 1. Perform next turn
        // Get the current list of Statistic objects
        List<Statistic> currentStats = mPlayer.getStatistics();
        int size = currentStats.size();
        
        // Perform next turn
        mPlayer.nextTurn();
        
        // Verify currentStats has increased by 1
        assertEquals( "Next turn did not add a Statistic object to the list",
                      ++size,
                      currentStats.size() );
    }

    /**
     * Test of addArmies method, of class Player.
     */
    public void testAddArmies() {
        System.out.println("addArmies");
        
        // Test 1. Add a single army
        // Get the original amount of extra armies
        int numArmies = mPlayer.getExtraArmies();
        
        // Add an army
        mPlayer.addArmies( 1 );
        
        // Verify an army has been added
        assertEquals( "Did not add a single army correctly",
                      ++numArmies,
                      mPlayer.getExtraArmies() );
        
        // Test 2. Add more than a single army
        // Add more than a single army
        mPlayer.addArmies( 25 );
        numArmies += 25;
        
        // Verify more than a single army has been added
        assertEquals( "Did not add more than a single army correctly",
                      numArmies,
                      mPlayer.getExtraArmies() );
        
        // Test 3. Add zero armies
        // Add zero armies
        mPlayer.addArmies( 0 );
        
        // Verify armies did not change
        assertEquals( "Armies changed",
                      numArmies,
                      mPlayer.getExtraArmies() );
        
        // Test 4. Add negative armies
        // Add negative armies
        mPlayer.addArmies( -5 );
        
        // Verify armies were not subtracted
        assertEquals( "Armies were subtracted",
                      numArmies,
                      mPlayer.getExtraArmies() );
    }

    /**
     * Test of loseExtraArmy method, of class Player.
     */
    public void testLoseExtraArmy() {
        System.out.println("loseExtraArmy");
        
        // Test 1. Lose a single army
        // Set up some armies
        mPlayer.addArmies( 10 );
        int numArmies = mPlayer.getExtraArmies();
        
        // Lose an army
        mPlayer.loseExtraArmy( 1 );
        
        // Verify an army has been lost
        assertEquals( "Did not lose a single army correctly",
                      --numArmies,
                      mPlayer.getExtraArmies() );
        
        // Test 2. Lose more than a single army
        // Lose more than a single army
        mPlayer.loseExtraArmy( 5 );
        numArmies -= 5;
        
        // Verify more than a single army has been lost
        assertEquals( "Did not lose more than a single army correctly",
                      numArmies,
                      mPlayer.getExtraArmies() );
        
        // Test 3. Lose zero armies
        // Lose zero armies
        mPlayer.loseExtraArmy(0 );
        
        // Verify armies did not change
        assertEquals( "Armies changed",
                      numArmies,
                      mPlayer.getExtraArmies() );
        
        // Test 4. Lose more armies than currently
        // Lose more than numArmies
        mPlayer.loseExtraArmy( numArmies + 1 );
        
        // Verify armies have not gone negative
        assertEquals( "Armies should not be negative", 
                      0,
                      mPlayer.getExtraArmies() );
        
        // Test 5. Lose negative armies
        // Reset armies and lose negative armies
        mPlayer.addArmies( 25 );
        numArmies = mPlayer.getExtraArmies();
        mPlayer.loseExtraArmy( -5 );
        
        // Verify armies were not added
        assertEquals( "Armies were added",
                      numArmies,
                      mPlayer.getExtraArmies() );
    }

    /**
     * Test of giveCard method, of class Player.
     */
    public void testGiveCard() {
        System.out.println("giveCard");

        // Test 1. Add a card
        // Get current card list
        // Mock a Card object
        Vector cards = mPlayer.getCards();
        int size = cards.size();
        Card mockCard = mock( Card.class );
        
        // Give mock Card
        mPlayer.giveCard( mockCard );
        
        // Verify card list has increased
        assertEquals( "Card was not given correctly", 
                      ++size,
                      cards.size() );
    }

    /**
     * Test of takeCard method, of class Player.
     */
    public void testTakeCard() {
        System.out.println("takeCard");
        
        // Test 1. Take first card from a one card list
        // Add a mocked card
        Card mockCard = mock( Card.class );
        mPlayer.getCards().add( mockCard );
        
        // Take the card
        Card takenCard = mPlayer.takeCard();
        
        // Verify the taken card is the correct card
        assertEquals( "Taken card from one card list failed", 
                      mockCard,
                      takenCard );
        
        // Test 2. Take first card from a multiple card list
        // Add mocked cards
        mPlayer.getCards().add( mockCard );
        Card mockCard2 = mock( Card.class );
        mPlayer.getCards().add( mockCard2 );
        Card mockCard3 = mock( Card.class );
        mPlayer.getCards().add( mockCard3 );
        
        // Take the first card
        takenCard = mPlayer.takeCard();
        
        // Verify the taken card is the correct card
        assertEquals( "Taken card from multiple card list failed", 
                      mockCard,
                      takenCard );

        // Test 3. Take first card from empty list
        // Clear the card list
        mPlayer.getCards().clear();
        try
        {
            Card firstCard = (Card)mPlayer.getCards().elementAt( 0 );
            
            // Take the card
            takenCard = mPlayer.takeCard();

            // Verify the taken card is the correct card
            assertEquals( "Taken card does not match first card", 
                          firstCard,
                          takenCard );
        }
        catch( ArrayIndexOutOfBoundsException e )
        {
            fail( "ArrayIndexOutOfBoundsException was thrown " + e.getMessage() );
        }
    }

    /**
     * Test of tradeInCards method, of class Player.
     */
    public void testTradeInCards() {
        System.out.println("tradeInCards");

        // Test 1. First card adds armies
        // Add mock territory
        Country mockTerritory = mock( Country.class );
        mPlayer.newCountry( mockTerritory );
        
        // Create the mocks for Card objects
        Card card1 = mock( Card.class );
        when( card1.getCountry() ).thenReturn( mockTerritory );
        Card card2 = mock( Card.class );
        Card card3 = mock( Card.class );
        mPlayer.giveCard( card1 );
        mPlayer.giveCard( card2 );
        mPlayer.giveCard( card3 );
        Vector cards = mPlayer.getCards();
        
        // Trade in cards
        mPlayer.tradeInCards( card1, card2, card3 );
        
        // Verify statistics were updated correctly
        double[] statistics = mPlayer.getStatistics( StatType.REINFORCEMENTS );
        assertEquals( "Card1 did not add extra armies",
                      2.0,
                      statistics[0] );
        // Verify all cards were removed
        assertTrue( "Cards were not removed correctly",
                    cards.isEmpty() );
        
        // Test 2. Second card adds armies
        // Add second mock territory, remove first mock territory
        Country mockTerritory2 = mock( Country.class );
        mPlayer.newCountry( mockTerritory2 );
        mPlayer.lostCountry( mockTerritory );
        
        // Create the mocks for Card objects
        when( card2.getCountry() ).thenReturn( mockTerritory2 );
        mPlayer.giveCard( card1 );
        mPlayer.giveCard( card2 );
        mPlayer.giveCard( card3 );
        
        // Trade in cards
        mPlayer.tradeInCards( card1, card2, card3 );
        
        // Verify statistics were updated correctly
        statistics = mPlayer.getStatistics( StatType.REINFORCEMENTS );
        assertEquals( "Card2 did not add extra armies",
                      4.0,
                      statistics[0] );
        // Verify all cards were removed
        assertTrue( "Cards were not removed correctly",
                    cards.isEmpty() );
        
        // Test 3. Third card adds armies
        // Add mock territory, remove second mock territory
        Country mockTerritory3 = mock( Country.class );
        mPlayer.newCountry( mockTerritory3 );
        mPlayer.lostCountry( mockTerritory2 );
        
        // Create the mocks for Card objects
        when( card3.getCountry() ).thenReturn( mockTerritory3 );
        mPlayer.giveCard( card1 );
        mPlayer.giveCard( card2 );
        mPlayer.giveCard( card3 );
        
        // Trade in cards
        mPlayer.tradeInCards( card1, card2, card3 );
        
        // Verify statistics were updated correctly
        statistics = mPlayer.getStatistics( StatType.REINFORCEMENTS );
        assertEquals( "Card3 did not add extra armies",
                      6.0,
                      statistics[0] );
        // Verify all cards were removed
        assertTrue( "Cards were not removed correctly",
                    cards.isEmpty() );
        
        // Test 4. Trade in cards not currently owned
        // Add cards and territories
        mPlayer.giveCard( card2 );
        mPlayer.giveCard( card3 );
        mPlayer.newCountry( mockTerritory );
        
        // Trade in cards
        mPlayer.tradeInCards(card1, card2, card3);
        
        // Verify statistics are not updated and cards are not removed
        statistics = mPlayer.getStatistics( StatType.REINFORCEMENTS );
        assertEquals( "Armies were added without card being owned",
                      6.0,
                      statistics[0] );
        // Verify cards were not removed
        assertFalse( "Cards were removed without all cards being owned",
                     cards.isEmpty() );
    }

        /**
     * Test of newCountry method, of class Player.
     */
    public void testNewCountry() {
        System.out.println("newCountry");

        // Test 1. Constructed player has empty territories
        // Get territory Vector
        Vector territories = mPlayer.getTerritoriesOwned();
        
        // Verify Vector is empty and number of territories is 0
        assertTrue( "Territories Vector is not empy on construction", 
                    territories.isEmpty() );
        //Verify size is 0
        assertEquals( "Number of territories is not 0", 
                      0,
                      mPlayer.getNoTerritoriesOwned() );
        
        // Test 2. Add a single new territory
        // Mock Country and add to territories
        Country mockTerritory = mock( Country.class );
        mPlayer.newCountry( mockTerritory );
        
        // Verify new territory was added correctly
        assertFalse( "Territory Vectory is empty",
                     territories.isEmpty() );
        assertEquals( "Number of territories is not 1", 
                      1,
                      mPlayer.getNoTerritoriesOwned() );
        assertEquals( "Territory does not match", 
                      territories.firstElement(),
                      mockTerritory );
        
        // Test 3. Add the same territory twice
        // Add the territory again
        mPlayer.newCountry( mockTerritory );
        
        // Verify the territory was not added again
        assertEquals( "Same territory was added twice", 
                      1,
                      mPlayer.getNoTerritoriesOwned() );
    }

    /**
     * Test of lostCountry method, of class Player.
     */
    public void testLostCountry() {
        System.out.println("lostCountry");

        // Test 1. Verify losing the only territory
        // Add a mocked territory
        Country mockTerritory = mock( Country.class );
        mPlayer.newCountry( mockTerritory );
        
        // Remove mocked territory
        mPlayer.lostCountry( mockTerritory );
        
        // Verify territory was removed
        Vector territories = mPlayer.getTerritoriesOwned();
        assertTrue( "Territory was not removed",
                    territories.isEmpty() );
        
        // Test 2. Verify losing a territory from a Vectory of more than 1
        // Add mocked territories
        Country lostTerritory = mock( Country.class );
        mPlayer.newCountry( mockTerritory );
        mPlayer.newCountry( lostTerritory );
        
        // Remove a territory
        mPlayer.lostCountry( lostTerritory );
        
        // Verify correct territory was removed
        assertFalse( "All territories were removed",
                     territories.isEmpty() );
        assertEquals( "Incorrect number of territories", 
                      1,
                      mPlayer.getNoTerritoriesOwned() );
        assertEquals( "Incorrect remaining territory", 
                      mockTerritory,
                      territories.firstElement() );
        
        // Test 3. Removing a territory not owned
        // Remove territory not owned
        mPlayer.lostCountry( lostTerritory );
        
        // Verify Vector is unchanged
        assertFalse( "Vectory is empty",
                     territories.isEmpty() );
        assertEquals( "Incorrect number of territories", 
                      1,
                      mPlayer.getNoTerritoriesOwned() );
        assertEquals( "Incorrect remaining territory", 
                      mockTerritory,
                      territories.firstElement() );
        
        // Test 4. Remove territory from empty Vector
        // Remove remaining territory
        mPlayer.lostCountry( mockTerritory );
        if( territories.isEmpty() == false )
        {
            fail( "Clean up went wrong" );
        }
        
        // Attempt to remove territory from empty Vector
        mPlayer.lostCountry( lostTerritory );
        assertTrue( "Vector is not empty",
                    territories.isEmpty() );
    }

    /**
     * Test of addPlayersEliminated method, of class Player.
     */
    public void testAddPlayersEliminated() {
        System.out.println("addPlayersEliminated");

        // Test 1. No players have been eliminated in construction
        // Verify empty eliminated players Vectory
        Vector eliminated = mPlayer.getPlayersEliminated();
        assertTrue( "Eliminated players not emtpy in construction",
                    eliminated.isEmpty() );
        
        // Test 2. Add a single eliminated player
        // Mock a new player and eliminate player
        Player eliminatedPlayer = mock( Player.class );
        mPlayer.addPlayersEliminated( eliminatedPlayer );
        
        // Verify player eliminated correctly
        assertFalse( "Single player was not eliminated",
                     eliminated.isEmpty() );
        assertEquals( "Incorrect number of eliminated players", 
                      1,
                      eliminated.size() );
        assertEquals( "Incorrect player eliminated", 
                      eliminatedPlayer,
                      eliminated.firstElement() );
        
        // Test 3. Add multiple eliminated players
        // Mock another new player to be eliminated
        Player eliminatedPlayer2 = mock( Player.class );
        mPlayer.addPlayersEliminated( eliminatedPlayer2 );
        
        // Verify player eliminated correctly
        assertFalse( "Multiple players not eliminated",
                     eliminated.isEmpty() );
        assertEquals( "Incorrect number of eliminated players", 
                      2,
                      eliminated.size() );
        assertEquals( "Incorrect player eliminated", 
                      eliminatedPlayer2,
                      eliminated.elementAt( 1 ) );
    }

    /**
     * Test of isAlive method, of class Player.
     */
    public void testIsAlive() {
        System.out.println("isAlive");

        // Test 1. Verify not alive during construction
        assertFalse( "Player is alive",
                     mPlayer.isAlive() );
        
        // Test 2. Player has an army (should be alive)
        // Add an army
        mPlayer.addArmies( 1 );
        
        // Verify player is now alive
        assertTrue( "Player has an army, but is not alive",
                    mPlayer.isAlive() );
        
        // Test 3. Player has a territory (should be alive)
        // Remove army
        mPlayer.loseExtraArmy( 1 );
        
        // Mock a territory and add it
        Country mockTerritory = mock( Country.class );
        mPlayer.newCountry( mockTerritory );
        
        // Verify player is alive
        assertTrue( "Player has a territory, but is not alive",
                    mPlayer.isAlive() );
        
        // Test 4. Player has an army and a territory (should be alive)
        // Add army
        mPlayer.addArmies( 1 );
        
        // Verify player is alive
        assertTrue( "Player has an army and a territory, but is not alive",
                    mPlayer.isAlive() );
        
        // Test 5. Remove all armies and territories (should not be alive)
        // Remove armies and territories
        mPlayer.loseExtraArmy( 1 );
        mPlayer.lostCountry( mockTerritory );
        
        // Verify player is not alive
        assertFalse( "Player has 0 armies and 0 territories, but is alive",
                     mPlayer.isAlive() );
    }
    
    /**
     * Test of rename method, of class Player.
     */
    public void testRename()
    {
        System.out.println("rename");
        
        // Test 1. Rename
        // Rename player
        String newName = "NewPlayerName";
        mPlayer.rename( newName );
        
        // Verify name has changed correctly
        assertEquals( "Player was not renamed correctly", 
                      newName,
                      mPlayer.getName() );
        
        // Test 2. Rename with NULL
        // Rename with null
        mPlayer.rename( null );
        
        // Verify name was not changed
        assertEquals( "Player name is NULL", 
                      newName,
                      mPlayer.getName() );
    }
    
    /**
     * Test of setColor method, of class Player.
     */
    public void testSetColor()
    {
        System.out.println("setColor");
        
        // Test 1. Set new color
        // Set new color
        mPlayer.setColor( ColorUtil.BLUE );
        
        // Verify color was changed
        assertEquals( "Color was not changed correctly", 
                      ColorUtil.BLUE,
                      mPlayer.getColor() );
    }
    
    /**
     * Test of toString, of class Player.
     */
    public void testToString()
    {
        System.out.println( "toString" );
        
        // Test 1. ToString returns constructed name
        // Verify ToString returns the constructed name
        assertEquals( "Name is not set to constructed name", 
                      "TestHuman",
                      mPlayer.toString() );
        
        // Test 2. ToString returns new name
        // Set new name
        String newName = "NewTestName";
        mPlayer.rename( newName );
        
        // Verify ToString returns the new name
        assertEquals( "Name is not set to the new name", 
                      newName,
                      mPlayer.toString() );
    }
    
    /**
     * Test of setAddress, of class Player.
     */
    public void testSetAddress()
    {
        System.out.println( "setAddress" );
        
        // Test 1. GetAddress returns constructed address
        // Verify GetAddress returns the constructed address
        assertEquals( "Address is not set to constructed address", 
                      "TestAddress",
                      mPlayer.getAddress() );
        
        // Test 2. ToString returns new name
        // Set new name
        String newAddress = "NewTestAddress";
        mPlayer.setAddress( newAddress );
        
        // Verify ToString returns the new name
        assertEquals( "Address is not set to the new address", 
                      newAddress,
                      mPlayer.getAddress() );
    }
    
    /**
     * Test of setCapital, of class Player.
     */
    public void testSetCapital()
    {
        System.out.println( "setCapital" );
        
        // Test 1. GetCapital returns NULL constructed capital
        // Verify GetCapital returns NULL constructed capital
        assertNull( "Default capital should not be set",
                    mPlayer.getCapital() );
        
        // Test 2. Set capital to a valid Country
        // Mock a Country and set it as capital
        Country mockCapital = mock( Country.class );
        mPlayer.setCapital( mockCapital );
        
        // Verify capital was set correctly
        assertEquals( "Capital was not set correctly", 
                      mockCapital,
                      mPlayer.getCapital() );
    }
    
    /**
     * Test of setMission, of class Player.
     */
    public void testSetMission()
    {
        System.out.println( "setMission" );
        
        // Test 1. GetMission returns NULL constructed mission
        // Verify GetMission returns NULL constructed mission
        assertNull( "Default mission should not be set",
                    mPlayer.getMission() );
        
        // Test 2. Set mission to a valid Mission
        // Mock a Mission and set it as mission
        Mission mockMission = mock( Mission.class );
        mPlayer.setMission( mockMission );
        
        // Verify capital was set correctly
        assertEquals( "Mission was not set correctly", 
                      mockMission,
                      mPlayer.getMission() );
    }
    
    /**
     * Test of getNoArmies, of class Player.
     */
    public void testGetNoArmies()
    {
        System.out.println("getNoArmies");
        
        // Test 1. Get number of armies after construction
        // Verify 0 armies after construction
        assertEquals( "Has armies immediately after construction", 
                      0,
                      mPlayer.getNoArmies() );
        
        // Test 2. Get number of armies with a single owned territory
        // Add mock territory
        Country mockTerritory = mock( Country.class );
        final int mockNumArmies = 5;
        when( mockTerritory.getArmies() ).thenReturn( mockNumArmies );
        mPlayer.newCountry( mockTerritory );
        
        // Verify correct number of armies
        assertEquals( "Incorrect number of armies for single territory", 
                      mockNumArmies,
                      mPlayer.getNoArmies() );
        
        // Test 3. Get number of armies with more than a single owned territory
        // Add another mock territory
        Country mockTerritory2 = mock( Country.class );
        final int mockNumArmies2 = 18;
        when( mockTerritory2.getArmies() ).thenReturn( mockNumArmies2 );
        mPlayer.newCountry( mockTerritory2 );
        
        // Verify correct number of armies
        assertEquals( "Incorrect number of armies for multiple territories",
                      ( mockNumArmies + mockNumArmies2 ),
                      mPlayer.getNoArmies() );
        
        // Test 4. Get number of armies after losing a territory
        // Remove territory
        mPlayer.lostCountry( mockTerritory );
        
        // Verify correct number of armies
        assertEquals( "Incorrect number of armies after losing a territory",
                      mockNumArmies2,
                      mPlayer.getNoArmies() );
    }
    
    /**
     * Test of setType, of class Player
     */
    public void testSetType()
    {
        System.out.println("setType");
        
        // Test 1. Get type after construction
        // Verify constructed type is correct
        assertEquals( "Type is not constructed correctly",
                      Player.PLAYER_HUMAN,
                      mPlayer.getType() );
        
        // Test 2. Setting to AI_CRAP
        // Set type to AI_CRAP and verify
        mPlayer.setType( Player.PLAYER_AI_CRAP );
        assertEquals( "Type was not set to AI_CRAP correctly",
                      Player.PLAYER_AI_CRAP,
                      mPlayer.getType() );
        
        // Test 3. Setting to AI_EASY
        // Set type to AI_EASY and verify
        mPlayer.setType( Player.PLAYER_AI_EASY );
        assertEquals( "Type was not set to AI_EASY correctly",
                      Player.PLAYER_AI_EASY,
                      mPlayer.getType() );
        
        // Test 4. Setting to AI_AVERAGE
        // Set type to AI_AVERAGE and verify
        mPlayer.setType( Player.PLAYER_AI_AVERAGE );
        assertEquals( "Type was not set to AI_AVERAGE correctly",
                      Player.PLAYER_AI_AVERAGE,
                      mPlayer.getType() );
        
        // Test 5. Setting to AI_HARD
        // Set type to AI_HARD and verify
        mPlayer.setType( Player.PLAYER_AI_HARD );
        assertEquals( "Type was not set to AI_HARD correctly",
                      Player.PLAYER_AI_HARD,
                      mPlayer.getType() );
        
        // Test 6. Setting to HUMAN
        // Set type to HUMAN and verify
        mPlayer.setType( Player.PLAYER_HUMAN );
        assertEquals( "Type was not set to HUMAN correctly",
                      Player.PLAYER_HUMAN,
                      mPlayer.getType() );
        
        // Test 7. Setting to invalid type
        // Set type to invalid type and verify
        mPlayer.setType( 50 );
        assertTrue( "Type was set to invalid type",
                      ( mPlayer.getType() != 50 ) );
    }
    
    /**
     * Test of setAutoEndGo, of class Player.
     */
    public void testSetAutoEndGo()
    {
        System.out.println("setAutoEndGo");
        
        // Test 1. Constructed to true
        // Verify autoendgo was constructed to true
        assertTrue( "Not constructed to True",
                    mPlayer.getAutoEndGo() );
        
        // Test 2. Set to false
        // Set autoendgo to false and verify
        mPlayer.setAutoEndGo( false );
        assertFalse( "Not set to false",
                     mPlayer.getAutoEndGo() );
        
        // Test 3. Set to true
        // Set autoendgo to true and verify
        mPlayer.setAutoEndGo( true );
        assertTrue( "Not set to true",
                    mPlayer.getAutoEndGo() );
        
        // Test 4. Set to itself
        // Set autoendgo to itself and verify
        boolean autoendgo = mPlayer.getAutoEndGo();
        mPlayer.setAutoEndGo( autoendgo );
        assertTrue( "Did not set to itself",
                    ( mPlayer.getAutoEndGo() == autoendgo ) );
    }
    
    /**
     * Test of setAutoDefend, of class Player.
     */
    public void testSetAutoDefend()
    {
        System.out.println("setAutoDefend");
        
        // Test 1. Constructed to true
        // Verify autodefend was constructed to true
        assertTrue( "Not constructed to True",
                    mPlayer.getAutoDefend() );
        
        // Test 2. Set to false
        // Set autoendgo to false and verify
        mPlayer.setAutoDefend( false );
        assertFalse( "Not set to false",
                     mPlayer.getAutoDefend() );
        
        // Test 3. Set to true
        // Set autoendgo to true and verify
        mPlayer.setAutoDefend( true );
        assertTrue( "Not set to true",
                    mPlayer.getAutoDefend() );
        
        // Test 4. Set to itself
        // Set autoendgo to itself and verify
        boolean autodefend = mPlayer.getAutoDefend();
        mPlayer.setAutoDefend( autodefend );
        assertTrue( "Did not set to itself",
                    ( mPlayer.getAutoDefend() == autodefend ) );
    }
}
