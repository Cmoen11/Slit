/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_details;

import javax.ejb.Remote;

/**
 *
 * @author marti
 */
@Remote
public interface UserBeanRemote {

    int createUser(String username, String password, String email);

    void addUserToCourse(String username, long courseID);
    
}
