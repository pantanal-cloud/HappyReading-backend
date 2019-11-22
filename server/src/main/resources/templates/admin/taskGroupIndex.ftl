<!DOCTYPE html>
<html>
<head>
    <#include "*/admin/common/header.ftl">
    <title>taskGroupList.htm</title>
</head>
<body>
<div class="container">
    <div class="row" style="height: 10px;">
        <div id="taskGroup-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none">
            <div class="modal-dialog" style="width: 800px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title">添加资源站</h4>
                    </div>
                    <form class="form-horizontal" role="form" id="taskGroup-form">
                        <input type="hidden"  name="taskGroup.id">
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="taskGroup-name" class="control-label col-sm-4">
                                    资源站点名<span class="text-danger">*</span>
                                </label>
                                <div class="col-sm-8">
                                    <input type="text" id="taskGroup-name" name="taskGroup.name" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="taskGroup-code" class="control-label col-sm-4">
                                    资源站点编码(例：dangdang)<span class="text-danger">*</span>
                                </label>
                                <div class="col-sm-8">
                                    <input type="text" id="taskGroup-code" name="taskGroup.code" class="form-control">
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" id="taskGroup-modal-cancel" class="btn btn-default waves-effect" data-dismiss="modal">取消</button>
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
                                            <input type="text" class="form-control datepicker" name="taskGroup.name" placeholder="资源站点名">
                                            <input type="text" class="form-control datepicker" name="taskGroup.code" placeholder="资源站点编码">
                                            <button type="button" id="queryButton" onclick="$('#taskGroupDataTable').DataTable().ajax.reload();" class="btn btn-success waves-effect waves-light m-l-10">查询</button>
                                            <button type="button" id="addButton" class='btn btn-default btn-pink m-l-10' data-toggle='modal' data-target='#taskGroup-modal'>添加</button>
                                        </div>
                                        <div class="pull-right">

                                        </div>
                                    </form>
                                </div>
                                <!-- panel-body -->

                                <table id="taskGroupDataTable" class="display" cellspacing="0" width="100%">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>名称</th>
                                        <th>编码</th>
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
    var taskGroupTable;
    $(document).ready(function() {
        taskGroupTable = $('#taskGroupDataTable').DataTable( {
            //"pageLength": 3,
            "ajax": {
                "url": "${base}/paramfix/taskGroupGridData.do",
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
                { "data": "code","defaultContent":"--"},
                { "data": "id","render": function ( data, type, row, meta ) {
                        return operation(data, row, meta.row);
                    }}
            ]
        } );

        //add taskGroup
        $("#addButton").on('click',function(){
            $("#taskGroup-form").data('validator').resetForm();
            $("#taskGroup-form")[0].reset();
            $("#taskGroup-form [name='taskGroup.id']").val("");
            $("#taskGroup-modal .modal-title").text('添加');
        })

        $("#taskGroup-form").validate({
            rules: {
                'taskGroup.name':{
                    maxlength:255,
                    required:true,
                },
                'taskGroup.code':{
                    required:true,
                }
            },
            submitHandler:function(form){
                $.ajax({
                    type:'post',
                    url: "${base}/json/taskGroupSave.do",
                    data: $('#taskGroup-form').serialize(),
                    dataType:'json',
                    success:function(data){
                        if(data.success){
                            $("#taskGroup-modal-cancel").trigger('click');
                            swal('操作成功', '', "success", {button: "${i18n('ok')}"});
                            taskGroupTable.ajax.reload(null,false);
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
        var detailHtml = "<button class='btn btn-default btn-pink sel_bt ' data-toggle='modal' data-target='#taskGroup-modal' onClick='javascript:toEdit("+index+")'>编辑</button>";

        detailHtml += '<div class="btn-group">';
        detailHtml += '<button type="button" style="margin-left:5px;" class="btn btn-success dropdown-toggle waves-effect waves-light" data-toggle="dropdown" aria-expanded="false">操作<span class="caret"></span></button>';
        detailHtml += '<ul class="dropdown-menu" role="menu">';
        detailHtml += "<li><a href='javascript:toDelete("+index+")'>删除</a></li>";
        detailHtml += '</ul></div>';
        return detailHtml;
    }

    //toEdit
    function toEdit(index) {
        var data = taskGroupTable.row( index ).data();
        $("#taskGroup-form").data('validator').resetForm();
        $("#taskGroup-form")[0].reset();

        $("#taskGroup-form [name='taskGroup.id']").val(data.id);
        $("#taskGroup-form [name='taskGroup.name']").val(data.name);
        $("#taskGroup-form [name='taskGroup.code']").val(data.code);


        $("#taskGroup-form .modal-title").text('编辑资源站点');
    }

    //释放 simcard
    function toDelete(index){
        var data = taskGroupTable.row( index ).data();
        swal({
            title: "提示",
            text:"确定删除资源站点： "+ (data.taskGroupname == null ? data.name : data.taskGroupname) +" 吗？",
            icon: "warning",
            buttons: ["${i18n('cancel')}","${i18n('ok')}"],
            dangerMode: true
        }).then((willRelease) => {
            if (willRelease) {
                $.ajax({
                    type:"GET",
                    url:"${base}/json/taskGroupDelete.do",
                    dataType: "json",
                    data : {
                        'id' : data.id
                    },
                    success:function(data){
                        if (data.success) {
                            swal("提示", "操作成功", "success", {button: "${i18n('ok')}"});
                            taskGroupTable.ajax.reload(null,false);
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