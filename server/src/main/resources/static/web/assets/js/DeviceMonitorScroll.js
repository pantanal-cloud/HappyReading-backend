/**
 * @param url:
 *            ajax url
 * @param dataType :
 *            ajax result type [json,xml,html]
 * @param params :
 *            json params
 * @param callback:
 *            json callback
 */

var DeviceMonitorScroll = function(targetObj, url, dataType, params, pageSize, count,
		flag, callback) {
	this.targetObj = targetObj;
	this.loadingFlag = 0;
	this.url = url;
	this.dataType = dataType;
	this.params = params;
	this.callback = callback;
	this.pageSize = pageSize;
	this.page = 1;
	this.count = count;
	this.flag = flag;
	var me = this;
}

// next page
DeviceMonitorScroll.prototype.loadData = function() {
	var me = this;
	if (me.params == undefined) {
		me.params = {};
	}
	if (me.flag == true) {
		$('#lastPage').attr('disabled', "true");
		$('#lastPage').css("color", "gray");
	} else {
		me.page = me.page + 1;
		$('#lastPage').removeAttr("disabled");
		$('#lastPage').css("color", "black");
	}
	if ((Math.ceil(me.count / me.pageSize) - me.page) <= 0) {
		$('#nextPage').attr('disabled', "true");
		$('#nextPage').css("color", "gray");
	} else {
		$('#nextPage').removeAttr("disabled");
		$('#nextPage').css("color", "black");
	}
	me.params['page'] = me.page;
	me.params['rows'] = me.pageSize;
	$.ajax({
		type : 'POST',
		url : me.url,
		dataType : me.dataType,
		data : me.params,
		beforeSend : function(XMLHttpRequest) {
		},
		success : function(data, textStatus, jqXHR) {
			if ((typeof data) == 'string') {
				data = $.trim(data);
			}
			if (data != undefined && data != null && data != '') {
				if (this.dataType == 'html') {
					me.flag = false;
					$(me.targetObj).html(data);
				} else if (this.dataType == "json") {
					if ((typeof me.jsonCallback) == 'object') {
						me.jsonCallback(data);
					}
				}
			}
			if ($("table#device_table_id td.mergeTd1_alarm").size() > 0) {
				alarmPlay();
			} else {
				alarmPause();
			}
		},
		complete : function(XMLHttpRequest, textStatus) {
			me.loadingFlag = 0;
		}
	});
}

// last page
DeviceMonitorScroll.prototype.loadDataLast = function() {
	var me = this;
	if (me.params == undefined) {
		me.params = {};
	}
	if (me.page <= 2) {
		$('#lastPage').attr('disabled', "true");
		$('#lastPage').css("color", "gray");
	}
	if ((Math.ceil(me.count / me.rows) - me.page) <= 0) {
		$('#nextPage').attr('disabled', "true");
		$('#nextPage').css("color", "gray");
	} else {
		$('#nextPage').removeAttr("disabled");
		$('#nextPage').css("color", "black");
	}
	me.page = me.page - 1;
	me.params['page'] = me.page;
	me.params['rows'] = me.pageSize;
	$.ajax({
		type : 'POST',
		url : me.url,
		dataType : me.dataType,
		data : me.params,
		beforeSend : function(XMLHttpRequest) {
		},
		success : function(data, textStatus, jqXHR) {
			if ((typeof data) == 'string') {
				data = $.trim(data);
			}
			if (data != undefined && data != null && data != '') {
				if (this.dataType == 'html') {
					$(me.targetObj).html(data);
				} else if (this.dataType == "json") {
					if ((typeof me.jsonCallback) == 'object') {
						me.jsonCallback(data);
					}
				}
			}
			if ($("table#device_table_id td.mergeTd1_alarm").size() > 0) {
				alarmPlay();
			} else {
				alarmPause();
			}
		},
		complete : function(XMLHttpRequest, textStatus) {
			me.loadingFlag = 0;
		}
	});
}

// refresh
DeviceMonitorScroll.prototype.refresh = function() {
	var me = this;
	if (me.params == undefined) {
		me.params = {};
	}
	me.params['page'] = me.page;
	me.params['rows'] = me.pageSize;
	$.ajax({
		type : 'POST',
		url : me.url,
		dataType : me.dataType,
		data : me.params,
		beforeSend : function(XMLHttpRequest) {
		},
		success : function(data, textStatus, jqXHR) {
			if ((typeof data) == 'string') {
				data = $.trim(data);
			}
			if (data != undefined && data != null && data != '') {
				if (this.dataType == 'html') {
					$(me.targetObj).html(data);
				} else if (this.dataType == "json") {
					if ((typeof me.jsonCallback) == 'object') {
						me.page = me.page;
						me.jsonCallback(data);
					}
				}
			}
			if ($("table#device_table_id td.mergeTd1_alarm").size() > 0) {
				alarmPlay();
			} else {
				alarmPause();
			}
		},
		complete : function(XMLHttpRequest, textStatus) {
			me.loadingFlag = 0;
		}
	});
}
// 告警(告警音)
function alarmPlay() {
	//$('#chatAudio')[0].currentTime = 0;
	//$('#chatAudio')[0].play();
}
// 告警(告警音)暂停
function alarmPause() {
	//$('#chatAudio')[0].pause();
}