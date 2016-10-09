/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course;

import auth.CourseInfo;
import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author Christian
 */
@Remote
public interface CourseBeanRemote {
    
    ArrayList<CourseInfo> getCourses();
    ArrayList<String>getCourseMembers(int courseID);
    
}
