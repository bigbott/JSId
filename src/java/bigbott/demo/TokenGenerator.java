/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.demo;

import bigbott.jsid.security.AES;
import bigbott.jsid.security.ITokenGenerator;
import bigbott.jsid.user.User;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Owner
 */
public final class TokenGenerator implements ITokenGenerator{
    
    //private TokenGenerator (){}
    
    @Override
    public  String generateAccessToken(User user) {
        String jwt = "{\"id\": \"" + user.id + "\" \"role\": \"" + user.role + "\"}";
        try {
            return AES.encrypt(jwt);
        } catch (Exception ex) {
            Logger.getLogger(TokenGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String generateRefreshToken(User user) {
        try {
            return AES.encrypt(user.id);
        } catch (Exception ex) {
            Logger.getLogger(TokenGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

