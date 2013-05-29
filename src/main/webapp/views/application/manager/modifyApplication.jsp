<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="msg" uri="http://mvc.one.sinosoft.com/validation/msg" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:c="http://www.springframework.org/schema/beans">
<head>
    <title>编辑应用信息</title>
    <%@include file="/WEB-INF/layouts/base.jsp"%>
    <script type="text/javascript">

        /*校验数据*/
        function isValid(form) {
            var appName="^[A-Za-z]+$";
            /*if (!form.applicationName.value.match(appName)) {
                msgAlert('系统消息','显示名称必须是英文！')
                return false;
            }*/
            if (form.cnName.value==null||form.cnName.value=="") {
                msgAlert('系统消息','中文名称不能为空！')
                return false;
            }
            var appIp="^[0-9.]+$";
            if(!form.applicationIp.value.match(appIp)){
                msgAlert('系统消息','主机IP地址必须是数字和\".\"的组合！')
                return false;
            }
            var appPort="^[0-9.]+$";
            if(!form.applicationPort.value.match(appPort)||form.applicationPort.value.length>5){
                msgAlert('系统消息','端口必须是5位以内的数字！')
                return false;
            }
            var appInterval="^[0-9.]+$";
            if(!form.interval.value.match(appInterval)||form.interval.value.length>10){
                msgAlert('系统消息','轮询间隔必须是10位以内的数字！')
                return false;
            }
            return true;
        }
    </script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
    <div class="main">
        <div class="add_monitor">
            <h2 class="title2"><b>更新应用信息　</b>
            </h2>
            <form:form id="addSystem" action="${ctx}/application/manager/appmanager/update/${application.id}" method="post"
                  class="form-horizontal" onsubmit="return isValid(this);">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="add_monitor_box add_form">
                    <tr>
                        <td colspan="2" class="group_name">基本信息</td>
                    </tr>
                    <tr>
                        <td width="25%">显示名称<span class="mandatory">*</span></td>
                        <td><%--<input id="applicationName" name="applicationName" value="${application.applicationName}" type="text" class="formtext"/>
                            <msg:errorMsg property="applicationName" type="message"/>--%> ${application.applicationName}
                        </td>
                    </tr>
                    <tr>
                        <td width="25%">中文名称<span class="mandatory"></span></td>
                        <td><input id="cnName" name="cnName" value="${application.cnName}" type="text" class="formtext"/></td>
                    </tr>
                    <tr>
                        <td>主机IP地址<span class="mandatory">*</span></td>
                        <td><input id="applicationIp" name="applicationIp" value="${application.applicationIp}" type="text" class="formtext" size="30"/>
                            <msg:errorMsg property="applicationIp" type="message"/>
                        </td>
                    </tr>
                    <!--<tr>
                      <td>子网掩码<span class="mandatory">*</span></td>
                      <td><input name="input3" type="text" class="formtext" value="255.255.255.0" size="30" /></td>
                    </tr>-->
                    <tr>
                        <td>端口<span class="mandatory">*</span></td>
                        <td><input id="applicationPort" name="applicationPort" value="${application.applicationPort}" type="text" class="formtext" size="5"/>
                            <msg:errorMsg property="applicationPort" type="message"/>
                        </td>
                    </tr>
                    <tr>
                        <td>轮询间隔<span class="mandatory"></span></td>
                        <td><input id="interval" name="interval" value="${application.interval}" type="text" class="formtext" size="10"/>
                            <msg:errorMsg property="interval" type="message"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="group_name">&nbsp;</td>
                        <td class="group_name">
                            <!--<input type="button" class="buttons" value="确定添加" onclick="save()" />　-->
                            <input id="submit" type="submit" class="buttons" value="确定修改"/>　
                            <input type="reset" class="buttons" value="重 置"/>　
                            <input type="button" class="buttons" value="取 消" onclick="window.history.back()"/>
                        </td>
                    </tr>
                </table>
            </form:form>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/layouts/foot.jsp"%>
</body>
</html>
