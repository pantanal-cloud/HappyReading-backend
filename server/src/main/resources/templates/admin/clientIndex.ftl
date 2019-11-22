<!DOCTYPE html>
<html>
<head>
    <#include "*/admin/common/header.ftl">
    <title>clientList.htm</title>
</head>
<body>
<div class="container">
    <div class="row" style="height: 10px;">
        <div id="client-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none">
            <div class="modal-dialog" style="width: 800px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title">添加客户端</h4>
                    </div>
                    <form class="form-horizontal" role="form" id="client-form">
                        <input type="hidden"  name="client.id">
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="client-hostname" class="control-label col-sm-2">
                                    Hostname<span class="text-danger">*</span>
                                </label>
                                <div class="col-sm-10">
                                    <input type="text" id="client-hostname" name="client.hostname" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="client-tasks" class="control-label col-sm-2">
                                    任务数
                                </label>
                                <div class="col-sm-10">
                                    <input type="text" id="client-tasks" name="client.tasks" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="client-finished" class="control-label col-sm-2">
                                    已完成
                                </label>
                                <div class="col-sm-10">
                                    <select id="collector-finished" name="client.finished" class="form-control">
                                        <option value="false">否</option>
                                        <option value="true">已完成</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="client-dirInfo" class="control-label col-sm-2">
                                    目录信息
                                </label>
                                <div class="col-sm-10">
                                    <input type="text" id="client-dirInfo" name="client.dirInfo" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="client-description" class="control-label col-sm-2">
                                    描述
                                </label>
                                <div class="col-sm-10">
                                    <textarea type="text" id="client-description" name="client.description" class="form-control" rows="5"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" id="client-modal-cancel" class="btn btn-default waves-effect" data-dismiss="modal">取消</button>
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
                                            <input type="text" class="form-control datepicker" name="client.id" placeholder="客户端ID">
                                            <input type="text" class="form-control datepicker" name="client.hostname" placeholder="客户端hostname">
                                            <button type="button" id="queryButton" onclick="$('#clientDataTable').DataTable().ajax.reload();" class="btn btn-success waves-effect waves-light m-l-10">查询</button>
                                            <button type="button" id="addButton" class='btn btn-default btn-pink m-l-10' data-toggle='modal' data-target='#client-modal'>添加</button>
                                        </div>
                                        <div class="pull-right">

                                        </div>
                                    </form>
                                </div>
                                <!-- panel-body -->

                                <table id="clientDataTable" class="display" cellspacing="0" width="100%">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>hostname</th>
                                        <th>任务数</th>
                                        <th>已完成</th>
                                        <th>目录信息</th>
                                        <th>描述</th>
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
    var clientTable;
    $(document).ready(function() {
        clientTable = $('#clientDataTable').DataTable( {
            //"pageLength": 3,
            "ajax": {
                "url": "${base}/paramfix/clientGridData.do",
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
                { "data": "hostname","defaultContent":"--"},
                { "data": "tasks","defaultContent":"--"},
                { "data": "finished","defaultContent":"--","render": function ( data, type, row, meta ) {
                    if(data == true){
                        return "已完成"
                    }
                }},
                { "data": "dirInfo","defaultContent":"--"},
                { "data": "description","defaultContent":"--"},
                { "data": "id","defaultContent":"--","render": function ( data, type, row, meta ) {
                        return operation(data, row, meta.row);
                }}
            ]
        } );

        //add client
        $("#addButton").on('click',function(){
            $("#client-form").data('validator').resetForm();
            $("#client-form")[0].reset();
            $("#client-form [name='client.id']").val("");
            $("#client-modal .modal-title").text('添加');
        })

        $("#client-form").validate({
            rules: {
                'client.hostname':{
                    maxlength:255,
                    required:true,
                }
            },
            submitHandler:function(form){
                $.ajax({
                    type:'post',
                    url: "${base}/json/clientSave.do",
                    data: $('#client-form').serialize(),
                    dataType:'json',
                    success:function(data){
                        if(data.success){
                            $("#client-modal-cancel").trigger('click');
                            swal('操作成功', '', "success", {button: "${i18n('ok')}"});
                            clientTable.ajax.reload(null,false);
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
        var detailHtml = "<button class='btn btn-default btn-pink sel_bt ' data-toggle='modal' data-target='#client-modal' onClick='javascript:toEdit("+index+")'>编辑</button>";

        detailHtml += '<div class="btn-group">';
        detailHtml += '<button type="button" style="margin-left:5px;" class="btn btn-success dropdown-toggle waves-effect waves-light" data-toggle="dropdown" aria-expanded="false">操作<span class="caret"></span></button>';
        detailHtml += '<ul class="dropdown-menu" role="menu">';
        detailHtml += "<li><a href='javascript:toDelete("+index+")'>删除</a></li>";
        detailHtml += '</ul></div>';
        return detailHtml;
    }

    //toEdit
    function toEdit(index) {
        var data = clientTable.row( index ).data();
        $("#client-form").data('validator').resetForm();
        $("#client-form")[0].reset();

        $("#client-form [name='client.id']").val(data.id);
        $("#client-form [name='client.hostname']").val(data.hostname);
        $("#client-form [name='client.tasks']").val(StringUtil.defaultValue(data.tasks));
        $("#client-form [name='client.finished']").val(''+ (data.finished ? true:false) + '');
        $("#client-form [name='client.dirInfo']").val(data.dirInfo);
        $("#client-form [name='client.description']").val(data.description);

        $("#client-form .modal-title").text('编辑客户端');
    }

    //释放 simcard
    function toDelete(index){
        var data = clientTable.row( index ).data();
        swal({
            title: "提示",
            text:"确定删除客户端： "+ (data.hostname == null ? data.hostname : data.hostname) +" 吗？",
            icon: "warning",
            buttons: ["${i18n('cancel')}","${i18n('ok')}"],
            dangerMode: true
        }).then((willRelease) => {
            if (willRelease) {
                $.ajax({
                    type:"GET",
                    url:"${base}/json/clientDelete.do",
                    dataType: "json",
                    data : {
                        'id' : data.id
                    },
                    success:function(data){
                        if (data.success) {
                            swal("提示", "操作成功", "success", {button: "${i18n('ok')}"});
                            clientTable.ajax.reload(null,false);
                        } else {
                            swal("提示", data.msg ? data.msg : "操作失败", "error", {button: "${i18n('ok')}"});
                        }
                    }
                });
            }
        });
    }
</script>
</body>
</html>