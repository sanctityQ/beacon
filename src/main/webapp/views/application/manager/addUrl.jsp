<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新增Url</title>
<%@include file="/WEB-INF/layouts/base.jsp"%>
<script type="text/javascript">

function save(){
	msgSuccess("系统消息", "操作成功，监视器已保存！");
	window.location.href="manageBusScene.html";
}
function trim(){
    String.prototype.trim = function(){
        return this.replace(/(^\s*|\s*$)/g,'');
    }
}
/*校验数据*/
function isValid(form) {
    String.prototype.trim = function(){
        return this.replace(/(^\s*|\s*$)/g,'');
    }
    if (form.url.value==null||form.url.value.trim()=="") {
        msgAlert('系统消息','URL地址不能为空或者空格！')
        return false;
    }
    if (form.url.value.length>500) {
        msgAlert('系统消息','URL地址长度不能超过500！')
        return false;
    }
    if (form.description.value==null||form.description.value.trim()=="") {
        msgAlert('系统消息','URL描述不能为空或者空格！')
        return false;
    }
    if (form.description.value.length>300) {
        msgAlert('系统消息','URL描述长度不能超过300！')
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
       	  <h2 class="title2"><b>添加URL　</b>
          	
          </h2>
          <form:form id="addBizScenario" action="${ctx}/application/manager/urlmanager/addurl/${bizScenarioId}" method="post"
                     class="form-horizontal" onsubmit="return isValid(this);">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="add_monitor_box add_form">
              <tr>
                <td colspan="2" class="group_name">基本信息</td>
              </tr>
              <tr>
                <td width="25%">URL地址<span class="mandatory">*</span></td>
                <td><input id="url" name="url" value="${url.url}" type="text" class="formtext" style="width: 700px;" />
                    <msg:errorMsg property="url" type="message"/>
                </td>
              </tr>
              <tr>
                  <td width="25%">URL描述<span class="mandatory">*</span></td>
                  <td><input id="description" name="description" value="${url.description}" type="text" class="formtext" />
                      <msg:errorMsg property="description" type="message"/>
                  </td>
              </tr>
              <%--<tr>
                  <td width="25%">URL阈值<span class="mandatory">*</span></td>
                  <td><input name="threshold" type="text" class="formtext" /></td>
              </tr>--%>
              <tr>
                <td class="group_name">&nbsp;</td>
                <td class="group_name">
                	<input id="submit" type="submit" class="buttons" value="确定添加" <%--onclick="save()"--%> />　
                    <input type="reset" class="buttons" value="重 置" />　
                    <input type="button" class="buttons" value="取 消" onclick="window.history.back()" />
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
