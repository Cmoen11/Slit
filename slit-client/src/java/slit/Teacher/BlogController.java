/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slit.Teacher;

import blog.BlogkekRemote;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Christian
 */
public class BlogController {
    
    public void clickMe() {
        lookupBlogkekRemote().erlendKek();
    }

    private BlogkekRemote lookupBlogkekRemote() {
        try {
            Context c = new InitialContext();
            return (BlogkekRemote) c.lookup("java:comp/env/Blogkek");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
