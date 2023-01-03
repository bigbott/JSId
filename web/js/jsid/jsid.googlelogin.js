'use strict';
/* global jsid, google*/
jsid.googlelogin = {
    init: function () {
        let scriptEl = document.createElement('script');
        $('body').append(scriptEl);
        scriptEl.onload = function () {
            google.accounts.id.initialize({
                client_id: "490926528357-9hdffhuhvrs426tfl6upecar1i5tgi7m.apps.googleusercontent.com",
                callback: handleCredentialResponse
            });

            google.accounts.id.prompt(); // also display the One Tap dialog
        };
        scriptEl.src = 'https://accounts.google.com/gsi/client';
        function handleCredentialResponse(response) {
            //console.log("Encoded JWT ID token: " + response.credential);
            console.log(jwt_decode(response.credential));
            let credential = jwt_decode(response.credential);
            jsid.model.googleLogin(credential.email, credential.name, credential.picture);
        }
    },
    renderButton: function () {
        $('#googleButtonDiv').css('visibility', 'hidden');
        google.accounts.id.renderButton(
                document.getElementById("googleButtonDiv"),
                {theme: "outline", size: "large", width: 270}  // customization attributes
        );
        
        setTimeout (function(){
            $('#googleButtonDiv').css('visibility', 'visible');
        },1000); 
    },
    logout: function () {
        google.accounts.id.disableAutoSelect();
    },
    relogin: function () {
         google.accounts.id.prompt();
    }
};


