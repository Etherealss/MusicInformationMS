/**
 * @fileoverview 登录注册
 * @author  wtk
 * @date 2021-06-08
 */
/* ------------------------------------登录------------------------------------ */
/**
 * 登录时检查输入框是否为空
 * 如果通过验证则调用函数提交登录数据
 * @param {Boolean} isAdminLogin
 */
function validateLogin(isAdminLogin) {
    console.log("登录检查");
    //获取提示框对象
    let tipObj = $("#login_tip");

    let id = $("#login_id").val();
    if (id == null || id === "") {
        //设置提示框内容
        setTip(tipObj, "请输入账号ID", "warning");
        return;
    } else if (!standardId(id)) {
        //检查是否为标准id
        //进入if语句则不是标准id
        //用户名不存在，可以使用
        setTip(tipObj, "账号ID仅能包含数字且在6~20个字符内！", "warning");
        return;
    }

    let password = $("#login_password").val();
    if (password == null || password === "") {
        //设置提示框内容
        setTip(tipObj, "请输入账号密码", "warning");
        return;
    } else if (!standardPw(password)) {
        //密码已输入，但不符合规定
        setTip(tipObj, "密码由6~20个大小写英文字母、数字或者下划线组成，请检查哦", "warning");
        return;
    }

    //通过验证，调用函数登录。
    ajaxlogin(isAdminLogin);
}

/**
 * 登录，数据提交后台
 * @param {Boolean} isAdminLogin
 */
function ajaxlogin(isAdminLogin) {
    console.log("提交登录");
    $.ajax({
        //请求类型
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        url: "/user/login",
        data: JSON.stringify({
            "userId": $("#login_id").val(),
            "password": $("#login_password").val(),
            "isAdminLogin": isAdminLogin
        }),
        /**
         * 下面这些是返回参数的文档注释
         * @param msg
         * @param {Number} msg.code 结果码
         * @param {String} msg.message 登录提示信息
         * @param {Object} msg.data.user 用户信息
         */
        success: function (msg) {
            console.log("登录成功：")
            console.log(msg)
            if (msg.code == 200) {
                //登录后跳转
                toastr.success("登录成功！即将跳转");
                // 刷新页面
                window.setTimeout(function () {
                    window.location.replace("musiclist.html");
                }, 500)
            } else {
                //登录失败，提示框显示登录原因
                if (msg.code == 404) {
                    setTip($("#login_tip"), "账号不存在", "danger");
                } else if (msg.code == 400) {
                    // 密码错误、非管理员
                    setTip($("#login_tip"), msg.message, "danger");
                } else if (msg.code == 1004) {
                    setTip($("#login_tip"), "用户已登录", "danger");
                } else {
                    setTip($("#login_tip"), "登录失败，请重试！", "danger");
                }
            }
        },
        error: function (msg) {
            //登录失败
            console.log("登录失败：")
            console.log(msg);
            if (msg.code == 404) {
                setTip($("#login_tip"), "账号不存在！", "danger");
            } else if (msg.code == 1004) {
                setTip($("#login_tip"), "用户已登录", "danger");
            } else {
                console.log("登录失败")
                setTip($("#login_tip"), "登录失败，请重试！", "danger");
            }
        }
    });
}

