<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="msg" uri="http://mvc.one.sinosoft.com/validation/msg" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Tuxedo站点</title>
<%@include file="/WEB-INF/layouts/base.jsp" %>
</head>

<body>

<%@include file="/WEB-INF/layouts/menu.jsp"%>

<div id="layout_center">
    <div class="main-linux" id="main">
        <ul class="crumbs">
            <li><a href="${ctx}/appServer/tuxedo/manager" target="_blank">Tuxedo监视器</a> ></li>
            <li><b>${serverName}</b></li>
        </ul>

        <div id="errorMsg" class="alert alert-danger" style="display: none;"></div>
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
                                    <td class="monitorinfoeven"><span id="tuxVersion">${tuxVersion}</span></td>
                                    <td align="right" class="monitorinfoodd" >启动时间： </td>
                                    <td class="monitorinfoeven"><span id="systemboot">${systemboot}</span></td>
                                </tr>
                                <tr>
                                    <td align="right" class="monitorinfoodd" >监控时间：</td>
                                    <td class="monitorinfoeven">${rectime}</td>
                                    <td align="right" class="monitorinfoodd" >服务个数：</td>
                                    <td class="monitorinfoeven"><span id="tuxRunSvr">${tuxRunSvr}</span></td>
                                </tr>
                                <tr>
                                    <td align="right" class="monitorinfoodd">队列个数：</td>
                                    <td class="monitorinfoeven"><span id='tuxRunQueue'>${tuxRunQueue}</span></td>
                                    <td align="right" class="monitorinfoodd">客户端个数： </td>
                                    <td class="monitorinfoeven"><span id='tuxRunClt'>${tuxRunClt}</span></td>
                                </tr>
                                <tr>
                                    <td align="right" class="monitorinfoodd"> 操作系统：</td>
                                    <td class="monitorinfoeven"><span id='osVersion'>${osVersion}</span></td>
                                    <td align="right" class="monitorinfoodd">CPU空余：</td>
                                    <td class="monitorinfoeven"><span id='cpuIdle'>${cpuIdle}</span></td>
                                </tr>
                                <tr>
                                    <td align="right" class="monitorinfoodd"> 内存空余：</td>
                                    <td class="monitorinfoeven"><span id='memFree'>${memFree}</span>M</td>
                                    <td align="right" class="monitorinfoodd">Agent版本：</td>
                                    <td class="monitorinfoeven"><span id='agentVer'>${agentVer}</span></td>
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
                                    物理内存剩余-最近6小时
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
                        <span style="float: left">TUX_SERVER</span>
                        <a id="serverRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="tuxSERVER"></div>
                </div>
                <br />
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo queRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach queRefreshInput" />
                        <span style="float: left">TUX_QUEUE</span>
                        <a id="queRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="tuxQUEUE"></div>
                </div>
                <br />
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo cltRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach cltRefreshInput" />
                        <span style="float: left">TUX_CLIENT</span>
                        <a id="cltRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="tuxCLIENT"></div>
                </div>
                <br />
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo sysRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach sysRefreshInput" />
                        <span style="float: left">TUX_SYSTEM</span>
                        <a id="sysRefresh" href="javascript:void(0)"  class="refresh" style="float: left;margin-left:6px" title="刷新"></a>
                    </div>
                    <div id="tuxSYSTEM"></div>
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
<script type="text/javascript">
//需要将当前页面的刷新间隔时间调整为从站点对象中动态获取
var interval = ${interval};

    function getServerLatestData(){
        $.ajax({
            url: '${ctx}/appServer/tuxedo/view/${serverName}/latest',
            dataType : 'json',
            type : 'get',
            async : false,
            error : function (XMLHttpRequest,errorThrown) {
                console.error("数据加载出错！" + errorThrown);
            },
            success: function(data){
                $('#count').html(data.count);
                $('#cpuIdle').html(data.cpuIdle);
                $('#memFree').html(data.memFree);
                $('#tuxRunQueue').html(data.tuxRunQueue);
                $('#tuxRunClt').html(data.tuxRunClt);
                $('#systemboot').html(data.systemboot);
                $('#tuxVersion').html(data.tuxVersion);
                $('#osVersion').html(data.osVersion);
                $('#agentVer').html(data.agentVer);
                $('#tuxRunSvr').html(data.tuxRunSvr);
                if(data.stop=='true'){
                    $('#errorMsg').html(tuxedoStopMessage);
                    $('#errorMsg').fadeIn();
                }
                else if(data.agentStop == 'true'){
                    $('#errorMsg').html(agentStopMessage)
                    $('#errorMsg').fadeIn();
                }
                else{
                    $('#errorMsg').fadeOut();
                }
            }
        });
    }

    setInterval(getServerLatestData, 1000 * interval);

    var tuxedoStopMessage = '<strong>错误：</strong>Tuxedo系统监控已经停止，请检查Tuxedo是否正常运行!';
    var agentStopMessage = '<strong>错误：</strong>Tuxedo系统监控无法连接到agent端，请检查agent端是否正常运行!';
    <c:if test="${stop}">
        $('#errorMsg').html(tuxedoStopMessage);
        $('#errorMsg').fadeIn();
    </c:if>
    <c:if test="${agentStop}">
        $('#errorMsg').html(agentStopMessage)
        $('#errorMsg').fadeIn();
    </c:if>


