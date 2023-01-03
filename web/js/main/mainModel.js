
'use strict';
/* global main*/

/**
 * Code below intentionally ignores DRY principle in favor of better visibility. 
 * */

main.model = {
    filters: [],
    addNumbers: function (firstNumber, secondNumber) {
        let url = '/jsid/demo/AddNumbers';
        let data = {dataType: "Integer", data: [firstNumber, secondNumber]};
        $.ajax({
            url: url,
            type: "POST",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                main.view.showAddNumbersResult(response.data[0]);
            }
        });
    },
    submitWords: function (firstWord, secondWord, thirdWord) {
        let url = '/jsid/demo/EchoWords';
        let data = {dataType: "String", data: [firstWord, secondWord, thirdWord]};
        $.ajax({
            url: url,
            type: "POST",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "text",
            success: function (response) {
                if (response === 'login_required'){
                    $().jsid('login');
                    return;
                }
                let responseObject = JSON.parse(response);
                main.view.showSentence(responseObject.data);
            }
        });
    },
    getUsersByFilters: function () {
        let url = '/jsid/demo/GetUsersByFilters';
        let data = {dataType: "UserFilter", data: main.model.filters};
        $.ajax({
            url: url,
            type: "POST",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (response) {
                main.view.showUsers(response.data);
            }
        });
    }
};



