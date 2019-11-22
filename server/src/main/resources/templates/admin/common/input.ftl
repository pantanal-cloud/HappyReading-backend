<!DOCTYPE html>
<html>
  <head>
        <meta charset="utf-8" />
        <title>Input Error</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
        <meta content="A fully featured admin theme which can be used to build CRM, CMS, etc." name="description" />
        <meta content="Coderthemes" name="author" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />

</head>

<body >
    <span style="font-size:18px;">
    <#if actionErrors?size gt 0 || actionMessages?size gt 0 || fieldErrors?size gt 0>    
	  <center>    
		  
		<div id="actionMessages" class="hightLightDiv">    
		  
		 <ul>    
		   <#list actionErrors as error>    
		  
		    <li>${error?default("你请求的页面出错了！")?html?replace("\r\n","<br>")}</li>    
		  
		  </#list>  
		  
		  
		   <#list actionMessages as message>    
		  
		    <li>${message?default("你请求的页面出错了！")?html?replace("\r\n","<br>")}</li>    
		  
		  </#list>    
		  
		 <#list fieldErrors?keys as field>    
		  
		  <li>${field}:${fieldErrors[field]?default("你请求的页面出错了！")?html?replace("\r\n","<br>")}</li>    
		  
		    </#list>    
		  
		    </ul>    
		  
		</div>    
		  
		</center>    
	  
	</#if>    
	</span>  

</body>
</html>
