/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import blog.Post;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Christian
 */
@Remote
public interface NewsBeanRemote {

    void createPost(Post post);
    public void editPost(Post post);

    List<Post> getPostsFromCourse(int courseID);
}
