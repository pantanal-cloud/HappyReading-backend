<!DOCTYPE html>
<html>
<head>
    <#include "*/admin/common/header.ftl">
    <title>taskList.htm</title>
</head>
<body>
<div class="container">
    <div class="row" style="height: 10px;">
        <div id="task-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none">
            <div class="modal-dialog" style="width: 800px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title">添加任务</h4>
                    </div>
                    <form class="form-horizontal" role="form" id="task-form">
                        <input type="hidden"  name="task.id">
                        <input type="hidden"  name="task.clientId">
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="task-groupId" class="control-label col-sm-4">资源站<span class="text-danger">*</span></label>
                                <div class="col-sm-8">
                                    <select id="task-groupId" name="task.groupId" class="form-control">
                                        <#list taskGroupList as taskGroup>
                                            <option value="${taskGroup.id}">${taskGroup.name}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="task-collectorId" class="control-label col-sm-4">采集器<span class="text-danger">*</span></label>
                                <div class="col-sm-8">
                                    <select id="task-collectorId" name="task.collectorId" class="form-control">
                                        <#list collectorList as collector>
                                            <option value="${collector.id}">${collector.name}</option>
                                        </#list>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="task-name" class="control-label col-sm-4">
                                    任务名<span class="text-danger">*</span>
                                </label>
                                <div class="col-sm-8">
                                    <input type="text" id="task-name" name="task.name" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="param-seedUrl" class="control-label col-sm-4">
                                    种子url<span class="text-danger">*</span>
                                </label>
                                <div class="col-sm-8">
                                    <input type="text" id="param-seedUrl" name="param.seedUrl" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="task-target" class="control-label col-sm-4">采集目标数</label>
                                <div class="col-sm-8">
                                    <input type="text" id="task-target" name="task.target" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="param-crawlerDataName" class="control-label col-sm-4">
                                    采集数据名(本地保存文件夹,长度254)<span class="text-danger">*</span>
                                </label>
                                <div class="col-sm-8">
                                    <input type="text" id="param-crawlerDataName" name="param.crawlerDataName" class="form-control" maxlength="254">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="param-threads" class="control-label col-sm-4">线程数<span class="text-danger">*</span></label>
                                <div class="col-sm-8">
                                    <input type="text" id="param-threads" name="param.threads" class="form-control" value="50">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="param-mode" class="control-label col-sm-4">采集模式<span class="text-danger">*</span></label>
                                <div class="col-sm-8">
                                    <select id="param-mode" name="param.mode" class="form-control">
                                        <option value="0">重新采</option>
                                        <option value="1">接着采</option>
                                        <option value="2">增量采</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="param-proxy" class="control-label col-sm-4">使用代理<span class="text-danger">*</span></label>
                                <div class="col-sm-8">
                                    <select id="param-proxy" name="param.proxy" class="form-control">
                                        <option value="true">是</option>
                                        <option value="false">否</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="param-proxyPoolSize" class="control-label col-sm-4">代理IP池大小<span class="text-danger">*</span></label>
                                <div class="col-sm-8">
                                    <input type="number" min="1" max="${sysconfig.proxyPoolSize!1}" id="param-proxyPoolSize" name="param.proxyPoolSize" class="form-control" value="1">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="param-username" class="control-label col-sm-4">网站登录用户名</label>
                                <div class="col-sm-8">
                                    <input type="text" id="param-username" name="param.username" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="param-password" class="control-label col-sm-4">网站登录密码</label>
                                <div class="col-sm-8">
                                    <input type="text" id="param-password" name="param.password" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="task-conf" class="control-label col-sm-4">
                                    Crawler Config(JSON)
                                </label>
                                <div class="col-sm-8">
                                    <textarea type="text" id="task-conf" name="confJson" class="form-control" rows="5"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="task-description" class="control-label col-sm-4">
                                    描述
                                </label>
                                <div class="col-sm-8">
                                    <textarea type="text" id="task-description" name="task.description" class="form-control" rows="5"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" id="task-modal-cancel" class="btn btn-default waves-effect" data-dismiss="modal">取消</button>
                            <button type="submit" class="btn btn-info waves-effect waves-light">确认</button>
                        </div>
                    </form>
                </div>
            </div>
        </div><!-- /.modal -->
    </div>

    <div class="row" >
        <div class="col-lg-12">
            <div class="tab-content" style="margin-bottom: 20px;padding: 0px;">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="m-b-15">
                                    <form id="queryForm" class="form-inline" role="form">
                                        <div class="pull-left">
                                            <input type="text" class="form-control datepicker" name="task.id" placeholder="任务ID">
                                            <input type="text" class="form-control datepicker" name="task.name" placeholder="任务名">
                                            <select name="task.groupId" class="form-control">
                                                <option value="">--资源站点--</option>
                                                <#list taskGroupList as taskGroup>
                                                    <option value="${taskGroup.id}">${taskGroup.name}</option>
                                                </#list>
                                            </select>
                                            <button id="queryButton" type="button" onclick="$('#taskDataTable').DataTable().ajax.reload();" class="btn btn-success waves-effect waves-light m-l-10">查询</button>
                                            <button type="button" id="addButton" class='btn btn-default btn-pink m-l-10' data-toggle='modal' data-target='#task-modal'>添加</button>
                                        </div>
                                        <div class="pull-right">

                                        </div>
                                    </form>
                                </div>
                                <!-- panel-body -->

                                <table id="taskDataTable" class="display" cellspacing="0" width="100%">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>名称</th>
                                        <th>状态</th>
                                        <th>资源站</th>
                                        <th>采集器</th>
                                        <th>客户端</th>
                                        <th>采集数</th>
                                        <th>新增数</th>
                                        <th>消耗IP</th>
                                        <th>耗时(秒)</th>
                                        <th>开始时间</th>
                                        <th>结束时间</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </div>

                </div> <!-- End Row -->
            </div>
        </div>
    </div>
