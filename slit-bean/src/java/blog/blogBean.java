
package blog;

import auth.UserDetails;
import database.Blogpost;
import database.Courses;
import database.Users;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Merethe
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
    /**
     * Convert entity object to transfer object
     * @param post
     * @return 
     */
    private Post entityObjecToTransferObject(Blogpost post) {
        Post output = new Post();
        output.setPostID(post.getPostID());
        output.setContent(post.getContent());
        output.setCourseID(post.getCourseID().getCourseID());
        output.setUserID(post.getUserID().getUserID());
        output.setTitle(post.getTitle());
        output.setCreationDate(post.getCreationDate());
        
        return output;
    }
    
    @Override
    public void createPost(Post post) {
        em.persist(transferObjectToEntityObject(post));    
    }

    @Override
    public ArrayList<Post> getPostFromUserAndCourse(UserDetails user) {
        List<Blogpost> results = em.createNamedQuery("Blogpost.findByUserIDAndCourseID", Blogpost.class)
                .setParameter("courseID", em.find(Courses.class, user.getCourseID()))
                .setParameter("userID", em.find(Users.class, user.getId()))
                .getResultList();
        
        ArrayList<Post> output = new ArrayList<Post>();
        
        
        for (Blogpost i : results)
            output.add(entityObjecToTransferObject(i));
        
        return output;
       
    }

    @Override
    public void deleteBlogPost(Post post) {
        em.remove(em.find(Blogpost.class, post.getPostID()));
    }

    @Override
    public void updatePost(Post post) {
        Blogpost input = em.find(Blogpost.class, post.getPostID());
        input.setTitle(post.getTitle());
        input.setContent(post.getContent());
        em.merge(input);
    }
    
    
    
    
    
    

}
    
    
