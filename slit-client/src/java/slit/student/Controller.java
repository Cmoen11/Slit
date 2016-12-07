/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slit.student;

import auth.UserDetails;
import slit.client.Main;

/**
 *
 * @author Martin Nenseth
 */

public class Controller {
    
    
    static UserDetails user;
    
    
    public static UserDetails getUser() {
        return user;
    }
      
    public static void setUser(UserDetails user){
        Controller.user = user;
    }
    

    public void logOut() {
        Main.runGUI();
    }  
    
}
