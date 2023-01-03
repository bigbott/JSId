/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.jsid.command;

import bigbott.jsid.JsidResponseMessage;
import bigbott.jsid.ResponseUtil;
import bigbott.jsid.user.User;
import bigbott.jsid.user.UserManagerFactory;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import bigbott.jsid.security.SecurityManager;
import bigbott.jsid.user.IUserManager;

/**
 *
 * @author Owner
 */
public class FacebookLogin implements JsidCommand {

    @Override
    public void execute(User userFromRequest, Map<String, Cookie> cookieMap, HttpServletResponse response) {

        if (userFromRequest.email == null) {
            ResponseUtil.sendResponse(response, JsidResponseMessage.oops.name());
        }

        IUserManager userManager = UserManagerFactory.getInstance();
        User user = userManager.getUserByEmail(userFromRequest.email);
        if (user == null) {
            user = userManager.createUser(userFromRequest);
        }
        user.name = userFromRequest.name;
        user.picture = userFromRequest.picture;
        user.isFacebookUser = 1;
        user.isLoggedIn = 1;

        SecurityManager.setAuthCookies(response, user);
        userManager.updateUser(user);

        ResponseUtil.sendResponse(response, JsidResponseMessage.success.name());
    }

//    public UserActionResult facebookLogin(String email, String name, String picture) {
//        User user = users.get(email);
//        if (user == null) {
//            user = new User();
//            user.id = generateUserId();
//        }
//        user.name = name;
//        user.picture = picture;
//        user.isFacebookUser = 1;
//
//        users.put(email, user);
//        UserActionResult result = new UserActionResult();
//        result.user = user;
//        result.result = "success";
//
//        return result;
//    }
}
