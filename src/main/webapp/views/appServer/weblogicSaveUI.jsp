<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="msg" uri="http://mvc.one.sinosoft.com/validation/msg" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>新建监视器</title>
    <%@include file="/WEB-INF/layouts/base.jsp" %>
    <script type="text/javascript" src="${ctx}/global/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${ctx}/global/js/jquery.metadata.js"></script>
    <script type="text/javascript" src="${ctx}/global/js/validate.js"></script>
    <script type="text/javascript" src="${ctx}/global/js/jquery.form.js"></script>
    <script type="text/javascript">
        var weblogicValidator; //生成校验器
        $(function() {
            $("#monitorType").find("optgroup:eq(0)").find("option:eq(1)").attr("selected","selected");
            var editFlag = '${empty server}' == 'false';
            if(editFlag) {
                $("#serverName").attr({"readonly":true});
                var isSsl = '${server.isSsl}';
                $(".server_isSsl[value="+isSsl+"]").attr("checked", true);
            }
            weblogicValidator = buildValidator("weblogic_fm"); //构建校验器
            weblogicValidator.form(); clearError(); //先from可以实现实时校验
            $("input[name='isSsl']").click(function(){ //实现单选功能
                var flag = $(this).attr("checked");
                $("input[name='isSsl']").removeAttr("checked");
                if(flag) $(this).attr({"checked":"checked"});
            });

            $(".number_validate").keyup(function() {
                this.value=this.value.replace(/\D/g,'');
            }).blur(function() {
                this.value=this.value.replace(/\D/g,'');
            });

            $(".number2_validate").keyup(function() {
                this.value=this.value.replace(/[^\d.]/g,'');
            }).blur(function() {
                this.value=this.value.replace(/[^\d.]/g,'');
            });
        });
        function save(){
            if(!weblogicValidator.form()) return false;
            var data_ = $('#weblogic_fm').serialize();
            $.ajax({
                url: rootPath + '/appServer/weblogic/save',
                type:'POST',
                dataType:'JSON',
                data: data_,
                success: function(data){
                    if(data.type == 'success') {
                        window.location.href="${ctx}/appServer/weblogic/manager";
                    }
                }
            });
        }
    </script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp"%>
<div id="layout_center">
    <div class="main">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td>
                    <div class="add_monitor">
                        <%@include file="/WEB-INF/layouts/selectMonitorType.jsp"%>
                        <form id="weblogic_fm" action="${ctx}/appServer/weblogic/save" method="post">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="add_monitor_box add_form">
                                <tr>
                                    <td colspan="2" class="group_name">基本信息</td>
                                </tr>
                                <tr>
                                    <td width="25%">版本<span class="mandatory">*</span></td>
                                    <td>
                                        <select id="version" name="version" type="text" class="formtext" value="${server.version}">
                                            <option value="weblogic 9.x">weblogic 9.x</option>
                                            <option value="weblogic 10.x">weblogic 10.x</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="25%">站点名称<span class="mandatory">*</span></td>
                                    <td id="v_serverName">
                                        <input id="serverName" name="serverName" type="text" class="validate {required:true} formtext" value="${server.serverName}"/>
                                        <span id="v_serverName_validate"></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>IP地址<span class="mandatory">*</span></td>
                                    <td id="v_listenAddress">
                                        <input id="listenAddress" name="listenAddress" type="text" class="validate {required:true,IP_v:true} formtext" maxlength="15" size="30" value="${server.listenAddress}" />
                                        <span id="v_listenAddress_validate"></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>轮询间隔(s)<span class="mandatory">*</span></td>
                                    <td id="v_interval">
                                        <input id="interval" name="interval" type="text" class="validate {required:true,digits:true,min:30} number_validate formtext" maxlength="8" size="8" value="${server.interval}"/> <span class="mandatory">最小间隔30秒</span>
                                        <span id="v_interval_validate"></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>端口<span class="mandatory">*</span></td>
                                    <td id="v_listenPort">
                                        <input id="listenPort" name="listenPort" type="text" class="validate {required:true,port_v:true} number_validate formtext" maxlength="5" size="5" value="${server.listenPort}"/>
                                        <span id="v_listenPort_validate"></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" class="group_name">Weblogic监听信息</td>
                                </tr>

                                <tr>
                                    <td>weblogic监听地址<span class="mandatory">*</span></td>
                                    <td id="v_weblogicIp">
                                        <input id="weblogicIp" name="weblogicIp" type="text" class="validate {required:true,IP_v:true} number2_validate formtext" maxlength="15" size="30" value="${server.weblogicIp}"/>
                                        <span id="v_weblogicIp_validate"></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>weblogic监听端口<span class="mandatory">*</span></td>
                                    <td id="v_weblogicPort">
                                        <input id="weblogicPort" name="weblogicPort" type="text" class="validate {required:true,port_v:true} number_validate formtext" maxlength="5" size="5" value="${server.weblogicPort}"/>
                                        <span id="v_weblogicPort_validate"></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>domain名称<span class="mandatory">*</span></td>
                                    <td id="v_domainName">
                                        <input id="domainName" name="domainName" type="text" class="validate {required:true} formtext" maxlength="20" value="${server.domainName}"/>
                                        <span id="v_domainName_validate"></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>用户名<span class="mandatory">*</span></td>
                                    <td id="v_userName">
                                        <input id="userName" name="userName" type="text" class="validate {required:true} formtext" value="${server.userName}"/>
                                        <span id="v_userName_validate"></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>密码<span class="mandatory">*</span></td>
                                    <td id="v_password">
                                        <input name="password" type="text" class="validate {required:true} formtext" value="${server.password}" />
                                        <span id="v_password_validate"></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>使用SSL<span class="mandatory">*</span></td>
                                    <td id="v_isSsl">
                                        <input type="checkbox" class="validate {required:true} server_isSsl" name="isSsl" value="0"/>未使用
                                        <input type="checkbox" class="validate {required:true} server_isSsl" name="isSsl" value="1"/>使用
                                        <span id="v_isSsl_validate"></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="group_name">&nbsp;</td>
                                    <td class="group_name">
                                        <c:if test="${empty server}" >
                                            <input type="button" class="buttons" value="确定添加" onclick="save()" />　
                                        </c:if>
                                        <c:if test="${not empty server}" >
                                            <input type="button" class="buttons" value="确定修改" onclick="save()" />　
                                        </c:if>

                                        <input type="reset"  class="buttons" value="重 置" />　
                                        <input type="button" class="buttons" value="取 消" onclick="window.history.back()" />
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </td>
                <td width="15">&nbsp;</td>
                <td width="33%" style="vertical-align:top">
                    <div class="conf_box help">
                        <div class="conf_title">
                            <div class="conf_title_r"></div>
                            <div class="conf_title_l"></div>
                            <span>帮助信息</span>
                        </div>
                        <div class="conf_cont_box">
                            <div class="conf_cont">
                                最小轮询间隔为30秒<br />
                                其它帮助信息待定……<br />
                                <br /> <br /> <br /> <br /> <br /> <br /> <br />
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>
<%@include file="/WEB-INF/layouts/foot.jsp"%>
</body>
</html>
<c:if test="${not empty server}" >
    <script>
        $("#siteName").attr("readonly",true);
    </script>
</c:if>