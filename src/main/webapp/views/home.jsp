<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>monitor监控系统</title>
<script language="javascript" src="${pageContext.request.contextPath}/global/js/jquery-1.7.1.js"></script>
<script type="text/javascript">
var opens = false;
$(function(){	
	var pts = $("#parents");
	var left = $("#leftMenu");
	left.hover(function(){
		onOff(pts);
	})
});
function onOff(pts){
	if(opens){
		var sunUl = $(window.frames["leftMenu"].document).find("#leftBar");
		sunUl.find("li.show").removeClass("show select");
		opens = false;		
		pts.attr("cols","42,*");	
		resize();
	}else{
		pts.attr("cols","170,*");
		opens = true;
		resize();
	}
}
function resize(){
	if(navigator.userAgent.indexOf('MSIE 10.0') != -1 || navigator.userAgent.indexOf("MSIE 9.0") > 0 || navigator.userAgent.indexOf('MSIE 8.0') > 0) {
        var w = document.body.clientWidth;
        document.body.style.width = w + 1 + 'px';
        setTimeout(function(){
            document.body.style.width = w - 1 + 'px';
            document.body.style.width = 'auto';
        }, 0);  // 这个延时时间看情况可能需要适当调大
    }
}
</script>
</head>
<frameset id="parents"  cols="42,*" frameborder="0" framespacing="0" border="0">
    <frame id="leftMenu" name="leftMenu" src="${pageContext.request.contextPath}/left" frameborder="no" scrolling="no"/>
    <frame id="rightMain" name="rightMain" src="${pageContext.request.contextPath}/index"/>
</frameset>
<noframes>您的浏览器版本太低,请安装IE5.5或以上版本!</noframes>
</html>
