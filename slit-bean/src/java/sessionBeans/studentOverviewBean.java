
package sessionBeans;

import javax.ejb.Stateless;

/**
 *
 * @author TorOle
 */
@Stateless
public class studentOverviewBean implements studentOverviewRemote {

    @Override
    public String clickMe() {
        return "Christian er kul!";
    }
    
}
