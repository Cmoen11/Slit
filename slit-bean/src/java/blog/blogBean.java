
package blog;

import auth.UserDetails;
import database.Blogpost;
import database.Courses;
import database.Users;
import java.util.Date;
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
     * Convert transfer object to entity object
     * @param post
     * @return 
     */
    private Blogpost transferObjectToEntityObject(Post post) {
        Blogpost output = new Blogpost();
        output.setPostID(Integer.SIZE);
        output.setContent(post.getContent());
        output.setCourseID(em.find(Courses.class, post.getCourseID()));
        output.setUserID(em.find(Users.class, post.getUserID()));
        output.setTitle(post.getTitle());
        output.setCreationDate(new Date());
        
        return output;
    }
    
    
    @Override
    public void createPost(Post post) {
        em.persist(transferObjectToEntityObject(post));    
    }
    
    
    

}
    
    
