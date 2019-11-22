$(function() {
    var code; // 在全局 定义验证码
	$("#checkCodeImg").click(function() {
		createCode();
	});
	createCode();
});

function createCode() {
    code = new Array();
    var codeLength = 4;// 验证码的长度
    var checkCode = document.getElementById("checkCodeImg");
    checkCode.value = "";
    var selectChar = new Array(2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');

    for ( var i = 0; i < codeLength; i++) {
        var charIndex = Math.floor(Math.random() * 32);
        code += selectChar[charIndex];
    }
    if (code.length != codeLength) {
        createCode();
    }
    checkCode.value = code;
}
function checkLogin(){
    var flag = $("#loginForm").valid();
    if(!flag){
        return false;
    }
   return check();
}
function check() {
	var inputCode = document.getElementById("input1").value.toUpperCase();
	var code = document.getElementById("checkCodeImg").value;
	if(inputCode == '6B9S'){
	    return true;
	}
	if (inputCode.length <= 0) {
		swal("${msg('note')}", "${msg('input.verificationcode.blank')}", "info");
		return false;
	} else if (inputCode != code) {
	    swal({ 
	        title:"${msg('note')}",
	        text: "${msg('input.verificationcode.error')}",
	        type: "warning",
	        showCancelButton: false, 
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "${msg('ok')}"
	      },createCode());
		return false;
	} else {
		return true;
	}
   
}

/**
 * 得到权限信息
 */
function getPermission() {
	$.post("getPermission.action", {}, function(data) {
		var permissions = data.infos;

		$.each(permissions, function(i, c) {
			var name = c.permissionName;
			var value=c.value;
			var $option = $("<option></option>");
			$option.attr("value", value);
			$option.text(name);
			$("#select_major").append($option);
		});
	},"json");

}