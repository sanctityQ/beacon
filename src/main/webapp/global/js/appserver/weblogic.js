var serverName;

function chart_init(){
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
                            url:rootPath + "/appServer/weblogic/chart/cpu/"+serverName+"/latest",
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
                    url:rootPath + "/appServer/weblogic/chart/cpu/"+serverName+"/123",
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
                            url:rootPath + "/appServer/weblogic/chart/memory/"+serverName+"/latest",
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
                    url:rootPath + "/appServer/weblogic/chart/memory/"+serverName+"/all",
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

    //server_ram chart
    new Highcharts.Chart({
        chart: {
            renderTo: 'server_ram_line',
            type: 'line',
            height:230,
            events: {
                load: function() {

                    var series = this.series;
                    setInterval(function() {
                        $.ajax({
                            url:rootPath + "/appServer/weblogic/chart/server_ram/"+serverName+"/latest",
                            cache:false,
                            async:false,
                            success:function(back){
                                $(back).each(function(i,d) {
                                    if(series[i].data.length < 20){
                                        series[i].addPoint(d, true, false);
                                    }
                                    else{
                                        series[i].addPoint(d, true, true);
                                    }
                                });
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
        series: (function() {
            // generate an array of random data
            var data = [];
            $.ajax({
                url: rootPath + "/appServer/weblogic/chart/server_ram/"+serverName+"/123",
                cache:false,
                async:false,
                success:function(back){
                    data = back;
                }
            })
            return data;
        })(),
        colors: ['#87bdc9']
    });

    //server_throughput chart
    new Highcharts.Chart({
        chart: {
            renderTo: 'server_throughput_line',
            type: 'line',
            height:230,
            events: {
                load: function() {

                    var series = this.series;
                    //update chart data
                    setInterval(function() {
                         $.ajax({
                         url:rootPath + "/appServer/weblogic/chart/server_throughput/"+serverName+"/latest",
                         cache:false,
                         async:false,
                         success:function(back){
                             $(back).each(function(i,d) {
                                 $(back).each(function(i,d) {
                                     if(series[i].data.length < 20){
                                         series[i].addPoint(d, true, false);
                                     }
                                     else{
                                         series[i].addPoint(d, true, true);
                                     }
                                 });
                             });
                         }})
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
        series: (function() {
            // generate an array of random data
            var data = [];
            $.ajax({
                url: rootPath + "/appServer/weblogic/chart/server_throughput/"+serverName+"/123",
                cache:false,
                async:false,
                success:function(back){
                    data = back;
                }
            })
            return data;
        })(),
        colors: ['#87bdc9']
    });
};

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
    dynamic_.state.push(new ServerInfo());

    dynamic_.state.push(new WlsServer());
    dynamic_.state.push(new JVM());
    dynamic_.state.push(new ThreadPool());
    dynamic_.state.push(new JDBC());
    dynamic_.state.push(new Component());
    dynamic_.state.push(new JMS());
    dynamic_.state.push(new EjbPool());
    dynamic_.state.push(new EjbCache());
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
    this.switch.removeClass('refresh').addClass('refresh_dynamic');
    this.intervalId = setInterval(this.start,1000 * 30);
}
DataState.prototype.cancel = function(){
    this.switch.removeClass('refresh_dynamic').addClass('refresh')
    clearInterval(this.intervalId);
    this.intervalId = null;
}


function buildDataList(type, colums) {
    var DataList = function(){
        this.init('#'+type+"Refresh");
    };
    DataList.prototype = new DataState();
    DataList.prototype.start = function () {
        $("#"+type).empty();
        $("#"+type).Grid({
            url : rootPath + "/appServer/weblogic/data/"+type+"/"+serverName,
            dataType: "json",
            colDisplay: false,
            clickSelect: true,
            draggable:false,
            height: "auto",
            searchClass:"serverRefreshInput",
            searchBtn:"serverRefreshButton",
            colums:colums,
            rowNum:10,
            pager : true,
            number:false,
            multiselect: false
        });
    }
    return DataList;
}

var ServerInfo = function(){
    this.init('#serverInfoRefresh');
};
ServerInfo.prototype = new DataState();
ServerInfo.prototype.start = function () {
    $("#serverInfo").empty();
    $("#serverInfo").Grid({
        url: rootPath + "/appServer/weblogic/serverInfo/"+serverName,
        dataType: "json",
        colDisplay: false,
        clickSelect: true,
        draggable: false,
        height: "auto",
        colums: [
            {id: '1', text: '服务器名称', name: "server_name", index: '1', align: ''},
            {id: '2', text: '监听地址', name: "listen_address", index: '1', align: ''},
            {id: '3', text: '监听端口', name: "listen_port", index: '1', align: ''},
            {id: '4', text: '健康状态', name: "health", index: '1', align: ''},
            {id: '5', text: '运行状态', name: "state", index: '1', align: ''}
        ],
        rowNum: 10,
        pager: false,
        number: false,
        multiselect: false
    });
}

var WlsServer = buildDataList("WlsServer", [
    {id: '1', text: '排名', name: "sort", index: '1', align: ''},
    {id: '2', text: 'ServerName', name: "ServerName", index: '1', align: ''},
    {id: '3', text: 'ListenAddress', name: "ListenAddress", index: '1', align: ''},
    {id: '4', text: 'ListenPort', name: "ListenPort", index: '1', align: ''},
    {id: '5', text: 'Health', name: "Health", index: '1', align: ''},
    {id: '6', text: 'State', name: "State", index: '1', align: ''},
    {id: '7', text: 'OpenSocketNum', name: "OpenSocketNum", index: '1', align: ''}
]);


var JVM = buildDataList("JVM", [
    {id: '1', text: '排名', name: "sort", index: '1', align: ''},
    {id: '2', text: 'server名称', name: "ServerName", index: '1', align: ''},
    {id: '3', text: '空闲heap', name: "FreeHeap", index: '1', align: ''},
    {id: '4', text: '当前heap使用数', name: "CurrentHeap", index: '1', align: ''},
    {id: '5', text: '空闲heap百分比', name: "FreePercent", index: '1', align: ''}
]);
var ThreadPool = buildDataList("ThreadPool", [
    {id: '1', text: '排名', name: "sort", index: '1', align: ''},
    {id: '2', text: 'Server名称', name: "ServerName", index: '1', align: ''},
    {id: '3', text: '空闲数量', name: "IdleCount", index: '1', align: ''},
    {id: '4', text: '备用数量', name: "StandbyCount", index: '1', align: ''},
    {id: '5', text: '总量', name: "TotalCount", index: '1', align: ''},
    {id: '6', text: '吞吐量', name: "Thoughput", index: '1', align: ''},
    {id: '7', text: '队列大小', name: "QueueLength", index: '1', align: ''}
]);
var JDBC = buildDataList("JDBC", [
    {id: '1', text: '排名', name: "sort", index: '1', align: ''},
    {id: '2', text: 'Server名称', name: "ServerName", index: '1', align: ''},
    {id: '3', text: '连接池名称', name: "JDBCName", index: '1', align: ''},
    {id: '4', text: '活动连接数', name: "ActiveCount", index: '1', align: ''},
    {id: '5', text: '连接最高值', name: "ActiveHigh", index: '1', align: ''},
    {id: '6', text: '连接池大小', name: "CurrCapacity", index: '1', align: ''},
    {id: '7', text: '连接泄露数', name: "LeakCount", index: '1', align: ''},
    {id: '8', text: '状态', name: "State", index: '1', align: ''}
]);
var Component = buildDataList("Component", [
    {id: '1', text: '排名', name: "sort", index: '1', align: ''},
    {id: '2', text: 'Server名称', name: "ServerName", index: '1', align: ''},
    {id: '3', text: '应用名称', name: "WebAppName", index: '1', align: ''},
    {id: '4', text: '部署状态', name: "DeploymentState", index: '1', align: ''},
    {id: '5', text: '状态', name: "Status", index: '1', align: ''},
    {id: '6', text: '组件名称', name: "State", index: '1', align: ''},
    {id: '7', text: '最高会话数', name: "OpenSessionsHighCount", index: '1', align: ''},
    {id: '8', text: '当前会话数', name: "OpenSessionsCurrentCount", index: '1', align: ''} ,
    {id: '9', text: '累计打开会话数', name: "SessionsOpenedTotalCount", index: '1', align: ''}
]);
var JMS = buildDataList("JMS", [
    {id: '1', text: '排名', name: "sort", index: '1', align: ''},
    {id: '2', text: 'Server名称', name: "ServerName", index: '1', align: ''},
    {id: '3', text: '名称', name: "name", index: '1', align: ''},
    {id: '4', text: '当前字节数', name: "BytesCurrentCount", index: '1', align: ''},
    {id: '5', text: '最高字节数', name: "BytesHighCount", index: '1', align: ''},
    {id: '6', text: '挂起字节数', name: "BytesPendingCount", index: '1', align: ''},
    {id: '7', text: '接受字节数', name: "BytesReceivedCount", index: '1', align: ''},
    {id: '8', text: '当前消息数', name: "MessagesCurrentCount", index: '1', align: ''},
    {id: '9', text: '最高消息数', name: "MessagesHighCount", index: '1', align: ''},
    {id: '10', text: '挂起消息数', name: "MessagesPendingCount", index: '1', align: ''},
    {id: '11', text: '接受消息数', name: "MessagesReceivedCount", index: '1', align: ''}
]);
var EjbPool = buildDataList("EjbPool", [
    {id: '1', text: '排名', name: "sort", index: '1', align: ''},
    {id: '2', text: 'Server名称', name: "ServerName", index: '1', align: ''},
    {id: '3', text: '名称', name: "EJBPoolName", index: '1', align: ''},
    {id: '4', text: '使用中bean数量', name: "BeansInUseCount", index: '1', align: ''},
    {id: '5', text: '当前使用中bean数量', name: "BeansInUseCurrentCount", index: '1', align: ''},
    {id: '6', text: '总计访问数', name: "AccessTotalCount", index: '1', align: ''},
    {id: '7', text: '总计销毁数', name: "DestroyedTotalCount", index: '1', align: ''},
    {id: '8', text: '空闲bean数量', name: "IdleBeansCount", index: '1', align: ''},
    {id: '9', text: '总计错失数量', name: "MissTotalCount", index: '1', align: ''},
    {id: '10', text: '当前池化bean数量', name: "PooledBeansCurrentCount", index: '1', align: ''},
    {id: '11', text: '总计超时数量', name: "TimeoutTotalCount", index: '1', align: ''},
    {id: '12', text: '当前等待数量', name: "WaiterCurrentCount", index: '1', align: ''}
]);
var EjbCache = buildDataList("EjbCache", [
    {id: '1', text: '排名', name: "sort", index: '1', align: ''},
    {id: '2', text: 'Server名称', name: "ServerName", index: '1', align: ''},
    {id: '3', text: '名称', name: "EJBCacheName", index: '1', align: ''},
    {id: '4', text: '缓存访问数量', name: "CacheAccessCount", index: '1', align: ''},
    {id: '5', text: '激活数量', name: "ActivationCount", index: '1', align: ''},
    {id: '6', text: '缓存bean当前数量', name: "CacheBeansCurrentCount", index: '1', align: ''},
    {id: '7', text: '缓存击中数量', name: "CacheHitCount", index: '1', align: ''},
    {id: '8', text: '缓存错失数量', name: "CacheMissCount", index: '1', align: ''},
    {id: '9', text: '钝化数量', name: "PassivationCount", index: '1', align: ''}
]);
