// Se define la función globalmente para que Google ReCAPTCHA pueda invocarla
function onLoadCallBack() {
    grecaptcha.render('divRecaptcha', {
        sitekey: '6LcYmIYpAAAAABssFnlPyL7CD1pRU0I0CxO_BVMK',
        callback: successCallback,
    });
}

function successCallback(token) {
    var loginButton = document.getElementById("loginButton");
    if (loginButton) {
        loginButton.disabled = false; // Habilita el botón tras pasar el captcha
    }
}

document.addEventListener("DOMContentLoaded", function () {
    var loginButton = document.getElementById("loginButton");
    if (loginButton) {
        loginButton.disabled = true; // Deshabilita el botón al cargar la página
    }
});
