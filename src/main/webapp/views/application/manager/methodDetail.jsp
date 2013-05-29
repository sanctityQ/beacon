<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://mvc.one.sinosoft.com/tags/pipe" prefix="mvcpipe"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>monitor监控系统</title>
    <%@include file="/WEB-INF/layouts/base.jsp"%>
    <script type="text/javascript">
        var methodId = "${methodId}";
    </script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
    <div class="main">
        <ul class="crumbs">
            <li><a href="${ctx}/application/manager/appmanager/applist/1">应用性能</a> ></li>
            <li><a href="${ctx}/application/manager/appmanager/applist/1">应用列表</a> ></li>
            <li><a href="${ctx}/application/manager/detail/main/${applicationId}">应用透视</a> ></li>
            <li><a href="${ctx}/application/manager/url/main/${applicationId}/${urlId}">URL透视</a> ></li>
            <li><a href="${ctx}/application/manager/appmethod/viewLogDetail/${applicationId}/${urlId}/${logId}">事件日志透视</a> ></li>
            <li><b>方法透视</b></li>
        </ul>

        <div class="conf_box">
            <div class="conf_title">
                <div class="conf_title_r"></div>
                <div class="conf_title_l"></div>
                方法参数
            </div>
            <div class="conf_cont_box">
                <div class="conf_cont">
                    <ul>
                        <li><b>方法参数：</b>${inParam}</li>
                    </ul>
                </div>
            </div>
        </div>
        <br/>
        <div class="conf_title">
            <div class="conf_title_r"></div>
            <div class="conf_title_l"></div>
            返回值
        </div>
        <div class="conf_cont_box">
            <div class="conf_cont">
                <ul>
                    <li><b> 返回值：</b> ${outParam}</li>
                </ul>

            </div>
        </div>
</div>
</div>
<%@include file="/WEB-INF/layouts/foot.jsp"%>
</body>
</html>