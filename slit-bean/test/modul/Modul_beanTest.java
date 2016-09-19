/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modul;

import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Christian
 */
public class Modul_beanTest {
    
    public Modul_beanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createModule method, of class Modul_bean.
     */
    @Test
    public void testCreateModule() throws Exception {
        System.out.println("createModule");
        String name = "test";
        String desc = "test";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ModulRemote instance = (ModulRemote)container.getContext().lookup("java:global/classes/Modul_bean");
        boolean expResult = false;
        boolean result = instance.createModule(name, desc);
        assertEquals(expResult, result);
        container.close();
    }
    
}
