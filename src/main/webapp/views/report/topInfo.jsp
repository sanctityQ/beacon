<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>monitor监控系统</title>
<%@include file="/WEB-INF/layouts/base.jsp" %>
    <script language="javascript" src="${ctx}/global/js/one.layout.js"></script>
<script type="text/javascript">
    $(function(){
        $(".form").parent().parent().addClass("seleck").siblings().removeClass("seleck");
        $("body").layout();

        function query(){
            var loc = window.location.toString().split('&');
            var p = loc[0].split('?');
            loc[0] = p[0]+'?top='+ $('#top').val();
            window.location=loc[0]+"&dateSeries="+$('#dateSeries').val()+"&"+loc[2];
        }

        $('#dateSeries').change(query);
        $('#top').change(query);

        <c:if test="${not empty dateSeries}">
        <%--时间间隔赋值--%>
        $('#dateSeries').val('${dateSeries}');
        </c:if>
        $('#top').val('${top}');
        <c:if test="${not empty gridData}">
        var grid_data = ${gridData};

        $("#sevenday_grid").Grid({
            girdData : grid_data,
            dataType: "json",
            height: 'auto',
            colums:[
                {id:'1',text:'进程名称',name:"processName",width:'',index:'1',align:'',color:''},
                {id:'2',text:'占用',name:"occupy",width:'',index:'1',align:'',color:''},
                {id:'3',text:'进程ID',name:"processId",width:'',index:'1',align:'',color:''}
            ],
            rowNum:100000,
            pager : false,
            number:false,
            multiselect:false
        });

        var chart3 = new Highcharts.Chart({
            chart: {
                renderTo: 'container',
                type: 'column',
                margin: [ 50, 50, 100, 80]
            },
            title: {
                text: ''
            },
            xAxis: {
                categories: ${chartCategories},
                labels: {
                    rotation: -45,
                    align: 'right',
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: '${attribute.attributeCn}(${attribute.units})'
                }
            },
            legend: {
                enabled: false
            },
            credits: {
                text: '',
                href: ''
            },
            tooltip: {
                formatter: function() {
                    return '<b>${attribute.attributeCn}('+ this.x +
                            '):</b>'+ Highcharts.numberFormat(this.y, 1) +
                            ' ${attribute.units}';
                }
            },
            series: [{
                name: '${attribute.attribute}',

                //color:[], TODO 根据优先级定义不同的颜色
                data: ${chartData},
                dataLabels: {
                    enabled: false,
                    rotation: -90,
                    color: '#FFFFFF',
                    align: 'right',
                    x: -3,
                    y: 10,
                    formatter: function() {
                        return this.y;
                    },
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
            }]
        });



            $('#expPdf').click(function(e){
                if(chart3.xAxis[0].categories.length == 0){
                    msgAlert("Alert", "当前chart没有数据，无法导出.");
                    return;
                }
                var svg=chart3.getSVG();
                post_to_url('${ctx}/report/export',
                        {'svg':svg,
                         'attribute':'${attribute.attribute}',
                         'title':'${dateSeries.description}-${type.description}的${attribute.attributeCn}(${attribute.units})',
                         'gridData':'${gridData}',
                         'gridTitle':"['进程名称','占用','进程ID']"
                        });
            });

        </c:if>

    });


    //自动创建form并提交
    function post_to_url(path, params, method) {
        method = method || "post"; // Set method to post by default, if not specified.
        // The rest of this code assumes you are not using a library.
        // It can be made less wordy if you use one.
        var form = document.createElement("form");
        form.setAttribute("method", method);
        form.setAttribute("action", path);

        for(var key in params) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key].toString());

            form.appendChild(hiddenField);
        }
        document.body.appendChild(form);
        form.submit();
    }


</script>
</head>

<body>
<div id="layout_center">
    <div class="main" style="padding-bottom:60px">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="repor_tool">
            <tr>
                <td>
                    <select id="top" name="top" class="diySelect" style="width:80px">
                        <option value="five">前5</option>
                        <option value="ten">前10</option>
                    </select>
                    <select id="dateSeries" name="dateSeries" class="diySelect" style="width:80px">
                        <option value="today">今天</option>
                        <option value="yesterday">昨天</option>
                        <option value="lastWeek">最近一周</option>
                        <option value="lastMonth">最近一月</option>
                    </select>
                </td>
                <td align="right"></td>
                <td width="120">

                </td>
                <td width="100" align="right"><a id='expPdf' href="javascript:void(0);" class="expor_pdf"><img src="${ctx}/global/images/icon_pdf.gif" width="16" height="16" />导出PDF</a></td>
            </tr>
        </table>
        <c:if test="${empty gridData}">
            <p class="hint">暂未数据……</p>
            <br />
        </c:if>

        <c:if test="${not empty gridData}">
        <div class="threshold_file">
            <div class="sub_title">${dateSeries.description}-${type.description}的${attribute.attributeCn}(${attribute.units})</div>
            <table class="base_info" width="100%" cellpadding="0" cellspacing="0">
                <tr>
                    <td colspan="2"><div id="container" style="width:98%" ></div></td>
                </tr>
            </table>
        </div>
        <br />
        <div class="threshold_file">
            <div class="sub_title">${attribute.attributeCn}(${attribute.units})</div>
            <div id="sevenday_grid"></div>
        </div>
        <br />
        </c:if>
        <div class="threshold_file">
            <div class="sub_title">备注</div>
            <ul class="remark">
                <li><b>存档:</b> 所有数据每小时存档一次。例如早上10点到11点的数据被存档并标记为早上11点。如果在归档间隔的1小时中监视器实例完全停止，则本时段的该监视器的数据不进行归档.</li>
                <li><b>最小值:</b> 它表示一小时之内所收集的最低值。例如，2013年9月1日14点显示的6就表示从2013年9月1日13点到14点之间收集的最低值为'6'.</li>
                <li><b>最大值:</b> 它表示一小时之内所收集的最高值。例如，2013年9月1日14点显示的12就表示从2013年9月1日13点到14点之间收集的最高值为'12'.</li>
                <li><b>每小时平均值:</b> 它表示一小时之内对所收集值取的平均值。例如，2013年9月1日14点显示的9就表示从2013年9月1日13点到14点之间收集值的平均值为'9'.</li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
