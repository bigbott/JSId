'use strict';
/* global jsid*/

jsid.html = {
    loginDialogHtml:        `<div id="loginDialog" class="jsidDialog">
                                <h3>Login</h3>
                                <input type="text" id="email" placeholder="email"/><br>
                                <input type="password" id="password" placeholder="password"/><br>
                                <a href="#" id="forgotPassword">Forgot password?</a><br>
                                <button type="button" id="loginButton">Login</button><br>
                                <a href="#" id="registerLink">Not a user yet? - Register</a><br>
                                <h4>or</h4>
                                <button type="button" id="loginWithFb">Login with Facebook</button>
                                <div id="googleButtonDiv"></div> 
                             </div>`, 
    modal:                  `<div id="modal"></div>`, 
    registerDialogHtml:     `<div id="registerDialog" class="jsidDialog">
                                <h3>Register</h3>
                                <input type="text" id="email" placeholder="email"/><br>
                                <input type="password" id="password" placeholder="password"/><br>
                                <input type="password" id="passwordRepeat" placeholder="repeat password"/><br>
                                <button type="button" id="registerButton">Register</button><br>                                
                             </div>`,
    sendResetLinkDialogHtml: `<div id="sendResetLinkDialog" class="jsidDialog">
                                <h3>Send Reset link</h3>
                                <input type="text" id="email" placeholder="email"/><br>
                                <button type="button" id="submitButton">Submit</button><br>                                
                             </div>`,
    changePasswordDialogHtml: `<div id="changePasswordDialog" class="jsidDialog">
                                <h3>Change Password</h3>
                                <input type="password" id="currentPassword" placeholder="current password"/><br>
                                <input type="password" id="newPassword" placeholder="new password"/><br>
                                <input type="password" id="newPasswordRepeat" placeholder="repeat new password"/><br>
                                <button type="button" id="submitButton">Submit</button><br>                                
                             </div>`,
    registrationSuccessDialogHtml: `<div id="registrationSuccessDialog" class="jsidDialog">
                                <h3>Registration success</h3>   
                                <p>We have sent email verification letter to the email address provided. </p>
                                <p>Please check email folders including Spam folder and verify. </p>
                                <button type="button" id="loginAfterRegisterButton">Login</button><br>                                
                             </div>`,
    resetLinkSendSuccessDialogHtml: `<div id="resetLinkSendSuccessDialog" class="jsidDialog">
                                <h3>Reset link sent</h3>   
                                <p>Please check your email.</p>
                                <button type="button" id="okButton">Ok</button><br>                                
                             </div>`, 
    verifyEmailSuccessDialogHtml: `<div id="verifyEmailSuccessDialog" class="jsidDialog">
                                <h3>Email verified</h3>   
                                <button type="button" id="loginButton">Login</button><br>                                
                             </div>`, 
    passwordChangeSuccessDialogHtml: `<div id="passwordChangeSuccessDialog" class="jsidDialog">
                                <h3>Password changed</h3>   
                                <button type="button" id="loginButton">Login</button><br>                               
                             </div>`,
    resetLinkSendFailureDialogHtml: `<div id="resetLinkSendFailureDialog" class="jsidDialog">
                                <h3>User was not found.</h3>   
                                <p>Try to register first.</p>
                                <button type="button" id="registerButton">Register</button><br>                                
                             </div>`
};



