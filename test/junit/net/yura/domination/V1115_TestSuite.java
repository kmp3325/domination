/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yura.domination;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.yura.domination.engine.*;
import net.yura.domination.engine.core.*;

/**
 *
 * @author Matt Thornton
 */
public class V1115_TestSuite
{
    
    public static Test suite()
    {
        TestSuite suite = new TestSuite( V1115_TestSuite.class.getName() );
        
        suite.addTestSuite( ColorUtilTest.class );
        suite.addTestSuite( PlayerTest.class );
        suite.addTestSuite( RiskGameTest.class );
        
        return suite;
    }
}
