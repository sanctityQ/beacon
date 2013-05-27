<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="<%=request.getContextPath()%>"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>用户管理</title>
<%@include file="/WEB-INF/layouts/base.jsp" %>
<script type="text/javascript">
$(function(){

    if($.browser.msie && ($.browser.version == "7.0")){
        var center = $("#layout_center");
        $("#main").width(center.width() - 31).height(center.height() - 30);
    }
	$("#thresholdList").Grid({
		type : "post",
		url : "${ctx}/account/user/data",  
		dataType: "json",
		colDisplay: false,  
		clickSelect: true,
		draggable:false,
		height: "auto",  
		colums:[  
			{id:'1',text:'用户名',name:"loginName",index:'1',align:''},
			{id:'2',text:'姓名',name:"name",index:'1',align:''},
			{id:'3',text:'状态',name:"status",index:'1',align:''},
			{id:'4',text:'手机号',name:"phone",index:'1',align:''},
			{id:'5',text:'邮箱',name:"email",index:'1',align:''},
			{id:'6',text:'创建时间',name:"createTime",index:'1',align:''},
			{id:'7',text:'操作',name:"operation",index:'1',align:''}
		],  
		rowNum:10,
		pager : false,
		number:false,  
		multiselect: true  
	});
});

function updRow(e){
	var rows = $(e).parent().parent();
	var id = rows.attr('id');
	location.href="${ctx}/account/user/update/"+id.replace("row_","");
}
function navHover(){
	$(this).toggleClass("hover")
}

function delRow(e){
	var rows = $(e).parent().parent();
	var id = rows.attr('id');
	msgConfirm('系统消息','确定要删除该用户吗？',function(){
		rows.remove();
		delUser("${ctx}/account/user/delete/"+id.replace("row_",""));
	});
}
function batchDel(){
	var $g = $("#thresholdList div.grid_view > table");
	var selecteds = $("td.multiple :checked",$g);
	if(selecteds.length > 0){
		msgConfirm('系统消息','确定要删除选中的用户吗？',function(){
			var checks = [];
			selecteds.each(function(){
				var rows = $(this).parent().parent();
				checks.push(rows.attr('id').replace("row_",""));
				rows.remove();
			});
			var delUrl = "${ctx}/account/user/batchDelete/"+checks;
			delUser(delUrl);
		});
	}else{
		msgAlert('系统消息','没有选中的文件！<br />请选择要删除的文件后，继续操作。')
	};
}
function delUser(url){
	$.ajax({
		type : "post",
		url : url,
		dataType : "json",
		success : function(data) {
			msgSuccess("系统消息", "操作成功，用户已删除！");
		},
		error:function(){
			msgFailed("系统消息", "操作失败，用户未被删除！");
			location.href="${ctx}/account/user/list";
		}
	});
}


function viewRelevance(e){
	var rows = $(e).parent().parent();
	var id = rows.attr('id');
	var name = rows.children("td").eq(1).text();
	var title = "用户: " + name;
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
</script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
	<div class="main" id="main">
    	<div class="threshold_file user_manager">
       	  <h2 class="title2"><strong class="right"><a href="${ctx}/account/user/create">创建新用户</a></strong><b>用户列表　</b></h2>
          <div class="tool_bar_top"><a href="javascript:void(0);" class="batch_del" onclick="batchDel()">批量删除</a></div>
          <div id="thresholdList"></div>
          <div class="tool_bar"></div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/layouts/foot.jsp"%>
</body>
</html>
