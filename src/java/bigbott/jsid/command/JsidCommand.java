/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.jsid.command;

import bigbott.jsid.user.User;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Owner
 */
public interface JsidCommand {
    void execute(User user, Map<String, Cookie> cookieMap, HttpServletResponse response);
}
