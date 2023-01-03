'use strict';
/* global jsid, google, FB*/
jsid.ctrl = {
    bind: function () {
        $('#modal').click(function () {
            jsid.view.removeDialog();
        });
        $('#loginLink').on('click', function () {
            $().jsid('login');
        });
    },
    bindLoginButtonClick: function () {
        $('#loginButton').click(function () {
            let email = $('#email').val();
            if (!email) {
                $('#email').css('border-color', 'red');
                return;
            } else {
                $('#email').css('border-color', '#115C5C');
            }

            let password = $('#password').val();
            if (!password) {
                $('#password').css('border-color', 'red');
                return;
            } else {
                $('#password').css('border-color', '#115C5C');
            }
            jsid.model.login(email, password);
            $('#loginDialog').remove();
            $('#modal').hide();

        });
    },
    bindLoginWithFbButtonClick: function () {
        $('#loginWithFb').click(function () {
            jsid.fblogin.login();
            $('#loginDialog').remove();
            $('#modal').hide();
        });
    },
    bindForgotPasswordLinkClick: function () {
        $('#forgotPassword').click(function () {
            $('#loginDialog').remove();
            jsid.view.showSendResetLinkDialog();
        });
    },
    bindRegisterLinkClick: function () {
        $('#registerLink').click(function () {
            $('#loginDialog').remove();
            jsid.view.showRegisterDialog();
        });
    },
    bindInputBlur: function () {
        $('.jsidDialog input').each(function () {
            $(this).blur(function () {
                if ($(this).val()) {
                    $(this).css('border-color', '#115C5C');
                }
            });
        });
    },
    bindLoginAfterRegisterButtonClick: function () {
        $('#loginAfterRegisterButton').click(function () {
            $('#registrationSuccessDialog').remove();
            jsid.view.showLoginDialog();
        });
    },
    bindRegisterButtonClick: function () {
        $('#registerButton').click(function () {
            let email = $('#email').val();
            if (!email) {
                $('#email').css('border-color', 'red');
                return;
            } else {
                $('#email').css('border-color', '#115C5C');
            }

            let password = $('#password').val();
            if (!password) {
                $('#password').css('border-color', 'red');
                return;
            } else {
                $('#password').css('border-color', '#115C5C');
            }
            let passwordRepeat = $('#passwordRepeat').val();
            if (!passwordRepeat) {
                $('#passwordRepeat').css('border-color', 'red');
                return;
            } else {
                $('#passwordRepeat').css('border-color', '#115C5C');
            }
            if (password !== passwordRepeat) {
                $('#password').css('border-color', 'red');
                $('#passwordRepeat').css('border-color', 'red');
                return;
            }
            jsid.model.register(email, password);
            $('#registerDialog').remove();
        });
    },
    bindLogoutLinkClick: function () {
        $('#logoutLink').click(function () {
            jsid.model.logout();
            //google.accounts.id.disableAutoSelect();
            //FB.logout();
        });
    },
    bindSubmitButtonClick: function () {
        $('#submitButton').click(function () {
            let email = $('#email').val();
            if (!email) {
                $('#email').css('border-color', 'red');
                return;
            }
            jsid.model.resetPassword(email);
            $('#sendResetLinkDialog').remove();
        });
    },
    sendResetLinkSuccessOkButtonClick: function () {
        $('#okButton').click(function () {
            $('#resetLinkSendSuccessDialog').remove();
            $('#modal').hide();
        });
    },
    verifyEmailSuccessLoginButtonClick: function () {
        $('#loginButton').click(function () {
            $('#verifyEmailSuccessDialog').remove();
            jsid.view.showLoginDialog();
        });
    },
    changePasswordSuccessLoginButtonClick: function () {
        $('#loginButton').click(function () {
            $('#passwordChangeSuccessDialog').remove();
            jsid.view.showLoginDialog();
        });
    }, 
    sendResetLinkFailureRegisterButtonClick: function () {
        $('#registerButton').click(function () {
            $('#resetLinkSendFailureDialog').remove();
            jsid.view.showRegisterDialog();
        });
    }, 
    passwordChangeSubmitButtonClick: function() {
        $('#submitButton').click(function () {
            let currentPassword = $('#currentPassword').val();
            if (!currentPassword) {
                $('#currentPassword').css('border-color', 'red');
                return;
            } else {
                $('#currentPassword').css('border-color', '#115C5C');
            }
            let newPassword = $('#newPassword').val();
            if (!newPassword) {
                $('#newPassword').css('border-color', 'red');
                return;
            } else {
                $('#newPassword').css('border-color', '#115C5C');
            }
            let newPasswordRepeat = $('#newPasswordRepeat').val();
            if (!newPasswordRepeat) {
                $('#newPasswordRepeat').css('border-color', 'red');
                return;
            } else {
                $('#newPasswordRepeat').css('border-color', '#115C5C');
            }
            if (newPassword !== newPasswordRepeat) {
                $('#newPassword').css('border-color', 'red');
                $('#newPasswordRepeat').css('border-color', 'red');
                return;
            }
            $('#changePasswordDialog').remove();
            $('#modal').hide();
            jsid.model.changePassword(currentPassword, newPassword);
        });
    }
};



