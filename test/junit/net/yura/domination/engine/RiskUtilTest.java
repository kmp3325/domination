/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yura.domination.engine;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import junit.framework.TestCase;
import net.yura.domination.engine.core.Player;
import net.yura.domination.engine.core.RiskGame;
import net.yura.domination.ui.swinggui.SwingGUIPanel;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author keegan
 */
public class RiskUtilTest extends TestCase {
    
    public RiskUtilTest(String testName) {
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
     * Test of openMapStream method, of class RiskUtil.
     */
    public void testOpenMapStream() throws Exception {
        System.out.println("openMapStream");
        
        RiskIO streamOpener = mock(RiskIO.class);
        RiskUtil.streamOpener = streamOpener;
        String a = "a";
        InputStream expResult = new ByteArrayInputStream(new byte[1]);
        when(streamOpener.openMapStream(a)).thenReturn(expResult);

        InputStream result = RiskUtil.openMapStream(a);
        assertEquals(expResult, result);
    }

    /**
     * Test of openStream method, of class RiskUtil.
     */
    public void testOpenStream() throws Exception {
        System.out.println("openStream");
        
        RiskIO streamOpener = mock(RiskIO.class);
        RiskUtil.streamOpener = streamOpener;
        String a = "a";
        InputStream expResult = new ByteArrayInputStream(new byte[1]);
        when(streamOpener.openStream(a)).thenReturn(expResult);
        
        InputStream result = RiskUtil.openStream(a);
        assertEquals(expResult, result);
    }

    /**
     * Test of getResourceBundle method, of class RiskUtil.
     */
    public void testGetResourceBundle() {
        System.out.println("getResourceBundle");
        
        RiskIO streamOpener = mock(RiskIO.class);
        RiskUtil.streamOpener = streamOpener;
        
        Class c = String.class;
        String n = "n";
        Locale l = Locale.ENGLISH;
        ResourceBundle expResult = null;
        try {
            expResult = new PropertyResourceBundle(new ByteArrayInputStream(new byte[1]));
        } catch (IOException ex) {
            Logger.getLogger(RiskUtilTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        when(streamOpener.getResourceBundle(c, n, l)).thenReturn(expResult);
        ResourceBundle result = RiskUtil.getResourceBundle(c, n, l);
        assertEquals(expResult, result);
    }

    /**
     * Test of openURL method, of class RiskUtil.
     */
    public void testOpenURL() throws Exception {
        System.out.println("openURL");
        
        RiskIO streamOpener = mock(RiskIO.class);
        RiskUtil.streamOpener = streamOpener;
        
        URL url = new URL("http://example.com");
        RiskUtil.openURL(url);
    }

    /**
     * Test of openDocs method, of class RiskUtil.
     */
    public void testOpenDocs() throws Exception {
        System.out.println("openDocs");
        
        RiskIO streamOpener = mock(RiskIO.class);
        RiskUtil.streamOpener = streamOpener;
        
        String docs = "docs";
        RiskUtil.openDocs(docs);
    }

    /**
     * Test of saveFile method, of class RiskUtil.
     */
    public void testSaveFile() throws Exception {
        System.out.println("saveFile");
        
        RiskIO streamOpener = mock(RiskIO.class);
        RiskUtil.streamOpener = streamOpener;
        
        String file = "file";
        RiskGame aThis = mock(RiskGame.class);
        RiskUtil.saveFile(file, aThis);
    }

    /**
     * Test of getLoadFileInputStream method, of class RiskUtil.
     */
    public void testGetLoadFileInputStream() throws Exception {
        System.out.println("getLoadFileInputStream");
        
        RiskIO streamOpener = mock(RiskIO.class);
        RiskUtil.streamOpener = streamOpener;
        
        String file = "file";
        InputStream expResult = new ByteArrayInputStream(new byte[1]);
        when(streamOpener.loadGameFile(file)).thenReturn(expResult);
        
        InputStream result = RiskUtil.getLoadFileInputStream(file);
        assertEquals(expResult, result);
    }

    /**
     * Test of createGameString method, of class RiskUtil.
     */
    public void testCreateGameString() {
        System.out.println("createGameString");
        
        int easyAI = 0;
        int averageAI = 1;
        int hardAI = 2;
        int gameMode = 0;
        int cardsMode = 0;
        boolean AutoPlaceAll = false;
        boolean recycle = false;
        String mapFile = "mapFile";

        String expResult = "1\n0\n2\nchoosemap mapFile\nstartgame domination increasing";
        String result = RiskUtil.createGameString(easyAI, averageAI, hardAI, gameMode, cardsMode, AutoPlaceAll, recycle, mapFile);
        assertEquals(expResult, result);
        
        AutoPlaceAll = true;
        gameMode = 2;
        cardsMode = 1;
        expResult = "1\n0\n2\nchoosemap mapFile\nstartgame capital fixed autoplaceall";
        result = RiskUtil.createGameString(easyAI, averageAI, hardAI, gameMode, cardsMode, AutoPlaceAll, recycle, mapFile);
        assertEquals(expResult, result);
    
        recycle = true;
        gameMode = 3;
        cardsMode = 2;
        expResult = "1\n0\n2\nchoosemap mapFile\nstartgame mission italianlike autoplaceall recycle";
        result = RiskUtil.createGameString(easyAI, averageAI, hardAI, gameMode, cardsMode, AutoPlaceAll, recycle, mapFile);
        assertEquals(expResult, result);
    
    }

    /**
     * Test of getMapNameFromLobbyStartGameOption method, of class RiskUtil.
     */
    public void testGetMapNameFromLobbyStartGameOption() {
        System.out.println("getMapNameFromLobbyStartGameOption");
        
        String options = "1\n2\n3\nchoosemap whatever";
        String expResult = "whatever";
        String result = RiskUtil.getMapNameFromLobbyStartGameOption(options);
        assertEquals(expResult, result);
    }

    /**
     * Test of getGameDescriptionFromLobbyStartGameOption method, of class RiskUtil.
     */
    public void testGetGameDescriptionFromLobbyStartGameOption() {
        System.out.println("getGameDescriptionFromLobbyStartGameOption");
        
        String options = "1\n2\n3\nchooseMap whatever\nstartgame now";
        String expResult = "AI:6 now";
        String result = RiskUtil.getGameDescriptionFromLobbyStartGameOption(options);
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of printStackTrace method, of class RiskUtil.
     */
    public void testPrintStackTrace() {
        System.out.println("printStackTrace");
        
        Throwable ex = new NullPointerException("This should be logged as a test.");
        RiskUtil.printStackTrace(ex);
    }

    /**
     * Test of donate method, of class RiskUtil.
     */
    public void testDonate() throws Exception {
        System.out.println("donate");
        
        RiskIO streamOpener = mock(RiskIO.class);
        RiskUtil.streamOpener = streamOpener;
        
        RiskUtil.donate();
    }

    /**
     * Test of donatePayPal method, of class RiskUtil.
     */
    public void testDonatePayPal() throws Exception {
        System.out.println("donatePayPal");
        
        RiskIO streamOpener = mock(RiskIO.class);
        RiskUtil.streamOpener = streamOpener;
        
        RiskUtil.donatePayPal();
    }

    /**
     * Test of getPlayerSettings method, of class RiskUtil.
     */
    public void testGetPlayerSettings() {
        System.out.println("getPlayerSettings");
        
        Risk risk = mock(Risk.class);
        String value = "value";
        when(risk.getRiskConfig(anyString())).thenReturn(value);
        Class uiclass = String.class;
        String key = "whatever";
        
        Properties result = RiskUtil.getPlayerSettings(risk, uiclass);
        assertEquals(value, result.getProperty(key));
        
        uiclass = SwingGUIPanel.class;
        Preferences expPreferences = Preferences.userNodeForPackage(uiclass);
        result = RiskUtil.getPlayerSettings(risk, uiclass);
        assertEquals(expPreferences.get(key, value), result.getProperty(key));
    }

    /**
     * Test of loadPlayers method, of class RiskUtil.
     */
    public void testLoadPlayers() {
        System.out.println("loadPlayers");

        Risk risk = mock(Risk.class);
        String name = "name";
        Class uiclass = SwingGUIPanel.class;
        
        RiskUtil.loadPlayers(risk, uiclass);
    }

    /**
     * Test of savePlayers method, of class RiskUtil.
     */
    public void testSavePlayers_Risk_Class() {
        System.out.println("savePlayers");
        
        Risk risk = mock(Risk.class);  
        Class uiclass = SwingGUIPanel.class;
        RiskGame riskGame = mock(RiskGame.class);
        when(risk.getGame()).thenReturn(riskGame);
        Vector v = new Vector();
        String name = "name";
        v.add(new Player(0, name, 0, "0"));
        when(riskGame.getPlayers()).thenReturn(v);
        when(risk.getType(0)).thenReturn("type");
        
        RiskUtil.savePlayers(risk, uiclass);
        Preferences expPreferences = Preferences.userNodeForPackage(uiclass);
        String expNameKey = "default.player1.name";
        assertEquals(name, expPreferences.get(expNameKey, null));
    }

    /**
     * Test of savePlayers method, of class RiskUtil.
     */
    public void testSavePlayers_List_Class() {
        System.out.println("savePlayers");
        
        List v = new ArrayList();
        String[] player = new String[3];
        player[0] = "name";
        player[1] = "color";
        player[2] = "type";
        v.add(player);
        Class uiclass = SwingGUIPanel.class;
        
        RiskUtil.savePlayers(v, uiclass);
        Preferences expPreferences = Preferences.userNodeForPackage(uiclass);
        String expNameKey = "default.player1.name";
        assertEquals("name", expPreferences.get(expNameKey, null));
    }

    /**
     * Test of readMap method, of class RiskUtil.
     */
    public void testReadMap() throws Exception {
        System.out.println("readMap");
        
        
        byte[] b = new byte[3];
        InputStream in = new ByteArrayInputStream(b);
        BufferedReader result = RiskUtil.readMap(in);
        assertNotNull(result);
        
        b[0] = (byte) 0xEF;
        result = RiskUtil.readMap(in);
        assertNotNull(result);
        
        
        b[1] = (byte) 0xBB;
        result = RiskUtil.readMap(in);
        assertNotNull(result);
        
        b[2] = (byte) 0xBF;
        result = RiskUtil.readMap(in);
        assertNotNull(result);
    }

    /**
     * Test of loadInfo method, of class RiskUtil.
     */
    public void testLoadInfo() {
        System.out.println("loadInfo");
        // TODO
    }

    /**
     * Test of saveGameLog method, of class RiskUtil.
     */
    public void testSaveGameLog() throws Exception {
        System.out.println("saveGameLog");
        
        File logFile = new File("testSaveGameLog.txt");
        RiskGame game = mock(RiskGame.class);
        Vector v = new Vector();
        v.add("test command");
        when(game.getCommands()).thenReturn(v);
        RiskUtil.saveGameLog(logFile, game);
    }

    /**
     * Test of getOutputStream method, of class RiskUtil.
     */
    public void testGetOutputStream() throws Exception {
        System.out.println("getOutputStream");
        
        
        File dir = new File("testGetOutputStreamDir");
        String fileName = "testGetOutputStream.txt";
        OutputStream result = RiskUtil.getOutputStream(dir, fileName);
        assertNotNull(result);
    }

    /**
     * Test of rename method, of class RiskUtil.
     */
    public void testRename() {
        System.out.println("rename");
        
        File oldFile = mock(File.class);
        File newFile = mock(File.class);
        when(newFile.exists()).thenReturn(true);
        when(newFile.delete()).thenReturn(false);
        try {
            RiskUtil.rename(oldFile, newFile);
            fail("Should not be able to rename file that doesn't exist/cannot be deleted.");
        } catch (RuntimeException e) {}
        
        when(newFile.delete()).thenReturn(true);
        when(oldFile.renameTo(newFile)).thenReturn(false);
        when(oldFile.delete()).thenReturn(false);
        when(oldFile.toString()).thenReturn("oldFile");
        when(oldFile.isDirectory()).thenReturn(true);
        when(newFile.exists()).thenReturn(true);
        when(oldFile.list()).thenReturn(new String[0]);
        
        System.out.println("The next line should print an error 'can not del source file: oldFile'");
        RiskUtil.rename(oldFile, newFile);
        
        when(oldFile.list()).thenThrow(new RuntimeException("This runTimeException should occur."));
        try {
        RiskUtil.rename(oldFile, newFile);
        fail("Runtime exception should have occured.");
        } catch (RuntimeException e) {}
    }

    /**
     * Test of asVector method, of class RiskUtil.
     */
    public void testAsVector() {
        System.out.println("asVector");

        List list = new ArrayList();
        assertTrue(RiskUtil.asVector(list) instanceof Vector);
        list = new Vector();
        assertTrue(RiskUtil.asVector(list) instanceof Vector);
    }

    /**
     * Test of asHashtable method, of class RiskUtil.
     */
    public void testAsHashtable() {
        System.out.println("asHashtable");
        
        Map map = new Hashtable();
        assertTrue(RiskUtil.asHashtable(map) instanceof Hashtable);
    }

    /**
     * Test of replaceAll method, of class RiskUtil.
     */
    public void testReplaceAll() {
        System.out.println("replaceAll");
        
        String string = "this string is a string";
        String notregex = "string";
        String replacement = "thing";
        String expResult = "this thing is a thing";
        String result = RiskUtil.replaceAll(string, notregex, replacement);
        assertEquals(expResult, result);
    }

    /**
     * Test of copy method, of class RiskUtil.
     */
    public void testCopy() throws Exception {
        System.out.println("copy");
        
        File src = mock(File.class);
        File dest = mock(File.class);
        
        when(src.isDirectory()).thenReturn(true);
        when(dest.exists()).thenReturn(false);
        String[] files = new String[0];
        when(src.list()).thenReturn(files);
        RiskUtil.copy(src, dest);
    }
}
