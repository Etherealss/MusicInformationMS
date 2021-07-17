/**
 * @fileoverview 用户登录后的操作，如退出登录，修改密码
 * @author  wtk
 * @date 2021-06-16
 */
/**
 * 删除用户记录
 * @param userId
 */
function deleteUser(userId) {
    let confimeDelete = confirm("确定要删除ID为【" + userId + "】的用户吗？");
    if (confimeDelete) {
        ajaxDeleteUser(userId);
    }
}

/**
 * 请求删除用户
 * @param userId
 */
function ajaxDeleteUser(userId) {
    $.ajax({
        //请求类型
        type: "DELETE",
        dataType: "json",
        url: "users/" + userId,
        success: function (msg) {
            console.log(msg);
            if (msg.code == 200) {
                toastr.success("删除成功");
                // 刷新页面
                window.setTimeout(function () {
                    window.location.reload();
                }, 500)
            } else if (msg.code == 404) {
                // 请求执行失败
                toastr.warning("没有匹配数据！");
            } else {
                toastr.error("删除失败！请重试！");
            }

        },
        error: function () {
            toastr.error("删除失败！请刷新页面或者稍后重新进入本页！");
        }
    });
}

function deleteMultiUser() {
    let deleteIds = [];
    $("input:checkbox:checked").each(function () {
        deleteIds.push($(this).val());
    })
    if (deleteIds.length == 0) {
        return;
    }
    let deleteConfime = confirm("确定要删除所选" + deleteIds.length + "条记录吗？");
    console.log(deleteIds)
    if (deleteConfime) {
        let userIdsStr = deleteIds[0]
        for (let i = 1; i < deleteIds.length; i++) {
            userIdsStr += "," + deleteIds[i];
        }
        ajaxDeleteMultiUser(userIdsStr);
    }
}

/**
 * 多选删除
 * @param userIdsStr
 */
function ajaxDeleteMultiUser(userIdsStr) {
    $.ajax({
        //请求类型
        type: "DELETE",
        dataType: "json",
        url: "users?ids=" + userIdsStr,
        success: function (msg) {
            console.log(msg);
            if (msg.code == 200) {
                toastr.success("删除成功");
                // 刷新页面
                window.setTimeout(function () {
                    window.location.reload();
                }, 500)
            } else if (msg.code == 404) {
                // 请求执行失败
                toastr.warning("没有匹配到可删除记录！");
            } else {
                toastr.error("删除失败！请重试！");
            }

        },
        error: function () {
            toastr.error("删除失败！请刷新页面或者稍后重新进入本页！");
        }
    });
}


/**
 * 设置用户权限
 * @param userId
 * @param addPerm
 * @param removePerm
 */
function ajaxSetPermission(userId, addPerm, removePerm) {
    let data = JSON.stringify({
        "userId": userId,
        "addPerm": addPerm,
        "removePerm": removePerm
    });
    console.log(data)

    $.ajax({
        url: "/users/permissions/" + userId,
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: data,
        success: function () {

        },
        error: function () {

        }
    });

}