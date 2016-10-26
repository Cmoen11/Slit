/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blog;

import database.CourseMembers;
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
public class Blogkek implements BlogkekRemote {
    @PersistenceContext()
    EntityManager em;
    
    @Override
    public void erlendKek() {
        Users user = em.find(Users.class, 1);
        for (CourseMembers course : user.getCourseMembersCollection()) {
            System.out.println(course.getCourses().getCourseName());
        }
        
    }
    
}
