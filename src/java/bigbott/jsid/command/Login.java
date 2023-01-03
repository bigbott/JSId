/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.jsid.command;

import bigbott.jsid.JsidResponseMessage;
import bigbott.jsid.ResponseUtil;
import bigbott.jsid.user.IUserManager;
import bigbott.jsid.user.User;
import bigbott.jsid.user.UserManagerFactory;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import bigbott.jsid.security.SecurityManager;

/**
 *
 * @author Owner
 */
public class Login implements JsidCommand {

    @Override
    public void execute(User userFromRequest, Map<String, Cookie> cookieMap, HttpServletResponse response) {
        IUserManager userManager = UserManagerFactory.getInstance();
        User user = userManager.getUserByEmail(userFromRequest.email);
        if (user == null) {
            // trade off: make user life easier and send user_not_found message or make hacker life harder and send generic oops
            ResponseUtil.sendResponse(response, JsidResponseMessage.user_not_found.name());
            return;
        }
        if (!user.password.equals(userFromRequest.password)){
            // trade off: make user life easier and send wrong_password message or make hacker life harder and send generic oops
            ResponseUtil.sendResponse(response, JsidResponseMessage.wrong_password.name());
            return;
        }
        
        SecurityManager.setAuthCookies(response, user);
        user.isLoggedIn = 1;
        userManager.updateUser(user);

        ResponseUtil.sendResponse(response, JsidResponseMessage.success.name());
    }

//    public UserActionResult login(String email, String password) {
//        User user = users.get(email);
//        UserActionResult result = new UserActionResult();
//        result.user = user;
//        result.result = "success";
//        if (user == null) {
//            result.result = "failure";
//            result.error = "User does not exist.";
//            return result;
//        }
//        if (!user.password.equalsIgnoreCase(password)) {
//            result.result = "failure";
//            result.error = "Wrong password.";
//            return result;
//        }
//
//        return result;
//    }

}
