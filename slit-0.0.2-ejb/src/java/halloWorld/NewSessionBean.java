/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package halloWorld;

import javax.ejb.Stateless;

/**
 *
 * @author Christian
 */
@Stateless
public class NewSessionBean implements NewSessionBeanRemote {

    @Override
    public String helloWorld() {
        return "Hello World";
    }
    
    
}
