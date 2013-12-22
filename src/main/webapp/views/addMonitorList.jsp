<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新建监视器</title>
<%@include file="/WEB-INF/layouts/base.jsp"%>
<script type="text/javascript">
    $(function() {
        leftMenuSelected('left_menu_newMonitor');
    })
</script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
	<div class="main">
    	<div class="add_monitor">
       	  <h2 class="title2"><b>新建监视器<span>(选择并配置监视器)</span></b></h2>
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="add_monitor_box">
              <tr>
                <td width="25%">
                      <h3 class="title3">应用服务器</h3>
                      <ul>
                          <%--<li><a href="${ctx}/addmonitor/server/tuxedo">Tuxedo</a></li>--%>
                          <li><a href="${ctx}/appServer/weblogic/addUI">Weblogic</a></li>
                      </ul>
                  </td>
                <td width="25%" class="center">
                	<%--<h3 class="title3">应用系统</h3>--%>
                	<%--<ul>--%>
                    	<%--<li><a href="${ctx}/addmonitor/addapp">应用系统</a></li>--%>
                    <%--</ul>--%>
                </td>
                <td width="25%" class="center">
               	  <%--<h3 class="title3">数据库</h3>--%>
                	<%--<ul>--%>
                    	<%--<li><a href="${ctx}/addmonitor/addoracle">Oracle</a></li>--%>
                    <%--</ul>--%>
                </td>
                <td width="25%" class="center">
               	  <%--<h3 class="title3">操作系统</h3>--%>
                	<%--<ul>--%>
                    	<%--<li><a href="${ctx}/addmonitor/addos">Linux</a></li>--%>
                    <%--</ul>--%>
                </td>

              </tr>
            </table>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/layouts/foot.jsp"%>
</body>
</html>
