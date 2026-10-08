
const ERROR_REQUIRED = "入力必須の項目です。";
const ERROR_TOO_LONG = "登録可能な文字数を超過しています。";
const ERROR_INVALID_CHARACTER = "使用できない文字が含まれています。";
const ERROR_EMAIL = "メールアドレスの形式が不正です。";
const ERROR_DATE = "日付の形式が不正です。";


function validateEmployeeNum(value) {
    if (value === "") {
        return ERROR_REQUIRED;
    } else if (value.length > 4) {
        return ERROR_TOO_LONG;
    } else if (!/^[0-9]+$/.test(value)) {
        return ERROR_INVALID_CHARACTER;
    }
    return "";
}

function validateKanjiName(lastName, firstName) {
    if (lastName === "" || firstName === "") {
        return ERROR_REQUIRED;
    } else if (lastName.length > 30 || firstName.length > 30) {
        return ERROR_TOO_LONG;
    }
    return "";
}

function validateRomanName(lastName, firstName) {
    if (lastName === "" || firstName === "") {
        return ERROR_REQUIRED;
    } else if (lastName.length > 30 || firstName.length > 30) {
        return ERROR_TOO_LONG;
    } else if (!/^[A-Za-z]+$/.test(lastName) || !/^[A-Za-z]+$/.test(firstName)) {
        return ERROR_INVALID_CHARACTER;
    }
    return "";
}

function validateEmail(email) {
    if (email !== "") {
        if (email.length > 100) {
            return ERROR_TOO_LONG;
        } else if (!/^[A-Za-z0-9][A-Za-z0-9._-]*[A-Za-z0-9]$/.test(email)) {
            return ERROR_EMAIL;
        }
    }
    return "";
}

function validateDate(date) {
    if (date !== "") {
        if (!/^[0-2]0[0-9][0-9]-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[01])$/.test(date)) {
            return ERROR_DATE;

        }
    }
    return "";
}

