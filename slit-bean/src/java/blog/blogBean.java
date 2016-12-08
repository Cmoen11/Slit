
package blog;

import auth.UserDetails;
import database.Blogpost;
import database.Courses;
import database.Users;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Christian
 */
@Stateless
public class blogBean implements blogBeanRemote {

    @PersistenceContext()
    EntityManager em;
    
    /**
     * Opprett en blogg.
     * @param userObj 
     */
    @Override
    public void createBlog(UserDetails userObj) {

    }
    
    /**
     * Opprett en post.
     * @param userObj
     * @param postObj 
     */
    @Override
    public void createPost(UserDetails userObj, Post postObj) {

        
    }
    
    
    
    
    
}
