var length = 0;
$(function(){
	var ie_window_width = document.body.clientWidth;
	var ie_window_height = document.body.clientHeight;
	var window_width = $(window).width();
	var window_height = $(window).height();
	var middle_height = $(".middle").height();
	var navWidth = $(".nav_div").width();
	var navHeight = $(".nav_div").height();
	var navUlWidth = $(".nav_ul").width();
	var navUlHeight = $(".nav_ul").height();
	var treeWidth = navWidth - navUlWidth-10;
	var treeHeight = navHeight - 30;
//	$(".nav_div").height(middle_height-30);
//	$(".nav_tree").height(treeHeight);
	$(".middle").height(ie_window_height);
	$("#device_ul").width(ie_window_width-300);
	
	
	$("#nav_ul img").each(function(index){
		$(this).click(function(){
			$(this).attr("src","${base}/images/"+index+"_p.png");
			for(var i=0;i<4;i++){
				if(i!=index){
					$("#nav"+i).attr("src","${base}/images/"+i+"_n.png");
				}
			}
		});
	});
	
});

function showWin(title){
	$('#win').window({
	    width:635,  
	    height:380,  
	    title:title,
	    top:50,
	    minimizable:false,
	    maximizable:false,
	    modal:true  
	});
}
function showDataWin(title,height){
	$('#win').window({
	    width:960,  
	    height:height,  
	    title:title,
	    top:50,
	    minimizable:false,
	    maximizable:false,
	    modal:true  
	});
}
function showWinActive(title,width,height){
	$('#win').window({
	    width:width,  
	    height:height,  
	    title:title,
	    top:50,
	    minimizable:false,
	    maximizable:false,
	    modal:true  
	});
}
function closeWin(id){
	$(id).window('close');
}
function showAddWin(title){
	$('#addWin').window({
	    width:620,  
	    height:400,  
	    title:title,
	    minimizable:false,
	    maximizable:false,
	    modal:true  
	});
}
function showDevWin(title){
	$('#deviceWin').window({
		width:640,  
		height:400,  
		title:title,
		minimizable:false,
		maximizable:false,
		modal:true  
	});
}

function session(flag){
	if(flag==0){
		parent.location.href="${base}/pages/error.jsp";
	}
}

/**
 * 是否是数字
 **/
function isDigit(strValue){
  if(typeof strValue == 'undefined' || strValue == null){
    return false;
  }
  return /^\d+$/g.test(strValue);
}

//add  by  lihaiguang 校验此处录入项是否为数字20140619
function checkPhone(field){
    var phoneNum=field.value;
    if(phoneNum!=""&&!isDigit(phoneNum)){
        $.messager.alert("${msg('note')}","${msg('input.phone.invalid')}",'info',function(){
          field.select();
        });
        return false;
    }
    return true;
}

//add  by  lihaiguang 校验输入的数值是否为有效正整数20140623
function checkInteger(field){
    var Num=field.value;
    if(Num!=""&&!isDigit(Num)){
        $.messager.alert("${msg('note')}","${msg('input.param.isNum')}");
        field.value="";
        field.focus();
        return false;
    }else if (Num!=""&&Num<=0){
        $.messager.alert("${msg('note')}","${msg('input.param.isNum')}");
        field.value="";
        field.focus();
        return false;
    }else{
        return true;
    }
}

function getDateString(/* long */datetime) {
    var date = new Date();
    date.setTime(datetime);
    var timeInfo = date.getFullYear() + "-" + (date.getMonth() + 1) + "-"
            + date.getDate() + " " + date.getHours() + ":" + date.getMinutes()
            + ":" + date.getSeconds();
    return timeInfo;
}

Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
/**
 * 
 * @param field
 * @param length
 * @returns {Boolean}
 */
function checkDigitalPWD(field,length){
  if(isDigit(field.value) == false){
     $.messager.alert("${msg('note')}","${msg('input.digital.password')}");
     return false;
  }
 
  var count = field.value.length;
  if(count != length){
    $.messager.alert("${msg('note')}","${msg('input.num','"+length+"')}");
    return false;
  }else{
    return true; 
  }
}

function validatemobile(mobile){
    if(mobile != "" && !isDigit(mobile)){
        $.messager.alert("${msg('note')}","${msg('input.phone.invalid')}");
        return false;
    }
    return true;
}

/**
 * 校验用户名
 * @param username
 */
function validateUsername(username) {
  if (typeof username == 'undefined' || username == null) {
    username = "";
  }
  if (username.length < 6 || username.length > 20) {
    topWin.$.messager.alert("${msg('note')}",
        "${msg('user.username.validation.length','6','20')}");
    return false;
  }
  var workType;
  for (var i = 0; i < username.length; i++) {
    workType = username.charCodeAt(i);
    if ((workType >= 65 && workType <= 90)
        || (workType >= 97 && workType <= 122)
        || (workType >= 48 && workType <= 57) || (workType == 95)) {//ASCII码值判断为字母数字或下划线
      continue;
    } else {//不符合则输出提示
      topWin.$.messager.alert("${msg('note')}",
          "${msg('user.username.validation.formmat')}");
      return false;
    }
  }
  return true;
}

/**
 * 时间对象转换成字符串
 * timezone 原值 为GMT+08:00 GMT-11:30 需要转换为时区整数值 如：+8、-11.5
 * 时区时间转换公式为:UTC时间（本地时间+格林时间差） + 时区差值
 */
function dateToStr(timezone){
    var date = new Date();
    if(timezone != null && timezone != ""){
      var min =  timezone.substr(7,2);
      timezone = timezone.substr(3,3);
      if(timezone.substr(1,1)=="0"){
          timezone=timezone.substr(0,1)+timezone.substr(2,1);
      }
      if(min == "30"){//时区 暂时只考虑精确到半点
          timezone=timezone+".5";
      }
      date =new Date(date.getTime() +date.getTimezoneOffset()*60000+ (3600000*timezone));
    } 
    var y = date.getFullYear();
    var m = date.getMonth() + 1;if(m<10){m="0"+m;}
    var d = date.getDate();if(d<10){d="0"+d;}
    var h = date.getHours();if(h<10){h="0"+h;}
    var i = date.getMinutes();if(i<10){i="0"+i;}
    var s = date.getSeconds();if(s<10){s="0"+s;}
    var ms = date.getMilliseconds();
    return y + '-' + m + '-' + d + ' ' + h + ':' + i + ':' + s;
}