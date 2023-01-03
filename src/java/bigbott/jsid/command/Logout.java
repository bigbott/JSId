/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.jsid.command;

import bigbott.jsid.JsidResponseMessage;
import bigbott.jsid.ResponseUtil;
import bigbott.jsid.security.AES;
import bigbott.jsid.user.User;
import bigbott.jsid.user.UserManagerFactory;
import bigbott.jsid.security.SecurityManager;
import bigbott.jsid.user.IUserManager;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import me.jrpc.example.users.UserManager;

/**
 *
 * @author Owner
 */
public class Logout implements JsidCommand {

    @Override
    public void execute(User userFromRequest, Map<String, Cookie> cookieMap, HttpServletResponse response) {

        if (cookieMap.get("jsid-rt") == null){
            ResponseUtil.sendResponse(response, JsidResponseMessage.success.name());
            return;
        }
        String refreshToken = cookieMap.get("jsid-rt").getValue();
        String userId = null;
        try {
            userId = AES.decrypt(refreshToken);
        } catch (Exception ex) {
            ResponseUtil.sendResponse(response, JsidResponseMessage.oops.name());
            return;
        }

        IUserManager userManager = UserManagerFactory.getInstance();
        User user = userManager.getUser(userId);
        if (user == null) {
            ResponseUtil.sendResponse(response, JsidResponseMessage.oops.name());
            return;
        }
        //user.refreshToken = null;
        user.isLoggedIn = 0;
        userManager.updateUser(user);

        SecurityManager.removeAuthCookies(response);

        ResponseUtil.sendResponse(response, JsidResponseMessage.success.name());
    }

}
