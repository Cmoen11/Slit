/*
 * This class is used to transfer userdata to the ejb and from the 
 * ejb to the client
 * 
 */
package auth;

import java.io.Serializable;

/**
 *
 * @author Christian
 */
public class UserDetails implements Serializable {
    String username, email;
    int id, courseID;
    
    public UserDetails(Integer id, String username, String email, int courseID) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.courseID = courseID;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
}