$(function(){
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


    //cpu chart
    new Highcharts.Chart({
        chart: {
            renderTo: 'CPU_line',
            type: 'line',
            height:230,
            events: {
                load: function() {

                    // set up the updating of the chart each second
                    var series = this.series[0];
                    //update chart data
                    setInterval(function() {
                        $.ajax({
                            url:"${ctx}/appServer/tuxedo/chart/cpu/${serverName}/latest",
                            cache:false,
                            async:false,
                            success:function(back){
                                if(back.length ==0)
                                    return;
                                if(series.data.length < 20){
                                    series.addPoint(back, true, false);
                                }
                                else{
                                    series.addPoint(back, true, true);
                                }
                            }
                        })
                    }, interval*1000);
                }
            }
        },

        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            type: 'datetime'
        },
        yAxis: {
            title: {
                text: '值%'
            },min:0
        },
        tooltip: {
            formatter: function() {
                return '<b>'+ this.series.name +'</b><br/>'+
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x)+'<br/>'+this.y+'%';
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: true,
                marker:{
                    enabled:false
                }
            }
        },
        credits: {
            text: '',
            href: ''
        },
        series: [{
            name: 'CPU使用率',
            data: (function() {
                // generate an array of random data
                var data = [];
                $.ajax({
                    url:"${ctx}/appServer/tuxedo/chart/cpu/${serverName}/123",
                    cache:false,
                    async:false,
                    success:function(back){
                        data = back;
                    }
                })
                return data;
            })()
        }],
        colors: ['#87bdc9']
    });



    new Highcharts.Chart({
        chart: {
            renderTo: 'RAM_line',
            type: 'line',
            height:230,
            events: {
                load: function() {

                    // set up the updating of the chart each second
                    var series = this.series[0];
                    //update chart data
                    setInterval(function() {
                        $.ajax({
                            url:"${ctx}/appServer/tuxedo/chart/memory/${serverName}/latest",
                            cache:false,
                            async:false,
                            success:function(back){
                                if(back.length ==0)
                                    return;
                                if(series.data.length < 20){
                                    series.addPoint(back, true, false);
                                }
                                else{
                                    series.addPoint(back, true, true);
                                }
                            }
                        })
                    }, interval*1000);
                }
            }
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            type: 'datetime'
        },
        yAxis: {
            title: {
                text: '值M'
            }
        },
        tooltip: {
            formatter: function() {
                return '<b>'+ this.series.name +'</b><br/>'+
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x)+'<br/>'+this.y+'M';
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: true,
                marker:{
                    enabled:false
                }
            }
        },
        credits: {
            text: '',
            href: ''
        },
        series: [{
            name: '物理内存剩余',
            data: (function() {
                // generate an array of random data
                var data = [];
                $.ajax({
                    url:"${ctx}/appServer/tuxedo/chart/memory/${serverName}/all",
                    cache:false,
                    async:false,
                    success:function(back){
                        data = back;
                    }
                })
                return data;
            })()
        }],
        colors: ['#769f5d']
    });


    //吞吐量
    new Highcharts.Chart({
        chart: {
            renderTo: 'throughput_line',
            type: 'line',
            height:230,
            events: {
                load: function() {
                    // set up the updating of the chart each second
                    var series = this.series[0];
                    //update chart data
                    setInterval(function() {
                        $.ajax({
                            url:"${ctx}/appServer/tuxedo/chart/thoughtPut/${serverName}/latest",
                            cache:false,
                            async:false,
                            success:function(back){
                                if(back.length ==0)
                                    return;
                                if(series.data.length < 20){
                                    series.addPoint(back, true, false);
                                }
                                else{
                                    series.addPoint(back, true, true);
                                }
                            }
                        })
                    }, interval*1000);
                }
            }
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            type: 'datetime'
        },
        yAxis: {
            title: {
                text: '值'
            },
            min:0,
            allowDecimals:false
        },
        tooltip: {
            formatter: function() {
                return '<b>'+ this.series.name +'</b><br/>'+
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x)+'<br/>'+this.y;
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: true,
                marker:{
                    enabled:false
                }
            }
        },
        credits: {
            text: '',
            href: ''
        },
        series: [{
            name: '吞吐量',
            data: (function() {
                // generate an array of random data
                var data = [];
                $.ajax({
                    url:"${ctx}/appServer/tuxedo/chart/thoughtPut/${serverName}/all",
                    cache:false,
                    async:false,
                    success:function(back){
                        data = back;
                    }
                })
                return data;
            })()
        }],
        colors: ['#ffa200']
    });

    new Highcharts.Chart({
        chart: {
            renderTo: 'client_line',
            type: 'line',
            height:230,
            events: {
                load: function() {

                    // set up the updating of the chart each second
                    var series0 = this.series[0];
                    var series1 = this.series[1];
                    //update chart data
                    setInterval(function() {
                        $.ajax({
                            url:"${ctx}/appServer/tuxedo/chart/client/${serverName}/latest/total",
                            cache:false,
                            async:false,
                            success:function(back){
                                if(back.length ==0)
                                    return;
                                if(series0.data.length < 20){
                                    series0.addPoint(back, true, false);
                                }
                                else{
                                    series0.addPoint(back, true, true);
                                }
                            }
                        })
                    }, interval*1000);
                    setInterval(function() {
                        $.ajax({
                            url:"${ctx}/appServer/tuxedo/chart/client/${serverName}/latest/busy",
                            cache:false,
                            async:false,
                            success:function(back){
                                if(back.length ==0)
                                    return;
                                if(series1.data.length < 20){
                                    series1.addPoint(back, true, false);
                                }
                                else{
                                    series1.addPoint(back, true, true);
                                }
                            }
                        })
                    }, interval*1000);
                }
            }
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            type: 'datetime'
        },
        yAxis: {
            title: {
                text: '值'
            },
            min:0,
            allowDecimals:false
        },
        tooltip: {
            formatter: function() {
                return '<b>'+ this.series.name +'</b><br/>'+
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x)+'<br/>'+this.y;
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: true,
                marker:{
                    enabled:false
                }
            }
        },
        credits: {
            text: '',
            href: ''
        },
        series: [{
            name: '全部客户端',
            data: (function() {
                // generate an array of random data
                var data = [];
                $.ajax({
                    url:"${ctx}/appServer/tuxedo/chart/client/${serverName}/all/total",
                    cache:false,
                    async:false,
                    success:function(back){
                        data = back;
                    }
                })
                return data;
            })()
        },{
            name: '繁忙客户端',
            data: (function() {
                // generate an array of random data
                var data = [];
                $.ajax({
                    url:"${ctx}/appServer/tuxedo/chart/client/${serverName}/all/busy",
                    cache:false,
                    async:false,
                    success:function(back){
                        data = back;
                    }
                })
                return data;
            })()
        }],
        colors: ['#00B58A','#F80B28']
    });





});


