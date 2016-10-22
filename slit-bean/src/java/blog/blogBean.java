
package blog;

import auth.UserDetails;
import database.Blog;
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
        Blog blog = new Blog();
        blog.setBlogID(Integer.SIZE);
        blog.setUserID(em.find(Users.class, userObj.getId()));
        blog.setCreationDate(new java.util.Date());
        
        em.persist(blog);
    }
    
    /**
     * Opprett en post.
     * @param userObj
     * @param postObj 
     */
    @Override
    public void createPost(UserDetails userObj, Post postObj) {
        Blogpost post = new Blogpost();
        int userID = userObj.getId();
        Blog blog = (Blog) em.createNamedQuery("Blog.findByUserID").getSingleResult();
        if (blog != null) { // om brukeren har blogg. 
            post.setPostID(Integer.SIZE);
            post.setTitle(postObj.getTitle());
            post.setContent(post.getContent());
            post.setCreationDate(post.getCreationDate());
            post.setCourseID(em.find(Courses.class, userObj.getCourseID()));
            post.setBlogID(blog);
            em.persist(post);
        } else {    // opprett blogg, og publiser innlegget.
            createBlog(userObj);
            createPost(userObj, postObj);
        }
        
    }
    
    
    
    
    
}
