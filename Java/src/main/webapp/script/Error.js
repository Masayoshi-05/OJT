const requiredErrorMessage = "入力必須の項目です。";
const tooLongErrorMessage = "登録可能な文字数を超過しています。";
const invalidCharacterErrorMessage = "使用できない文字が含まれています。";
const emailErrorMessage = "メールアドレスの形式が不正です。";
const dateErrorMessage = "日付の形式が不正です。";


form = document.getElementById("employeeForm");
form.addEventListener("submit", function(event) {
    let hasError = false;

    employeeNum = document.getElementById("employeeNum");
    employeeNumError = document.getElementById("employeeNumError");
    employeeNumError.textContent = "";
    if (employeeNum.value === "") {
        employeeNumError.textContent = requiredErrorMessage;
        hasError = true;
    } else if (employeeNum.value.length > 4) {
        employeeNumError.textContent = tooLongErrorMessage;
        hasError = true;
    } else if (!/^[0-9]+$/.test(employeeNum.value)) {
        employeeNumError.textContent = invalidCharacterErrorMessage;
        hasError = true;
    }

    kanjiLastName = document.getElementById("kanjiLastName");
    kanjiFirstName = document.getElementById("kanjiFirstName");
    kanjiNameError = document.getElementById("kanjiNameError");
    kanjiNameError.textContent = "";
    if (kanjiLastName.value === "" || kanjiFirstName.value === "") {
        kanjiNameError.textContent = requiredErrorMessage;
        hasError = true;
    } else if (kanjiLastName.value.length > 30 || kanjiFirstName.value.length > 30) {
        kanjiNameError.textContent = tooLongErrorMessage;
        hasError = true;
    }

    romanLastName = document.getElementById("romanLastName");
    romanFirstName = document.getElementById("romanFirstName");
    romanNameError = document.getElementById("romanNameError");
    romanNameError.textContent = "";
    if (romanLastName.value === "" || romanFirstName.value === "") {
        romanNameError.textContent = requiredErrorMessage;
        hasError = true;
    } else if (romanLastName.value.length > 30 || romanFirstName.value.length > 30) {
        romanNameError.textContent = tooLongErrorMessage;
        hasError = true;
    } else if (!/^[A-Za-z]+$/.test(romanLastName.value) || !/^[A-Za-z]+$/.test(romanFirstName.value)) {
        romanNameError.textContent = invalidCharacterErrorMessage;
        hasError = true;
    }

    email = document.getElementById("email");
    emailError = document.getElementById("emailError");
    emailError.textContent = "";
    if (email.value !== "") {
        if (email.value.length > 100) {
            emailError.textContent = tooLongErrorMessage;
            hasError = true;
        } else if (!/^[A-Za-z0-9][A-Za-z0-9._-]*[A-Za-z0-9]$/.test(email.value)) {
            emailError.textContent = emailErrorMessage;
            hasError = true;
        }
    }

    birthday = document.getElementById("birthday");
    birthdayError = document.getElementById("birthdayError");
    birthdayError.textContent = null;
    if (birthday.value !== "") {
        if (!/^[0-2]0[0-9][0-9]-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[01])$/.test(birthday.value)) {
            birthdayError.textContent = dateErrorMessage;
            hasError = true;
        }
    }

    joinDate = document.getElementById("joinDate");
    joinDateError = document.getElementById("joinDateError");
    joinDateError.textContent = null;
    if (joinDate.value !== "") {
        if (!/^[0-2]0[0-9][0-9]-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[01])$/.test(joinDate.value)) {
            joinDateError.textContent = dateErrorMessage;
            hasError = true;
        }
    }

    if (hasError) {
        event.preventDefault();
    }
});