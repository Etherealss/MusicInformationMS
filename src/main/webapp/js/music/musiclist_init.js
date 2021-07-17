/**
 * @fileoverview 初始化音乐列表界面
 * @author  wtk
 * @date 2021-06-10
 */
/**
 * 参数对象
 */
let params = {};
let user = {};
let isMusicAdmin = false;
$(function () {

    user = ajaxGetAndSetUserInfo();
    if (user.permissions != null) {
        for (let permission of user.permissions) {
            if (permission == "OperateMusic") {
                isMusicAdmin = true;
                showAdminBtn();
            } else if (permission == "OperateUser") {
                showUserAdminBtn();
            }
        }
    }
    params = getUrlParams();
    // 如果url中没有p属性，则访问第一页
    // let curPage = params.p == null ? 1 : params.p;
    // let offset = params.offset == null ? 10 : params.offset;

    //去除url中的“?”，保留之后的所有字符
    let urlparam = location.search.substr(1);
    let requestUrl = "/musics?";
    if (urlparam.indexOf("p") == -1) {
        requestUrl += "p=1";
    }
    if (urlparam.indexOf("offset") == -1) {
        requestUrl += "&offset=10";
    }
    requestUrl += "&" + urlparam;

    getMusicList(requestUrl);

})

function showAdminBtn() {
    let str = "<li>\n" +
        "          <button type=\"button\" id=\"musics_add_music_btn\"\n" +
        "                  class=\"btn btn-primary\" onclick=\"toAddMusic()\">\n" +
        "              添加音乐\n" +
        "          </button>\n" +
        "      </li>\n" +
        "      <li>\n" +
        "          <button type=\"button\" id=\"musics_delete_music_btn\"\n" +
        "                  class=\"btn btn-default\" onclick=\"deleteMultiMusic()\">\n" +
        "              删除所选音乐\n" +
        "          </button>\n" +
        "      </li>";
    $("#musics_nav_btn").append(str);
}

function showUserAdminBtn() {
    let str = "<li>\n" +
        "          <button type=\"button\" id=\"musics_add_music_btn\"\n" +
        "                  class=\"btn btn-primary\" onclick=\"toUserlist()\">\n" +
        "              前往用户列表\n" +
        "          </button>\n" +
        "      </li>";
    $("#musics_nav_btn").append(str);
}

function toUserlist() {
    window.location.href = "../userlist.html";
}

/**
 * 请求获取音乐列表
 */
function getMusicList(url) {
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
         * @param {Number} msg.data.page.totalCount 总音乐数
         * @param {Number} msg.data.page.totalPage 总页数
         * @param {Number} msg.data.page.curPage 当前页码
         * @param {Number} msg.data.page.offset 偏移量，即每一页显示的记录数
         * @param {Object} msg.data.page.dataList 当前页的音乐数据
         */
        success: function (msg) {
            let page = msg.data.page;
            if (msg.code == 200) {
                // 请求成功
                let list = page.dataList;
                //记得传入数字类型，否则在计算页码时会变成字符串拼接
                createPageBtn(Number(page.curPage), Number(page.totalPage), page.offset, "musiclist.html");
                // createPageBtn(4, 26, 10);
                createMusicList(list, Number(page.curPage), Number(page.offset));
            } else if (msg.code == 400) {
                // 请求执行失败
                toastr.error("没有匹配数据！即将跳转");
                window.setTimeout(function () {
                    // 自动跳转到最后一页
                    window.location.href = 'musiclist.html';
                }, 500)
            } else {

                toastr.error("请求失败！请重试！");
            }

        },
        error: function () {
            toastr.error("获取音乐数据出现意外！请刷新页面或者稍后重新进入本页！");
        }
    });
}

/**
 * 形成url的参数
 * @param {Number} toPage 前往的页码数
 * @param {Number} offset
 * @returns {string} url
 */
function getUrlWithParam(toPage, offset) {
    let url = "?p=" + toPage;
    url += "&offset=" + offset;
    if (params.name != null) {
        url += "&name=" + params.name;
    } else if (params.singer != null) {
        url += "&singer=" + params.singer;
    } else if (params.id != null) {
        url += "&id=" + params.id;
    }
    return url;
}

/**
 * 音乐数据显示到页面上
 * @param list 音乐数据
 * @param {Number} list.id
 * @param {String} list.name 音乐名
 * @param {} list.singers 歌手
 * @param {} list.singers.id 歌手id
 * @param {} list.singers.singerName 歌手名
 * @param {} list.singers.sex 性别
 * @param {String} list.lyrics 歌词下载
 * @param {String} list.mediaFilePath 媒体文件路径
 * @param {String} list.permission 免费
 * @param {Number} list.releaseDate 发行时间
 * @param {Number} list.price 价格
 * @param {Number} currentPage 当前页
 * @param {Number} rows 每页显示的行数
 */
function createMusicList(list, currentPage, rows) {
    //获取list的数据储存在数组里，方便调用
    let arr = [];
    for (let item in list) {
        arr.push(list[item]);
    }
    let permissions = ["付费", "免费", "会员免费", "其他"];
    for (let i = 0; i < arr.length; i++) {
        //获取序号
        let index = (currentPage - 1) * rows + i + 1;
        // 权限
        let permission = permissions[arr[i].permission];

        // 拼接歌手姓名
        let singerNames = "";
        for (let j = 0; j < arr[i].singers.length; j++) {
            singerNames += arr[i].singers[j].singerName + " ";
        }

        let str =
            "<tr>" +
            "    <td>" +
            "        <div class=\"multi_delete_checkbox\">\n" +
            "             <input type=\"checkbox\" name=\"multi_delete\" value=\"" + arr[i].id + "\">\n" +
            "        </div>" +
            "    </td>" +
            "    <td>" + index + "</td>\n" +
            "    <td>" + arr[i].id + "</td>\n" +
            "    <!--悬浮鼠标显示完整内容，下同-->" +
            "    <td title='" + arr[i].name + "'>" + arr[i].name + "</td>\n" +
            "    <td title='" + singerNames + "'>" + singerNames + "</td>\n" +
            "    <td>" + dateFormat(arr[i].releaseDate, false) + "</td>\n" +
            "    <td>" + permission + "</td>\n" +
            "    <td>" + arr[i].price + "</td>\n" +
            "    <td id='musics_options_btn'>\n" +
            "        <a href=\"musicdetail.html?id=" + arr[i].id + "\" class=\"btn btn-primary btn-xs\">\n" +
            "            <span class=\"glyphicon glyphicon-info-sign\"></span>详情\n" +
            "        </a>";

        if (isMusicAdmin) {
            // 是管理员，可以添加相关操作
            str += "     <a href=\"addmusic.html?id=" + arr[i].id + "\" class=\"btn btn-info btn-xs\">\n" +
                "            <span class=\"glyphicon glyphicon-edit\"></span> 编辑\n" +
                "        </a>" +
                "        <a href=\"javascript:void(0)\" class=\"btn btn-danger btn-xs\" onclick=\"deleteMusic(" + arr[i].id + ")\">\n" +
                "            <span class=\"glyphicon glyphicon-remove\"></span> 删除\n" +
                "        </a>";
        }
        str += "  </td>" +
            "</tr>";
        //将字符串添加到表格中
        $(str).appendTo($("thead"));
    }
}