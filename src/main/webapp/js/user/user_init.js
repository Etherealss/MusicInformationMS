/**
 * @fileoverview 用户页眉信息初始化
 * @author  wtk
 * @date 2021-06-17
 */
/**
 * 获取并设置用户信息
 * 因为ajax是异步的，所以在回调函数内设置
 */
function ajaxGetAndSetUserInfo() {
    let retrunUser = {};
    $.ajax({
        type: "GET",
        url: "/user/curUser",
        dataType: "json",
        // 同步
        async:false,
        success: function (msg) {
            if (msg.code == 200) {
                initUserInfo(msg.data.user);
                console.log("返回获取的user:", msg.data.user)
                retrunUser = msg.data.user;
            }
        },
        error: function (msg) {
            console.log("获取当前用户信息失败：")
            console.log(msg)
        }
    });
    return retrunUser;
}

/**
 * 向页眉的用户信息处填充数据
 * @param user
 */
function initUserInfo(user) {
    $("#header_username_btn").text(user.username);
    $("#header_user_center").on("click", function () {
        window.location.href = "../userdetail.html?id=" + user.id;
    })
    $("#header_user_logout").on("click", function () {
        let flag = confirm("是否退出登录？");
        if (flag) {
            ajaxLogout();
        }
    })
}

function ajaxLogout() {
    $.ajax({
        type: "GET",
        url: "/users/logout",
        dataType: "json",
        success: function (msg) {
            if (msg.code == 200) {
                // 退出登录成功
                alert("退出登录成功！")
                window.setTimeout(function () {
                    window.location.replace("/index.html");
                }, 500)
            } else {
                toastr.error("退出登录失败！")
            }
        },
        error: function (msg) {
            toastr.error("退出登录失败！")
            console.log("退出登录失败：{}", msg)
        }
    });
}