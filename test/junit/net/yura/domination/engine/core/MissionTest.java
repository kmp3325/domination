/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yura.domination.engine.core;

import junit.framework.TestCase;
import static org.mockito.Mockito.mock;

/**
 *
 * @author keegan
 */
public class MissionTest extends TestCase {
    Player player;
    int noc;
    int noa;
    Continent c1;
    Continent c2;
    Continent c3;
    String description;
    Mission mission;
    
    public MissionTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        player = mock(Player.class);
        noc = 3;
        noa = 1;
        c1 = mock(Continent.class);
        c2 = mock(Continent.class);
        c3 = mock(Continent.class);
        description = "description";
        mission = new Mission(player, noc, noa, c1, c2, c3, description);
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getPlayer method, of class Mission.
     */
    public void testGetPlayer() {
        System.out.println("getPlayer");
        
        assertEquals(player, mission.getPlayer());
        Player newPlayer = mock(Player.class);
        mission.setPlayer(newPlayer);
        assertEquals(newPlayer, mission.getPlayer());
    }

    /**
     * Test of getNoofcountries method, of class Mission.
     */
    public void testGetNoofcountries() {
        System.out.println("getNoofcountries");
        
        assertEquals(noc, mission.getNoofcountries());
        int newNoc = 7;
        mission.setNoofcountries(newNoc);
        assertEquals(newNoc, mission.getNoofcountries());
    }

    /**
     * Test of getNoofarmies method, of class Mission.
     */
    public void testGetNoofarmies() {
        System.out.println("getNoofarmies");
        
        assertEquals(noa, mission.getNoofarmies());
        int newNoa = 9;
        mission.setNoofarmies(newNoa);
        assertEquals(newNoa, mission.getNoofarmies());
    }

    /**
     * Test of getContinent1 method, of class Mission.
     */
    public void testGetContinent1() {
        System.out.println("getContinent1");
        
        assertEquals(c1, mission.getContinent1());
        Continent newC = mock(Continent.class);
        mission.setContinent1(newC);
        assertEquals(newC, mission.getContinent1());
    }

    /**
     * Test of getContinent2 method, of class Mission.
     */
    public void testGetContinent2() {
        System.out.println("getContinent2");
        
        assertEquals(c2, mission.getContinent2());
        Continent newC = mock(Continent.class);
        mission.setContinent2(newC);
        assertEquals(newC, mission.getContinent2());
    }

    /**
     * Test of getContinent3 method, of class Mission.
     */
    public void testGetContinent3() {
        System.out.println("getContinent3");
        
        assertEquals(c3, mission.getContinent3());
        Continent newC = mock(Continent.class);
        mission.setContinent3(newC);
        assertEquals(newC, mission.getContinent3());
    }

    /**
     * Test of getDiscription method, of class Mission.
     */
    public void testGetDiscription() {
        System.out.println("getDiscription");
        
        assertEquals(description, mission.getDiscription());
        String newDescription = "new";
        mission.setDiscription(newDescription);
        assertEquals(newDescription, mission.getDiscription());
    }

    /**
     * Test of toString method, of class Mission.
     */
    public void testToString() {
        System.out.println("toString");
        
        assertEquals(description, mission.toString());
        mission.setDiscription("");
        assertEquals("(Discription Missing)", mission.toString());
    }
}
