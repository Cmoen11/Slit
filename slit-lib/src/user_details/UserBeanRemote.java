/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_details;

import auth.UserDetails;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author marti
 */
@Remote
public interface UserBeanRemote {

    List<String> usersToAdd = new ArrayList<>();
            
    int createUser(String username, String password, String email);

    void addUserToCourse(String username, long courseID);

    public void bulkUsers(List<String> usersToAdd, long courseID);
    public UserDetails getUserByUsername(String username);

    public void editUser(UserDetails obj, String password);
}