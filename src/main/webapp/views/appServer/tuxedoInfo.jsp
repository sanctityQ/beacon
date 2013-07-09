<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="msg" uri="http://mvc.one.sinosoft.com/validation/msg" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Tuxedo站点</title>
<%@include file="/WEB-INF/layouts/base.jsp" %>
<script type="text/javascript">
$(function(){
    //todo 需要将当前页面的刷新间隔时间调整为从站点对象中动态获取

    var autoWidth = $("#layout_center").width() - 100;
    $("#grid_cpudo,#grid_cpudo_tool").width(autoWidth)
    $("#cipan_space_detail").width(autoWidth + 65)

    $("#emergencyList").Grid({
        url : "${ctx}/alarm/manager/resource/${serverName}",
        dataType: "json",
        colDisplay: false,
        clickSelect: true,
        draggable:false,
        height: "auto",
        colums:[
            {id:'1',text:'状态',name:"appellation",index:'1',align:'',width:'52'},
            {id:'2',text:'消息',name:"appellation",index:'1',align:''},
            {id:'5',text:'时间',name:"appellation",index:'1',align:''}
        ],
        rowNum:10,
        pager : false,
        number:false,
        multiselect: false
    });

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
    $("#RAM_top5").Grid({
        url : "${ctx}/appServer/tuxedo/memory/top/${serverName}",
        dataType: "json",
        colDisplay: false,
        clickSelect: true,
        draggable:false,
        height: "auto",
        colums:[
            {id:'1',text:'排名',name:"sort",index:'1',align:''},
            {id:'2',text:'PID',name:"pid",index:'1',align:''},
            {id:'3',text:'使用内存',name:"used",index:'1',align:''}
        ],
        rowNum:10,
        pager : false,
        number:false,
        multiselect: false
    });
    $("#trade_top5").Grid({
        url : "${ctx}/appServer/tuxedo/transcation/top/${serverName}",
        dataType: "json",
        colDisplay: false,
        clickSelect: true,
        draggable:false,
        height: "auto",
        colums:[
            {id:'1',text:'排名',name:"sort",index:'1',align:''},
            {id:'2',text:'rqdone',name:"rqdone",index:'1',align:''},
            {id:'3',text:'progname',name:"progname",index:'1',align:''},
            {id:'4',text:'pid',name:"pid",index:'1',align:''}
        ],
        rowNum:10,
        pager : false,
        number:false,
        multiselect: false
    });


    new DataState().init();
    //setInterval(dataState(), 1000 * 30);

    $("#tabs").tabs({closeTab:false});
    if($.browser.msie && ($.browser.version == "7.0")){
        var center = $("#layout_center")
        $("#main").width(center.width() - 31).height(center.height() - 30)
    };


    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });

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
                                if(series.data.length < 20){
                                    series.addPoint(back, true, false);
                                }
                                else{
                                    series.addPoint(back, true, true);
                                }
                            }
                        })
                    }, 30000);
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
            }
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
                                if(series.data.length < 20){
                                    series.addPoint(back, true, false);
                                }
                                else{
                                    series.addPoint(back, true, true);
                                }
                            }
                        })
                    }, 30000);
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
            name: '内存使用率',
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
                                if(series0.data.length < 20){
                                    series0.addPoint(back, true, false);
                                }
                                else{
                                    series0.addPoint(back, true, true);
                                }
                            }
                        })
                    }, 30000);
                    setInterval(function() {
                        $.ajax({
                            url:"${ctx}/appServer/tuxedo/chart/client/${serverName}/latest/busy",
                            cache:false,
                            async:false,
                            success:function(back){
                                if(series1.data.length < 20){
                                    series1.addPoint(back, true, false);
                                }
                                else{
                                    series1.addPoint(back, true, true);
                                }
                            }
                        })
                    }, 30000);
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
            tickInterval: 1
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
        colors: ['#e77c52']
    });

    function getServerLatestData(){
        $.ajax({
            url: '${ctx}/appServer/tuxedo/view/${serverName}/latest',
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

    setInterval(getServerLatestData, 1000 * 30);

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
                                if(series.data.length < 20){
                                    series.addPoint(back, true, false);
                                }
                                else{
                                    series.addPoint(back, true, true);
                                }
                            }
                        })
                    }, 30000);
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
            tickInterval: 0.5
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

});



