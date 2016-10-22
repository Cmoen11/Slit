
package blog;

import java.util.Date;

/**
 *
 * @author Christian
 */
public class Post {
    String title, content;
    Date creationDate;

    public Post(String title, String content, Date creationDate) {
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
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
