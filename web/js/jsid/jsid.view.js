'use strict';
/* global jsid*/

jsid.view = {
    showLoginDialog: function () {
        $('#modal').show();
        $('body').append(jsid.html.loginDialogHtml);
        jsid.ctrl.bindLoginButtonClick();
        jsid.ctrl.bindLoginWithFbButtonClick();
        jsid.ctrl.bindForgotPasswordLinkClick();
        jsid.ctrl.bindRegisterLinkClick();
        jsid.ctrl.bindInputBlur();
        jsid.googlelogin.renderButton();

    },
    showRegisterDialog: function () {
        $('body').append(jsid.html.registerDialogHtml);
        jsid.ctrl.bindRegisterButtonClick();

    },
    showSendResetLinkDialog: function () {
        $('body').append(jsid.html.sendResetLinkDialogHtml);
        jsid.ctrl.bindSubmitButtonClick();
    },
    showPasswordChangeDialog: function () {
        $('#modal').show();
        $('body').append(jsid.html.changePasswordDialogHtml);
        jsid.ctrl.passwordChangeSubmitButtonClick();
    },
    showOkDialog: function () {

    },
    showLogoutLink: function () {
        $('#logoutLink').show();
        $('#loginLink').hide();
        jsid.ctrl.bindLogoutLinkClick();
    },
    showLoginLink: function () {
        $('#logoutLink').hide();
        $('#loginLink').show();
    },
    showRegistrationSuccessDialog: function () {
        $('body').append(jsid.html.registrationSuccessDialogHtml);
        jsid.ctrl.bindLoginAfterRegisterButtonClick();
    },
    showSendResetLinkSuccessDialog: function () {
        $('body').append(jsid.html.resetLinkSendSuccessDialogHtml);
        jsid.ctrl.sendResetLinkSuccessOkButtonClick();
    },
    showSendResetLinkFailureDialog: function () {
        $('body').append(jsid.html.resetLinkSendFailureDialogHtml);
        jsid.ctrl.sendResetLinkFailureRegisterButtonClick();
    },
    showLoggedUserPicture: function (src) {
        $('#userImageDiv img').attr('src', src);
    },
    showDefaultUserPicture: function (src) {
        $('#userImageDiv img').attr('src', 'images/icon_user_whiteongrey.svg');
    },
    removeDialog: function () {
        $('.jsidDialog').remove();
        $('#modal').hide();
    }, 
    showVerifyEmailSuccessDialog: function() {
        $('#modal').show();
        $('body').append(jsid.html.verifyEmailSuccessDialogHtml);
        jsid.ctrl.verifyEmailSuccessLoginButtonClick();
    }, 
    showPasswordChangeSuccessDialog: function() {
        $('body').append(jsid.html.passwordChangeSuccessDialogHtml);
        jsid.ctrl.changePasswordSuccessLoginButtonClick();
    }
};


