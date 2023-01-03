/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.jsid.user;

/**
 *
 * @author Owner
 */
public interface IUserManager {
    //UserActionResult login (String email, String password);
    //UserActionResult register (String email, String password);
    //UserActionResult changePassword (User user);
    //UserActionResult verifyEmail (String userId);
    //String logout(User user);
    User createUser (User user);
    User updateUser (User user);
    User getUser (String userId);
    User getUserByEmail(String email);
    //String createJwt (User user);

    //public UserActionResult googleLogin(String email, String name, String picture);
    //public UserActionResult facebookLogin(String email, String name, String picture);
}
