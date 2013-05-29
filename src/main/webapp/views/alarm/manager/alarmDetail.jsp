<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>告警明细</title>
    <%@include file="/WEB-INF/layouts/base.jsp" %>
    <script type="text/javascript">
        $(function () {
            $("#thresholdList").Grid({
                type: "post",
                url: "${ctx}/alarm/manager/alarmmanager/history/${monitorId}",
                dataType: "json",
                colDisplay: false,
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
            if(traceId=="notExist"){
                traceId=-1;
                window.location.href = "${ctx}/application/manager/appmethod/viewLogDetail/" + applicationId + "/" + urlId + "/" + traceId+"?alarmDetailId=${alarm.id}";
            }else{
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
<div id="layout_top">
    <div class="header">
        <%@include file="/WEB-INF/layouts/menu.jsp" %>
    </div>
</div>
<div id="layout_center">
    <div class="main" id="main">
        <div class="add_monitor alertDef">
            <h2 class="title2"><strong class="right" onclick="window.history.back()"><a href="javascript:void(0);"
                                                                                        onclick="${ctx}/alarm/manager/alarmmanager/list">返回告警</a></strong><b>告警明细　</b>
            </h2>

            <form>
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="my_table" id="threshold">
                    <tr>
                        <td width="20%"><strong>监视器名称</strong></td>
                        <td width="8">${monitorName}</td>
                    </tr>
                    <tr>
                        <td><strong>属性</strong></td>
                        <td>${_cnName}</td>
                    </tr>
                    <tr>
                        <td>创建时间</td>
                        <td><fmt:formatDate value="${alarm.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                    <tr>
                        <td>健康状态</td>
                        <td><img src="${ctx}/global/images/${alarmImage}" width="14" height="13"/> ${_cnName}</td>
                    </tr>
                    <tr>
                        <td>告警信息</td>
                        <td>
                            <p class="magess">
                                <c:if test="${monitorType eq 'APPLICATION'}">
                                    <a  href="javascript:void(0);"  onclick="viewLogDetail('${monitorId}','${urlTraceLogUrlId}','${urlTraceLogId}');"> ${alarm.message}</a>
                                </c:if>
                                <c:if test="${monitorType ne 'APPLICATION'}">
                                     ${alarm.message}
                                </c:if>
                        </p></td>
                    </tr>
                </table>
            </form>
        </div>
        <br/>

        <div class="threshold_file">
            <h3 class="title3">历史告警信息：</h3>

            <div id="thresholdList"></div>
        </div>
        <div class="tool_bar big">
            <input type="button" class="buttons" value="返回列表" onclick="window.history.back()"/>
        </div>
    </div>
</div>
<div id="layout_bottom">
    <p class="footer">Copyright &copy; 2013 Sinosoft Co.,Lt</p>
</div>
</body>
</html>