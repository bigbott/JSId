/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.jsid.command;

import bigbott.jsid.JsidResponseMessage;
import bigbott.jsid.ResponseUtil;
import bigbott.jsid.email.EmailClientFactory;
import bigbott.jsid.user.IUserManager;
import bigbott.jsid.user.User;
import bigbott.jsid.user.UserManagerFactory;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Owner
 */
public class Register implements JsidCommand {

    @Override
    public void execute(User userFromRequest, Map<String, Cookie> cookieMap, HttpServletResponse response) {
        
        IUserManager userManager = UserManagerFactory.getInstance();
        User user = userManager.getUserByEmail(userFromRequest.email);
        if (user != null){
            ResponseUtil.sendResponse(response, JsidResponseMessage.user_exists.name());
            return;
        }
        
        user = userManager.createUser(userFromRequest);
        
        EmailClientFactory.getMailClient().sendEmailVerificationEmail(user);

        ResponseUtil.sendResponse(response, JsidResponseMessage.success.name()); 
    }

//    public UserActionResult register(String email, String password) {
//        UserActionResult result = new UserActionResult();
//        if (users.containsKey(email)) {
//            result.user = null;
//            result.result = "failure";
//            result.error = "User already exists.";
//            return result;
//        }
//        User user = new User();
//        user.email = email;
//        user.password = password;
//        user.id = generateUserId();
//        users.put(email, user);
//
//        result.user = user;
//        result.result = "success";
//
//        return result;
//    }

}
