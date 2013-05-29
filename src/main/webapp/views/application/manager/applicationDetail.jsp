<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://mvc.one.sinosoft.com/tags/pipe" prefix="mvcpipe"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <title>应用透视</title>
    <%@include file="/WEB-INF/layouts/base.jsp"%>
    <script type="text/javascript">
        var applicationId = "${applicationId}";
        function urlDetail(applicationId, urlId) {
            location.href = "${ctx}/application/manager/url/main/" + applicationId + "/" + urlId;
        }
    </script>
    <script language="javascript" src="${ctx}/global/js/mvc-pipe.js"></script>
    <script language="javascript" src="${ctx}/global/js/apmservice/apmservice.js"></script>

</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
    <div class="service_all" id="main">
        <ul class="crumbs">
            <li><a href="${ctx}/application/manager/appmanager/applist/1">应用性能</a> ></li>
            <li><a href="${ctx}/application/manager/appmanager/applist/1">应用列表</a> ></li>
            <li><b>应用透视</b></li>
        </ul>
        <table cellpadding="0" cellspacing="0" width="100%" >
            <tbody>
            <tr>
                <td class="health" id="alarm">

                </td>
                <td class="pie_chart" id="pie">

                </td>
            </tr>
            </tbody>
        </table>
        <div class="grid_info">
            <div class="threshold_file">

                <div class="tool_bar_top">
                    URL地址信息
                </div>
                <div id="grid_info_table"></div>

            </div>
        </div>

    </div>
</div>
</div>
<%@include file="/WEB-INF/layouts/foot.jsp"%>
</body>
</html>
<mvcpipe:writes>
    <mvcpipe:write id = "alarmId" lazyLoad = "false" targetId="alarm" />
    <mvcpipe:write id = "pieId" lazyLoad = "false" targetId="pie" />
</mvcpipe:writes>
