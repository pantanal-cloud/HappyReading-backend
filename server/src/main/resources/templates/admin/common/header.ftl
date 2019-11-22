<meta charset="utf-8">
<!-- <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width"> -->
<meta name="description" content="">
<meta name="author" content="Coderthemes">
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"> <!--<![endif]-->

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<!--[if lt IE 10 ]>    
<script type="text/javascript">window.location.href="${base}/moltran2/browserDownload.html";</script>
<![endif]-->
    
<link rel="shortcut icon" href="${base}/index/images/logo22.png">

<!--Form Wizard-->
<link rel="stylesheet" type="text/css" href="${base}/moltran2/assets/plugins/jquery.steps/demo/css/jquery.steps.css">
<!--datatables-->
<link href="${base}/js/datatables/datatables.css" rel="stylesheet" type="text/css">
    
<link href="${base}/moltran2/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${base}/moltran2/assets/css/core.css" rel="stylesheet" type="text/css">
<link href="${base}/moltran2/assets/css/icons.css" rel="stylesheet" type="text/css">
<link href="${base}/moltran2/assets/css/components.css" rel="stylesheet" type="text/css">
<link href="${base}/moltran2/assets/css/pages.css" rel="stylesheet" type="text/css">
<link href="${base}/moltran2/assets/css/menu.css" rel="stylesheet" type="text/css">
<link href="${base}/moltran2/assets/css/responsive.css" rel="stylesheet" type="text/css">
<link href="${base}/moltran2/assets/plugins/toggles/toggles.css" rel="stylesheet" type="text/css">
<link href="${base}/moltran2/assets/plugins/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css" rel="stylesheet">
<link href="${base}/js/jquery-overhang/css/overhang.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${base}/js/jquery-ui/jquery-ui.css">

<script src="${base}/moltran2/assets/js/modernizr.min.js"></script>
<script src="${base}/js/jquery-1.11.3.min.js"></script>
<script src="${base}/js/jquery-ui/jquery-ui.js"></script>
<script src="${base}/js/jquery-overhang/overhang.js"></script>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
<![endif]-->

<#setting number_format="0.####">
<#if (locale_lang!'zh_CN') == 'en'>
    <#setting date_format="MM/dd/yyyy">
    <#setting time_format="HH:mm:ss">
    <#setting datetime_format="MM/dd/yyyy HH:mm:ss"> 
<#else> 
    <#setting date_format="yyyy-MM-dd"> 
    <#setting time_format="HH:mm:ss"> 
    <#setting datetime_format="yyyy-MM-dd HH:mm:ss"> 
</#if>
<style type="text/css">
   html,body{
   width: 100%;
   height: 100%;
   }
	table.dataTable thead th, table.dataTable thead td {
	    padding: 10px 10px;
	}
	
    .datagrid-cell{
        overflow: visible;
    }
    .datagrid-btable tr{
        height:45px;
    }
    .imtop{
        top: -300px !important;
    }
    .tag-item{
		border: 1px solid #337ab76b !important;
	}
	.tag-checked{
		border: 1px solid #337ab76b !important;
	}
</style>

<script type="text/javascript">
  var topWin = window;
  while(topWin.parent != topWin){
      topWin = topWin.parent;
  }
</script> 