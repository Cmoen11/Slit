/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_details;

import auth.UserDetails;
import database.*;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * 
 * @author marti
 */
@Stateless
public class UserBean implements UserBeanRemote {
    @PersistenceContext()
    EntityManager em;

    
    @Override
    public int createUser(String username, String password, String email) {
        //Users user = new Users(username, password, email);
        //em.persist(user); 
        
        return 0;
    }
    /* 
    * TO DO FIX.
    * @param1: username
    * @param: course id
    */
    @Override
    public void addUserToCourse(String username, long courseID) {
       // Query for username.
       Query query = em.createNamedQuery("Users.findByUsername", Users.class);
       List<Users> results = query.getResultList();
       Users user = results.get(0);
       // If @param1 username was not found, create a new user.
       if (user == null){
          createUser("halv geir", "hel geir", "halv@geir.no");
       }
       // Persist the entry to CourceClass table.
       //CourseClass studentEntry = new CourseClass(user.getId(), courseID, false);
       //em.persist(studentEntry);
       } 
    
    @Override
    public UserDetails getUserByUsername(String username) {
        Query query = em.createNamedQuery("Users.findByUsername", Users.class);
        Object result = query.getSingleResult();
        if (result == null)return null;
        
        Users user = (Users) result;
        return new UserDetails(user.getUserID(), user.getUsername(), user.getEmail(), 0,0);
    }
    
    /**
     * TO DO : FIX
     * Legg til vilkårlig antall studenter til et emne
     * OBS! Funksjonalitet for å legge username til i lista må implementeres.
     * @param usersToAdd
     * @param courseID
     */
    @Override
    public void bulkUsers(List<String> usersToAdd, long courseID){
        //if (em.find(Course.class, courseID) == null) {
        System.out.println("Nope"); 
    }
    //    for (String username: usersToAdd)
    //        addUserToCourse(username, courseID);

    @Override
    public void editUser(UserDetails obj, String password) {
        Users user = em.find(Users.class, obj.getId());
        System.out.println(user.getUsername());
        user.setEmail(obj.getEmail());
        user.setPassword(password);
        em.merge(user);
        
    }
    // Integer id, String username, String email, int courseID, int isTeacher, String firstname, String lastname
    @Override
    public ArrayList<UserDetails> getAllUsers() {
        List<Users> list = em.createNamedQuery("Users.findAll").getResultList();
        ArrayList<UserDetails> output = new ArrayList<>();
        for (Users user : list) output.add(new UserDetails(
                user.getUserID(), user.getUsername(), user.getEmail(),
                -1, -1, user.getFirstname(), user.getLastname()
        ));
        return output;
    }

    
    
    
    
}
