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
    private final int dark_blue = 0xFF000066;
    
    // Color strings
    private final String dark_blue_name = "darkblue";
    private final String black_name = "black";
    
    // Hash maps
    private Map intToString;
    private Map stringToInt;
    
    /**
     * ColorUtilTest constructor
     * @param testName String name of the test
     */
    public ColorUtilTest(String testName) {
        super(testName);
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
        ColorUtil.add(dark_blue, dark_blue_name);
        
        // Verify both maps have increased in size by one
        assertEquals((intToString_size + 1), ColorUtil.intToString.size());
        assertEquals((stringToInt_size + 1), ColorUtil.stringToInt.size());
        
        // Verify new color was added propertly
        assertEquals("Added color name does not match intToString get",
                     dark_blue_name,
                     ColorUtil.intToString.get(dark_blue));
        assertEquals("Added color value does not match stringToInt get",
                     dark_blue,
                     ColorUtil.stringToInt.get(dark_blue_name));
        
        // New color added successfully, set new map sizes
        intToString_size = ColorUtil.intToString.size();
        stringToInt_size = ColorUtil.stringToInt.size();
        
        
        // Test 2. Add same color
        // Add the new color again
        ColorUtil.add(dark_blue, dark_blue_name);
        
        // Verify both maps have not changed in size
        assertEquals("intToString size changed after adding the same color",
                     intToString_size,
                     ColorUtil.intToString.size());
        assertEquals("stringToInt size changed after adding the same color",
                     stringToInt_size,
                     ColorUtil.stringToInt.size());
        
        
        // Test 3. Add predefined color
        // Add a predefined color
        ColorUtil.add(ColorUtil.BLACK, black_name);
        
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
                     "#" + Integer.toHexString((dark_blue & 0xffffff) | 0x1000000).substring(1),
                     ColorUtil.getStringForColor(dark_blue));
        
        
        // Test 3. Get string from added color
        ColorUtil.intToString.put(dark_blue, dark_blue_name);
        assertEquals("Returned new color string does not match added name",
                     dark_blue_name,
                     ColorUtil.getStringForColor(dark_blue));
    }

    /**
     * Test of getColor method, of class ColorUtil.
     */
    public void testGetColor() {
        System.out.println("Starting testGetColor");
        
        // Test 1. Get numeric color from predefined color string
        assertEquals("Returned predefined color numeric does not match stringToInt get",
                     ColorUtil.BLACK,
                     ColorUtil.getColor(black_name));
        
        // Test 2. Get numeric from non-exsistent color
        assertEquals("Found an existing color matching a non-exsistent color string",
                     0,
                     ColorUtil.getColor(dark_blue_name));
        
        // Test 3. Get numeric from added color
        ColorUtil.stringToInt.put(dark_blue_name, dark_blue);
        assertEquals("Returned new color numeric does not match added HEX", 
                     dark_blue,
                     ColorUtil.getColor(dark_blue_name));
        
        // Test 4. Get numeric from a string with a parsable integer
        assertEquals("Returned incorrect numeric from string with a parsable integer",
                     0xFF1234AB,
                     ColorUtil.getColor( "0x1234AB" ) );
        
        // Test 5. Get numeric from a string without a parsable integer
        assertEquals("Returned an integer from a string without a parsable integer",
                     0,
                     ColorUtil.getColor( "MATT" ) );
    }

    /**
     * Test of getTextColorFor method, of class ColorUtil.
     */
    public void testGetTextColorFor() {
        System.out.println("Starting testGetTextColorFor");
        
        // Test 1. Boundary testing expecting return BLACK
        int[] colorArray = new int[] { 0xFFF53333,
                                       0xFF33F533,
                                       0xFFC8C833,
                                       0xFFF0F133,
                                       0xFFF1F033,
                                       ColorUtil.BLACK
                                     };
        for( int i = 0; i < colorArray.length; i++ )
        {
            assertEquals( "Did not return BLACK enumeration",
                          ColorUtil.BLACK,
                          ColorUtil.getTextColorFor( colorArray[i] ) );
        }
        
        // Test 2. Boundary testing expecting return WHITE
        colorArray = new int[] { 0xFF323233,
                                 0xFF969733,
                                 0xFF979633,
                                 0xFFF09633,
                                 0xFF96F033,
                                 ColorUtil.WHITE
                               };
        
        for( int i = 0; i < colorArray.length; i++ )
        {
            assertEquals( "Did not return WHITE enumeration",
                          ColorUtil.WHITE,
                          ColorUtil.getTextColorFor( colorArray[i] ) );
        }
    }

    /**
     * Test of getRed method, of class ColorUtil.
     */
    public void testGetRed() {
        System.out.println("Starting testGetRed");
        
        // Test 1. Verify the correct red component value is returned
        assertEquals( "Returned an incorrect value for the red component",
                      33,
                      ColorUtil.getRed( 0xFF2180CA ) );
        
        // Test 2. Verify a color with the max red component returns correcty
        assertEquals( "Did not return the max red component",
                      255,
                      ColorUtil.getRed( 0xFFFF4E2A) );
        
        // Test 3. Verify a color without a red component returns correctly
        assertEquals( "Returned a red component instead of 0",
                      0,
                      ColorUtil.getRed( 0xFF0080CA ) );
    }

    /**
     * Test of getGreen method, of class ColorUtil.
     */
    public void testGetGreen() {
        System.out.println("getGreen");
        
        // Test 1. Verify the correct green component value is returned
        assertEquals( "Returned an incorrect value for the green component",
                      33,
                      ColorUtil.getGreen( 0xFF602160 ) );
        
        // Test 2. Verify a color with the max green component returns correcty
        assertEquals( "Did not return the max green component",
                      255,
                      ColorUtil.getGreen( 0xFF60FF60) );
        
        // Test 3. Verify a color without a green component returns correctly
        assertEquals( "Returned a green component instead of 0",
                      0,
                      ColorUtil.getGreen( 0xFF600060 ) );
    }

    /**
     * Test of getBlue method, of class ColorUtil.
     */
    public void testGetBlue() {
        System.out.println("getBlue");
        
        // Test 1. Verify the correct blue component value is returned
        assertEquals( "Returned an incorrect value for the blue component",
                      33,
                      ColorUtil.getBlue( 0xFF606021 ) );
        
        // Test 2. Verify a color with the max blue component returns correcty
        assertEquals( "Did not return the max blue component",
                      255,
                      ColorUtil.getBlue( 0xFF6060FF) );
        
        // Test 3. Verify a color without a blue component returns correctly
        assertEquals( "Returned a blue component instead of 0",
                      0,
                      ColorUtil.getBlue( 0xFF606000 ) );
    }

    /**
     * Test of getAlpha method, of class ColorUtil.
     */
    public void testGetAlpha() {
        System.out.println("getAlpha");
        
        // Test 1. Verify the correct alpha component value is returned
        assertEquals( "Returned an incorrect value for the alpha component",
                      33,
                      ColorUtil.getAlpha( 0x218060CA ) );
        
        // Test 2. Verify a color with the max alpha component returns correcty
        assertEquals( "Did not return the max alpha component",
                      255,
                      ColorUtil.getAlpha( 0xFF8060CA) );
        
        // Test 3. Verify a color without a alpha component returns correctly
        assertEquals( "Returned a alpha component instead of 0",
                      0,
                      ColorUtil.getAlpha( 0x008060CA ) );
    }

    /**
     * Test of getHexForColor method, of class ColorUtil.
     */
    public void testGetHexForColor() {
        System.out.println("getHexForColor");
        
        // Test 1. Test correct string returned
        assertEquals( "Returned incorrect string",
                      "#123456",
                      ColorUtil.getHexForColor( 0xFF123456 ) );
        assertEquals( "Returned incorrect string",
                       "#000000",
                        ColorUtil.getHexForColor( 0xFF000000 ) );
        assertEquals( "Returned incorrect string",
                      "#ffffff",
                      ColorUtil.getHexForColor( 0xFFFFFFFF ) );
        
        // Test 2. Test correct enumeration string returned
        assertEquals( "Returned incorrect enumeration string",
                      "#000000",
                      ColorUtil.getHexForColor( ColorUtil.BLACK ) );
        assertEquals( "Returned incorrect enumeration string",
                      "#ffffff",
                      ColorUtil.getHexForColor( ColorUtil.WHITE ) );
        assertEquals( "Returned incorrect enumeration string",
                      "#c0c0c0",
                      ColorUtil.getHexForColor( ColorUtil.LIGHT_GRAY ) );
        assertEquals( "Returned incorrect enumeration string",
                      "#808080",
                      ColorUtil.getHexForColor( ColorUtil.GRAY ) );
        assertEquals( "Returned incorrect enumeration string",
                      "#404040",
                      ColorUtil.getHexForColor( ColorUtil.DARK_GRAY ) );
        assertEquals( "Returned incorrect enumeration string",
                      "#ff0000",
                      ColorUtil.getHexForColor( ColorUtil.RED ) );
        assertEquals( "Returned incorrect enumeration string",
                      "#ffafaf",
                      ColorUtil.getHexForColor( ColorUtil.PINK ) );
        assertEquals( "Returned incorrect enumeration string",
                      "#ffc800",
                      ColorUtil.getHexForColor( ColorUtil.ORANGE ) );
        assertEquals( "Returned incorrect enumeration string",
                      "#ffff00",
                      ColorUtil.getHexForColor( ColorUtil.YELLOW ) );
        assertEquals( "Returned incorrect enumeration string",
                      "#00ff00",
                      ColorUtil.getHexForColor( ColorUtil.GREEN ) );
        assertEquals( "Returned incorrect enumeration string",
                      "#ff00ff",
                      ColorUtil.getHexForColor( ColorUtil.MAGENTA ) );
        assertEquals( "Returned incorrect enumeration string",
                      "#00ffff",
                      ColorUtil.getHexForColor( ColorUtil.CYAN ) );
        assertEquals( "Returned incorrect enumeration string",
                      "#0000ff",
                      ColorUtil.getHexForColor( ColorUtil.BLUE ) );
    }
}
