
/**
 * 
 */
var SelectUtil = function(){
  
}
/**
 * 
 */
SelectUtil.move = function(fromObjId, toObjId){
  $("#" + fromObjId + " option:selected").remove().appendTo($("#" + toObjId));
}

/**
 * 
 */
SelectUtil.moveAll = function(fromObjId, toObjId){
  $("#" + fromObjId + " option").remove().appendTo($("#" + toObjId));
}