<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>报表</title>
    <%@include file="/WEB-INF/layouts/base.jsp" %>
    <script type="text/javascript">
        $(function(){
         //   $("#reporMenu li").on('click',reporMenu);

            $.ajax({
                type:"post",
                url:"${ctx}/alarm/manager/configemergency/monitornames/Tuxedo",
                dataType:"json",
                async:false,
                success:function(data){
                    var $mn = $("#monitorName");
                    //防止每次查询时，表格中的数据不断累积
                    $mn.html("");
                    $("#monitorName").append("<option value='' >" +"--选择一个监视器--"+" </option> ");

                    for(var i = 0; i<data.length;i++){
                        var _key = data[i].monitorId;
                        var _name =data[i].monitorName;
                        $("#monitorName").append("<option value='"+_key+"' > "+_name+" </option> ");
                    }
                    //$(".conf_box").slideDown("fast");
                }
            });
        });
        function reporMenu(){
            var index = $(this).index();
            var contant = $("#contant > div.reporCont");
            $(this).addClass("select").siblings().removeClass("select");
            contant.eq(index).slideDown(200)
                    .siblings().slideUp(200);
        }
        function viewWindow(attribute,attribute_name){

            if($("#monitorName").val()==''){
                return;
            }
            var url = "${ctx}/report/resourceType/Tuxedo/attribute/"+attribute+"?dateSeries=today&resourceId="+$("#monitorName").val();
            if(attribute == 'PROGRESS_CPU_USED'||attribute=='PROGRESS_MEM_USED'){
                url = "${ctx}/report/resourceType/Tuxedo/attribute/"+attribute+"/top?top=five&dateSeries=today&resourceId="+$("#monitorName").val();
            }

            var temWin = $("body").window({
                "id":"window_"+attribute,
                "title":attribute_name,
                "url":encodeURI(url),
                "hasIFrame":true,
                "width": 840,
                "height":540,
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
    </script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
    <div class="main">
        <div class="repor_view">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td class="repor_box">
                        <div class="report_title">报表</div>
                        <ul id="reporMenu" class="report_menu">
                            <%--<li><img src="${ctx}/global/images/icon_hyper_vhost.gif" width="15" height="16" />Linux</li>--%>
                            <li class="center select"><img src="${ctx}/global/images/icon_monitors_cam.gif" width="16" height="16" />Tuxedo</li>
                        </ul>
                    </td>
                    <td>
                        <div id="contant">
                            <%--<div class="reporCont">--%>
                                <%--<p class="hint">此功能暂未开放。</p>--%>
                            <%--</div>--%>

                            <div class="reporCont" style="display:block">
                                <div class="selec_list">
                                    选择服务器
                                    <select id="monitorName" class="diySelect" style="width:150px">
                                    </select>
                                </div>
                                <table width="100%" border="0" cellspacing="0" cellpadding="0"  class="report_list">
                                    <c:forEach var="attribute" items="${attributes}" varStatus="status">
                                    <c:if test="${status.count%2 == 1}">
                                        <c:set var="trFlag" value="0"></c:set>
                                        <tr>
                                    </c:if>
                                        <td width="50%">
                                            <h4 class="title4"><a href="javascript:void(0);" onclick="viewWindow('${attribute.attribute}','${attribute.attributeCn}');">
                                            ${attribute.attributeCn}</a></h4>
                                        </td>
                                        <c:if test="${status.count%2 == 0}">
                                            <c:set var="trFlag" value="1"></c:set>
                                           </tr>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${trFlag == 0}">
                                        <td></td></tr>
                                    </c:if>
                                </table>

                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/layouts/foot.jsp" %>
</body>
</html>
