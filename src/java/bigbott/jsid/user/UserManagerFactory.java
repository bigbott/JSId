/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.jsid.user;

import bigbott.jsid.JsidFilter;

/**
 *
 * @author Owner
 */
public class UserManagerFactory {

    static private IUserManager userManager;

    public static IUserManager getInstance() {
        return userManager;
    }

    public static void createInstance() {
        try {
            Class userManagerClass = Class.forName(JsidFilter.getInitParameter("userManagerClass"));
            userManager = (IUserManager) userManagerClass.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
