
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
    
    /**
     * Create an new post
     * @param post Postdata
     */
    @Override
    public void createPost(Post post) {
        em.persist(transferObjectToEntityObject(post));    
    }
    
    /**
     * get all the post from user and course
     * @param user object with course index.
     * @return an arrayList with all posts 
     */
    @Override
    public ArrayList<Post> getPostFromUserAndCourse(UserDetails user) {   
        ArrayList<Post> output = new ArrayList<Post>();

        for (Blogpost post : em.find(Users.class, user.getId()).getBlogpostCollection()) {
            if (post.getCourseID().getCourseID() == user.getCourseID()) {
                output.add(entityObjecToTransferObject(post));
            }
        }
        
        
        return output;
       
    }
    /**
     * Get all the posts from the given user.
     * @param user
     * @return 
     */
    @Override
    public ArrayList<Post> getAllPostsFromUser(UserDetails user) {
        ArrayList<Post> output = new ArrayList<Post>();

        for (Blogpost post : em.find(Users.class, user.getId()).getBlogpostCollection()) {
            output.add(entityObjecToTransferObject(post));
        }
        
        
        return output;
    }
    
    /**
     * Delete an existing post
     * @param post the post that are to be deleted
     */
    @Override
    public void deleteBlogPost(Post post) {
        em.remove(em.find(Blogpost.class, post.getPostID()));
    }
    
    /**
     * Update a current blog post.
     * @param post the post that are to be updated
     */
    @Override
    public void updatePost(Post post) {
        Blogpost input = em.find(Blogpost.class, post.getPostID());
        input.setTitle(post.getTitle());
        input.setContent(post.getContent());
        em.merge(input);
    }

}
    
    
