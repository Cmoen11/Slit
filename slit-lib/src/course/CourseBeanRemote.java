/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course;

import auth.UserDetails;
import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author Christian
 */
@Remote
public interface CourseBeanRemote {
    
    ArrayList<CourseInfo> getCourses();
    ArrayList<UserDetails>getCourseMembers(int courseID);

    void addMemberToCourse(int userID, int courseID, int teacher);
    ArrayList<UserDetails> getAllUsersNotInCourse(int courseID);

    void editCourse(CourseInfo newInfo);

    void removeUserFromCourse(int userID, int courseID);

    void switchUserStudentTeacher(int userID, int courseID);
    
}
