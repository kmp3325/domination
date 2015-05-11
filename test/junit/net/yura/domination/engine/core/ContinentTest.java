/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yura.domination.engine.core;

import java.util.Vector;
import junit.framework.TestCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author keegan
 */
public class ContinentTest extends TestCase {
    
    String id;
    String n;
    int noa;
    int c;
    Continent continent;
    
    public ContinentTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        id = "id";
        n = "n";
        noa = 4;
        c = 1;
        continent = new Continent(id, n, noa, c);
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of toString method, of class Continent.
     */
    public void testToString() {
        System.out.println("toString");
        
        String expResult = id+" ["+noa+"]";
        assertEquals(expResult, continent.toString());
        
        continent.setArmyValue(0);
        assertEquals(id, continent.toString());
    }

    /**
     * Test of getIdString method, of class Continent.
     */
    public void testGetIdString() {
        System.out.println("getIdString");
        
        assertEquals(id, continent.getIdString());
        String newId = "newId";
        continent.setIdString(newId);
        assertEquals(newId, continent.getIdString());
    }

    /**
     * Test of getName method, of class Continent.
     */
    public void testGetName() {
        System.out.println("getName");
        
        assertEquals(n, continent.getName());
        String newName = "newName";
        continent.setName(newName);
        assertEquals(newName, continent.getName());
    }

    /**
     * Test of getColor method, of class Continent.
     */
    public void testGetColor() {
        System.out.println("getColor");
        
        assertEquals(c, continent.getColor());
        int newC = 9;
        continent.setColor(newC);
        assertEquals(newC, continent.getColor());
    }

    /**
     * Test of getArmyValue method, of class Continent.
     */
    public void testGetArmyValue() {
        System.out.println("getArmyValue");
        
        assertEquals(noa, continent.getArmyValue());
        int newNoa = 5;
        continent.setArmyValue(newNoa);
        assertEquals(newNoa, continent.getArmyValue());
    }

    public void testGetNumberOwned() {
        Country c = mock(Country.class);
        Player p = mock(Player.class);
        when(c.getOwner()).thenReturn(p);
        continent.addTerritoriesContained(c);
        assertEquals(1, continent.getNumberOwned(p));
    }
}
