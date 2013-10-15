<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>查看预警配置文件</title>
<%@include file="/WEB-INF/layouts/base.jsp"%>
<script type="text/javascript">
$(function(){
    getAlarmListOfGivenTimeAndType();
    $("#timeSelect").bind("change",function(){getAlarmListOfGivenTimeAndType(window.severityLevel)});
    $("#typeSelect").bind("change",function(){getAlarmListOfGivenTimeAndType(window.severityLevel)});
});

/*得到指定时间段和指定类型的告警信息列表*/
function getAlarmListOfGivenTimeAndType(severityLevel){
    if(severityLevel)
        window.severityLevel = severityLevel;
    var _givenTime=$("#timeSelect").val();
    var _givenType=$("#typeSelect").val();
    var _url="${ctx}/alarm/manager";
    if(_givenTime){
        _url = _url+"/day/"+_givenTime;
    }
    if(_givenType){
        _url = _url+"/resourceType/"+_givenType;
    }


    var _data = {severityLevel:severityLevel};
    var $mn = $("#thresholdList");
    //防止每次查询时，表格中的数据不断累积
    $mn.html("");
    var grid =  $("#thresholdList").Grid({
        type:"get",
        url : _url,
        dataType: "json",
        data:_data,
        colDisplay: false,
        afterRepage:true,
        clickSelect: true,
        draggable:false,
        height: "auto",
        colums:[
            {id:'1',text:'状态',name:"status",index:'1',align:'',width:'52'},
            {id:'2',text:'消息',name:"message",index:'1',align:''},
            {id:'3',text:'名称',name:"appName",index:'1',align:''},
            {id:'4',text:'类型',name:"monitorType",index:'1',align:''},
            {id:'5',text:'时间',name:"recordTime",index:'1',align:''}/*,
             {id:'6',text:'用户',name:"ownerName",index:'1',align:''}*/
        ],
        rowNum:10,
        pager : true,
        number:false,
        multiselect: true
    }).grid;

//    var rows = grid.checked();
//    var rows = [row,row]
}
function alarmDetailInfo(e){
    var rows = $(e).parent().parent();
    var id = rows.attr('id');
    /*id前面多了“rows”*/
    var _alarmId=id.substr(4,32);
    /*告警详细信息页面*/
    window.location.href="${ctx}/alarm/manager/detail/"+_alarmId;
}

function delRow(e){
	var rows = $(e).parent().parent();
	var id = rows.attr('id');
	msgConfirm('系统消息','确定要删除该条配置文件吗？',function(){
		msgSuccess("系统消息", "操作成功，配置已删除！");
		//alert(id);
		rows.remove();
	});
}
function batchDel(){
	var $g = $("#thresholdList div.grid_view > table");
	var selecteds = $("td.multiple :checked",$g);
	if(selecteds.length > 0){
		msgConfirm('系统消息','确定要删除该条配置文件吗？',function(){
			var _alarmIds = [];
			selecteds.each(function(){
				var rows = $(this).parent().parent();
                /*id前面多了“rows”*/
                _alarmIds.push(rows.attr('id').substr(4,32));
			});
            $.ajax({
                type:"post",
                url:"${ctx}/alarm/manager/alarmmanager/batchdelete/",
                data:{alarmIds:_alarmIds},
                async:false,
                success:function(data){
                    if("successDeleted"==data){
                        /*selecteds.each(function(){
                            $(this).parent().parent().remove();
                        });*/
                        msgSuccess("系统消息", "删除成功！");
                        /*调用ajax，重新获得告警信息列表*/
                        getAlarmListOfGivenTimeAndType();
                    }
                },
                error:function(){
                    msgSuccess("系统消息", "删除失败！");
                }
            });
		});
	}else{
		msgAlert('系统消息','没有选中的文件！<br />请选择要删除的文件后，继续操作。')
	};
}
function viewRelevance(){
	var temWin = $("body").window({
		"id":"window",   
        "title":'根本原因分析',  
		"url":"basicReaon.html",   
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

function alert(){

}
</script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
	<div class="main" id="main">
    	<div class="threshold_file alerts">
       	  <h2 class="title2">
          	<strong class="right">筛选表单：
                <select id="timeSelect" name="timeSelect" class="diySelect">
                    <option value="">选择时间</option>
                    <option value="1">最近24小时</option>
                    <option value="30">最近30天</option>
                </select>
                <select id="typeSelect" name="typeSelect" class="diySelect">
                    <option value="">选择类型</option>
                    <option value="APP_SERVER">TUXEDO</option>
                    <%--<option value="APPLICATION">应用系统</option>--%>
                    <%--<option value="OS">操作系统</option>--%>
                    <%--<option value="DB">数据库</option>--%>
                </select>
            </strong>
          	<b>告警信息列表　</b>
              <a href="javascript:void(0)" onclick="javascript:getAlarmListOfGivenTimeAndType()" class="itemize">全部告警</a>
              <a href="javascript:void(0)" onclick="javascript:getAlarmListOfGivenTimeAndType('CRITICAL')" class="itemize">严重</a>
              <a href="javascript:void(0)" onclick="javascript:getAlarmListOfGivenTimeAndType('WARNING')" class="itemize">警告</a>
              <a href="javascript:void(0)" onclick="javascript:getAlarmListOfGivenTimeAndType('INFO')" class="itemize">正常</a>
          </h2>
          <div class="tool_bar_top"><shiro:hasPermission name="admin"><a href="javascript:void(0);" class="batch_del" onclick="batchDel()">批量删除</a></shiro:hasPermission></div>
          <div id="thresholdList"></div>
        </div>
        <table border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="75" height="30"><img src="${ctx}/global/images/bussinessY2.gif" width="14" height="14" /> 严重告警</td>
                <td width="50"><img src="${ctx}/global/images/bussinessY3.gif" width="12" height="12" /> 告警</td>
                <td width="50"><img src="${ctx}/global/images/bussinessY.gif" width="14" height="13" /> 正常</td>
            </tr>
        </table>
    </div>
</div>

<%@include file="/WEB-INF/layouts/foot.jsp"%>
</body>
</html>
