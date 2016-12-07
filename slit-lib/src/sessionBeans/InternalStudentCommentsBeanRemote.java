/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import java.util.ArrayList;
import javax.ejb.Remote;
import transferClasses.InternalStudentComments;

/**
 *
 * @author Christian
 */
@Remote
public interface InternalStudentCommentsBeanRemote {

    void addComment(InternalStudentComments obj);

    ArrayList<InternalStudentComments> getAllComments(int courseID, int userID);
    
}
