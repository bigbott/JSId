/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.jsid.command;

import bigbott.jsid.JsidFilter;
import bigbott.jsid.JsidResponseMessage;
import bigbott.jsid.ResponseUtil;
import bigbott.jsid.security.AES;
import bigbott.jsid.user.User;
import bigbott.jsid.user.UserManagerFactory;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Owner
 */
public class VerifyEmail implements JsidCommand {

    @Override
    public void execute(User userFromRequest, Map<String, Cookie> cookieMap, HttpServletResponse response) {
        
        if (userFromRequest.token != null){
            try {
                userFromRequest.id = AES.decrypt(userFromRequest.token);
            } catch (Exception ex) {
                ResponseUtil.sendResponse(response, JsidResponseMessage.oops.name());
                Logger.getLogger(JsidFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        User user = UserManagerFactory.getInstance().getUser(userFromRequest.id);
        if (user == null) {
            ResponseUtil.sendResponse(response, JsidResponseMessage.user_not_found.name());
        }
        user.isEmailVerified = 1;
        UserManagerFactory.getInstance().updateUser(user);

        ResponseUtil.sendResponse(response, JsidResponseMessage.success.name());
    }

//    public UserActionResult verifyEmail(String userId) {
//        UserActionResult result = new UserActionResult();
//        User user = getUser(userId);
//        if (user == null) {
//            result.result = "failure";
//            result.error = "Unknow error.";
//        }
//        result.result = "success";
//        result.user = user;
//        return result;
//    }

}
