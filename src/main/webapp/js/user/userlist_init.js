/**
 * @fileoverview 初始化用户列表界面
 * @author  wtk
 * @date 2021-06-17
 */
/**
 * 参数对象
 */
let params = {};
let user = {};
let isUserAdmin = false;
/**用户数据*/
let pagedata = [];
$(function () {

    user = ajaxGetAndSetUserInfo();
    ajaxGetPermissions();
    if (user.permissions != null) {
        for (let permission of user.permissions) {
            if (permission == "OperateUser") {
                isUserAdmin = true;
                showAdminBtn();
                break;
            }
        }
    }

    params = getUrlParams();

    //去除url中的“?”，保留之后的所有字符
    let urlparam = location.search.substr(1);
    let requestUrl = "/users?";
    if (urlparam.indexOf("p") == -1) {
        requestUrl += "p=1";
    }
    if (urlparam.indexOf("offset") == -1) {
        requestUrl += "&offset=10";
    }
    if (urlparam.length > 0) {
        requestUrl += "&" + urlparam;
    }

    getUserList(requestUrl);

})

function showAdminBtn() {
    let str = "<li>\n" +
        "          <button type=\"button\" id=\"users_add_user_btn\"\n" +
        "                  class=\"btn btn-primary admin_btn\" onclick=\"window.location.href = 'index.html'\">\n" +
        "              注册用户\n" +
        "          </button>\n" +
        "      </li>\n" +
        "      <li>\n" +
        "          <button type=\"button\" id=\"users_delete_user_btn\"\n" +
        "                  class=\"btn btn-default admin_btn\" onclick=\"deleteMultiUser()\">\n" +
        "              删除所选用户\n" +
        "          </button>\n" +
        "      </li>";
    $("#users_nav_btn").append(str);
}

/**
 * 请求获取用户列表
 */
function getUserList(url) {
    $.ajax({
        //请求类型
        type: "GET",
        dataType: "json",
        url: url,
        /**
         * @param msg
         * @param {Number} msg.code 结果码
         * @param {Number} msg.data 数据
         * @param {Object} msg.data.page 页面信息包
         * @param {Number} msg.data.page.totalCount 总用户数
         * @param {Number} msg.data.page.totalPage 总页数
         * @param {Number} msg.data.page.curPage 当前页码
         * @param {Number} msg.data.page.offset 偏移量，即每一页显示的记录数
         * @param {Object} msg.data.page.dataList 当前页的用户数据
         */
        success: function (msg) {
            let page = msg.data.page;
            if (msg.code == 200) {
                // 请求成功
                let list = page.dataList;
                //记得传入数字类型，否则在计算页码时会变成字符串拼接
                createPageBtn(Number(page.curPage), Number(page.totalPage), page.offset, "userlist.html");
                createUserList(list, Number(page.curPage), Number(page.offset));
            } else if (msg.code == 400) {
                // 请求执行失败
                toastr.error("没有匹配数据！即将跳转");
                window.setTimeout(function () {
                    // 自动跳转第一页
                    window.location.href = 'userlist.html';
                }, 500)
            } else {
                toastr.error("请求失败！请重试！");
            }
        },
        error: function () {
            toastr.error("获取用户数据出现意外！请刷新页面或者稍后重新进入本页！");
        }
    });
}

/**
 * 用户数据显示到页面上
 * @param list 用户数据
 * @param {Number} currentPage 当前页
 * @param {Number} rows 每页显示的行数
 * @param {Number} list.id
 * @param {String} list.username 用户名
 * @param {Boolean} list.sex 性别
 * @param {Date} list.registerDate 注册时间
 * @param {Number} list.balance 余额
 * @param {Array} list.permissions 用户权限
 */
