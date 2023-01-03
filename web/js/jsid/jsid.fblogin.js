'use strict';
/* global jsid*/
jsid.fblogin = {
    init: function () {
        window.fbAsyncInit = function () {
            FB.init({
                appId: '1065199793636333',
                cookie: true,
                xfbml: true,
                version: 'v15.0'
            });

            FB.AppEvents.logPageView();

            FB.getLoginStatus(function (response) {
                if (response.status === 'connected') {
                    jsid.fblogin.getFbUserData();
                }
            });

        };

        (function (d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) {
                return;
            }
            js = d.createElement(s);
            js.id = id;
            js.src = "https://connect.facebook.net/en_US/sdk.js";
            fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));
    },
    login: function () {
        FB.login(function (response) {
            if (response.authResponse) {
                // Get and display the user profile data
                jsid.fblogin.getFbUserData();
            } else {
                document.getElementById('status').innerHTML = 'User cancelled login or did not fully authorize.';
            }
        }, {scope: 'email'});
    },
    getFbUserData: function () {
        FB.api('/me', {locale: 'en_US', fields: 'id,first_name,last_name,email,link,gender,locale,picture'},
                function (response) {
//                    document.getElementById('fbLink').setAttribute("onclick", "fbLogout()");
//                    document.getElementById('fbLink').innerHTML = 'Logout from Facebook';
//                    document.getElementById('status').innerHTML = '<p>Thanks for logging in, ' + response.first_name + '!</p>';
//                    document.getElementById('userData').innerHTML = '<h2>Facebook Profile Details</h2><p><img src="' + response.picture.data.url + '"/></p><p><b>FB ID:</b> ' + response.id + '</p><p><b>Name:</b> ' + response.first_name + ' ' + response.last_name + '</p><p><b>Email:</b> ' + response.email + '</p><p><b>Gender:</b> ' + response.gender + '</p><p><b>FB Profile:</b> <a target="_blank" href="' + response.link + '">click to view profile</a></p>';

                    jsid.model.facebookLogin(response.email, response.first_name + ' ' + response.last_name, response.picture.data.url);

                });
    },
    logout: function () {
        FB.getLoginStatus(function (response) {
            FB.logout(function (response) {
                
            });
        });
    }
};


