/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.demo;

import bigbott.jsid.email.IMailClient;
import bigbott.jsid.security.TokenGeneratorFactory;
import bigbott.jsid.user.User;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Owner
 */
public class GoogleMailClient implements IMailClient {

//    private GoogleMailClient() {
//    }

    String from = "";  // email address used to send emails
    String password = ""; //Application specific password  

    boolean isTest = true;   // change to false for production
    String host = isTest ? "http://localhost:8080/" : "https://phoneparator.com/";

    @Override
    public void sendEmailVerificationEmail(User user) {
        String verificationToken = TokenGeneratorFactory.getTokenGenerator().generateRefreshToken(user);
        String subject = "JSId: please verify your email";
        String content = "<p>You just have registered new account on JSId demo. <br>"
                + "Please verify your email by clicking link below</p>"
                + "<p><a href=\"" + host + "jsid/index.html?action=verifyEmail&token=" + verificationToken + "\">Verify email</p>";

        try {
            sendEmail(user.email, subject, content);
        } catch (Exception ex) {
            ex.printStackTrace();
            //Logger.getLogger(EmailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sendResetPasswordEmail(User user) {
        String resetPasswordToken = TokenGeneratorFactory.getTokenGenerator().generateRefreshToken(user);
        String subject = "JSId: password reset requested";
        String content = "<p>Someone requested password reset on JSId demo. <br>"
                + "In case it was you, please click link below:</p>"
                + "<p><a href=\"" + host + "jsid/index.html?action=changePassword&token=" + resetPasswordToken + "\">Reset password</p>";
        try {
            sendEmail(user.email, subject, content);
        } catch (Exception ex) {
            ex.printStackTrace();
            //Logger.getLogger(EmailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void sendEmail(String to, String subject, String content) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session   
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        //compose message    
        MimeMessage message = new MimeMessage(session);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setContent(content, "text/html");
        message.setFrom(from);
        //send message  
        Transport.send(message);
    }

    public static void main(String[] args) throws Exception {
        new GoogleMailClient().sendEmail("u.novicow@gmail.com", "googleEmailClient test", "hello");
    }
}
