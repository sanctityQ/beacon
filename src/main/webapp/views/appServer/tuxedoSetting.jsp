<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Tuxedo - 站点设置</title>
    <%@include file="/WEB-INF/layouts/base.jsp" %>
</head>
<body>
<form id="settingForm" action="${ctx}/appServer/tuxedo/setting/save" method="POST" >
<div id="layout_center">
    <div class="window_main">
        <div class="threshold_file">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="add_monitor_box add_form" id="threshold">
                <tr>
                    <td width="25%" style="vertical-align:top">
                        <input id="saveAll" name="dataSave.saveAll" type="checkbox" value="ENABLE" class="m_b" />
                        <label for="saveAll">是否保存所有数据</label>
                    </td>
                    <td>
                        <input id="client" name="dataSave.saveAllClient" type="checkbox" value="ENABLE" class="m_b" />
                        <label for="client">保存所有客户端信息</label>
                    </td>
                </tr>
                <tr>
                    <td style="vertical-align:top">当保存Server数据时</td>
                    <td><select name="dataSave.saveServerData" class="diySelect" id="server" style="width:200px">
                        <option value="ALL">保存所有Server信息</option>
                        <option value="MEMORY">保存内存使用最多的十个Server信息</option>
                        <option value="RQDONE">保存负载最大的十个Server信息</option>
                        <option value="CPU">保存CPU消耗最多的十个Server信息</option>
                    </select></td>
                </tr>
                <tr>
                    <td style="vertical-align:top">保存保存列队信息时</td>
                    <td><select name="dataSave.saveQueueData" class="diySelect" id="queue" style="width:200px">
                        <option value="ALL">保存所有队列信息</option>
                        <option value="TOP10">保存排队最多的十个Server信息</option>
                    </select></td>
                </tr>
            </table>
        </div>
    </div>
    <br /><input type="hidden" name="siteName" value="${serverName}">
    <br />
    <br />
</div>
</form>
</body>
</html>
<script>
    if('ENABLE'=='${dataSave.saveAll}'){
        $('#saveAll').attr('checked','checked');
    }
    if('ENABLE'=='${dataSave.saveAllClient}'){
        $('#client').attr('checked','checked');
    }
    $('#server').val('${dataSave.saveServerData}');
    $('#queue').val('${dataSave.saveQueueData}');
</script>