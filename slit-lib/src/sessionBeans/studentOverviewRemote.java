
package sessionBeans; 
import auth.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Remote;

/**
 *
 * @author TorOle
 */
@Remote
public interface studentOverviewRemote {

    String clickMe();
    ArrayList<UserDetails> allStudentsList();
}


