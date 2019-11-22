/**
 * 
 */
var NumberUtil = function() {

}
/**
 * 
 */
NumberUtil.defaultVal = function(v) {
    if (typeof v === undefined || v == null || v == '') {
        return 0;
    } else {
        return v;
    }
}
/**
 * 
 * @param d
 * @returns
 */
NumberUtil.parseDouble = function(d) {
    try {
        return parseFloat(d);
    } catch (e) {
        return NaN;
    }
}

/**
 * 
 * @param d
 * @returns
 */
NumberUtil.parseInt = function(d) {
    try {
        return parseInt(d);
    } catch (e) {
        return NaN;
    }
}

/**
 *@param d
 * @returns 
 */
NumberUtil.parseHex = function (d) {
    if (typeof d == 'undefined' || d == null || d.length == 0) {
        return "0x";
    }else{
        d = d.toString(16);
        if(d.length == 1){
            d="0x000"+d;
        }else if(d.length == 2){
            d="0x00"+d;
        }else if(d.length == 3){
            d="0x0"+d;
        }else {
            d="0x"+d;
        } 
        return d;
    }
}
/**
 * 
 * @param str1 格式：数字
 * @param str2 格式：数字
 * @returns 1:参数1大于参数2; 0:参数1等于小于参数2; -1:参数1小于参数2
 */
NumberUtil.compareNumber = function (str1, str2){
    if(isFinite(str1) && isFinite(str2)){
        var t1s = parseFloat(str1);
        var t2s = parseFloat(str2);
        if(t1s > t2s){
            return 1;
        }else if(t1s == t2s){
            return 0;
        }else{
            return -1;
        }
    }else{
        return 0;
    }
}

/**
 * 
 * @param timeStr1 格式：HH:mm:ss
 * @param timeStr2 格式：HH:mm:ss
 * @returns 1:参数1大于参数2; 0:参数1等于小于参数2; -1:参数1小于参数2
 */
NumberUtil.compareTime = function (timeStr1, timeStr2){
   var t1s = timeStr1.split(":");
   var t2s = timeStr2.split(":");
   if(t1s.length == 3 && t2s.length == 3){
       if(parseInt(t1s[0]) > parseInt(t2s[0])){
           return 1;
       }else if(parseInt(t1s[0]) < parseInt(t2s[0])){
           return -1;
       }else if (parseInt(t1s[1]) > parseInt(t2s[1])){
           return 1;
       }else if (parseInt(t1s[1]) < parseInt(t2s[1])){
           return -1;
       }else if (parseInt(t1s[2]) > parseInt(t2s[2])){
           return 1;
       }else if (parseInt(t1s[2]) < parseInt(t2s[2])){
           return -1;
       }else {
           return 0;
       }
   }else{
       return 0;
   }
}

/**
 * 
 * @param dateStr1 格式：MM/dd/yyyy or yyyy-MM-dd
 * @param dateStr2 格式：MM/dd/yyyy or yyyy-MM-dd
 * @returns 1:参数1大于参数2; 0:参数1等于小于参数2; -1:参数1小于参数2
 */
NumberUtil.compareDate = function (dateStr1, dateStr2){
   if(dateStr1.split("/").length == 3 && dateStr2.split("/").length == 3){
       var t1s = dateStr1.split("/");
       var t2s = dateStr2.split("/");  
       if(parseInt(t1s[2]) > parseInt(t2s[2])){
           return 1;
       }else if (parseInt(t1s[2]) < parseInt(t2s[2])){
           return -1;
       }else if (parseInt(t1s[0]) > parseInt(t2s[0])){
           return 1;
       }else if (parseInt(t1s[0]) < parseInt(t2s[0])){
           return -1;
       }else if (parseInt(t1s[1]) > parseInt(t2s[1])){
           return 1;
       }else if (parseInt(t1s[1]) < parseInt(t2s[1])){
           return -1;
       }else {
           return 0;
       }
       
   }else if(dateStr1.split("-").length == 3 && dateStr2.split("-").length == 3){
       var t1s = dateStr1.split("-");
       var t2s = dateStr2.split("-"); 
       if(parseInt(t1s[0]) > parseInt(t2s[0])){
           return 1;
       }else if(parseInt(t1s[0]) < parseInt(t2s[0])){
           return -1;
       }else if (parseInt(t1s[1]) > parseInt(t2s[1])){
           return 1;
       }else if (parseInt(t1s[1]) < parseInt(t2s[1])){
           return -1;
       }else if (parseInt(t1s[2]) > parseInt(t2s[2])){
           return 1;
       }else if (parseInt(t1s[2]) < parseInt(t2s[2])){
           return -1;
       }else {
           return 0;
       }
   }else{
       return 0;
   }
}

/**
 * 
 * @param dateTimeStr1 格式：MM/dd/yyyy HH:mm:ss or yyyy-MM-dd HH:mm:ss
 * @param dateTimeStr2 格式：MM/dd/yyyy HH:mm:ss or yyyy-MM-dd HH:mm:ss
 * @returns 1:参数1大于参数2; 0:参数1等于小于参数2; -1:参数1小于参数2
 */
NumberUtil.compareDateTime = function (dateTimeStr1, dateTimeStr2){
   var tm1s = dateTimeStr1.split(" ");
   var tm2s = dateTimeStr2.split(" ");
   if(tm1s.length == 2 && tm2s.length == 2){
       var dateCompare = NumberUtil.compareDate(tm1s[0],tm2s[0]);
       var timeCompare = NumberUtil.compareTime(tm1s[1],tm2s[1]);
       if(dateCompare > 0){
           return 1;
       }else if(dateCompare < 0){
           return -1;
       }else if(timeCompare > 0){
           return 1;
       }else if(timeCompare < 0){
           return -1;
       }else{
           return 0;
       }
   }else{
       return 0;
   }
}

NumberUtil.isValidate = function(str) {
    var regu = "^[+-]?\\d+(\\.\\d+)?$";
    var re = new RegExp(regu);
    if(re.test(str)){
        return true;
    }else{
        return false;
    }
}
