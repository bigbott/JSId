/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.demo;

import bigbott.jsid.user.IUserManager;
import bigbott.jsid.user.User;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Owner
 */
public final class InMemoryUserManager implements IUserManager {
    
    //private InMemoryUserManager () {}

    Map<String, User> users = new ConcurrentHashMap<>();

    private String generateUserId() {
        return System.currentTimeMillis() + "";
    }

    @Override
    public User updateUser(User user) {

        users.put(user.email, user);
        return user;
    }

    @Override
    public User getUser(String userId) {
        for (User user : users.values()) {
            if (userId.equalsIgnoreCase(user.id)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        if (users.containsKey(email)) {
            return users.get(email);
        }
        return null;
    }

    @Override
    public User createUser(User user) {
        user.id = generateUserId();
        users.put(user.email, user);
        return user;
    }    

}