function viewWindow(e){
    var rows = $(e).parent().parent();
    var id = rows.attr('id');
    var name = rows.children("td").eq(1).text();
    var title = "历史记录: " + name;
    var temWin = $("body").window({
        "id":"window",
        "title":title,
        "url":"sevenDayAvailableLinux.html",
        "hasIFrame":true,
        "width": 740,
        "height":440,
        "diyButton":[{
            "id": "btOne",
            "btClass": "buttons",
            "value": "关闭",
            "onclickEvent" : "selectLear",
            "btFun": function() {
                temWin.closeWin();
            }
        }
        ]
    });
}
function viewWindow30(e){
    var rows = $(e).parent().parent();
    var id = rows.attr('id');
    var name = rows.children("td").eq(1).text();
    var title = "历史记录: " + name;
    var temWin = $("body").window({
        "id":"window",
        "title":title,
        "url":"thirdthDayAvailableLinux.html",
        "hasIFrame":true,
        "width": 740,
        "height":440,
        "diyButton":[{
            "id": "btOne",
            "btClass": "buttons",
            "value": "关闭",
            "onclickEvent" : "selectLear",
            "btFun": function() {
                temWin.closeWin();
            }
        }
        ]
    });
}
function viewWindowCPU(e){
    var rows = $(e).parent().parent();
    var id = rows.attr('id');
    var name = rows.children("td").eq(1).text();
    var title = "历史记录: " + name;
    var temWin = $("body").window({
        "id":"window",
        "title":title,
        "url":"historyCPULinux.html",
        "hasIFrame":true,
        "width": 740,
        "height":440,
        "diyButton":[{
            "id": "btOne",
            "btClass": "buttons",
            "value": "关闭",
            "onclickEvent" : "selectLear",
            "btFun": function() {
                temWin.closeWin();
            }
        }
        ]
    });
}
function viewWindowNC(e){
    var rows = $(e).parent().parent();
    var id = rows.attr('id');
    var name = rows.children("td").eq(1).text();
    var title = "历史记录: " + name;
    var temWin = $("body").window({
        "id":"window",
        "title":title,
        "url":"historyDiskLinux.html",
        "hasIFrame":true,
        "width": 740,
        "height":440,
        "diyButton":[{
            "id": "btOne",
            "btClass": "buttons",
            "value": "关闭",
            "onclickEvent" : "selectLear",
            "btFun": function() {
                temWin.closeWin();
            }
        }
        ]
    });
}
function viewWindowCP(e){
    var rows = $(e).parent().parent();
    var id = rows.attr('id');
    var name = rows.children("td").eq(1).text();
    var title = "历史记录: " + name;
    var temWin = $("body").window({
        "id":"window",
        "title":title,
        "url":"historyMemoryLinux.html",
        "hasIFrame":true,
        "width": 740,
        "height":440,
        "diyButton":[{
            "id": "btOne",
            "btClass": "buttons",
            "value": "关闭",
            "onclickEvent" : "selectLear",
            "btFun": function() {
                temWin.closeWin();
            }
        }
        ]
    });
}



/**
* DataState 数据状态对象
* @constructor
 */
var DataState = function(){}
DataState.prototype.init = function(){
    (new Server()).start();
    (new Queue()).start();
    (new Client()).start();
    (new System()).start();
    //this.start();
}
DataState.prototype.start = function(){
}
DataState.prototype.toggle = function(){
    if(this.intervalId){
        $(this).removeClass('refresh_dynamic');
        $(this).addClass('refresh');
        clearInterval(this.intervalId);
        this.intervalId = null;
    }else{
        $(this).removeClass('refresh');
        $(this).addClass('refresh_dynamic');
        this.intervalId = setInterval(this.start,1000 * 30);
      //  alert('2:'+this.intervalId);
    }
}


/**
* Server对象
* @constructor
*/
var Server = function(){};
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
//alert(Server.prototype.constructor);
var Queue = function(){
    var refreshId = '#queRefresh';
    $(refreshId).click(this.toggle);
}
Queue.prototype = new DataState();
Queue.prototype.constructor =  Queue.constructor;
Queue.prototype.start = function(){
    //alert()
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

var Client = function(){}
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

var System = function(){};
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
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
    <div class="main-linux" id="main">
        <ul class="crumbs">
            <li><a href="#" target="_blank">Tuxedo监视器</a> ></li>
            <li><b>${serverName}</b></li>
        </ul>
        <hr class="top_border" />
        <div id="tabs">
            <ul>
                <li class="tabs_select">状态监控</li>
                <li>数据监控</li>
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
                                    <td class="monitorinfoeven">${tuxVersion}</td>
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
                                    <a href="javascript:void(0)" class="refresh" title="刷新"></a>
                                    <a href="alertList.html" class="alerts_list">历史预警</a>
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
                        <a href="javascript:void(0)" class="refresh" title="刷新"></a>
                        列队统计TOP5
                    </div>
                    <div id="rank_top"></div>
                </div>


                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="49%">
                            <div class="hr_box h_b">
                                <div class="head-cpu">
                                    <a href="javascript:void(0)" class="refresh" title="刷新"></a>
                                    CPU使用率-最近6小时
                                </div>
                                <div id="CPU_line" style="height:230px;padding-top:15px"></div>
                            </div>
                        </td>
                        <td width="2%">&nbsp;</td>
                        <td width="49%">
                            <div class="hr_box h_b">
                                <div class="head-cpu">
                                    <a href="javascript:void(0)" class="refresh" title="刷新"></a>
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
                            <div class="head-cpu"> <a href="javascript:void(0)" class="refresh" title="刷新"></a> 内存使用最多TOP5</div>
                            <div id="RAM_top5"></div>
                        </div></td>
                        <td width="2%">&nbsp;</td>
                        <td width="49%"><div class="hr_box h_b">
                            <div class="head-cpu"> <a href="javascript:void(0)" class="refresh" title="刷新"></a> 交易调用最多TOP5 </div>
                            <div id="trade_top5"></div>
                        </div></td>
                    </tr>
                </table>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="49%"><div class="hr_box h_b">
                            <div class="head-cpu"> <a href="javascript:void(0)" class="refresh" title="刷新"></a> 客户端统计</div>
                            <div id="client_line" style="height:230px;padding-top:15px"></div>
                        </div></td>
                        <td width="2%">&nbsp;</td>
                        <td width="49%"><div class="hr_box h_b">
                            <div class="head-cpu"> <a href="javascript:void(0)" class="refresh" title="刷新"></a> 吞吐量 </div>
                            <div id="throughput_line" style="height:230px;padding-top:15px"></div>
                        </div></td>
                    </tr>
                </table>

            </div>
            <div class="second">
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo serverRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach serverRefreshInput" />TUX_SERVER
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
                        <input type="text" class="formtext list_seach cltRefreshInput" />TUX_CLIENT
                    </div>
                    <div id="tuxCLIENT"></div>
                </div>
                <br />
                <div class="hr_box h_b">
                    <div class="head-cpu">
                        <a href="javascript:void(0)" class="refresh ico_seo sysRefreshButton" title="刷新"></a>
                        <input type="text" class="formtext list_seach sysRefreshInput" />TUX_SYSTEM
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
