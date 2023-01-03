/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.jsid.command;

import bigbott.jsid.JsidResponseMessage;
import bigbott.jsid.ResponseUtil;
import bigbott.jsid.security.AES;
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
public class ChangePassword implements JsidCommand {

    @Override
    public void execute(User userFromRequest, Map<String, Cookie> cookieMap, HttpServletResponse response) {        
        IUserManager userManager = UserManagerFactory.getInstance();
        String userId = null;
        try {
            userId = AES.decrypt(userFromRequest.resetPasswordToken);
        } catch (Exception ex) {
            ResponseUtil.sendResponse(response, JsidResponseMessage.oops.name());
            return;
        }
        User user = userManager.getUser(userId);
        if (user == null){
            ResponseUtil.sendResponse(response, JsidResponseMessage.oops.name());
            return;
        }
        user.password = userFromRequest.newPassword;
        userManager.updateUser(user);
        
        ResponseUtil.sendResponse(response, JsidResponseMessage.success.name());
    }
    
//    public UserActionResult changePassword(User userFromRequest) {
//        UserActionResult result = new UserActionResult();
//        String userId = null;
//        try {
//            userId = AES.decrypt(userFromRequest.resetPasswordToken);
//        } catch (Exception ex) {
//            result.result = "failure";
//            Logger.getLogger(InMemoryUserManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        User user = getUser(userId);
//        
//        if (user != null && user.password.equals(userFromRequest.password)) {
//            user.password = userFromRequest.newPassword;
//            result.user = user;
//            result.result = "success";
//            return result;
//        }
//        result.error = "Something went wrong.";
//        result.result = "failure";
//        return result;
//    }

}
