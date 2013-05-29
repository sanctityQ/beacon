<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新增业务场景</title>
<%@include file="/WEB-INF/layouts/base.jsp"%>
<script type="text/javascript">
/*校验数据*/
function isValid(form) {
    if (form.name.value==null||form.name.value=="") {
        msgAlert('系统消息','场景名称不能为空！')
        return false;
    }
    var bsGrade=form.bizScenarioGrade.value;
    if(bsGrade!="高"&&bsGrade!="中"&&bsGrade!="低"){
        msgAlert('系统消息','必须选择场景级别！')
        return false;
    }
    return true;
}
function save(){
    msgSuccess("系统消息", "操作成功，监视器已保存！");
    window.location.href="manageBusScene.html";
}
</script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
	<div class="main">
    	<div class="add_monitor">
       	  <h2 class="title2"><b>添加业务场景　</b>
          	
          </h2>
          <form:form id="addBizScenario" action="${ctx}/application/manager/bsmanager/addbizscenario/${appId}" method="post"
                     class="form-horizontal" onsubmit="return isValid(this);">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="add_monitor_box add_form">
              <tr>
                <td colspan="2" class="group_name">基本信息</td>
              </tr>
              <tr>
                <td width="25%">场景名称<span class="mandatory">*</span></td>
                <td><input id="name" name="name" value="${bizScenario.name}" type="text" class="formtext" />
                    <msg:errorMsg property="name" type="message"/>
                </td>
              </tr>
              <%--<tr>
                <td>id<span class="mandatory"></span></td>
                <td><input id="appId" name="input" type="text" value="${appId}" class="formtext" size="32" /></td>
              </tr>--%>
              <%--<tr>
                <td>添加时间<span class="mandatory">*</span></td>
                <td><input name="input3" type="text" class="formtext" value="255.255.255.0" size="30" /></td>
              </tr>--%>
              <tr>
                <td>级别<span class="mandatory">*</span></td>
                <td>
                	<select id="bizScenarioGrade" name="bizScenarioGrade" class="diySelect" >
                    <option value="">选择级别</option>
                    <option value="高">高</option>
                    <option value="中">中</option>
                    <option value="低">低</option>
                  </select>
                    <msg:errorMsg property="bizScenarioGrade" type="message"/>
                </td>
              </tr>
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
