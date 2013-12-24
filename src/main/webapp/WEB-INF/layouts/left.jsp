<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script>

var selectedTool;
$(function(){
	var bar = $("#leftBar");
	var li = bar.children("li");
	var subLi = bar.find("ul.sub > li");
	
	li.click(function(){
		$(this).addClass("show select")
			.siblings().removeClass("show select");
	});
	
	subLi.click(leftMenuSubLiClick);



    $("#left_content").hover(function(){
        //setTimeout()
        $("#left_content").width(170);
        if(selectedTool){
            if($('#'+selectedTool).parent().hasClass("sub")){
                $('#'+selectedTool).parent().parent().addClass("show");
                $('#'+selectedTool).addClass("selected");
            }
        }

    },function(){
        $("#left_content").width(42);
        subLi.removeClass("selected");
        li.removeClass("show select");
        leftMenuSelected(selectedTool);
    });

    function leftMenuLiClick(){
        $(this).addClass("show select")
                .siblings().removeClass("show select");
    }

    function leftMenuSubLiClick(){
        $("ul.sub > li.selected").removeClass("selected");
        $(this).addClass("selected")
    }


})

function leftMenuSelected(id){
    navClear();
    selectedTool = id;

    //代表为sub li
    if($('#'+id).parent().hasClass("sub")){
        //$('#'+id).addClass("selected");
        $('#'+id).parent().parent().addClass("select");
    }else{
        $('#'+id).addClass("show select")
    }
    //$('#left_content').width(170);
}


</script>

<div id="left_content" style="top:0px;height:100%;background:#222;width:42px;overflow: hidden;float: left">

<ul class="left_menu" id="leftBar">
    <shiro:hasPermission name="admin">
    <li id="left_menu_newMonitor"><a href="${ctx}/addmonitor/list"><i class="icon01"></i>新建监视器</a></li>
    </shiro:hasPermission>
    <li class="has"><a href="javascript:void(0);"><i class="icon02"></i>阈值配置文件</a>
    	<ul class="sub">
        <shiro:hasPermission name="admin">
        	<li id="left_menu_newThreshold"><a href="${ctx}/threshold/create">新建阈值文件</a></li>
        </shiro:hasPermission>
            <li id="left_menu_thresholdList"><a href="${ctx}/threshold/list">查看阈值配置文件</a></li>
        </ul>
    </li>
    <%--<li><a href="deployMonitor.html" target="rightMain"><i class="icon03"></i>配置监视器</a></li>--%>
    <li class="has"><a href="javascript:void(0);"><i class="icon04"></i>动作</a>
    	<ul class="sub">
        	<li id="left_menu_actionList"><a href="${ctx}/action/email/list">显示动作</a></li>
        	<%--<li><a href="message.html" target="rightMain">短信动作</a></li>--%>
            <shiro:hasPermission name="admin">
            <li id="left_menu_mailAction"><a href="${ctx}/action/email/create">邮件动作</a></li>
            </shiro:hasPermission>
        </ul>
    </li>
    <shiro:hasPermission name="admin">
        <li id="left_menu_configEmergency"><a href="${ctx}/alarm/manager/configemergency/config"><i class="icon05"></i>配置告警</a></li>
    </shiro:hasPermission>
    <li style="height: 100%;background:#222">&nbsp;</li>
</ul>
</div>
