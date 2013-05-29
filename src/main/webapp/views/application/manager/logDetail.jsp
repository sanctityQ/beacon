<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://mvc.one.sinosoft.com/tags/pipe" prefix="mvcpipe"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>monitor监控系统</title>
    <%@ include file="/WEB-INF/layouts/base.jsp"%>
    <link href="${ctx}/global/css/status.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/global/css/logDetail/logDetail.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
        var applicationId = "${applicationId}";
        var urlId = "${urlId}";
        var logId = "${logId}";
        var alarmId = "${alarmId}";
        var alarmDetailId = "${alarmDetailId}";
        var existLogId = "${existLogId}";
    </script>
    <script language="javascript" src="${ctx}/global/js/application/manager/logDetail.js"></script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
    <div class="log_detail" >
        <ul class="crumbs">
            <li><a href="${ctx}/application/manager/appmanager/applist/1">应用性能</a> ></li>
            <li><a href="${ctx}/application/manager/appmanager/applist/1">应用列表</a> ></li>
            <li><a href="${ctx}/application/manager/detail/main/${applicationId}">应用透视</a> ></li>
            <li><a href="${ctx}/application/manager/url/main/${applicationId}/${urlId}">URL透视</a> ></li>
            <li><b>事件日志透视</b></li>
        </ul>
        <div style="width:49%;float:left;">
            <div id="log_detail_grid" >
                <div class="threshold_file">
                    <div class="tool_bar_top">日志操作详细</div>
                    <table id="logDetail" class="log_detail_table" width="100%" cellpadding="0" cellspacing="0">
                    </table>
                </div>
            </div>
            <%--<div class="stuats_mark">
                <ul>
                    <li><div class="red_status"></div><div>严重</div></li>
                    <li><div class="yellow_status"></div><div>警告</div></li>
                    <li><div class="green_status"></div><div>正常</div></li>
                </ul>
            </div>--%>
        </div>
        <div style="width:49%;float:left; margin-left:5px;">
            <div class="threshold_file">
                <div class="tool_bar_top"> 参数信息</div>
                <div id="detail_grid"></div>
            </div>
        </div>
        <div style="float:left;width:100%;margin-top:20px;">
            <div class="conf_title">
                <div class="conf_title_r"></div>
                <div class="conf_title_l"></div>
                异常信息
            </div>
            <div class="conf_cont_box">
                <div class="conf_cont">
                    <ul>
                        <li id="exceptionInfo"></li>
                    </ul>

                </div>
            </div>
        </div>
        <div style="float:left;width:100%;margin-top:20px;">
            <div class="conf_title">
                <div class="conf_title_r"></div>
                <div class="conf_title_l"></div>
                告警信息
            </div>
            <div class="conf_cont_box">
                <div class="conf_cont">
                    <ul>
                        <li id="alarmInfo"></li>
                    </ul>

                </div>
            </div>
        </div>
    </div>

</div>
</div>
<%@include file="/WEB-INF/layouts/foot.jsp"%>
</body>
</html>