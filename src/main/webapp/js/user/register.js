/**
 * @fileoverview 注册相关
 * @author  wtk
 * @date 2021-06-14
 */

/**
 * 添加注册模态框的HTML
 */
function addRegisterModal() {
    //注册模态框中输入框自动获取焦点
    $('#register_modal').on('shown.bs.modal', function () {
        $("#register_nickname").focus();
    });
    //注册时输入框自动检查输入
    addAutoCheck();
}

/**
 * 重置注册模态框
 */
function registerReset() {
    //重置表单
    document.getElementById("register_form").reset();
    //重置提示框
    getPreparedTip($("#register_tip"));
}

/**
 * 注册时的自动检测功能
 */
addAutoCheck = function () {
    //获取注册提示框对象
    let regiTip = $("#register_tip");

    //获取昵称输入框
    let nickObj = $("#register_nickname");
    //输入改变时检查
    nickObj.on("change", function () {
        let nickVal = nickObj.val();
        if (nickVal != null && nickVal !== "") {
            //昵称非空时检查用户昵称是否可用
            validateNickname(regiTip, nickVal);
        }
    })

    /*
    自动检测两次密码是否相同，同时会检测密码的合法性
    因为注册和修改密码都会有相同的操作所以写在函数里
    注意！提示框会在函数中调用getTipDiv()再次获取，以清除原有样式
    因此这里只需要传入提示框对象即可
     */
    pwOnchangeCheck(regiTip, $("#register_pw1"), $("#register_pw2"));

    //注册时ID输入框输入改变就判断用户ID是否已存在
    let useridObj = $("#register_userid");
    useridObj.on("change", function () {
        validateUserId(regiTip, useridObj.val());
    })
}

/**
 * 注册时检查输入的数据
 * 如果通过验证则调用函数，提交注册数据
 */
function validateRegister() {
    //获取提示框对象
    let tipObj = $("#register_tip");
    /*
     * 这里会判断用户id是否包含非数字字符 或者已存在
     * 虽然在id输入框输入改变时会自动调用该方法检测，
     * 但是用户忽略提示提交代码，
     * 也可能因为触发其他错误导致该错误被覆盖
     * 所以还是要在提交时再一次检查id
     */
    let id = $("#register_userid").val();
    if (!validateUserId(tipObj, id)) {
        /*
         * id不可用时，函数返回值为false，这里会进入该if语句
         * 相应的提示语句已经在validateUserId()中给出，此处不必再次提示
         */
        return;
    }

    let nickname = $("#register_nickname").val();
    if (nickname == null || nickname === "") {
        setTip(tipObj, "请选择一个昵称", "warning");
        return;
    } else if (!validateNickname(tipObj, nickname)) {
        //昵称非空时检查用户昵称是否可用
        return;
    }

    //如果输入了密码，则再次验证密码是否规范
    //不这么做的话，密码不符合规定依旧可以提交
    if (!validatePw(tipObj, $("#register_pw1").val(), $("#register_pw2").val())) {
        //validatePw()函数返回false，说明验证不通过
        //提示信息在validatePw函数中已给出
        return;
    }

    //所有信息都没有问题，调用函数通过ajax提交数据给后台
    ajaxPostRegister();
}

/**
 * 注册数据提交后台
 */
function ajaxPostRegister() {
    $.ajax({
        //请求类型
        type: "POST",
        //预期服务器返回的数据类型
        dataType: "json",
        url: "/user/register",
        data: $("#register_form").serialize(),
        success: function (msg) {
            console.log("注册ajax success：")
            console.log(msg)
            if (msg.code == 200) {
                //注册成功
                //关闭注册窗口
                $("#register_close").click();
                toastr.success("注册成功！请登录");
            } else {
                //注册失败
                setTip($("#register_tip"), msg.message, "danger");
            }
        },
        error: function (msg) {
            console.log("注册ajax error：")
            console.log(msg)
            //注册失败
            setTip($("#register_tip"), "注册出现异常，请重试！", "danger");
        }
    });
}