/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_details;

import auth.UserDetails;
import course.CourseBean;
import database.*;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * 
 * @author marti og christian
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
    public void findUser(String username) {
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
        Users user = (Users) query.getSingleResult();
        if (user == null)return null;
        return new UserDetails(user.getUserID(), user.getUsername(), user.getEmail(), 0,0);
    }
    
    /**
     * @param usersToAdd Lista med brukere som skal legges til. 
     */
    @Override
    public void bulkUsers(List<UserDetails> usersToAdd){
          for (UserDetails user : usersToAdd) {
            new CourseBean().addMemberToCourse(user.getId(), user.getCourseID(), user.isTeacher()?1:0);
          }
        
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

    @Override
    public UserDetails getUserObj(String username) {
        Users user = (Users) em.createNamedQuery("Users.findByUsername").setParameter("username", username).getSingleResult();
        
        return null;
    }

    @Override
    public UserDetails getUserByID(int x) {
        Integer userID = (Integer) x;
        Users user = (Users) em.createNamedQuery("Users.findByUserID")
                .setParameter("userID", x).getSingleResult();
        return new UserDetails(
                user.getUserID(), user.getUsername(), user.getEmail(), 0, -1, 
                user.getFirstname(), user.getLastname()
        );
        
    }

    @Override
    public ArrayList<UserDetails> getAllStudentFromCourse(int courseID) {
        Courses course = em.find(Courses.class, courseID);
        Query query = em.createNamedQuery("CourseMembers.findByCourseID")
                .setParameter("courseID", course.getCourseID());
        
        List<CourseMembers> cm = query.getResultList();
        
        ArrayList<UserDetails> output = new ArrayList<>();
        for (CourseMembers users : cm) {
            output.add(UsersToUserDetails(users.getUsers()));
        }
        return output;
    }
    
    
    private UserDetails UsersToUserDetails(Users user) {
        return new UserDetails(
                user.getUserID(), user.getUsername(), user.getEmail(),
                -1, -1, user.getFirstname(), user.getLastname());
        
    }
    
    
    
    
    
    
}
