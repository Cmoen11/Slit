/*
 * This class is used to transfer userdata to the ejb and from the 
 * ejb to the client
 * 
 */
package auth;

import java.io.Serializable;

/**
 *
 * @author Christian og litt Tor Ole 
 */
public class UserDetails implements Serializable {
    String username, email, firstname, lastname;
    int id, courseID, passedModules;
    boolean teacher;
    
    /**
     * Create a consturctor without first and lastname.
     * @param id
     * @param username
     * @param email
     * @param passedModules
     * @param courseID
     * @param isTeacher 
     */
    public UserDetails(Integer id, String username, String email, int passedModules, int courseID, int isTeacher) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passedModules = passedModules;
        this.courseID = courseID;
        this.teacher = (isTeacher == 1);  
    }
    
    /**
     * Create an constructor with first and lastname
     * @param id
     * @param username
     * @param email
     * @param passedModules
     * @param courseID
     * @param isTeacher
     * @param firstname
     * @param lastname 
     */
    public UserDetails(Integer id, String username, String email, int passedModules, int courseID, int isTeacher, String firstname, String lastname) {
        this(id, username, email, passedModules, courseID, isTeacher);
        this.firstname = firstname;
        this.lastname = lastname;
    }
    
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        if (teacher)
            return username + " | Foreleser";
        return username; 
    }

    public boolean isTeacher() {
        return teacher;
    }

    public void setTeacher(boolean teacher) {
        this.teacher = teacher;
    }
    
    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPassedModules() {
        return passedModules;
    }

    public void setPassedModules(int passedModules) {
        this.passedModules = passedModules;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
}