var TAB_STATE = {data:0,state:1};
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
    this.intervalId = setInterval(this.start,1000 * interval);
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
            {id: '2', text: '交易执行次数', name: "rqdone", index: '1', align: ''},
            {id: '3', text: '进程名称', name: "progname", index: '1', align: ''},
            {id: '4', text: '进程号', name: "pid", index: '1', align: ''}
        ],
        pager: false,
        number: false,
        multiselect: false
    });
}
var RamTop = function(){
    this.init('#memTopRefresh');
};
RamTop.prototype = new DataState();
RamTop.prototype.start = function(){
    $("#RAM_top5").empty();
    $("#RAM_top5").Grid({
        url : "${ctx}/appServer/tuxedo/memory/top/${serverName}",
        dataType: "json",
        colDisplay: false,
        clickSelect: true,
        draggable:false,
        height: "auto",
        colums:[
            {id:'1',text:'排名',name:"sort",index:'1',align:''},
            {id:'2',text:'进程号',name:"pid",index:'1',align:''},
            {id:'3',text:'使用内存（页）',name:"used",index:'1',align:''}
        ],
        rowNum:10,
        pager : false,
        number:false,
        multiselect: false
    });
}

var QueueTop = function(){
    this.init('#queTopRefresh');
};
QueueTop.prototype = new DataState();
QueueTop.prototype.start = function(){
    $("#rank_top").empty();
    $("#rank_top").Grid({
        url : "${ctx}/appServer/tuxedo/queue/top/${serverName}",
        dataType: "json",
        colDisplay: false,
        clickSelect: true,
        draggable:false,
        height: "auto",
        colums:[
            {id:'1',text:'排名',name:"sort",index:'1',align:''},
            {id:'2',text:'名称',name:"name",index:'1',align:''},
            {id:'3',text:'队列消息数',name:"queued",index:'1',align:''}
        ],
        rowNum:10,
        pager : false,
        number:false,
        multiselect: false
    });
}

