<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>查看预警配置文件</title>
    <%@include file="/WEB-INF/layouts/base.jsp" %>
    <script type="text/javascript">
        $(function () {
            $("#thresholdList").Grid({
                type: "post",
                url: "${ctx}/action/email/data",
                dataType: "json",
                colDisplay: false,
                clickSelect: true,
                draggable: false,
                colums: [
                    {id: '1', text: '名称', name: "name", index: '1', align: ''},
                    {id: '2', text: '发件人', name: "fromAddress", index: '1', align: ''},
                    {id: '3', text: '到', name: "toAddress", index: '1', align: ''},
                    {id: '4', text: '主题', name: "subject", index: '1', align: ''},
                    {id: '5', text: '附加消息', name: "content", index: '1', align: ''},
                    {id: '6', text: '操作', name: "operation", index: '1', align: ''}

                ],
                rowNum: 9999,
                pager: false,
                number: false,
                multiselect: false
            });
        });

        function updRow(e) {
            var rows = $(e).parent().parent();
            var id = rows.attr('id');
            location.href = "${ctx}/action/email/update/" + id.replace("row_", "");
        }

        function delRow(e) {
            var rows = $(e).parent().parent();
            var id = rows.attr('id');
            rows.remove();
            delEmail("${ctx}/action/email/delete/" + id.replace("row_", ""));
        }

        function delEmail(url) {
            $.ajax({
                type: "post",
                url: url,
                dataType: "json",
                success: function (data) {
                    msgSuccess("系统消息", "操作成功，配置已删除！");
                },
                error: function (data) {
                    msgSuccess("系统消息", "操作失败，配置未被删除！");
                    location.href = "${ctx}/action/email/list";
                }
            });
        }
        function batchDel() {
            var $g = $("#thresholdList div.grid_view > table");
            var selecteds = $("td.multiple :checked", $g);
            if (selecteds.length > 0) {
                msgConfirm('系统消息', '确定要删除选中的邮件动作吗？', function () {
                    var checks = [];
                    selecteds.each(function () {
                        var rows = $(this).parent().parent();
                        checks.push(rows.attr('id').replace("row_", ""));
                        rows.remove();
                    });
                    var delUrl = "${ctx}/action/email/batchDelete/" + checks;
                    delEmail(delUrl);
                });
            } else {
                msgAlert('系统消息', '没有选中的文件！<br />请选择要删除的文件后，继续操作。')
            }
            ;
        }
        function eidRow(e) {
            var rows = $(e).parent().parent();
            var id = rows.attr('id');
            var name = rows.children("td").eq(1).text();
            var title = "邮件动作: " + name;
            var temWin = $("body").window({
                "id": "window",
                "title": title,
                "url": "thresholdEdit.html",
                "hasIFrame": true,
                "height": 260,
                "diyButton": [
                    {
                        "id": "btOne",
                        "btClass": "define",
                        "value": "确定",
                        "onclickEvent": "selectLear",
                        "btFun": function () {
                            var selectTreeCheck = $("#window_iframe").contents().find("#checkObj").val();
                            msgSuccess("", selectTreeCheck);
                            temWin.closeWin();
                        }
                    },
                    {
                        "id": "btTwo",
                        "btClass": "def_btn",
                        "value": "取消",
                        "btFun": function () {
                            temWin.closeWin();
                        }
                    }
                ]
            });
        }
    </script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp" %>
<div id="layout_center">
    <div class="main" id="main" style="padding: 10px">
        <div class="threshold_filed">
            <h2 class="title3"><b>邮件动作　</b></h2>

            <div id="thresholdList"></div>
            <div class="tool_bar"></div>
        </div>
    </div>
    <%--<div class="main">--%>
    <%--<div class="threshold_filed">--%>
    <%--<h2 class="title3"><b>短信动作　</b></h2>--%>
    <%--<div id="thresholdList2"></div>--%>
    <%--<div class="tool_bar"></div>--%>
    <%--</div>--%>
    <%--</div>--%>
</div>
<%@include file="/WEB-INF/layouts/foot.jsp" %>
</body>
</html>
