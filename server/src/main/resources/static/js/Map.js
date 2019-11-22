/**
   Map类
*/
var Map = function(){
  this.data = new Object(); 
  var keyArray = new Array();
}

/**
 * put
 */
Map.prototype.put = function(key,value){
  this.data[key + "$"] = value;
}

/**
 * gut
 */
Map.prototype.get = function(key){
  var value = this.data[key + "$"];
  if(typeof value == 'undefined'){
      value = null;
  }
  return value;
}

/**
 * put
 */
Map.prototype.remove = function(key){
    delete this.data[key + "$"];
}

/**
 * key set
 */
Map.prototype.keySet = function() {
  var keyArray = new Array();
  for ( var p in this.data) {
    if (typeof p == 'string' && p.substring(p.length - 1) == "$") {
      keyArray.push(p.substring(0, p.length - 1));
    }
  }
  return keyArray;
};

/**
 * values
 */
Map.prototype.values = function() {
  var valueArray = new Array();
  for ( var p in this.data) {
    if (typeof p == 'string' && p.substring(p.length - 1) == "$") {
      valueArray.push(this.data[p]);
    }
  }
  return valueArray;
};
  