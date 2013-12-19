<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${pageContext.request.contextPath}/global/css/base.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/global/css/left.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="${pageContext.request.contextPath}/global/js/jquery-1.7.1.js"></script>
<script>
$(function(){
	var bar = $("#leftBar");
	var li = bar.children("li");
	var subLi = bar.find("ul.sub > li");
	
	li.click(function(){
		$(this).addClass("show select")
			.siblings().removeClass("show select");
	});
	
	subLi.click(function(){
		$("ul.sub > li.selected").removeClass("selected");
		$(this).addClass("selected")
	})
})
</script>
</head>

<body>

<ul class="left_menu" id="leftBar">
	<li><a href="${pageContext.request.contextPath}/addmonitor/list" target="rightMain"><i class="icon01"></i>新建监视器</a></li>
    <li class="has"><a href="javascript:void(0);"><i class="icon02"></i>阈值配置文件</a>
    	<ul class="sub">
        	<li><a href="${pageContext.request.contextPath}/threshold/create" target="rightMain">新建阈值文件</a></li>
            <li><a href="${pageContext.request.contextPath}/threshold/list" target="rightMain">查看阈值配置文件</a></li>
        </ul>
    </li>
    <%--<li><a href="deployMonitor.html" target="rightMain"><i class="icon03"></i>配置监视器</a></li>--%>
    <li class="has"><a href="javascript:void(0);"><i class="icon04"></i>动作</a>
    	<ul class="sub">
        	<li><a href="${pageContext.request.contextPath}/action/email/list" target="rightMain">显示动作</a></li>
        	<%--<li><a href="message.html" target="rightMain">短信动作</a></li>--%>
            <shiro:hasPermission name="admin">
            <li><a href="${pageContext.request.contextPath}/action/email/create" target="rightMain">邮件动作</a></li>
            </shiro:hasPermission>
        </ul>
    </li>
    <shiro:hasPermission name="admin">
        <li><a href="${pageContext.request.contextPath}/alarm/manager/configemergency/config" target="rightMain"><i class="icon05"></i>配置告警</a></li>
    </shiro:hasPermission>
</ul>
</body>
</html>
