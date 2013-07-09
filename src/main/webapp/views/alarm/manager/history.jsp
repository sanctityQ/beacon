<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>历史告警信息</title>
    <%@include file="/WEB-INF/layouts/base.jsp" %>
    <script type="text/javascript">
        $(function () {
            $("#thresholdList").Grid({
                type: "post",
                url: "${ctx}/alarm/manager/history/${monitorId}",
                dataType: "json",
                colDisplay: false,
                afterRepage: true,
                clickSelect: true,
                draggable: false,
                height: "auto",
                colums: [
                    {id: '1', text: '状态', name: "status", index: '1', align: ''},
                    {id: '2', text: '时间', name: "recordTime", index: '1', align: ''},
                    {id: '3', text: '消息', name: "message", index: '1', align: ''}
                ],
                rowNum: 10,
                pager: true,
                number: false,
                multiselect: false
            });


        });
        function viewLogDetail(applicationId, urlId, traceId) {
            if (traceId == "notExist") {
                traceId = -1;
                window.location.href = "${ctx}/application/manager/appmethod/viewLogDetail/" + applicationId + "/" + urlId + "/" + traceId + "?alarmDetailId=${alarm.id}";
            } else {
                window.location.href = "${ctx}/application/manager/appmethod/viewLogDetail/" + applicationId + "/" + urlId + "/" + traceId;
            }
        }
        function navHover() {
            $(this).toggleClass("hover")
        }
        function navClick() {
            $(this).addClass("seleck").siblings().removeClass("seleck");
            if ($(this).hasClass('has_sub')) {
                var subMav = $(this).children("ul.add_sub_menu");
                var isAdd = false;
                if ($(this).parent().attr("id") == "menu") {
                    isAdd = true;
                }
                subMav.slideDown('fast', function () {
                    $(document).bind('click', {dom: subMav, add: isAdd}, hideNav);
                    return false;
                });
            }
        }
        function hideNav(e) {
            var subMenu = e.data.dom;
            var isAdd = e.data.add;
            subMenu.slideUp('fast', function () {
                if (isAdd) {
                    subMenu.parent().removeClass('seleck');
                }
            });
            $(document).unbind();
        }
    </script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp" %>
<div id="layout_center">
    <div class="main" id="main">
        <div class="add_monitor alertDef">
            <h2 class="title2"><strong class="right" onclick="window.history.back()">
                <a href="javascript:void(0);" onclick="window.history.back()">返回</a></strong><b>历史告警信息(${monitorName})</b>
            </h2>

            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="my_table" id="threshold">
                <tr>
                    <td width="20%"><strong>监视器名称</strong></td>
                    <td width="8">${monitorName}</td>
                </tr>
            </table>
        </div>
        <br/>

        <div class="threshold_file">
            <div id="thresholdList"></div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/layouts/foot.jsp" %>
</body>
</html>