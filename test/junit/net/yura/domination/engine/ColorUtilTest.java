package net.yura.domination.engine;

import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;

/**
 *
 * @author Matt Thornton
 */
public class ColorUtilTest extends TestCase {
    
    // Color integers
    private final int new_color = 0xFF000066;
    
    // Color strings
    private final String name = "darkblue";
    
    // Hash maps
    private Map intToString;
    private Map stringToInt;
    
    /**
     * ColorUtilTest constructor
     * @param testName String name of the test
     */
    public ColorUtilTest(String testName) {
        super(testName);
        
        // TODO: anything to do here?
    }
    
    /**
     * Sets up test environment before each test
     * @throws Exception 
     */
    @Override
    protected void setUp() throws Exception {
        // Store original maps to be restored during teardown
        this.intToString = new HashMap(ColorUtil.intToString);
        this.stringToInt = new HashMap(ColorUtil.stringToInt);
        
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        // Restore original maps
        ColorUtil.intToString = this.intToString;
        ColorUtil.stringToInt = this.stringToInt;
        
        super.tearDown();
    }

    /**
     * Test of add method, of class ColorUtil.
     */
    public void testAdd() {
        System.out.println("Starting testAdd");
        
        // Test 1. Add brand new color
        // Get the original map sizes
        int intToString_size = ColorUtil.intToString.size();
        int stringToInt_size = ColorUtil.stringToInt.size();
        
        // Add a new color
        ColorUtil.add(new_color, name);
        
        // Verify both maps have increased in size by one
        assertEquals((intToString_size + 1), ColorUtil.intToString.size());
        assertEquals((stringToInt_size + 1), ColorUtil.stringToInt.size());
        
        // Verify new color was added propertly
        assertEquals("Added color name does not match intToString get",
                     name,
                     ColorUtil.intToString.get(new_color));
        assertEquals("Added color value does not match stringToInt get",
                     new_color,
                     ColorUtil.stringToInt.get(name));
        
        // New color added successfully, set new map sizes
        intToString_size = ColorUtil.intToString.size();
        stringToInt_size = ColorUtil.stringToInt.size();
        
        
        // Test 2. Add same color
        // Add the new color again
        ColorUtil.add(new_color, name);
        
        // Verify both maps have not changed in size
        assertEquals("intToString size changed after adding the same color",
                     intToString_size,
                     ColorUtil.intToString.size());
        assertEquals("stringToInt size changed after adding the same color",
                     stringToInt_size,
                     ColorUtil.stringToInt.size());
        
        
        // Test 3. Add predefined color
        // Add a predefined color
        ColorUtil.add(ColorUtil.BLACK, "black");
        
        // Verify both maps have not changed in size
        assertEquals("intToString size changed after adding a predefined color",
                     intToString_size,
                     ColorUtil.intToString.size());
        assertEquals("stringToInt size changed after adding a predefined color",
                     stringToInt_size,
                     ColorUtil.stringToInt.size());
    }

    /**
     * Test of getStringForColor method, of class ColorUtil.
     */
    public void testGetStringForColor() {
        System.out.println("Starting testGetStringForColor");
        
        // Test 1. Get string from predefined color
        assertEquals("Returned predefined color string does not match intToString get",
                     ColorUtil.intToString.get(ColorUtil.BLUE),
                     ColorUtil.getStringForColor(ColorUtil.BLUE));
        
        
        // Test 2. Get string from non-exsistent color
        assertEquals("Incorrect HEX string returned from non-exsistent color",
                     "#" + Integer.toHexString((new_color & 0xffffff) | 0x1000000).substring(1),
                     ColorUtil.getStringForColor(new_color));
        
        
        // Test 3. Get string from added color
        ColorUtil.intToString.put(new_color, name);
        assertEquals("Returned new color string does not match intToString get",
                     ColorUtil.intToString.get(new_color),
                     ColorUtil.getStringForColor(new_color));
    }

    /**
     * Test of getColor method, of class ColorUtil.
     */
    public void testGetColor() {
        System.out.println("Starting testGetColor");
        
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTextColorFor method, of class ColorUtil.
     */
    public void testGetTextColorFor() {
        System.out.println("Starting testGetTextColorFor");
        
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRed method, of class ColorUtil.
     */
    public void testGetRed() {
        System.out.println("Starting testGetRed");
        
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGreen method, of class ColorUtil.
     */
    public void testGetGreen() {
        System.out.println("getGreen");
        
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBlue method, of class ColorUtil.
     */
    public void testGetBlue() {
        System.out.println("getBlue");
        
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAlpha method, of class ColorUtil.
     */
    public void testGetAlpha() {
        System.out.println("getAlpha");
        
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHexForColor method, of class ColorUtil.
     */
    public void testGetHexForColor() {
        System.out.println("getHexForColor");
        
        fail("The test case is a prototype.");
    }
}
