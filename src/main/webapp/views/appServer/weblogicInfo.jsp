<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="msg" uri="http://mvc.one.sinosoft.com/validation/msg" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Weblogic站点</title>
<%@include file="/WEB-INF/layouts/base.jsp" %>
<script type="text/javascript" src="${ctx}/global/js/appserver/weblogic.js"></script>
<script type="text/javascript">
    interval = '${interval}';
    serverName = '${serverName}';
$(function() {

    var autoWidth = $("#layout_center").width() - 100;
    $("#grid_cpudo,#grid_cpudo_tool").width(autoWidth);
    $("#cipan_space_detail").width(autoWidth + 65);

    var toggle = new DataState().start();
    $("#tabs").tabs({closeTab:false});
    $("#state_tab").click(function(){setTimeout(toggle.stateShow, 50)});
    $("#data_tab").click(function(){setTimeout(toggle.dataShow, 50)});

    if($.browser.msie && ($.browser.version == "7.0")){
        var center = $("#layout_center")
        $("#main").width(center.width() - 31).height(center.height() - 30)
    };

    function getServerLatestData(){
        $.ajax({
            url: '${ctx}/appServer/weblogic/view/${serverName}/latest',
            dataType : 'json',
            type : 'get',
            async : false,
            error : function (XMLHttpRequest,errorThrown) {
                alert("数据加载出错！" + errorThrown);
            },
            success: function(data){
                $('#count').html(data.count);
                $('#cpuIdle').html(data.cpuIdle);
                $('#memFree').html(data.memFree);
                $('#tuxRunQueue').html(data.tuxRunQueue);
                $('#tuxRunClt').html(data.tuxRunClt);
            }
        });
    }

    setInterval(getServerLatestData, 1000 * interval);

    chart_init();
});

