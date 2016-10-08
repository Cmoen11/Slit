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
    int id;
    
    public UserDetails(Integer id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
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
