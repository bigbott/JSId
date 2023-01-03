
(function ($) {

    $.fn.jsid = function (action) {

        if (action === 'login') {
            if (jsid.model.loggedInWithFb){
                jsid.fblogin.login();
                return this;
            }
            if (jsid.model.loggedInWithGoogle){
                jsid.googlelogin.relogin();
                return this;
            }
            jsid.view.showLoginDialog();
            return this;
        }
        if (action === 'changePassword') {
            jsid.init();
            let token = jsid.util.getQueryVariable('token');
            jsid.model.changePasswordToken = token;
            jsid.view.showPasswordChangeDialog();
            return this;
        }
        if (action === 'verifyEmail') {
            jsid.init();
            let token = jsid.util.getQueryVariable('token');
            jsid.model.verifyEmail(token);
            return this;
        }
        jsid.init();
        return this;
    };

}(jQuery));