var EmergencyMsg = function(){
    this.init('#emergencyRefresh');
};
EmergencyMsg.prototype = new DataState();
EmergencyMsg.prototype.start = function(){
    $("#emergencyList").empty();
    $("#emergencyList").Grid({
        url : "${ctx}/alarm/manager/resource/${serverName}",
        dataType: "json",
        colDisplay: false,
        clickSelect: true,
        draggable:false,
        height: "auto",
        colums:[
            {id:'1',text:'状态',name:"appellation",index:'1',align:'',width:'52'},
            {id:'2',text:'消息',name:"appellation",index:'1',align:'',width:'420'},
            {id:'5',text:'时间',name:"appellation",index:'1',align:''}
        ],
        rowNum:10,
        pager : false,
        number:false,
        multiselect: false
    });
}

/**
 * Server对象
 * @constructor
 */
var Server = function(){
    this.init('#serverRefresh');
};
Server.prototype = new DataState();
Server.prototype.start = function(){
    $("#tuxSERVER").empty();
    $("#tuxSERVER").Grid({
        url : "${ctx}/appServer/tuxedo/data/server/${serverName}",
        dataType: "json",
        colDisplay: false,
        clickSelect: true,
        draggable:false,
        height: "auto",
        searchClass:"serverRefreshInput",
        searchBtn:"serverRefreshButton",
        colums:[
            {id:'1',text:'Server',name:"server",index:'1',align:''},
            {id:'2',text:'Queueid',name:"queueId",index:'1',align:''},
            {id:'3',text:'ProcessID',name:"processId",index:'1',align:''},
            {id:'4',text:'Rpdone',name:"rqDone",index:'1',align:''},
            {id:'5',text:'CurrSvc',name:"currentSvc",index:'1',align:''},
            {id:'6',text:'SvrMin',name:"svrMin",index:'1',align:''},
            {id:'7',text:'SvrMax',name:"svrMax",index:'1',align:''},
            {id:'8',text:'UseMem',name:"memUsed",index:'1',align:''},
            {id:'9',text:'UseCPU',name:"cpuUsed",index:'1',align:''}
        ],
        rowNum:10,
        pager : true,
        number:false,
        multiselect: false
    });
}
var Queue = function(){
    this.init('#queRefresh');
}
Queue.prototype = new DataState();
Queue.prototype.constructor =  Queue;
Queue.prototype.start = function(){
    $("#tuxQUEUE").empty();
    $("#tuxQUEUE").Grid({
        url : "${ctx}/appServer/tuxedo/data/queue/${serverName}",
        dataType: "json",
        colDisplay: false,
        clickSelect: true,
        draggable:false,
        height: "auto",
        searchClass:"queRefreshInput",
        searchBtn:"queRefreshButton",
        colums:[
            {id:'1',text:'Server',name:"server",index:'1',align:''},
            {id:'2',text:'Queueid',name:"queueId",index:'1',align:''},
            {id:'3',text:'SrvCnt',name:"srvCnt",index:'1',align:''},
            {id:'4',text:'Queued',name:"queued",index:'1',align:''}
        ],
        rowNum:10,
        pager : true,
        number:false,
        multiselect: false
    });
}

