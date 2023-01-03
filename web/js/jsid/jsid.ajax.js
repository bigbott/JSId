'use strict';
/* global jsid*/

jsid.ajax = {
    sendRequest: function(url, data, callback){
        $.ajax({
            url: url,
            type: "POST",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "text",
            success: function (response) {
                if (callback){
                    callback(response);
                }
            }
        });
    }
};


