/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import blog.Post;
import database.Courses;
import database.Newsposts;
import database.Users;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Christian
 */
@Stateless
public class NewsBean implements NewsBeanRemote {
    
    @PersistenceContext()
    EntityManager em;

    /**
     * Create a new post.
     * @param post 
     */
    @Override
    public void createPost(Post post) {
        Newsposts newPost = new Newsposts();
        newPost.setPostID(Integer.SIZE);
        newPost.setTitle(post.getTitle());
        newPost.setContent(post.getContent());
        newPost.setCreationDate(post.getCreationDate());
        newPost.setUserID(em.find(Users.class, post.getUserID()));
        newPost.setCourseID(em.find(Courses.class, post.getCourseID()));
        
        em.persist(newPost);
    }

    @Override
    public void editPost(Post post) {
       Newsposts editedPost = em.find(Newsposts.class, post.getPostID());
       editedPost.setContent(post.getContent());
       editedPost.setTitle(post.getTitle());
       editedPost.setCreationDate(post.getCreationDate());
       editedPost.setUserID(em.find(Users.class, editedPost.getUserID()));
       editedPost.setCourseID(em.find(Courses.class, editedPost.getCourseID()));
       
       em.merge(editedPost);
    }

    @Override
    public List<Post> getPostsFromCourse(int courseID) {
        List<Newsposts> postData = em.createNamedQuery("Newsposts.findByCourseID")
                .setParameter("courseID", em.find(Courses.class, courseID))
                .getResultList();
        
        List<Post> output = new ArrayList<Post>();
        for (Newsposts post : postData) {
            output.add(new Post(
                    post.getTitle(), post.getContent(), post.getCreationDate(),
                    post.getUserID().getUserID(), post.getCourseID().getCourseID(),
                    post.getPostID()
            ));
        } 
        return output;
    }
    
    
    
    
    /**
     * edit a post
     */
    
}
