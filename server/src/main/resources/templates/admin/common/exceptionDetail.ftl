<!DOCTYPE HTML>
<html>
<head>
<title>${i18n("errorPage.title")}</title>
<script type="text/javascript">
    var showTxt = '${i18n("button.showDetails")}';
    var hideTxt = '${i18n("button.hideDetails")}';

    function toggleBtnDiv(/*String*/btnId, /*String*/divId) {
        var eDiv = document.getElementById(divId);
        var eBtn = document.getElementById(btnId);
        
        if (eDiv.style.display == "none") {
            eDiv.style.display = "block";
            eBtn.innerHTML = hideTxt;
        } else {
            eDiv.style.display = "none";
            eBtn.innerHTML = showTxt;
        }
    }
</script>
</head>
<body>
	<!-- This page is displayed when an exception occurs in an Action
  -- class that doesn't handle it, leaving it to bubble up to the
  -- BaseAction class.
  -->

	<h3>${i18n("errorPage.exceptionInAction.descr")}</h3>
	
	<!-- ${i18n("errorPage.contactSupport")} &nbsp;&nbsp; -->
	
	<button id="btnDetails"
		onclick="javascript:toggleBtnDiv('btnDetails', 'errorDetails');">
		${i18n("button.showDetails")}
    </button>
	<br>
	<br>
	<br>
	<div id="errorDetails" style="display: none;">
	   ${exceptionDetails!}
	</div>
</body>
</html>