<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="msg" uri="http://mvc.one.sinosoft.com/validation/msg" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link href="${ctx}/global/images/favicon.ico" rel="icon" type="image/x-icon"/>
<link href="${ctx}/global/images/favicon.ico" rel="shortcut icon" type="image/x-icon"/>
<link href="${ctx}/global/css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/global/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/global/css/bussiness.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/global/css/one.grid.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/global/css/one.window.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/global/css/one.message.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/global/css/one.tabs.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/global/css/oracle.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="${ctx}/global/js/jquery-1.7.1.js"></script>
<script language="javascript" src="${ctx}/global/js/one.layout.js"></script>
<script language="javascript" src="${ctx}/global/js/one.grid.js"></script>
<script language="javascript" src="${ctx}/global/js/one.window.js"></script>
<script language="javascript" src="${ctx}/global/js/one.message.js"></script>
<script language="javascript" src="${ctx}/global/js/one.tabs.js"></script>
<script language="javascript" src="${ctx}/global/js/highcharts.js"></script>
<script language="javascript" src="${ctx}/global/js/highcharts-more.js"></script>
<script language="javascript" src="${ctx}/global/js/exporting.js"></script>
<script type="text/javascript">
	var rootPath = '${ctx}';
    Highcharts.setOptions({
        exporting:{
            enabled:false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示
        },
        global: {
            useUTC: false
        }
    });
</script>
