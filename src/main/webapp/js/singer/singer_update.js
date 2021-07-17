/**
 * @fileoverview
 * @author  wtk
 * @date 2021-06-16
 */
$(function () {
    let params = getUrlParams();

    if (params.id != null) {
        ajaxGetSinger(params.id);
    }
})

/**
 * 获取音乐数据
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
 * 回显音乐数据
 * @param singer
 */
function echoSinger(singer) {
    console.log(singer)
    $("#add_singer_name").val(singer.singerName);
    $("#add_singer_disc").val(singer.disc);
    $("#add_singer_birthday_box input").val(dateFormat(singer.birthday, false));
    $("#add_singer_tel_box input").val(singer.tel)

    $("#sex_btn_box label").removeClass("active")
    if (singer.sex) {
        // 男
        $($("#sex_btn_box label")[0]).addClass("active")
    } else {
        $($("#sex_btn_box label")[1]).addClass("active")
    }
}