/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blog;

import auth.UserDetails;
import javax.ejb.Remote;

/**
 *
 * @author Christian
 */
@Remote
public interface blogBeanRemote {

    void createBlog(UserDetails userObj);

    void createPost(UserDetails userObj, Post postObj);
    
}
