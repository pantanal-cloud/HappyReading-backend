<script>
    var resizefunc = [];
</script>

<script type="text/javascript" src="${base}/moltran2/assets/plugins/jquery-validation/dist/jquery.validate.min.js"></script>
<script type="text/javascript" src="${base}/moltran2/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${base}/moltran2/assets/js/detect.js"></script>
<script type="text/javascript" src="${base}/moltran2/assets/js/fastclick.js"></script>
<script type="text/javascript" src="${base}/moltran2/assets/js/jquery.slimscroll.js"></script>
<script type="text/javascript" src="${base}/moltran2/assets/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="${base}/moltran2/assets/js/waves.js"></script>
<script type="text/javascript" src="${base}/moltran2/assets/js/wow.min.js"></script>
<script type="text/javascript" src="${base}/moltran2/assets/js/jquery.nicescroll.js"></script>
<script type="text/javascript" src="${base}/moltran2/assets/js/jquery.scrollTo.min.js"></script>
<script type="text/javascript" src="${base}/moltran2/assets/plugins/toggles/toggles.min.js"></script>
<script type="text/javascript" src="${base}/web/assets/js/jquery.app.js"></script>

<!-- datatables -->
<script type="text/javascript" charset="utf8" src="${base}/js/datatables/datatables.js"></script>
<script type="text/javascript" charset="utf8" src="${base}/js/datatables/DataTables-1.10.16/js/jquery.dataTables.min.js"></script>

<!-- jQuery瀑布流插件 Masonry -->
<script type="text/javascript" src="${base}/js/jquery-plugin/jquery.masonry.min.js"></script>

<!-- sweet alert  -->
<script src="${base}/js/sweetalert@2.1.2.min.js"></script>

<!--Form Wizard-->
<script src="${base}/moltran2/assets/plugins/jquery.steps/build/jquery.steps.min.js" type="text/javascript"></script>

<!-- moment js  -->
<script src="${base}/moltran2/assets/plugins/moment/moment.js"></script>

<!-- counters  -->
<script src="${base}/moltran2/assets/plugins/waypoints/lib/jquery.waypoints.js"></script>
<script src="${base}/moltran2/assets/plugins/counterup/jquery.counterup.min.js"></script>
<script src="${base}/moltran2/assets/plugins/moment/moment.js"></script>

<!-- Countdown -->
<script src="${base}/moltran2/assets/plugins/countdown/dest/jquery.countdown.min.js"></script>
<script src="${base}/moltran2/assets/plugins/simple-text-rotator/jquery.simple-text-rotator.min.js"></script>
 
<!-- todos app  -->
<script src="${base}/moltran2/assets/pages/jquery.todo.js"></script>
 
<!-- system utils -->
<script type="text/javascript" src="${base}/js/util.js"></script>
<script type="text/javascript" src="${base}/js/Map.js"></script>
<script type="text/javascript" src="${base}/js/SelectUtil.js"></script>
<script type="text/javascript" src="${base}/js/StringUtil.js"></script>
<script type="text/javascript" src="${base}/js/NumberUtil.js"></script>

<script type="text/javascript">
    $.extend(true, $.fn.dataTable.defaults, {
        "processing": true,
        "serverSide": true,
        "searching": false,
        "ordering": false,
        "lengthChange": false,
        "language": {
            "processing": "${i18n('msg.waiting')}",
            "info": "${i18n('page.info')}",
            "infoEmpty": "${i18n('page.info.empty')}",
            "sZeroRecords":"${i18n('sZeroRecords')}",
            "lengthMenu": "每页 _MENU_ 条.",
            "paginate": {
                "previous": "${i18n('page.back')}",
                "next": "${i18n('page.next')}"
            },
            "infoFiltered": "",
            "emptyTable":"${i18n('nodata2')}"
        }
    } );
    
    $.fn.dataTable.ext.errMode = function (s, h, m) {
        var responseText =s.jqXHR.responseText ;
        if(responseText.indexOf("logout") > -1){
          topWin.location = "userLogin.do";
        }else if(responseText.indexOf("unauthorized") > -1){
          swal("${i18n('unauthorized')}", "", "error", {button: "${i18n('ok')}"});
        }else{
          swal("${i18n('systemerror')}", "", "error", {button: "${i18n('ok')}"});
        } 
        if (h == 7) {
            swal("${i18n('datatables.return.data.error')}", "", "error", {button: "${i18n('ok')}"});
        }  
    }; 
    
    //全局ajax处理异常和logout
    $.ajaxSetup({
      error: function (xmlHttpRequest, textStatus, errorThrown) {
         // swal.close();
          var responseText = xmlHttpRequest.responseText;
          if(responseText.indexOf("logout") > -1){
            topWin.location = "userLogin.do";
          }else if(responseText.indexOf("unauthorized") > -1){
            swal("${i18n('unauthorized')}", "", "error", {button: "${i18n('ok')}"});
          }else{
            swal("${i18n('systemerror')}", "", "error", {button: "${i18n('ok')}"});
          }
        }
    });

    jQuery.validator.addMethod("isphoneNum", function(value, element) {
        var length = value.length;
        var mobile = /^1[3|5|8]{1}[0-9]{9}$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "${i18n('input.phone.invalid')}");
    
  //密码不能包括空格 
    jQuery.validator.addMethod("passwdBankCheck", function(value, element) {
        var workType;
        if(value.indexOf(" ") >= 0){
            return false;
        }else{
            return true;
        }
    }, "${i18n('user.password.validation.formmat')}");
    
    $.extend($.validator.messages, {
        required: "这是必填字段",
        remote: "请修正此字段",
        email: "请输入有效的电子邮件地址",
        url: "请输入有效的网址",
        date: "请输入有效的日期",
        dateISO: "请输入有效的日期 (YYYY-MM-DD)",
        number: "请输入有效的数字",
        digits: "只能输入数字",
        creditcard: "请输入有效的信用卡号码",
        equalTo: "你两次输入不相同",
        extension: "请输入有效的后缀",
        maxlength: $.validator.format("最多可以输入 {0} 个字符"),
        minlength: $.validator.format("最少要输入 {0} 个字符"),
        rangelength: $.validator.format("请输入长度在 {0} 到 {1} 之间的字符串"),
        range: $.validator.format("请输入范围在 {0} 到 {1} 之间的数值"),
        max: $.validator.format("请输入不大于 {0} 的数值"),
        min: $.validator.format("请输入不小于 {0} 的数值")
    });
    $(document).ready(function() {
	   /*
        $(".container").slimScroll({
	        height: '100%'
	    });
	   */
    });
</script>