</div> <!-- container -->
<#include "*/admin/common/footer.ftl">

<!-- 自定JS写在footer之后 -->
<script type="text/javascript">
    //构建表格展开的内容
    var taskTable;
    $(document).ready(function() {

        taskTable = $('#taskDataTable').DataTable( {
            //"pageLength": 3,
            "ajax": {
                "url": "${base}/paramfix/taskGridData.do",
                "type": "POST",
                //dataType: "json",
                // contentType: "application/json",
                "data": function ( d ) {
                    $("#queryForm").find("input,select").each(function(i){
                        if($.trim($(this).val()) != ""){
                            d[$(this).attr("name")] = $.trim($(this).val());
                        }
                    });

                    return d;
                    //return JSON.stringify(d);
                }
            },
            select: 'single',
            "columns": [
                { "data": "id","defaultContent":"--"},
                { "data": "name","defaultContent":"--"},
                { "data": "state","defaultContent":"--","render": function ( data, type, row, meta ) {
                        if(data == 'FAILURE'){
                            return "失败";
                        }
                        if(data == 'UNSTART'){
                            return "未启动";
                        }
                        if(data == "RUNNING"){
                            return "运行...";
                        }
                        if(data == "STOP"){
                            return "停止";
                        }
                    }},
                { "data": "group.name","defaultContent":"--"},
                { "data": "collector.name","defaultContent":"--"},
                { "data": "client.hostname","defaultContent":"--"},
                { "data": "total","defaultContent":"--","render": function ( data, type, row, meta ) {
                        if(row.target == null){
                            return data;
                        }else if(data == null){
                            return "--/" +row.target
                        }else if(data != null){
                            return data + "/" +row.target
                        }
                    }
                },
                { "data": "added","defaultContent":"--"},
                { "data": "costIp","defaultContent":"--"},
                { "data": "costSeconds","defaultContent":"--"},
                { "data": "startTime","defaultContent":"--","render": function ( data, type, row, meta ) {
                        if(data != null){
                            return new moment({ year :data.year, month :data.monthValue-1, day :data.dayOfMonth, hour :data.hour, minute :data.minute, second :data.second}).format("YYYY-MM-DD HH:mm:ss");
                        }
                    }
                },
                { "data": "endTime","defaultContent":"--","render": function ( data, type, row, meta ) {
                       if(data != null){
                          return new moment({ year :data.year, month :data.monthValue-1, day :data.dayOfMonth, hour :data.hour, minute :data.minute, second :data.second}).format("YYYY-MM-DD HH:mm:ss");
                       }
                    }
                },
                { "data": "id","render": function ( data, type, row, meta ) {
                        return operation(data, row, meta.row);
                    }}
            ]
        } );

        //add task
        $("#addButton").on('click',function(){
            $("#task-form").data('validator').resetForm();
            $("#task-form")[0].reset();
            $("#task-form [name='task.id']").val("");
            $("#task-form [name='task.clientId']").val("");
            $("#task-modal .modal-title").text('添加任务');
        })

        $("#task-form").validate({
            rules: {
                'task.name':{
                    maxlength:255,
                    required:true,
                },
                'task.groupId':{
                    required:true,
                },
                'task.collectorId':{
                    required:true,
                },
                'param.seedUrl':{
                    required:true,
                },
                'param.proxy':{
                    required:true,
                },
                'param.proxyPoolSize':{
                    required:true,
                },
                'param.crawlerDataName':{
                    required:true,
                    maxlength:254,
                },
                'param.threads':{
                    required:true,
                }
            },
            submitHandler:function(form){
                $.ajax({
                    type:'post',
                    url: "${base}/json/taskSave.do",
                    data: $('#task-form').serialize(),
                    dataType:'json',
                    success:function(data){
                        if(data.success){
                            $("#task-modal-cancel").trigger('click');
                            swal('操作成功', '', "success", {button: "${i18n('ok')}"});
                            taskTable.ajax.reload(null,false);
                        }else{
                            swal('操作失败', data.msg ? data.msg : '' ,"error", {button: "${i18n('ok')}"});
                        }
                    }
                })
            }
        });

        $("#queryForm").find("input,select").keypress(function (e) {
            var key = e.which; //e.which是按键的值
            if (key == 13) {
               $("#queryButton").trigger("click");
            }
        })
    });

    //操作
    function operation(value,row,index) {
        var detailHtml = "<button class='btn btn-default btn-pink sel_bt ' data-toggle='modal' data-target='#task-modal' onClick='javascript:toEdit("+index+")'>编辑</button>";

        detailHtml += '<div class="btn-group">';
        detailHtml += '<button type="button" style="margin-left:5px;" class="btn btn-success dropdown-toggle waves-effect waves-light" data-toggle="dropdown" aria-expanded="false">操作<span class="caret"></span></button>';
        detailHtml += '<ul class="dropdown-menu" role="menu">';
        detailHtml += "<li><a href='javascript:toDelete("+index+")'>删除</a></li>";
        detailHtml += '</ul></div>';
        return detailHtml;
    }

    //toEdit
    function toEdit(index) {
        var data = taskTable.row( index ).data();
        $("#task-form").data('validator').resetForm();
        $("#task-form")[0].reset();

        $("#task-form [name='task.id']").val(data.id);
        $("#task-form [name='task.name']").val(data.name);
        $("#task-form [name='task.clientId']").val(data.clientId);
        $("#task-form [name='task.groupId']").val(data.groupId);
        $("#task-form [name='task.target']").val(StringUtil.defaultValue(data.target));
        $("#task-form [name='task.collectorId']").val(data.collectorId);
        $("#task-form [name='task.description']").val(data.description);

        var params = data.params ? data.params : {};
        if(params.seedUrl == null && params.seedUrls != null && params.seedUrls.length > 0){
            $("#task-form [name='param.seedUrl']").val(StringUtil.defaultValue(params.seedUrls[0]));
        }else{
            $("#task-form [name='param.seedUrl']").val(StringUtil.defaultValue(params.seedUrl));
        }
        $("#task-form [name='param.crawlerDataName']").val(StringUtil.defaultValue(params.crawlerDataName));
        $("#task-form [name='param.threads']").val(StringUtil.defaultValue(params.threads));
        $("#task-form [name='param.mode']").val(params.mode ? params.mode : 0);
        $("#task-form [name='param.username']").val(StringUtil.defaultValue(params.username));
        $("#task-form [name='param.password']").val(StringUtil.defaultValue(params.password));
        $("#task-form [name='param.proxy']").val(''+ (params.proxy ? params.proxy:false) + '');
        $("#task-form [name='param.proxyPoolSize']").val(StringUtil.defaultValue(params.proxyPoolSize));
        $("#task-form [name='confJson']").val(params.conf ? JSON.stringify(params.conf) : "");

        $("#task-form .modal-title").text('编辑任务');
    }

    //释放 simcard
    function toDelete(index){
        var data = taskTable.row( index ).data();
        swal({
            title: "提示",
            text:"确定删除任务： "+ (data.taskname == null ? data.name : data.taskname) +" 吗？",
            icon: "warning",
            buttons: ["${i18n('cancel')}","${i18n('ok')}"],
            dangerMode: true
        }).then((willRelease) => {
            if (willRelease) {
                $.ajax({
                    type:"GET",
                    url:"${base}/json/taskDelete.do",
                    dataType: "json",
                    data : {
                        'id' : data.id
                    },
                    success:function(data){
                        if (data.success) {
                            swal("提示", "操作成功", "success", {button: "${i18n('ok')}"});
                            taskTable.ajax.reload(null,false);
                        } else {
                            swal("提示", "操作失败", "error", {button: "${i18n('ok')}"});
                        }
                    }
                });
            }
        });
    }
</script>
</body>
</html>