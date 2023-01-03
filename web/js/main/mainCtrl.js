'use strict';
/* global main*/
main.ctrl = {
    bind: function () {
        $('#calculate').click(function () {
            let isValid = true;
            let firstNumber = $('#firstNumber').val().trim();
            if (firstNumber.length === 0 || isNaN(firstNumber)){
                $('#firstNumber').css('border-color', 'red');
                isValid = false;
            } else {
                $('#firstNumber').css('border-color', '#5a5a5a');
            }
            let secondNumber = $('#secondNumber').val().trim();
            if (secondNumber.length === 0 || isNaN(secondNumber)){
                $('#secondNumber').css('border-color', 'red');
                isValid = false;
            } else {
                $('#secondNumber').css('border-color', '#5a5a5a');
            }
            if (!isValid){
                 $('#result').val('');
                return;
            }
            main.model.addNumbers(firstNumber, secondNumber);

        });
        $('#submit').click(function () {
            let firstWord = $('#firstWord').val();
            let secondWord = $('#secondWord').val();
            let thirdWord = $('#thirdWord').val();
            main.model.submitWords(firstWord, secondWord, thirdWord);

        });

        $('#ageCheckbox').click(function () {
            if ($(this).is(":checked")) {
                let filter = {type: 'age', value: 18};
                main.model.filters.push(filter);
            } else {
                for (let i = 0; i < main.model.filters.length; i++) {
                    if (main.model.filters[i].type === 'age') {
                        main.model.filters.splice(i, 1);
                        i--;
                    }
                }
            }
            main.model.getUsersByFilters();
        });

        $('#maleCheckbox').click(function () {
            if ($(this).is(":checked")) {
                $("#femaleCheckbox").prop("checked", false);
                removeFilter('gender');
                let filter = {type: 'gender', value: 1};
                main.model.filters.push(filter);
            } else {
                removeFilter('gender');
            }
            main.model.getUsersByFilters();
        });
        $('#femaleCheckbox').click(function () {
            if ($(this).is(":checked")) {
                $("#maleCheckbox").prop("checked", false);
                removeFilter('gender');
                let filter = {type: 'gender', value: 0};
                main.model.filters.push(filter);
            } else {
                 removeFilter('gender');
            }
            main.model.getUsersByFilters();
        });

        function removeFilter(type) {
            for (let i = 0; i < main.model.filters.length; i++) {
                if (main.model.filters[i].type === type) {
                    main.model.filters.splice(i, 1);
                    i--;
                }
            }
        }

    }
};


