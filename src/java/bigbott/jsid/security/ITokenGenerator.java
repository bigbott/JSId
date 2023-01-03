/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.jsid.security;

import bigbott.jsid.user.User;

/**
 *
 * @author Owner
 */
public interface ITokenGenerator {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);
}
