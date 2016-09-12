/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.ejb.Stateless;

/**
 *
 * @author Christian
 */
@Stateless
public class Coolboy implements CoolboyRemote {

    @Override
    public String HelloWorld() {
        return "Halla";
    }
    
    
    
}
