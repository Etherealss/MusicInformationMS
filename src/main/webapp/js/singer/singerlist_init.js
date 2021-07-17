/**
 * @fileoverview 初始化歌手列表界面
 * @author  wtk
 * @date 2021-06-17
 */
/**
 * 参数对象
 */
let params = {};
let user = {};
let isSingerAdmin = false;
$(function () {

    user = ajaxGetAndSetUserInfo();
    if (user.permissions != null) {
        for (let permission of user.permissions) {
            if (permission == "OperateSinger") {
                isSingerAdmin = true;
                showAdminBtn();
                break;
            }
        }
    }

    params = getUrlParams();
    // 如果url中没有p属性，则访问第一页
    // let curPage = params.p == null ? 1 : params.p;
    // let offset = params.offset == null ? 10 : params.offset;

    //去除url中的“?”，保留之后的所有字符
    let urlparam = location.search.substr(1);
    let requestUrl = "/singers?";
    if (urlparam.indexOf("p") == -1) {
        requestUrl += "p=1";
    }
    if (urlparam.indexOf("offset") == -1) {
        requestUrl += "&offset=10";
    }
    if (urlparam.length > 0) {
        requestUrl += "&" + urlparam;
    }

    console.log("requestUrl:{}", requestUrl)

    getSingerList(requestUrl);

})

function showAdminBtn() {
    let str = "<li>\n" +
        "          <button type=\"button\" id=\"singers_add_singer_btn\"\n" +
        "                  class=\"btn btn-primary admin_btn\" onclick=\"toAddSinger()\">\n" +
        "              添加歌手\n" +
        "          </button>\n" +
        "      </li>\n" +
        "      <li>\n" +
        "          <button type=\"button\" id=\"singers_delete_singer_btn\"\n" +
        "                  class=\"btn btn-default admin_btn\" onclick=\"deleteMultiSinger()\">\n" +
        "              删除所选歌手\n" +
        "          </button>\n" +
        "      </li>";
    $("#singers_nav_btn").append(str);
}
/**
 * 请求获取歌手列表
 */
function getSingerList(url) {
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
         * @param {Number} msg.data.page.totalCount 总歌手数
         * @param {Number} msg.data.page.totalPage 总页数
         * @param {Number} msg.data.page.curPage 当前页码
         * @param {Number} msg.data.page.offset 偏移量，即每一页显示的记录数
         * @param {Object} msg.data.page.dataList 当前页的歌手数据
         */
        success: function (msg) {
            let page = msg.data.page;
            if (msg.code == 200) {
                // 请求成功
                let list = page.dataList;
                //记得传入数字类型，否则在计算页码时会变成字符串拼接
                createPageBtn(Number(page.curPage), Number(page.totalPage), page.offset, "singerlist.html");
                createSingerList(list, Number(page.curPage), Number(page.offset));
            } else if (msg.code == 400) {
                // 请求执行失败
                toastr.error("没有匹配数据！即将跳转");
                window.setTimeout(function () {
                    // 自动跳转到最后一页
                    window.location.href = 'singerlist.html';
                }, 500)
            } else {
                toastr.error("请求失败！请重试！");
            }
        },
        error: function () {
            toastr.error("获取歌手数据出现意外！请刷新页面或者稍后重新进入本页！");
        }
    });
}

/**
 * 歌手数据显示到页面上
 * @param list 歌手数据
 * @param {Number} currentPage 当前页
 * @param {Number} rows 每页显示的行数
 * @param {Number} list.id
 * @param {String} list.singerName 歌手名
 * @param {Boolean} list.sex 性别
 * @param {Date} list.birthday 生日
 * @param {String} list.tel 电话
 * @param {String} list.description 歌手简介
 */
function createSingerList(list, currentPage, rows) {
    //获取list的数据储存在数组里，方便调用
    let arr = [];
    for (let item in list) {
        arr.push(list[item]);
    }
    for (let i = 0; i < arr.length; i++) {
        //获取用户的博客序号
        let index = (currentPage - 1) * rows + i + 1;
        let str = "<tr>" +
            "         <td>" +
            "             <div class=\"multi_delete_checkbox\">\n" +
            "                  <input type=\"checkbox\" name=\"multi_delete\" value=\"" + arr[i].id + "\">\n" +
            "             </div>" +
            "         </td>" +
            "         <td>" + index + "</td>\n" +
            "         <td>" + arr[i].id + "</td>\n" +
            "         <!--悬浮鼠标显示完整内容，下同-->" +
            "         <td title='" + arr[i].singerName + "'>" + arr[i].singerName + "</td>\n" +
            "         <td>" + (arr[i].sex ? "男" : "女") + "</td>\n" +
            "         <td>" + dateFormat(arr[i].birthday, false) + "</td>\n" +
            "         <td>" + arr[i].tel + "</td>\n" +
            "         <td>" + (arr[i].description == null ? "无" : arr[i].description) + "</td>\n" +
            "         <td id='singers_options_btn'>\n" +
            "             <a href=\"singerdetail.html?id=" + arr[i].id + "\" class=\"btn btn-primary btn-xs\">\n" +
            "                 <span class=\"glyphicon glyphicon-info-sign\"></span>详情\n" +
            "             </a>";
        if (isSingerAdmin) {
            str += "          <a href=\"addsinger.html?id=" + arr[i].id + "\" class=\"btn btn-info btn-xs\">\n" +
                "                 <span class=\"glyphicon glyphicon-edit\"></span> 编辑\n" +
                "             </a>" +
                "             <a href=\"javascript:void(0)\" class=\"btn btn-danger btn-xs\" onclick=\"deleteSinger(" + arr[i].id + ")\">\n" +
                "                 <span class=\"glyphicon glyphicon-remove\"></span> 删除\n" +
                "             </a>";
        }
        str += "      </td>" +
            "     </tr>";
        //将字符串添加到表格中
        $(str).appendTo($("thead"));
    }
}