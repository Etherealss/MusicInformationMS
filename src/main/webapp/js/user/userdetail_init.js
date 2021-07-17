/**
 * @fileoverview
 * @author  wtk
 * @date 2021-06-18
 */
let user;
$(function () {

    user = ajaxGetAndSetUserInfo();
    let params = getUrlParams();
    ajaxGetUser(params.id);

})

/**
 * 获取用户数据
 * @param userId
 */
function ajaxGetUser(userId) {
    $.ajax({
        //请求类型
        type: "GET",
        dataType: "json",
        url: "users/" + userId,
        success: function (msg) {
            if (msg.code == 200) {
                console.log(msg.data.user)
                echoUser(msg.data.user);
            } else if (msg.code == 404) {
                // 请求执行失败
                window.location.replace = "../../404.html";
            } else {
                toastr.error("获取数据失败！请重试！");
            }
        },
        error: function () {
            toastr.error("获取数据失败！请刷新页面或者稍后重新进入本页！");
        }
    });
}

/**
 * 回显用户数据
 * @param user
 */
function echoUser(user) {
    $("#userdetail_userId_box span").text(user.id)
    $("#userdetail_username_box span").text(user.username);
    $("#userdetail_sex_box span").text(user.sex ? "男" : "女");
    $("#userdetail_regitserdate_box span").text(dateFormat(user.registerDate, false))
    $("#userdetail_balance_box span").text(user.balance)

    let adminTypes = "";
    if (user.permissions != null) {
        for (let permission of user.permissions) {
            adminTypes += permission + " "
        }
    } else {
        adminTypes = "非管理员";
    }
    $("#userdetail_admintype_box span").text(adminTypes)
}