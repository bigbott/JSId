/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.jsid;

import bigbott.jsid.email.EmailClientFactory;
import bigbott.jsid.security.AES;
import bigbott.jsid.security.SecurityManager;
import bigbott.jsid.user.User;
import bigbott.jsid.command.JsidCommandFactory;
import bigbott.jsid.security.TokenGeneratorFactory;
import bigbott.jsid.user.UserManagerFactory;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Owner
 */
public class JsidFilter implements Filter {

    FilterConfig config;
    public static Gson gson = new Gson();
//    static String authStrategy; // all || none
//    static String toExclude;
//    static String toInclude;
//    public static int accessTokenMaxAge;
//    public static int refreshTokenMaxAge;
//    public static String userManagerClass;
//    public static String mailClientClass;

    private static final Map<String, String> initParameterMap = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {
        config = filterConfig;		//initializing the FilterConfig object
        getInitParameters();
        UserManagerFactory.createInstance();
        EmailClientFactory.createInstance();
        TokenGeneratorFactory.createInstance();
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        String pathInfo = ((HttpServletRequest) request).getPathInfo();
        String route = "";
        if (pathInfo != null) {
            route = pathInfo.replace("/", "");
        }

        Map<String, Cookie> cookieMap = createCookieMap(request);

        if (isJsidCommand(route)) {
            User user = getUserFromRequest(request, response);
            if (user == null){
                return;
            }
            JsidCommandFactory.getCommand(route).execute(user, cookieMap, (HttpServletResponse) response);
            return;
        }

        if (SecurityManager.isLoggedIn(cookieMap)) {
            SecurityManager.renewCookies(cookieMap, response);
            chain.doFilter(request, response);  //process request
        } else {
            if (!isLoginRequired(route)) {
                chain.doFilter(request, response);
            } else {
                ResponseUtil.sendResponse(response, JsidResponseMessage.login_required.name());
            }
        }

    }

    private User getUserFromRequest(ServletRequest request, ServletResponse response) {
        String method = ((HttpServletRequest) request).getMethod();
        if ("GET".equalsIgnoreCase(method)) {
            return getUserFromGetRequest((HttpServletRequest) request, (HttpServletResponse) response);
        }
        return getUserFromPostRequest((HttpServletRequest) request, (HttpServletResponse) response);

    }

    private Map<String, Cookie> createCookieMap(ServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookieArray = ((HttpServletRequest) request).getCookies();
        if (cookieArray != null) {
            for (Cookie cookie : ((HttpServletRequest) request).getCookies()) { 
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    private boolean isLoginRequired(String route) {
        if ("all".equalsIgnoreCase(getInitParameter("authStrategy"))) {
            String[] excludedPages = getInitParameter("exclude").split(",");
            List<String> excludedList = Arrays.asList(excludedPages);
            if (excludedList.contains(route)) {
                return false;
            }
            return true;
        } else {
            String[] includedPages = getInitParameter("include").split(",");
            List<String> includedList = Arrays.asList(includedPages);
            if (includedList.contains(route)) {
                return true;
            }
            return false;
        }
    }

    private void getInitParameters() {

        for (String parameterName : Collections.list(config.getInitParameterNames())) {
            initParameterMap.put(parameterName, config.getInitParameter(parameterName));
        }
    }

    private User getUserFromGetRequest(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getParameter("token");
        try {
            String id = AES.decrypt(token);
            User user = new User();
            user.id = id;
            return user;
        } catch (Exception ex) {
            ResponseUtil.sendResponse(response, "Failure: token expired.");
            Logger.getLogger(JsidFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private User getUserFromPostRequest(HttpServletRequest request, HttpServletResponse response) {
        StringBuilder jsonBuilder = new StringBuilder();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = jsonBuilder.toString();
        User user = gson.fromJson(json, User.class);

        return user;
    }

    static enum JsidCommand {
        login, register, verifyEmail, resetPassword, changePassword, logout, facebookLogin, googleLogin
    }

    public static boolean isJsidCommand(String route) {
        for (JsidCommand c : JsidCommand.values()) {
            if (c.name().equals(route)) {
                return true;
            }
        }
        return false;
    }

    public static String getInitParameter(String name) {
        return initParameterMap.get(name);
    }
}
