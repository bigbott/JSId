'use strict';

var jsid = {
    init: function(){
        $(jsid.css.style).appendTo('head');
        $('body').append(jsid.html.modal);
        $('#modal').hide();
        jsid.ctrl.bind();
        jsid.fblogin.init();
        jsid.googlelogin.init();
        
        
    }
};

