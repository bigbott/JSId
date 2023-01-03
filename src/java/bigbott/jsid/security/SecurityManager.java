/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.jsid.security;

import bigbott.jsid.JsidFilter;
import bigbott.jsid.user.User;
import bigbott.jsid.user.UserManagerFactory;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Owner
 */
public class SecurityManager {

    public static void setAuthCookies(ServletResponse response, User user) {
        ITokenGenerator tokenGenerator = TokenGeneratorFactory.getTokenGenerator();
        String accessToken = tokenGenerator.generateAccessToken(user);
        String refreshToken = tokenGenerator.generateRefreshToken(user);

        setCookie(response, "jsid-at", accessToken, Integer.parseInt(JsidFilter.getInitParameter("accessTokenMaxAge")));
        setCookie(response, "jsid-rt", refreshToken, Integer.parseInt(JsidFilter.getInitParameter("refreshTokenMaxAge")));
    }

    private static void setCookie(ServletResponse response, String cookieName, String cookieValue, int maxAge) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setSecure(false);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        ((HttpServletResponse) response).addCookie(cookie);
    }

    public static void removeAuthCookies(ServletResponse response) {
        setCookie(response, "jsid-at", "", 0);
        setCookie(response, "jsid-rt", "", 0);
    }

    static boolean verifyAccessToken(String accessToken) {
        try {
            User user = JsidFilter.gson.fromJson(AES.decrypt(accessToken), User.class);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(SecurityManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean isLoggedIn(Map<String, Cookie> cookieMap) {

        Cookie jsidAt = cookieMap.get("jsid-at");
        if (jsidAt != null && jsidAt.getValue() != null) {
            try {
                String accessToken = AES.decrypt(jsidAt.getValue());
                return true;
            } catch (Exception ex) {
                Logger.getLogger(SecurityManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        Cookie jsidRt = cookieMap.get("jsid-rt");
        if (jsidRt == null || jsidRt.getValue() == null) {
            return false;
        }
        String refreshToken = jsidRt.getValue();
        try {
            String userId = AES.decrypt(refreshToken);
            User user = UserManagerFactory.getInstance().getUser(userId);
            if (user != null && user.isLoggedIn == 1) {
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(SecurityManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void renewCookies(Map<String, Cookie> cookieMap, ServletResponse response) {
        Cookie jsidAt = cookieMap.get("jsid-at");
        Cookie jsidRt = cookieMap.get("jsid-rt");
        if (jsidAt != null && jsidAt.getValue() != null) {
            setCookie(response, "jsid-at", jsidAt.getValue(), Integer.parseInt(JsidFilter.getInitParameter("accessTokenMaxAge")));
        } else {
            try {
                String userId = AES.decrypt(jsidRt.getValue());
                User user = UserManagerFactory.getInstance().getUser(userId);
                String accessToken = TokenGeneratorFactory.getTokenGenerator().generateAccessToken(user);
                setCookie(response, "jsid-at", accessToken, Integer.parseInt(JsidFilter.getInitParameter("accessTokenMaxAge")));
            } catch (Exception ex) {
                Logger.getLogger(SecurityManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        setCookie(response, "jsid-rt", jsidRt.getValue(), Integer.parseInt(JsidFilter.getInitParameter("refreshTokenMaxAge")));

    }
}
