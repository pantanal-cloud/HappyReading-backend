<!DOCTYPE html>
<html>
<head>
    <#include "*/admin/common/header.ftl">
    <title>collectorList.htm</title>
</head>
<body>
<div class="container">
    <div class="row" style="height: 10px;">
        <div id="collector-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none">
            <div class="modal-dialog" style="width: 800px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title">添加采集器</h4>
                    </div>
                    <form class="form-horizontal" role="form" id="collector-form">
                        <input type="hidden"  name="collector.id">
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="collector-name" class="control-label col-sm-2">
                                    名称<span class="text-danger">*</span>
                                </label>
                                <div class="col-sm-10">
                                    <input type="text" id="collector-name" name="collector.name" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="collector-crawlerClass" class="control-label col-sm-2">
                                    爬取类全名<span class="text-danger">*</span>
                                </label>
                                <div class="col-sm-10">
                                    <input type="text" id="collector-crawlerClass" name="collector.crawlerClass" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="collector-resourceType" class="control-label col-sm-2">采集资源类别<span class="text-danger">*</span></label>
                                <div class="col-sm-10">
                                    <select id="collector-resourceType" name="collector.resourceType" class="form-control">
                                        <option value="EBOOK">图书</option>
                                        <option value="JOURNAL">期刊</option>
                                        <option value="THESIS">学位论文</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="collector-description" class="control-label col-sm-2">
                                    描述
                                </label>
                                <div class="col-sm-10">
                                    <textarea type="text" id="collector-description" name="collector.description" class="form-control" rows="5"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" id="collector-modal-cancel" class="btn btn-default waves-effect" data-dismiss="modal">取消</button>
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
                                            <input type="text" class="form-control datepicker" name="collector.name" placeholder="采集器名">
                                            <input type="text" class="form-control datepicker" name="collector.crawlerClass" placeholder="爬取类">
                                            <button type="button" onclick="$('#collectorDataTable').DataTable().ajax.reload();" class="btn btn-success waves-effect waves-light m-l-10">查询</button>
                                            <button type="button" id="addButton" class='btn btn-default btn-pink m-l-10' data-toggle='modal' data-target='#collector-modal'>添加</button>
                                        </div>
                                        <div class="pull-right">

                                        </div>
                                    </form>
                                </div>
                                <!-- panel-body -->

                                <table id="collectorDataTable" class="display" cellspacing="0" width="100%">
                                    <thead>
                                    <tr>
                                        <th>名称</th>
                                        <th>爬取类</th>
                                        <th>爬取资源类别</th>
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
    var collectorTable;
    $(document).ready(function() {
        collectorTable = $('#collectorDataTable').DataTable( {
            //"pageLength": 3,
            "ajax": {
                "url": "${base}/paramfix/collectorGridData.do",
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
                { "data": "name","defaultContent":"--"},
                { "data": "crawlerClass","defaultContent":"--"},
                { "data": "resourceType","defaultContent":"--","render": function ( data, type, row, meta ) {
                        if(data == 'EBOOK'){
                            return "图书";
                        }
                        if(data == "JOURNAL"){
                            return "期刊";
                        }
                        if(data == "THESIS"){
                            return "学位论文";
                        }
                    }},
                { "data": "description","defaultContent":"--"},
                { "data": "id","render": function ( data, type, row, meta ) {
                        return operation(data, row, meta.row);
                    }}
            ]
        } );

        //add collector
        $("#addButton").on('click',function(){
            $("#collector-form").data('validator').resetForm();
            $("#collector-form")[0].reset();
            $("#collector-form [name='collector.id']").val("");
            $("#collector-modal .modal-title").text('添加');
        })

        $("#collector-form").validate({
            rules: {
                'collector.name':{
                    maxlength:255,
                    required:true,
                },
                'collector.crawlerClass':{
                    required:true,
                }
            },
            submitHandler:function(form){
                $.ajax({
                    type:'post',
                    url: "${base}/json/collectorSave.do",
                    data: $('#collector-form').serialize(),
                    dataType:'json',
                    success:function(data){
                        if(data.success){
                            $("#collector-modal-cancel").trigger('click');
                            swal('操作成功', '', "success", {button: "${i18n('ok')}"});
                            collectorTable.ajax.reload(null,false);
                        }else{
                            swal('操作失败', data.msg ? data.msg : '' ,"error", {button: "${i18n('ok')}"});
                        }
                    }
                })
            }
        });
    });

    //操作
    function operation(value,row,index) {
        var detailHtml = "<button class='btn btn-default btn-pink sel_bt ' data-toggle='modal' data-target='#collector-modal' onClick='javascript:toEdit("+index+")'>编辑</button>";

        detailHtml += '<div class="btn-group">';
        detailHtml += '<button type="button" style="margin-left:5px;" class="btn btn-success dropdown-toggle waves-effect waves-light" data-toggle="dropdown" aria-expanded="false">操作<span class="caret"></span></button>';
        detailHtml += '<ul class="dropdown-menu" role="menu">';
        detailHtml += "<li><a href='javascript:toDelete("+index+")'>删除</a></li>";
        detailHtml += '</ul></div>';
        return detailHtml;
    }

    //toEdit
    function toEdit(index) {
        var data = collectorTable.row( index ).data();
        $("#collector-form").data('validator').resetForm();
        $("#collector-form")[0].reset();

        $("#collector-form [name='collector.id']").val(data.id);
        $("#collector-form [name='collector.name']").val(data.name);
        $("#collector-form [name='collector.crawlerClass']").val(data.crawlerClass);
        $("#collector-form [name='collector.resourceType']").val(data.resourceType);
        $("#collector-form [name='collector.description']").val(data.description);

        $("#collector-form .modal-title").text('编辑采集器');
    }

    //释放 simcard
    function toDelete(index){
        var data = collectorTable.row( index ).data();
        swal({
            title: "提示",
            text:"确定删除采集器： "+ (data.name == null ? data.name : data.name) +" 吗？",
            icon: "warning",
            buttons: ["${i18n('cancel')}","${i18n('ok')}"],
            dangerMode: true
        }).then((willRelease) => {
            if (willRelease) {
                $.ajax({
                    type:"GET",
                    url:"${base}/json/collectorDelete.do",
                    dataType: "json",
                    data : {
                        'id' : data.id
                    },
                    success:function(data){
                        if (data.success) {
                            swal("提示", "操作成功", "success", {button: "${i18n('ok')}"});
                            collectorTable.ajax.reload(null,false);
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