/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth;

import java.util.HashMap;
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
public class LoginAuth_beanTest {

    @Test
    public void testAuthAccount() throws Exception {
        System.out.println("authAccount");
        String username = "test";
        String password = "test";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        LoginAuthRemote instance = (LoginAuthRemote)container.getContext().lookup("java:global/classes/LoginAuth_bean");
        boolean expResult = true;
        boolean result = instance.authAccount(username, password);
        assertEquals(expResult, result);
        container.close();
    }

    
}
