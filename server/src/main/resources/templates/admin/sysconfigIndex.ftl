<!DOCTYPE html>
<html>
<head>
    <#include "*/admin/common/header.ftl">
    <title>sysconfigList.htm</title>
</head>
<body>
<div class="container">
    <div class="row" style="height: 10px;">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading"><h3 class="panel-title">系统配置信息</h3></div>
                     <div class="panel-body">
                        <form class="form-horizontal" role="form" id="sysconfig-form">
                            <input type="hidden"  name="sysconfig.id" value="${sysconfig.id}">
                              <div class="form-group">
                                <label for="sysconfig-serverUrl" class="control-label col-sm-4">
                                    服务器url(例如：http://192.168.0.229:8099/server)<span class="text-danger">*</span>
                                </label>
                                <div class="col-sm-8">
                                    <input type="text" id="sysconfig-serverUrl" name="sysconfig.serverUrl" class="form-control" value="${sysconfig.serverUrl!}">
                                </div>
                              </div>
                              <div class="form-group">
                                <label for="sysconfig-collectorVersion" class="control-label col-sm-4">采集器版本</label>
                                <div class="col-sm-8">
                                    <input type="text" id="sysconfig-collectorVersion" name="sysconfig.collectorVersion" class="form-control" value="${sysconfig.collectorVersion!}">
                                </div>
                              </div>
                              <div class="form-group">
                                <label for="sysconfig-proxyPoolSize" class="control-label col-sm-4">代理ip池大小</label>
                                <div class="col-sm-8">
                                    <input type="number" min="1" id="sysconfig-proxyPoolSize" name="sysconfig.proxyPoolSize" class="form-control" value="${sysconfig.proxyPoolSize!}">
                                </div>
                              </div>
                              <div class="form-group">
                                <label for="sysconfig-crawlerParamText" class="control-label col-sm-4">采集器参数Key-Label对应关系(json)</label>
                                <div class="col-sm-8">
                                    <textarea type="text" id="sysconfig-crawlerParamText" name="sysconfig.crawlerParamText" class="form-control" rows="10">${sysconfig.crawlerParamText!}</textarea>
                                </div>
                              </div>

                              <div class="row" style="text-align:center;">
                                <div class="col-md-12">
                                    <button type="submit" class="btn btn-info waves-effect waves-light">保存</button>
                                </div>
                              </div>
                         </form>
                    </div>
            </div>
        </div><!-- /.modal -->
    </div>
    </div>


</div> <!-- container -->
<#include "*/admin/common/footer.ftl">

<!-- 自定JS写在footer之后 -->
<script type="text/javascript">
    //构建表格展开的内容
    $(document).ready(function() {
        $("#sysconfig-form").validate({
            rules: {
                'sysconfig.serverUrl':{
                    required:true,
                },
                'sysconfig.proxyPoolSize':{
                    required:true,
                },
                'sysconfig.collectorVersion':{
                    maxlength:255,
                    required:true
                }
            },
            submitHandler:function(form){
                $.ajax({
                    type:'post',
                    url: "${base}/json/sysconfigSave.do",
                    data: $('#sysconfig-form').serialize(),
                    dataType:'json',
                    success:function(data){
                        if(data.success){
                            $("#sysconfig-modal-cancel").trigger('click');
                            swal('操作成功', '', "success", {button: "${i18n('ok')}"});
                        }else{
                            swal('操作失败', data.msg ? data.msg : '' ,"error", {button: "${i18n('ok')}"});
                        }
                    }
                })
            }
        });
    });



</script>
</body>
</html>