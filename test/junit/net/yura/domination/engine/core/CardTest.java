package net.yura.domination.engine.core;

import junit.framework.TestCase;

/**
 *
 * @author keegan
 */
public class CardTest extends TestCase {
    
    public CardTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getName method, of class Card.
     */
    public void testGetName() {
        Card card = new Card(Card.INFANTRY, new Country());
        assertEquals(Card.INFANTRY, card.getName());
    }

    /**
     * Test of setName method, of class Card.
     */
    public void testSetName() {
        Card card = new Card(Card.INFANTRY, new Country());
        try {
            card.setName("something");
            fail();
        } catch (IllegalArgumentException e) {}
        card.setName(Card.CANNON);
        assertEquals(Card.CANNON, card.getName());
    }

    /**
     * Test of getCountry method, of class Card.
     */
    public void testGetCountry() {
        Country country = new Country(1, "id", "n", new Continent("id", "n", 4, 5), 2, 3);
        Card card = new Card(Card.INFANTRY, country);
        assertEquals(country, card.getCountry());
    }

    /**
     * Test of setCountry method, of class Card.
     */
    public void testSetCountry() {
        Country country1 = new Country(1, "id", "n", new Continent("id", "n", 4, 5), 2, 3);
        Card card = new Card(Card.INFANTRY, country1);
        
        Country country2 = new Country(2, "id2", "n2", new Continent("id2", "n2", 5, 6), 3, 4);
        card.setCountry(country2);
        assertEquals(country2, card.getCountry());
    }

    /**
     * Test of toString method, of class Card.
     */
    public void testToString() {
        Country country = new Country(1, "id", "n", new Continent("id", "n", 4, 5), 2, 3);
        Card card = new Card(Card.INFANTRY, country);
        assertEquals("Infantry - id (1)", card.toString());
        
        Card card2 = new Card(Card.INFANTRY, null);
        assertEquals("Infantry", card2.toString());
    }
    
}
