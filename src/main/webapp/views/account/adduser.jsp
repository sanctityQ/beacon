<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="/WEB-INF/layouts/base.jsp" %>
    <script type="text/javascript" src="${ctx}/global/js/jquery.form.js"></script>
    <script type="text/javascript">

        function toSaveUser() {
            var loginName = $("#loginName").val();
            if(!loginName || loginName == "") {
                msgAlert("系统消息", "用户名不能为空");
                return;
            }
            var password = $("#password").val();
            if(!password || password == "") {
                msgAlert("系统消息", "密码不能为空");
                return;
            }

            var passwordConfirm = $("#passwordConfirm").val();
            if(!passwordConfirm || passwordConfirm == "" ) {
                msgAlert("系统消息", "确认密码不能为空");
                return;
            }
            if(password != passwordConfirm) {
                msgAlert("系统消息", "密码与确认密码不一致");
                return;
            }

            var name = $("#normalStatus").val();
            if(!$("#normalStatus").attr("checked") && !$("#lockStatus").attr("checked")) {
                msgAlert("系统消息", "状态必须选择");
                return;
            }

            $('#saveUserForm').ajaxForm({
                dataType: "json",
                success: function(data) {
                    if(data.status == "success") {
                        msgSuccess("系统消息", "用户保存成功！", function() {
                            location.href = "${ctx}/account/user/list";
                        });
                    }
                },
                error: function() {
                    msgSuccess("系统消息", "用户保存失败！");
                }
            }).submit();
            //$("#saveUserForm").submit();
        }

    </script>
</head>

<body>
<%@include file="/WEB-INF/layouts/menu.jsp" %>
<div id="layout_center">
    <div class="main">
        <div class="add_monitor user_manager">
            <h2 class="title2"><strong class="right"><a href="${ctx}/account/user/list">返回用户列表</a></strong><b>用户信息</b>
            </h2>

            <form action="${ctx}/account/user/save" method="post" id="saveUserForm">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="add_monitor_box add_form">
                    <input type="hidden" name="id" value="${user.id }"/>
                    <tr>
                        <td colspan="2" class="group_name">账户信息</td>
                    </tr>
                    <tr>
                        <td>用户名<span class="mandatory">*</span></td>
                        <td><input name="loginName" id="loginName" type="text" class="formtext" value="${user.loginName }"/></td>
                    </tr>
                    <tr>
                        <td>密码<span class="mandatory">*</span></td>
                        <td><input name="password" id="password" type="password" class="formtext" value="${user.password }"/></td>
                    </tr>
                    <tr>
                        <td>角色<span class="mandatory">*</span></td>
                        <td><input name="role" id="role" type="password" class="formtext" value="${user.password }"/></td>
                    </tr>
                    <tr>
                        <td>确认密码<span class="mandatory">*</span></td>
                        <td><input name="passwordConfirm" id="passwordConfirm" type="password" class="formtext" value="${user.password }"/></td>
                    </tr>
                    <tr>
                        <td>状态<span class="mandatory">*</span></td>
                        <td><input id="normalStatus" name="status" type="radio"
                                   value="1" ${user.status eq "1"  ? "checked='checked'" : ''} />
                            <label for="normalStatus">正常</label>
                            　 <input id="lockStatus" type="radio" name="status"
                                     value="0"  ${user.status eq "0"  ? "checked='checked'" : ''} />
                            <label for="lockStatus">锁定</label></td>
                    </tr>
                    <tr>
                        <td colspan="2" class="group_name">基本信息</td>
                    </tr>
                    <tr>
                        <td width="25%">姓名</td>
                        <td><input name="name" id="name" value="${user.name }" type="text" class="formtext"/></td>
                    </tr>
                    <tr>
                        <td>手机号</td>
                        <td><input name="phone" id="phone" type="text" class="formtext" value="${user.phone }"/></td>
                    </tr>
                    <tr>
                        <td>邮箱</td>
                        <td><input name="email" id="email" type="text" class="formtext" value="${user.email }"/></td>
                    </tr>

                    <tr>
                        <td class="group_name">&nbsp;</td>
                        <td class="group_name">
                            <input type="button" class="buttons" value="确定" onclick="toSaveUser()"/>　
                            <input type="reset" class="buttons" value="重 置"/>　
                            <input type="button" class="buttons" value="取 消" onclick="window.history.back()"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/layouts/foot.jsp" %>
</body>
</html>
