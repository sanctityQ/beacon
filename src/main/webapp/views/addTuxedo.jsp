<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="msg" uri="http://mvc.one.sinosoft.com/validation/msg" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新建监视器</title>
<%@include file="/WEB-INF/layouts/base.jsp" %>
<script type="text/javascript" src="${ctx}/global/js/jquery.form.js"></script>
<script type="text/javascript">
function save(){
    //TODO 需要增加站点名称校验与表单数据validation
    $('#tuxedo_fm').ajaxForm(function(data) {
        if(data=='success'){
            msgSuccess("系统消息", "操作成功，监视器已保存",function(){
                window.location.href="${ctx}/appServer/tuxedo/manager";
            });
        }else{
            msgFailed("系统消息","操作失败");
        }
    }).submit();
}
</script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
	<div class="main">
    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
            	<div class="add_monitor">
                    <%@include file="/WEB-INF/layouts/selectMonitorType.jsp"%>
          <form id="tuxedo_fm" action="${ctx}/appServer/tuxedo/save" method="post">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="add_monitor_box add_form">
              <tr>
                <td colspan="2" class="group_name">基本信息</td>
              </tr>
              <tr>
                <td width="25%">站点名称<span class="mandatory">*</span></td>
                <td><input id="siteName" name="siteName" type="text" class="formtext" value="${server.siteName}"/><span class="prompt" style="display: none">该名称已存在</span></td>
              </tr>
              <tr>
                <td>IP地址<span class="mandatory">*</span></td>
                <td><input name="siteIp" type="text" class="formtext" size="30" value="${server.siteIp}" /></td>
              </tr>
              <tr>
                <td>轮询间隔(s)<span class="mandatory">*</span></td>
                <td><input name="interval" type="text" class="formtext" size="8" value="${server.interval}"/> <span class="mandatory">最小间隔30秒</span></td>
              </tr>
              <tr>
                <td>端口<span class="mandatory">*</span></td>
                <td><input name="sitePort" type="text" class="formtext" size="8" value="${server.sitePort}"/></td>
              </tr>
              <tr>
                <td colspan="2" class="group_name">用户信息</td>
              </tr>
              <tr>
                <td>用户名<span class="mandatory">*</span></td>
                <td><input name="siteAuth" type="text" class="formtext" value="${server.siteAuth}"/></td>
              </tr>
              <tr>
                <td>密码<span class="mandatory">*</span></td>
                <td><input name="siteWd" type="text" class="formtext" value="${server.siteWd}" /></td>
              </tr>
              <tr>
                <td class="group_name">&nbsp;</td>
                <td class="group_name">
                  <c:if test="${empty server}" >
                    <input type="button" class="buttons" value="确定添加" onclick="save()" />　
                  </c:if>
                  <c:if test="${not empty server}" >
                    <input type="button" class="buttons" value="确定修改" onclick="save()" />　
                  </c:if>
                    <input type="reset"  class="buttons" value="重 置" />　
                    <input type="button" class="buttons" value="取 消" onclick="window.history.back()" />
                  </td>
              </tr>
            </table>
            </form>
        </div>
            </td>
            <td width="15">&nbsp;</td>
            <td width="33%" style="vertical-align:top">
            	<div class="conf_box help">
        	<div class="conf_title">
            	<div class="conf_title_r"></div>
                <div class="conf_title_l"></div>
           	  <span>帮助信息</span>
            </div>
            <div class="conf_cont_box">
            	<div class="conf_cont">
                	最小轮询间隔为30秒<br />
                    其它帮助信息待定……<br />
                         <br /> <br /> <br /> <br /> <br /> <br /> <br />
              </div>
            </div>
        </div>
            </td>
          </tr>
        </table>
    </div>
</div>
<%@include file="/WEB-INF/layouts/foot.jsp"%>
</body>
</html>
<c:if test="${not empty server}" >
<script>
     $("#siteName").attr("readonly",true);
     $('#tuxedo_fm').attr("action","${ctx}/appServer/tuxedo/update?_method=PUT");

</script>
</c:if>