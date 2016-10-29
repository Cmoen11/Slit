package blog;

import java.util.Date;

/**
 *
 * @author Christian
 */
public class Post {

    String title, content;
    Date creationDate;
    int userID, courseID, postID;

    public Post(String title, String content, Date creationDate, int userID, int courseID, int postID) {
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.userID = userID;
        this.courseID = courseID;
        this.postID = postID;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }
    
    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
