/**
 * 
 */
var StringUtil = function(){
  
};
/**
 * 
 */
StringUtil.encodeHTML = function(str){
  if (typeof str == 'undefined' || str == null || str.length == 0) 
    return "";
  var s = str.replace(/&/g, "&#38;");
  s = s.replace(/</g, "&#60;");
  s = s.replace(/>/g, "&#62;");
  s = s.replace(/\"/g, "&#34;");
  s = s.replace(/\'/g, "&#39;");
  return s;
} 

/**
 * 
 */
StringUtil.defaultValue = function(str){
  if (typeof str == 'undefined' || str == null || str.length == 0) 
    return "";
  else
    return str;
}