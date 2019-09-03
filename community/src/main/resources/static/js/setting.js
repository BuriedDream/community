$(function(){
    bsCustomFileInput.init();
});

$(function(){
    $("form").submit(check_data);
    $("form-control").focus(clear_error);
});

function check_data() {
    var pwd1 = $("#new-password").val();
    var pwd2 = $("#confirm-password").val();
    if(pwd1 != pwd2) {
        $("#confirm-password").addClass("is-invalid");
        return false;
    }
    return true;
}

function clear_error() {
    $(this).removeClass("is-invalid");
}

function check_password() {
    var password = $("#new-password").val();
    if (password.length < 8){
        $("#new-password").addClass("is-invalid");
        return false;
    }
    return true;
}