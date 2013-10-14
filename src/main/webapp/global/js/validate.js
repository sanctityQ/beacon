/**
 * 构建校验规则
 */
function buildValidator(formID) {
    return $("#"+formID).validate({
        submitHandler: function() {
            return false;
        },
        success:function (label) {
            var $obj = $(label).parent();
            $obj.removeClass("prompt");
            $obj.html("");
        },
        errorPlacement: function(error, element) {
            $(error).addClass("error_validate");
            var groupId = $(element).parent().attr("id");
            var $validate = $("#"+groupId+"_validate");
            $validate.addClass("prompt");
            $validate.html("").append(error);
        }
    });
}

/**
 * 清空错误信息
 */
function clearError() {
    $(".validate").each(function(i, d) {
        var groupId = $(d).parent().attr("id");
        var $validate = $("#"+groupId+"_validate");
        $validate.removeClass("prompt");
        $validate.html("");
    });
}

jQuery.validator.addMethod("IP_v",function(value, element, params) {
    return /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/.test(value);
},"IP地址不合法");

jQuery.validator.addMethod("port_v",function(value, element, params) {
    return /^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/.test(value);
},"端口号不合法");


jQuery.extend(jQuery.validator.messages, {
    required: "请填写本字段",
    remote: "验证失败",
    email: "请输入正确的电子邮件",
    url: "请输入正确的网址",
    date: "请输入正确的日期",
    dateISO: "请输入正确的日期 (ISO).",
    number: "请输入正确的数字",
    digits: "请输入正确的整数",
    creditcard: "请输入正确的信用卡号",
    equalTo: "请再次输入相同的值",
    accept: "请输入指定的后缀名的字符串",
    maxlength: jQuery.validator.format("允许的最大长度为 {0} 个字符"),
    minlength: jQuery.validator.format("允许的最小长度为 {0} 个字符"),
    rangelength: jQuery.validator.format("允许的长度为{0}和{1}之间"),
    range: jQuery.validator.format("请输入介于 {0} 和 {1} 之间的值"),
    max: jQuery.validator.format("请输入一个最大为 {0} 的值"),
    min: jQuery.validator.format("请输入一个最小为 {0} 的值")
});