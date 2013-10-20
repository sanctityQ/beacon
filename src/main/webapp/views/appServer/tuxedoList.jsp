<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="msg" uri="http://mvc.one.sinosoft.com/validation/msg" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Tuxedo - 批量配置视图</title>

    <%@include file="/WEB-INF/layouts/base.jsp" %>
    <script type="text/javascript" src="${ctx}/global/js/jquery.form.js"></script>
    <script type="text/javascript">
        var grid;
        $(function(){
                grid = $("#thresholdList").Grid({
                url : "${ctx}/appServer/tuxedo/manager/list",
                dataType: "json",
                isAsync:false,
                colDisplay: false,
                clickSelect: false,
                draggable:false,
                colums:[
                    {id:'1',text:'站点名称',name:"siteName",index:'1',align:''},
                    {id:'2',text:'IP地址',name:"siteIp",index:'1',align:''},
                    {id:'3',text:'端口',name:"sitePort",index:'1',align:''},
                    {id:'4',text:'轮询间隔',name:"interval",index:'1',align:''}<shiro:hasPermission name="admin">,
                    {id:'5',text:'操作',name:"operation",index:'1',align:''}
                    </shiro:hasPermission>
                ],
                pager : false,
                number: false,
                multiselect: true
            });
            $('#g_count').html(grid.grid.getDataTotalCount());
        });
        function delRow(e){
            var row = $(e).parent().parent();
            var id = row.attr('id');
            msgConfirm('系统消息','确定要删除该条配置文件吗？',function(){
                delServer(id.substring(4),row);
            });
        }
        function updRow(e){
            var rows = $(e).parent().parent();
            var id = rows.attr('id');
            location.href="${ctx}/appServer/tuxedo/manager/view/"+id.substring(4);
        }
        function batchDel(){
            var $g = $("#thresholdList div.grid_view > table");
            var selecteds = $("td.multiple :checked",$g);
            if(selecteds.length > 0){
                msgConfirm('系统消息','确定要删除该条配置文件吗？',function(){
                    var checks = [];
                    selecteds.each(function(){
                        var rows = $(this).parent().parent();
                        checks.push(rows.attr('id'));
                        rows.remove();
                    });
                    alert(checks);
                    msgSuccess("系统消息", "操作成功，配置已删除！");
                });
            }else{
                msgAlert('系统消息','没有选中的文件！<br />请选择要删除的文件后，继续操作。')
            }
        }
        function viewRelevance(e){
            var rows = $(e).parent().parent();
            var id = rows.attr('id');
            var name = rows.children("td").eq(1).text();
            var title = "监视器使用阈值: " + name;
            var temWin = $("body").window({
                "id":"window",
                "title":title,
                "url":"thresholdEdit.html",
                "hasIFrame":true,
                "width": 740,
                "height":440,
                "diyButton":[{
                    "id": "btOne",
                    "btClass": "buttons",
                    "value": "关闭",
                    "onclickEvent" : "selectLear",
                    "btFun": function() {
                        temWin.closeWin();
                    }
                }
                ]
            });
        }

        function delServer(serverName,row){
            $.ajax({
                type : "delete",
                url : "${ctx}/appServer/tuxedo/delete/"+serverName,
                dataType : "text",
                success : function(data) {
                    msgSuccess("系统消息", "操作成功，已删除！");
                    grid.grid.reload();
                    $('#g_count').html(grid.grid.getDataTotalCount());
                },
                error:function(){
                    msgFailed("系统消息", "操作失败，未被删除！");
                }
            });
        }

        function setTuxMergency(e){
            var rows = $(e).parent().parent();
            var id = rows.attr('id');
            var temWin = $("body").window({
                "id":"window",
                "title":'数据保存设置',
                "url":"${ctx}/appServer/tuxedo/setting/"+id.substring(4),
                "hasIFrame":true,
                "width": 740,
                "height":240,
                "diyButton":[{
                    "id": "btOne",
                    "btClass": "buttons",
                    "value": "保存",
                    "onclickEvent" : "selectLear",
                    "btFun": function() {
                        var form = $("#window_iframe").contents().find("#settingForm");
                        form.ajaxForm(function(data) {
                            if(data=='success'){
                                msgSuccess("系统消息", "操作成功，配置已保存");
                            }else{
                                msgFailed("系统消息","操作失败");
                            }
                        }).submit();
                        temWin.closeWin();
                    }
                },
                    {
                        "id": "btOne",
                        "btClass": "buttons",
                        "value": "关闭",
                        "onclickEvent" : "selectLear",
                        "btFun": function() {
                            temWin.closeWin();
                        }
                    }
                ]
            });
        };
    </script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
    <div class="main">
        <ul class="crumbs">
            <li><a href="#">监视器</a> &gt;</li>
            <li><b> Tuxedo - 批量配置视图 (总计<span id='g_count'></span>监视器)</b></li>
        </ul>
        <div class="threshold_file">
            <h2 class="title2"><b>Tuxedo列表视图</b></h2>
            <div class="tool_bar_top">
               <shiro:hasPermission name="admin">
                <a href="javascript:void(0);" class="batch_del" onclick="batchDel()">批量删除</a>
                <a href="${ctx}/addmonitor/server/tuxedo" class="add">新建Tuxedo</a>
               </shiro:hasPermission>
            </div>
            <div id="thresholdList"></div>
            <div class="tool_bar"></div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/layouts/foot.jsp" %>
</body>
</html>
