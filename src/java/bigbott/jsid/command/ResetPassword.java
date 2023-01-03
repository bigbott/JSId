/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.jsid.command;

import bigbott.jsid.JsidResponseMessage;
import bigbott.jsid.ResponseUtil;
import bigbott.jsid.email.EmailClientFactory;
import bigbott.jsid.user.User;
import bigbott.jsid.user.UserManagerFactory;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Owner
 */
public class ResetPassword implements JsidCommand {

    @Override
    public void execute(User userFromRequest, Map<String, Cookie> cookieMap, HttpServletResponse response) {
        User user = UserManagerFactory.getInstance().getUserByEmail(userFromRequest.email);
        if (user == null) {
            ResponseUtil.sendResponse(response, JsidResponseMessage.oops.name());
            return;
        }
        if (user.isFacebookUser == 1){
            ResponseUtil.sendResponse(response, JsidResponseMessage.facebook_user.name());
            return;
        }
        if (user.isGoogleUser == 1){
            ResponseUtil.sendResponse(response, JsidResponseMessage.google_user.name());
            return;
        }
        EmailClientFactory.getMailClient().sendResetPasswordEmail(user);
        ResponseUtil.sendResponse(response, JsidResponseMessage.success.name());
    }

}
