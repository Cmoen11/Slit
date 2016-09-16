/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modul;

import javax.ejb.Remote;

/**
 *
 * @author Christian
 */
@Remote
public interface ModulRemote {

    boolean createModule(String name, String desc);
    
}
