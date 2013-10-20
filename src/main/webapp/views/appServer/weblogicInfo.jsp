<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="msg" uri="http://mvc.one.sinosoft.com/validation/msg" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Weblogic站点</title>
<%@include file="/WEB-INF/layouts/base.jsp" %>
<script type="text/javascript">
$(function(){
    //todo 需要将当前页面的刷新间隔时间调整为从站点对象中动态获取

    var autoWidth = $("#layout_center").width() - 100;
    $("#grid_cpudo,#grid_cpudo_tool").width(autoWidth)
    $("#cipan_space_detail").width(autoWidth + 65)

    $("#tabs").tabs({closeTab:false});
    var toggle = new DataState().start();
    $("#state_tab").click(function(){setTimeout(toggle.stateShow,50)});
    $("#data_tab").click(function(){setTimeout(toggle.dataShow,50)});
    if($.browser.msie && ($.browser.version == "7.0")){
        var center = $("#layout_center")
        $("#main").width(center.width() - 31).height(center.height() - 30)
    };

    /**
     * DataState 数据状态对象
     * @constructor
     */
    var DataState = function(){}
    DataState.prototype.init = function(refreshId){
        var _target = this;
        this.switch = $(refreshId);
        this.switch.click(
                function () {
                    _target.toggle(_target);
                });
        // $(refreshId).trigger('click');
    }

    DataState.prototype.start = function(){
        //--状态监控---

        var dynamic_ = {state:[],data:[]}
        dynamic_.state.push(new TransTop());
        dynamic_.state.push(new EmergencyMsg());
        dynamic_.state.push(new QueueTop());
        dynamic_.state.push(new RamTop());

        dynamic_.data.push(new Server());
        dynamic_.data.push(new Queue());
        dynamic_.data.push(new Client());
        dynamic_.data.push(new System());
        $(dynamic_.state).each(function(){
            //  this.init();
            this.run();
            this.start();
        })
        return {
            stateShow: function () {
                $(dynamic_.state).each(function () {
                    this.run();
                    this.start();
                });
                $(dynamic_.data).each(function () {
                    this.cancel();
                })
            },
            dataShow: function () {
                $(dynamic_.state).each(function () {
                    this.cancel();
                });
                $(dynamic_.data).each(function () {
                    this.run();
                    this.start();
                })
            }
        }
//    return function(){
//        $(dynamic_.state).each(function(){
//            this.toggle();
//        })
//        $(dynamic_.data).each(function(){
//            this.toggle();
//        })
//    }

//    return dynamic_;
//    $(this.state).each(function(){
//        this.start();
//    })

//    (new TransTop()).start();
//    (new EmergencyMsg()).start();
//    (new QueueTop()).start();
//    (new RamTop()).start();

        //---数据监控---
//    (new Server()).start();
//    (new Queue()).start();
//    (new Client()).start();
//    (new System()).start();
    }

    DataState.prototype.toggle = function(obj){
        if(obj.intervalId){
            obj.cancel();
        }else{
            obj.run();
        }
    }
    DataState.prototype.run = function(){
        this.switch.removeClass('refresh').addClass('refresh_dynamic')
        this.intervalId = setInterval(this.start,1000 * 30);
    }
    DataState.prototype.cancel = function(){
        this.switch.removeClass('refresh_dynamic').addClass('refresh')
        clearInterval(this.intervalId);
        this.intervalId = null;
    }
    var TransTop = function(){
        this.init('#transTopRefresh');
    };
    TransTop.prototype = new DataState();
    TransTop.prototype.start = function () {
        $("#trade_top5").empty();
        $("#trade_top5").Grid({
            url: "${ctx}/appServer/tuxedo/transcation/top/${serverName}",
            dataType: "json",
            colDisplay: false,
            clickSelect: true,
            draggable: false,
            height: "auto",
            colums: [
                {id: '1', text: '排名', name: "sort", index: '1', align: ''},
                {id: '2', text: 'rqdone', name: "rqdone", index: '1', align: ''},
                {id: '3', text: 'progname', name: "progname", index: '1', align: ''},
                {id: '4', text: 'pid', name: "pid", index: '1', align: ''}
            ],
            rowNum: 10,
            pager: false,
            number: false,
            multiselect: false
        });
    }

});
</script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
    <div class="main-linux" id="main">
        <ul class="crumbs">
            <li><a href="${ctx}/appServer/list/tuxedo" target="_blank">Tuxedo监视器</a> ></li>
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
                                    <td align="right" class="monitorinfoodd" >服务个数：</td>
                                    <td class="monitorinfoeven">${tuxRunSvr}</td>
                                </tr>
                                <tr>
                                    <td align="right" class="monitorinfoodd">排队消息：</td>
                                    <td class="monitorinfoeven"><span id='tuxRunQueue'>${tuxRunQueue}</span></td>
                                    <td align="right" class="monitorinfoodd">客户端个数： </td>
                                    <td class="monitorinfoeven"><span id='tuxRunClt'>${tuxRunClt}</span></td>
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
                        <a id="queTopRefresh" href="javascript:void(0)" class="refresh" title="刷新"></a>
                        列队统计TOP5
                    </div>
                    <div id="rank_top"></div>
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
                        <td width="49%"><div class="hr_box h_b">
                            <div class="head-cpu"> <a id="memTopRefresh" href="javascript:void(0)" class="refresh" title="刷新"></a> 内存使用最多TOP5</div>
                            <div id="RAM_top5"></div>
                        </div></td>
                        <td width="2%">&nbsp;</td>
                        <td width="49%"><div class="hr_box h_b">
                            <div class="head-cpu"> <a id="transTopRefresh" href="javascript:void(0)" class="refresh" title="刷新"></a> 交易调用最多TOP5 </div>
                            <div id="trade_top5"></div>
                        </div></td>
                    </tr>
                </table>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="49%"><div class="hr_box h_b">
                            <div class="head-cpu"> <a href="javascript:void(0)" class="refresh_dynamic" title="刷新"></a> 客户端统计</div>
                            <div id="client_line" style="height:230px;padding-top:15px"></div>
                        </div></td>
                        <td width="2%">&nbsp;</td>
                        <td width="49%"><div class="hr_box h_b">
                            <div class="head-cpu"> <a href="javascript:void(0)" class="refresh_dynamic" title="刷新"></a> 吞吐量 </div>
                            <div id="throughput_line" style="height:230px;padding-top:15px"></div>
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
                        <a id="serverRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="wlsSERVER"></div>
                </div>
                <br />
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo queRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach queRefreshInput" />
                        <span style="float: left">WLS_JVMRuntime</span>
                        <a id="queRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="tuxQUEUE"></div>
                </div>
                <br />
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo cltRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach cltRefreshInput" />
                        <span style="float: left">WLS_ThreadPoolRuntime</span>
                        <a id="cltRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="tuxCLIENT"></div>
                </div>
                <br />
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo sysRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach sysRefreshInput" />
                        <span style="float: left">WLS_JDBCDataSourceRuntimeMBeans</span>
                        <a id="sysRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="tuxSYSTEM"></div>
                </div>
                <br />
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo sysRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach sysRefreshInput" />
                        <span style="float: left">WLS_ComponentRuntimes</span>
                        <a id="componentRuntimesRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="wlsComponentRuntimes"></div>
                </div>
                <br />
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo sysRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach sysRefreshInput" />
                        <span style="float: left">WLS_JMSServers</span>
                        <a id="JMSServersRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="wlsJMSServers"></div>
                </div>
                <br />
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo sysRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach sysRefreshInput" />
                        <span style="float: left">WLS_ApplicationRuntimes.ComponentRuntimes.EJBRuntimes.PoolRuntime</span>
                        <a id="app_comp_ejb_poolRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="app_comp_ejb_pool"></div>
                </div>
                <br />
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo sysRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach sysRefreshInput" />
                        <span style="float: left">WLS_ApplicationRuntimes.ComponentRuntimes.EJBRuntimes.CacheRuntime</span>
                        <a id="app_comp_ejb_cacheRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="app_comp_ejb_cache"></div>
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
