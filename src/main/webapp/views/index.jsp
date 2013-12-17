<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/WEB-INF/layouts/base.jsp" %>
<title>Beacon监控预警平台</title>

<script type="text/javascript">
var columStyle1 =
	[  
		{id:'1',text:'名称',name:"appellation",index:'1',align:''},
		{id:'2',text:'可用性',name:"appellation",index:'1',align:''},
		{id:'3',text:'健康状态',name:"appellation",index:'1',align:''}
	];
var columStyle2 = 
	[  
		{id:'1',text:'状态',name:"appellation",index:'1',align:'',width:'52'},
		{id:'2',text:'消息',name:"appellation",index:'1',align:''},
		{id:'3',text:'名称',name:"appellation",index:'1',align:''},
		{id:'4',text:'类型',name:"appellation",index:'1',align:''},
		{id:'5',text:'时间',name:"appellation",index:'1',align:''}
	];

var columnStyle3 =
        [
            {id:'1',text:'名称',name:"name",index:'1',align:''},
            {id:'2',text:'可用性',name:"availability",index:'1',align:''},
            {id:'3',text:'吞吐量',name:"rqdone",index:'1',align:''}
        ];

$(function(){

    //thresholdList emergencyList systemList oracleList
	var gridList = new Array();
	
	//gridList.push({"renderId":"thresholdList","url":rootPath+"/applicationList?time=" + new Date().getTime(), "columStyle":columStyle1});
	gridList.push({"renderId":"emergencyList","url":rootPath+"/alarmList?time=" + new Date().getTime(), "columStyle":columStyle2});
	//gridList.push({"renderId":"systemList","url":rootPath+"/os/systemList?time=" + new Date().getTime(), "columStyle":columStyle1});
	//gridList.push({"renderId":"oracleList","url":rootPath+"/db/oracle/thresholdList?time=" + new Date().getTime(), "columStyle":columStyle1});
    gridList.push({"renderId":"weblogicList","url":rootPath+"/appServer/weblogic/indexList?time=" + new Date().getTime(), "columStyle":columnStyle3});
	//gridList.push({"renderId":"tuxedoList","url":rootPath+"/appServer/tuxedo/list?time=" + new Date().getTime(), "columStyle":columnStyle3});


	$(gridList).each(function(i, d){
		var grid=  $("#"+d.renderId).Grid({
			url:d.url,
			dataType:"json",
			colDisplay:false,
			clickSelect:true,
			draggable:false,
			height:"auto",
			colums:d.columStyle,
			pager:false,
			number:false,  
			multiselect:false
        }).grid;
        setInterval(grid.reload,1000*30);
	});
});

</script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp" %>
<div id="layout_center">
	<div class="main" id="main">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="48%" rowspan="3" style="vertical-align:top">
	            <%--<div class="threshold_file">--%>
	                <%--<h3 class="title3">应用系统：</h3>--%>
	                <%--<div id="thresholdList"></div>--%>
	            <%--</div>
	            <br/> --%>
	        	<div class="threshold_file">
	                <h3 class="title3">预警信息：</h3>
	                <div id="emergencyList"></div>
	            </div>
	        </td>
	        <td width="4%">&nbsp;</td>
	        <td rowspan="3" style="vertical-align:top">
                <div class="threshold_file">
                    <h3 class="title3">weblogic服务器：</h3>
                    <div id="weblogicList"></div>
                </div>
                <br />
	        	<%--<div class="threshold_file">--%>
	                <%--<h3 class="title3">操作系统：</h3>--%>
	                <%--<div id="systemList"></div>--%>
	            <%--</div>--%>
	            <%--<br />--%>
	        	<%--<div class="threshold_file">--%>
	                <%--<h3 class="title3">数据库：</h3>--%>
	                <%--<div id="oracleList"></div>--%>
	            <%--</div>--%>
	        </td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td>&nbsp;</td>
	      </tr>
	    </table>
	</div>
</div>
<%@ include file="/WEB-INF/layouts/foot.jsp" %>
</body>
</html>
