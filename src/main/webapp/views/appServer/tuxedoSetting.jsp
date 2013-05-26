<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="msg" uri="http://mvc.one.sinosoft.com/validation/msg" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Tuxedo - 站点设置</title>
    <%@include file="/WEB-INF/layouts/base.jsp" %>
</head>
<body>
<div id="layout_center">
    <div class="window_main">
        <div class="threshold_file">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="add_monitor_box add_form" id="threshold">
                <tr>
                    <td width="25%" style="vertical-align:top"><input name="senior2" type="checkbox" value="" class="m_b"  onclick="rowsTogle()" id="senior2" /> <label for="senior2">保存所有客户端数据</label></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td style="vertical-align:top">当保存Server数据时执行</td>
                    <td><select name="select7" class="diySelect" id="monitorList" style="width:200px">
                        <option>保存负担最大前十条</option>
                    </select></td>
                </tr>
                <tr>
                    <td style="vertical-align:top">保存保存列队信息时执行</td>
                    <td><select name="select8" class="diySelect" id="select2" style="width:200px">
                        <option>保存队列排队前十条</option>
                    </select></td>
                </tr>
            </table>
        </div>
    </div>
    <br />
    <br />
    <br />
</div>
</body>
</html>