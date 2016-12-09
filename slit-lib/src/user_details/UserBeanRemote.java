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
 * @author Martin Nenseth
 */
@Remote
public interface UserBeanRemote {

    List<UserDetails> usersToAdd = new ArrayList<>();
            
    int createUser(String username, String password, String email);

    void findUser (String username);
    
    void bulkUsers(List<UserDetails> usersToAdd);
    UserDetails getUserByUsername(String username);

    void editUser(UserDetails obj, String password);

    ArrayList<UserDetails> getAllUsers();

    UserDetails getUserObj(String username);

    UserDetails getUserByID(int x);

    ArrayList<UserDetails> getAllStudentFromCourse(int courseID);

}