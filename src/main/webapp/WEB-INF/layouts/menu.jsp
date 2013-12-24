<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ page import="com.sinosoft.one.monitor.account.model.Account" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%
    Account account = (Account)SecurityUtils.getSubject().getPrincipal();
    String name = "管理员";
    if(!account.getGroupList().isEmpty()){
        if(!account.getGroupList().iterator().next().getName().equals("admin")){
            name="用户";
        }
    }
    request.setAttribute("name",name);

%>

<script type="text/javascript">
    $(function () {

        var bodyHeight = $('body').height();

        var topHeight = 106;
        var bottomHeight = 30;
        var cenHeight = bodyHeight - topHeight - bottomHeight;

        var bottomTop = topHeight + cenHeight;

        $("#layout_bottom").css({'top':bottomTop, 'left':0, 'height':30,'bottom':0});
        $("#layout_center").css({'top':topHeight, 'height':cenHeight});



        $("#nav").delegate('li', 'mouseover mouseout', navHover);
        $("#nav").delegate('li', 'click', navClick);

        if($.browser.msie && ($.browser.version == "7.0")){
            var center = $("#layout_center");
            $("#main").width(center.width() - 31).height(center.height() - 30);
        }
    });

    function navClear(){
        $("#nav").find('li').removeClass('seleck');
    }

    function navHover(){
        $(this).toggleClass("hover")
    }
    function navClick(){
        $(this).addClass("seleck").siblings().removeClass("seleck");
        if($(this).hasClass('has_sub')){
            var subMav = $(this).children("ul.add_sub_menu");
            var isAdd = false;
            if($(this).parent().attr("id") == "menu"){
                isAdd = true;
            };
            subMav.slideDown('fast',function(){
                $(document).bind('click',{dom:subMav,add:isAdd},hideNav);
                return false;
            });
        };
    }
    function hideNav(e){
        var subMenu = e.data.dom;
        var isAdd = e.data.add;
        subMenu.slideUp('fast',function(){
            if(isAdd){
                subMenu.parent().removeClass('seleck');
            };
        });
        $(document).unbind();
    }

    function rowsTogle() {
        var rows = $("#threshold tr.hideRows");
        if (rows.eq(0).is(':hidden')) {
            rows.show();
        } else {
            rows.hide();
        }
        return false;
    }

    function go_index(){
        window.location = '${ctx }/index';
    }



</script>

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td style="vertical-align:top;" width="42px">
    <%@include file="left.jsp"%>
</td>

<td>
<div id="layout_top">
    <div class="header">
        <p class="user">当前登录用户【${sessionScope.loginUserName}】 身份【${name}】 <span>|</span> <a href="${ctx}/login">退出系统</a></p>
        <div class="menu_box">
            <ul class="nav" id="nav">
                <li class="seleck"><a href="${ctx }/index"><i class="home"></i>首页</a></li>
                <li class="has_sub">
                    <a href="javascript:void(0)"><i class="monitor"></i>监视器</a><span class="show_sub_anv"></span>
                    <ul class="add_sub_menu" id="subNav">
                        <li class="action" style="border:none"><span class="middleware">应用服务器</span>
                            <ul class="list">
                                <%--<li><a href="${ctx}/appServer/tuxedo/manager">Tuxedo</a></li>--%>
                                <li><a href="${ctx}/appServer/weblogic/manager">Weblogic</a></li>
                            </ul>
                        </li>
                        <%--<li class="action"><span class="sever">操作系统</span>--%>
                            <%--<ul class="list">--%>
                                <%--<li><a href="${ctx}/os/toSystemMonitor">Linux</a></li>--%>
                            <%--</ul>--%>
                        <%--</li>--%>
                        <%--<li class="action"><span class="system">应用系统</span>--%>
                            <%--<ul class="list">--%>
                                <%--<li><a href="${ctx}/application/manager/appmanager/applist/1">应用系统</a></li>--%>
                            <%--</ul>--%>
                        <%--</li>--%>
                        <%--<li class="action" style="border:none"><span>数据库</span>--%>
                            <%--<ul class="list">--%>
                                <%--<li><a href="${ctx}/db/oracle/oracleMonitor">Oracle</a></li>--%>
                            <%--</ul>--%>
                        <%--</li>--%>
                        <li class="clear"></li>
                    </ul>

                </li>
                <li><a href="${ctx}/alarm/manager/list"><i class="alarm"></i>告警</a></li>
                <li><a href="${ctx}/report?resourceType=WEBLOGIC"><i class="form"></i>报表</a></li>
                <li><a href="${ctx}/account/user/list"><i class="users"></i>用户管理</a></li>
            </ul>
        </div>

    </div>
</div>