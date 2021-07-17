/**
 * @fileoverview
 * @author  wtk
 * @date 2021-06-18
 */
let user;
$(function () {

    user = ajaxGetAndSetUserInfo();
    let params = getUrlParams();
    ajaxGetSinger(params.id);

})

/**
 * 获取歌手数据
 * @param singerId
 */
function ajaxGetSinger(singerId) {
    $.ajax({
        //请求类型
        type: "GET",
        dataType: "json",
        url: "singers/" + singerId,
        success: function (msg) {
            if (msg.code == 200) {
                console.log(msg.data.singer)
                echoSinger(msg.data.singer);
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
 * 回显歌手数据
 * @param singer
 */
function echoSinger(singer) {
    $("#singerdetail_singername span").text(singer.singerName);
    if (singer.description != null && singer.description.length > 0) {
        $("#singerdetail_desc").val(singer.description);
    }
    $("#singerdetail_sex_box span").text(singer.sex ? "男" : "女");
    $("#singerdetail_birthday_box span").text(dateFormat(singer.birthday, false))
    $("#singerdetail_tel_box span").text(singer.tel)

}