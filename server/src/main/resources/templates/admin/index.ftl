
<!DOCTYPE html>
<html>
    <head>
<#include "*/admin/common/header.ftl">
        <title>江苏畅想数据采集后台</title>
        
        <style>
	      a.active{
	        background-color: transparent;
	      }
	    </style>
    </head>

    <body class="fixed-left" >
        
        <!-- Begin page -->
        <div id="wrapper">
        
            <!-- Top Bar Start -->
            <div class="topbar" id="topnav">
                <!-- LOGO -->
                
                
                <div class="topbar-left" style="background-color: rgb(235,239,240);">
                    <div class="text-center">
                        <a href="${base}/admin/index.do" class="logo">
                        <span style="color:RGB(157,159,159);">
				            江苏畅想数据采集后台
				         </span>
				       </a>
                    </div>
                </div>
                
                
                <!-- Button mobile view to collapse sidebar menu -->
                <div class="navbar navbar-default" role="navigation"  style="background-color: rgb(254,254,254);">
                    <div class="container">
                        <div class="">
                            <div class="pull-left">
                                <button type="button" class="button-menu-mobile open-left">
                                    <i class="fa fa-bars" style="color:black;"></i>
                                </button>
                                <span class="clearfix"></span>
                            </div>
                            <!--  
                            <form class="navbar-form pull-left" role="search">
                                <div class="form-group">
                                    <input type="text" class="form-control search-bar" placeholder="Type here for search...">
                                </div>
                                <button type="submit" class="btn btn-search"><i class="fa fa-search"></i></button>
                            </form>
                             -->
                            <ul class="nav navbar-nav navbar-right pull-right">
                                <li class="hidden-xs" >
                                    <a href="javascript:void(0);" id="btn-fullscreen" class="waves-effect waves-light"><i class="md md-crop-free"  style="color:black;"></i></a>
                                </li>
                                <!--
                                <li class="dropdown">
                                    <a href="" class="dropdown-toggle profile" data-toggle="dropdown" aria-expanded="true"><img src="${base}/moltran2/assets/images/users/avatar-1.jpg" alt="user-img" class="img-circle"> </a>
                                    <ul class="dropdown-menu">
                                        <li><a target="rightContent" href="${base}/admin/userUpdatePwd.do"><i class="md md-face-unlock"></i>${i18n('modify-password')}</a></li>
			                            <li><a href="${base}/admin/userLogin.do"><i class="md md-settings-power"></i>${i18n('exit')}</a></li>
                                    </ul>
                                </li>
                                -->
                            </ul>
                        </div>
                        <!--/.nav-collapse -->
                    </div>
                </div>
            </div>
            <!-- Top Bar End -->


            <!-- ========== Left Sidebar Start ========== -->

            <div class="left side-menu" id="leftnav">
                <div class="sidebar-inner slimscrollleft">
                    <div class="user-details">
                        <div class="pull-left">
                            <img src="${base}/moltran2/assets/images/users/avatar-1.jpg" alt="" class="thumb-md img-circle">
                        </div>
                        <!--
                        <div class="user-info">
                            <div class="dropdown">
                                <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a target="rightContent" href="${base}/admin/userUpdatePwd.do"><i class="md md-face-unlock"></i>${i18n('modify-password')}</a></li>
		                            <li><a href="${base}/admin/userLogin.do"><i class="md md-settings-power"></i>${i18n('exit')}</a></li>
                                </ul>
                            </div>
                            <p class="text-muted m-0"></p>
                        </div>
                        -->
                    </div>
                    <!--- Divider -->
                    <div id="sidebar-menu">
                        <ul>
                            <li class="has_sub"><a href="javascript:void(0);" class="waves-effect"><i class="ion-folder"></i><span>管理列表 </span><span class="pull-right"><i class="md md-add"></i></span></a>
                                <ul>
                                    <li><a href="${base}/admin/taskIndex.do" target="rightContent" class=""><i class="ion-navicon-round"></i><span>任务列表</span></a></li>
                                    <li><a href="${base}/admin/taskGroupIndex.do" target="rightContent" class=""><i class="ion-navicon-round"></i><span>资源列表</span></a></li>
                                    <li><a href="${base}/admin/clientIndex.do" target="rightContent" class=""><i class="ion-navicon-round"></i><span>客户端列表</span></a></li>
                                    <li><a href="${base}/admin/collectorIndex.do" target="rightContent" class=""><i class="ion-navicon-round"></i><span>采集器列表</span></a></li>
                                    <li><a href="${base}/admin/sysconfigIndex.do" target="rightContent" class=""><i class="ion-navicon-round"></i><span>系统配置</span></a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
            <!-- Left Sidebar End --> 



            <!-- ============================================================== -->
            <!-- Start right Content here -->
            <!-- ============================================================== -->                      
            <div class="content-page" style="height: 100%;">
                <!-- Start content -->
                <div class="content" style="height: 100%;padding: 0px;">
                    <div class="container" style="height: 100%;margin: 0px;padding: 0px;">

                        <!-- Page-Title -->
                        <div class="row" style="height: 100%;">
                            <div class="all_wrap col-sm-12" style="height: 100%;">
                                <iframe name="rightContent" id="rightContent"
		                         style="width: 100%;height: 100%;padding: 0px;margin: 0px;" scrolling="auto" frameborder="0"></iframe>
                            </div>
                        </div>
                    </div> <!-- container -->
                               
                </div> <!-- content -->

            </div>
            <!-- ============================================================== -->
            <!-- End Right content here -->
            <!-- ============================================================== -->
        </div>
       
<#include "*/admin/common/footer.ftl">
        
        <script type="text/javascript">
        $(function(){
            $('#sidebar-menu li a').click(function(){
                $('li a.active').css('color','');
                $('li a.active').removeClass('active');
                $(this).addClass('active');
                $('li a.active').css('color', 'rgb(47,155,201)');
            })
	    });
	  </script>
    </body>
</html>