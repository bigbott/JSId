/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.jsid.email;

import bigbott.jsid.user.User;

/**
 *
 * @author Owner
 */
public interface IMailClient {
    void sendEmailVerificationEmail(User user);
    void sendResetPasswordEmail(User user);
}
