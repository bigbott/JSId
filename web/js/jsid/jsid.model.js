'use strict';
/* global jsid*/
jsid.model = {
    changePasswordToken: null,
    loggedInWithFb : false,
    loggedInWithGoogle: false,
    login: function (email, password){
        let url = '/jsid/demo/login';
        let data = {email : email, password: password};
        jsid.ajax.sendRequest(url, data, callback);
        function callback(response){
            if (response === 'success'){
                jsid.view.showLogoutLink();
                return;
            }
            if (response === 'oops'){
                alert ('Something went wrong.');
                return;
            }
            if (response === 'user_not_found'){
                alert ('User not found.');
                return;
            }
            if (response === 'wrong_password.'){
                alert ('Wrong password.');
            }
        }
    }, 
    register: function (email, password){
        let url = '/jsid/demo/register';
        let data = {email : email, password: password};
        jsid.ajax.sendRequest(url, data, callback);
        function callback(response){
            if ('success' === response){
                jsid.view.showRegistrationSuccessDialog();
                return;
            }
            if (response === 'oops'){
                alert ('Something went wrong.');
                return;
            }
            if (response === 'user_exists'){
                alert ('User already exists.');
            }
        }
    },
    facebookLogin:  function (email, name, picture){
        let url = '/jsid/demo/facebookLogin';
        let data = {email : email, name: name, picture: picture};
        jsid.ajax.sendRequest(url, data, callback);
        function callback(response){
            if ('success' === response){
                jsid.model.loggedInWithFb = true;
                jsid.view.showLogoutLink();
                jsid.view.showLoggedUserPicture(picture);
                jsid.view.removeDialog();
                return;
            }
            if (response === 'oops'){
                alert ('Something went wrong.');
            }
        }
    },
    googleLogin: function (email, name, picture){
        let url = '/jsid/demo/googleLogin';
        let data = {email : email, name: name, picture: picture};
        jsid.ajax.sendRequest(url, data, callback);
        function callback(response){
            if ('success' === response){
                jsid.model.loggedInWithGoogle = true;
                jsid.view.showLogoutLink();
                jsid.view.showLoggedUserPicture(picture);
                jsid.view.removeDialog();
                return;
            }
            if (response === 'oops'){
                alert ('Something went wrong.');
            }
        }
    }, 
    changePassword: function (oldPassword, newPassword) {
        let url = '/jsid/demo/changePassword';
        let data = {password: oldPassword, newPassword: newPassword, resetPasswordToken: jsid.model.changePasswordToken};
        jsid.ajax.sendRequest(url, data, callback);
        function callback(response){
            if ('success' === response){
                jsid.view.showPasswordChangeSuccessDialog();
                return;
            }
            if (response === 'oops'){
                alert ('Something went wrong.');
            }
        }
    }, 
    logout: function() {
        let url = '/jsid/demo/logout';
        let data = {};
        jsid.ajax.sendRequest(url, data, callback);
        function callback(response){
            if ('success' === response){
                jsid.view.showLoginLink();
                jsid.view.showDefaultUserPicture();
                if (jsid.model.loggedInWithGoogle){
                    jsid.googlelogin.logout();
                }
                if (jsid.model.loggedInWithFb){
                    jsid.fblogin.logout();
                }
            }
        }
    }, 
    resetPassword: function (email){
        let url = '/jsid/demo/resetPassword';
        let data = {email : email};
        jsid.ajax.sendRequest(url, data, callback);
        function callback(response){
            if ('success' === response){
                jsid.view.showSendResetLinkSuccessDialog();
                return;
            } 
            if (response === 'facebook_user'){
                alert ('Facebook was previously used to login.');
                return;
            }
            if (response === 'google_user'){
                alert ('Google was previously used to login.');
                return;
            }
            if (response === 'oops'){
                alert ('Something went wrong.');
            }
            
            
        }
    }, 
    verifyEmail: function (token){
        let url = '/jsid/demo/verifyEmail';
        let data = {token : token};
        jsid.ajax.sendRequest(url, data, callback);
        function callback(response){
            if ('success' === response){
                jsid.view.showVerifyEmailSuccessDialog();
            }
        }
    }
};