var Client = function(){
    this.init('#cltRefresh');
}
Client.prototype = new DataState();
Client.prototype.start = function(){
    $("#tuxCLIENT").empty();
    $("#tuxCLIENT").Grid({
        url : "${ctx}/appServer/tuxedo/data/client/${serverName}",
        dataType: "json",
        colDisplay: false,
        clickSelect: true,
        draggable:false,
        height: "auto",
        searchClass:"cltRefreshInput",
        searchBtn:"cltRefreshButton",
        colums:[
            {id:'1',text:'Name',name:"name",index:'1',align:''},
            {id:'2',text:'ClentPID',name:"pid",index:'1',align:''},
            {id:'3',text:'ClentAddr',name:"addr",index:'1',align:''},
            {id:'4',text:'Status',name:"status",index:'1',align:''},
            {id:'5',text:'Contime',name:"conTime",index:'1',align:''}
        ],
        rowNum:10,
        pager : true,
        number:false,
        multiselect: false
    });
}

var System = function(){
    this.init('#sysRefresh');
};
System.prototype = new DataState();
System.prototype.start = function(){
    $("#tuxSYSTEM").empty();
    $("#tuxSYSTEM").Grid({
        url : "${ctx}/appServer/tuxedo/data/system/${serverName}",
        dataType: "json",
        colDisplay: false,
        clickSelect: true,
        draggable:false,
        height: "auto",
        searchClass:"sysRefreshInput",
        searchBtn:"sysRefreshButton",
        colums:[
            {id:'1',text:'CoreFind',name:"coreFind",index:'1',align:''},
            {id:'2',text:'ErrorFind',name:"errorFind",index:'1',align:''},
            {id:'3',text:'WarnFind',name:"warnFind",index:'1',align:''},
            {id:'4',text:'LargueFind',name:"largueFile",index:'1',align:''},
            {id:'5',text:'FreeMem',name:"freeMem",index:'1',align:''},
            {id:'6',text:'IdleCPU',name:"idleCPU",index:'1',align:''},
            {id:'7',text:'SvrCnt',name:"svrCnt",index:'1',align:''},
            {id:'8',text:'QueCnt',name:"queCnt",index:'1',align:''},
            {id:'9',text:'CltCnt',name:"cltCnt",index:'1',align:''}
        ],
        rowNum:10,
        pager : true,
        number:false,
        multiselect: false
    });
}
</script>