function createUserList(list, currentPage, rows) {
    //获取list的数据储存在数组里，方便调用
    pagedata = [];
    for (let item in list) {
        pagedata.push(list[item]);
    }
    for (let i = 0; i < pagedata.length; i++) {
        //获取用户的博客序号
        let index = (currentPage - 1) * rows + i + 1;

        let permissions = "";
        if (pagedata[i].permissions == null || pagedata[i].permissions.length == 0) {
            permissions = "无权限";
        } else {
            for (let permission of pagedata[i].permissions) {
                permissions += permission + " ";
            }
        }

        let str = "<tr>" +
            "         <td>" +
            "             <div class=\"multi_delete_checkbox\">\n" +
            "                  <input type=\"checkbox\" name=\"multi_delete\" value=\"" + pagedata[i].id + "\">\n" +
            "             </div>" +
            "         </td>" +
            "         <td>" + index + "</td>\n" +
            "         <td>" + pagedata[i].id + "</td>\n" +
            "         <!--悬浮鼠标显示完整内容，下同-->" +
            "         <td title='" + pagedata[i].username + "'>" + pagedata[i].username + "</td>\n" +
            "         <td>" + (pagedata[i].sex ? "男" : "女") + "</td>\n" +
            "         <td>" + dateFormat(pagedata[i].registerDate, false) + "</td>\n" +
            "         <td>" + pagedata[i].balance + "元</td>\n" +
            "         <td>" + permissions + "</td>\n" +
            "         <td id='users_options_btn'>\n" +
            "             <a href=\"userdetail.html?id=" + pagedata[i].id + "\" class=\"btn btn-primary btn-xs\">\n" +
            "                 <span class=\"glyphicon glyphicon-info-sign\"></span>详情" +
            "             </a>";
        if (isUserAdmin) {
            str += "      <a href=\"index.html\" class=\"btn btn-info btn-xs\">" +
                "             <span class=\"glyphicon glyphicon-edit\"></span> 编辑" +
                "         </a>" +
                "         <a class=\"btn btn-info btn-xs\" id='permission_btn" + i + "' data-toggle=\"modal\"\n" +
                "                 data-target=\"#users_permission_modal\">" +
                "             <span class=\"glyphicon glyphicon-edit\"></span> 授权" +
                "         </a>" +
                "         <a href=\"javascript:void(0)\" class=\"btn btn-danger btn-xs\" onclick=\"deleteUser(" + pagedata[i].id + ")\">\n" +
                "             <span class=\"glyphicon glyphicon-remove\"></span> 删除" +
                "         </a>";
        }
        str += "      </td>" +
            "     </tr>";
        //将字符串添加到表格中
        $(str).appendTo($("thead"));
        $("#permission_btn" + i).on("click", function () {
            console.log("执行方法")
            setPermission(pagedata[i].id, pagedata[i].permissions);
        })
    }
}

function setPermission(userId, permissions) {
    $("#users_permission_userid").val(userId)
    $("#users_permission_box select option").each(function () {
        let optionObj = $(this);
        if (permissions != null) {
            for (let permission of permissions) {
                if (optionObj.text() == permission) {
                    optionObj.attr("selected", "selected");
                    break;
                }
                // 如果有匹配的权限，那么会在上面的for循环添加选中，并退出循环
                // 否则，会来到这里，剔除可能存在的选中属性
                // 这是因为模态框只有一个，不同的表格行的按钮对应同一个模态框
                // 因此每一次点击都需要清除上一次的样式
                optionObj.removeAttr("selected");
            }
        }
    })
}

/**
 * 请求获取权限数据
 */
function ajaxGetPermissions() {
    $.ajax({
        //请求类型
        type: "GET",
        dataType: "json",
        url: "/permissions",
        success: function (msg) {
            if (msg.code == 200) {
                // 请求成功
                showPermissionSelect(msg.data.permissions);
            } else {
                toastr.error("请求失败！请重试！");
            }
        },
        error: function () {
            toastr.error("获取权限数据出现意外！请刷新页面或者稍后重新进入本页！");
        }
    });

}

function showPermissionSelect(permissions) {
    if (permissions != null) {
        let str = "";
        for (let permission of permissions) {
            str += "<option value='" + permission + "'>" + permission + "</option>";
        }
        $("#users_permission_box select").html(str);
    }
}

function updatePermission() {
    let userId = $("#users_permission_userid").val()

    // 计算新增权限和删除权限
    let curSelectPerm = []
    let selectedOption = $("#users_permission_box select option:selected");
    console.log(selectedOption)
    for (let option of selectedOption) {
        // option应该是DOM对象而不是jQuery对象
        // console.log("option对象：", option)
        // console.log("option.value：", option.value)  // 可以
        // console.log("jQuery:option.val():", $(option).val()) // 可以
        // console.log("option.innerHTML：", option.innerHTML) // 可以
        // console.log("option.val():", option.val()) // 报错
        // console.log("option.text():", option.text())  // 报错
        curSelectPerm.push(option.innerHTML)
    }

    let originPerm;
    for (let user of pagedata) {
        if (user.id == userId) {
            originPerm = user.permissions;
        }
    }
    // 在curSelectPerm而不在originPerm的元素
    let addPerm = subSet(curSelectPerm, originPerm)
    // 在originPerm而不在curSelectPerm的元素
    let removePerm = subSet(originPerm, curSelectPerm)

    if (addPerm.length == 0 && removePerm.length == 0) {
        // 没有改变
        console.log("权限没有改变")
        return;
    }
    // console.log("curSelectPerm:", curSelectPerm)
    // console.log("originPerm:", originPerm)
    // console.log("addPerm:", addPerm)
    // console.log("removePerm:", removePerm)

    ajaxSetPermission(userId, addPerm, removePerm);
}

function subSet(arr1, arr2) {
    let set1 = new Set(arr1);
    let set2 = new Set(arr2);

    let subset = [];

    for (let item of set1) {
        if (!set2.has(item)) {
            subset.push(item);
        }
    }

    return subset;
};