/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blog;

import auth.UserDetails;
import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author Christian
 */
@Remote
public interface blogBeanRemote {

    void createPost(Post post);

    ArrayList<Post> getPostFromUserAndCourse(UserDetails user);

    void deleteBlogPost(Post post);

    void updatePost(Post post);

    
}