</script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
    <div class="main-linux" id="main">
        <ul class="crumbs">
            <li><a href="${ctx}/appServer/list/tuxedo" target="_blank">Weblogic监视器</a> ></li>
            <li><b>${serverName}</b></li>
        </ul>
        <hr class="top_border" />
        <div id="tabs">
            <ul>
                <li id='state_tab' class="tabs_select">状态监控</li>
                <li id='data_tab'>数据监控</li>
            </ul>
            <br />
            <div class="first">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="49%">
                            <table width="100%" cellspacing="0" cellpadding="0" border="0" class="lrtbdarkborder" height="280px">
                                <tbody>
                                <tr>
                                    <td colspan="4" class="jsq-message">监视器信息</td>
                                </tr>
                                <tr>
                                    <td align="right" class="monitorinfoodd">服务器名称： </td>
                                    <td class="monitorinfoeven">${serverName}</td>
                                    <td align="right" class="monitorinfoodd">服务器类型： </td>
                                    <td class="monitorinfoeven">${serverType}<br /></td>
                                </tr>
                                <tr>
                                    <td align="right" class="monitorinfoodd">IP地址：</td>
                                    <td class="monitorinfoeven">${ip}</td>
                                    <td align="right" class="monitorinfoodd"> 端口：</td>
                                    <td class="monitorinfoeven">${port}</td>
                                </tr>
                                <tr>
                                    <td align="right" class="monitorinfoodd" >产品版本： </td>
                                    <td class="monitorinfoeven">${wlsVersion}</td>
                                    <td align="right" class="monitorinfoodd" >启动时间： </td>
                                    <td class="monitorinfoeven">${systemboot}</td>
                                </tr>
                                <tr>
                                    <td align="right" class="monitorinfoodd" >监控时间：</td>
                                    <td class="monitorinfoeven">${rectime}</td>
                                    <td align="right" class="monitorinfoodd" >服务器数量：</td>
                                    <td class="monitorinfoeven">${serverNum}</td>
                                </tr>
                                <tr>
                                    <td align="right" class="monitorinfoodd">Domain名称：</td>
                                    <td class="monitorinfoeven"><span id='domainName'>${domainName}</span></td>
                                    <td align="right" class="monitorinfoodd">管理服务器： </td>
                                    <td class="monitorinfoeven"><span id='adminServerName'>${adminServerName}</span></td>
                                </tr>
                                <tr>
                                    <td align="right" class="monitorinfoodd"> 操作系统：</td>
                                    <td class="monitorinfoeven">${osVersion}</td>
                                    <td align="right" class="monitorinfoodd">CPU空余：</td>
                                    <td class="monitorinfoeven"><span id='cpuIdle'>${cpuIdle}</span></td>
                                </tr>
                                <tr>
                                    <td align="right" class="monitorinfoodd"> 内存空余：</td>
                                    <td class="monitorinfoeven"><span id='memFree'>${memFree}</span>M</td>
                                    <td align="right" class="monitorinfoodd">Agent版本：</td>
                                    <td class="monitorinfoeven">${agentVer}</td>
                                </tr>
                                <tr>
                                    <td align="right" class="monitorinfoodd">监控次数：</td>
                                    <td class="monitorinfoeven"><span id='count'>${count}</span></td>
                                    <td align="right" class="monitorinfoodd">&nbsp;</td>
                                    <td class="monitorinfoeven">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td align="right" class="monitorinfoodd">&nbsp;</td>
                                    <td class="monitorinfoeven">&nbsp;</td>
                                    <td align="right" class="monitorinfoodd">&nbsp;</td>
                                    <td class="monitorinfoeven">&nbsp;</td>
                                </tr>
                                </tbody>
                            </table>
                        </td>
                        <td width="2%">&nbsp;</td>
                        <td style="vertical-align:top" width="49%">
                            <div class="tableheadingbborder" style="height:320px;width:100%">
                                <div class="head-cpu">
                                    <a id="emergencyRefresh" href="javascript:void(0)" class="refresh" title="刷新"></a>
                                    <a href="${ctx}/alarm/manager/resource/${serverName}/history" class="alerts_list">历史预警</a>
                                    预警消息最近10条
                                </div>
                                <div id="emergencyList"></div>
                                <div class="cpu-text"><b><a class="bodytext-img" href="${ctx}/alarm/manager/configemergency/config/APP_SERVER/${serverName}">警告配置</a></b></div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3">&nbsp;</td>
                    </tr>
                </table>

                <div class="hr_box">
                    <div class="head-cpu">
                        <a id="serverInfoRefresh" href="javascript:void(0)" class="refresh" title="刷新"></a>
                        server信息
                    </div>
                    <div id="serverInfo"></div>
                </div>


                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="49%">
                            <div class="hr_box h_b">
                                <div class="head-cpu">
                                    <a href="javascript:void(0)" class="refresh_dynamic" title="刷新"></a>
                                    CPU使用率-最近6小时
                                </div>
                                <div id="CPU_line" style="height:230px;padding-top:15px"></div>
                            </div>
                        </td>
                        <td width="2%">&nbsp;</td>
                        <td width="49%">
                            <div class="hr_box h_b">
                                <div class="head-cpu">
                                    <a href="javascript:void(0)" class="refresh_dynamic" title="刷新"></a>
                                    内存使用率-最近6小时
                                </div>
                                <div id="RAM_line" style="height:230px;padding-top:15px"></div>
                            </div>
                        </td>
                    </tr>
                </table>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="49%">
                            <div class="hr_box h_b">
                                <div class="head-cpu">
                                    <a href="javascript:void(0)" class="refresh_dynamic" title="刷新"></a>
                                    Server内存使用率-最近6小时
                                </div>
                                <div id="server_ram_line" style="height:230px;padding-top:15px"></div>
                            </div>
                        </td>
                        <td width="2%">&nbsp;</td>
                        <td width="49%">
                            <div class="hr_box h_b">
                                <div class="head-cpu">
                                    <a href="javascript:void(0)" class="refresh_dynamic" title="刷新"></a>
                                    Server吞吐量-最近6小时
                                </div>
                                <div id="server_throughput_line" style="height:230px;padding-top:15px"></div>
                            </div>
                        </td>
                    </tr>
                </table>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="49%"><div class="hr_box h_b">
                            <div class="head-cpu"> <a href="javascript:void(0)" class="refresh_dynamic" title="刷新"></a> server线程信息</div>
                            <div id="thdusage_line" style="height:230px;padding-top:15px"></div>
                        </div></td>
                        <td width="2%">&nbsp;</td>
                        <td width="49%"><div class="hr_box h_b">
                            <div class="head-cpu"> <a href="javascript:void(0)" class="refresh_dynamic" title="刷新"></a> server session信息 </div>
                            <div id="server_session_line" style="height:230px;padding-top:15px"></div>
                        </div></td>
                    </tr>
                </table>

            </div>
            <div class="second">
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo serverRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach serverRefreshInput" />
                        <span style="float: left">WLS_ServerRuntime</span>
                        <a id="WlsServerRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="WlsServer"></div>
                </div>
                <br />
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo queRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach queRefreshInput" />
                        <span style="float: left">WLS_JVMRuntime</span>
                        <a id="JVMRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="JVM"></div>
                </div>
                <br />
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo cltRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach cltRefreshInput" />
                        <span style="float: left">WLS_ThreadPoolRuntime</span>
                        <a id="ThreadPoolRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="ThreadPool"></div>
                </div>
                <br />
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo sysRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach sysRefreshInput" />
                        <span style="float: left">WLS_JDBCDataSourceRuntimeMBeans</span>
                        <a id="JDBCRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="JDBC"></div>
                </div>
                <br />
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo sysRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach sysRefreshInput" />
                        <span style="float: left">WLS_ComponentRuntimes</span>
                        <a id="ComponentRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="Component"></div>
                </div>
                <br />
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo sysRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach sysRefreshInput" />
                        <span style="float: left">WLS_JMSServers</span>
                        <a id="JMSRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="JMS"></div>
                </div>
                <br />
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo sysRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach sysRefreshInput" />
                        <span style="float: left">WLS_ApplicationRuntimes.ComponentRuntimes.EJBRuntimes.PoolRuntime</span>
                        <a id="EjbPoolRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="EjbPool"></div>
                </div>
                <br />
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo sysRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach sysRefreshInput" />
                        <span style="float: left">WLS_ApplicationRuntimes.ComponentRuntimes.EJBRuntimes.CacheRuntime</span>
                        <a id="EjbCacheRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="EjbCache"></div>
                </div>
                <br />
            </div>
        </div>
    </div>

</div>
</div>
<%@include file="/WEB-INF/layouts/foot.jsp" %>
</body>
</html>
