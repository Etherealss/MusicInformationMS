/**
 * @fileoverview
 * @author  wtk
 * @date 2021-06-18
 */
let user;
$(function () {

    user = ajaxGetAndSetUserInfo();
    let params = getUrlParams();
    ajaxGetMusic(params.id);

})

/**
 * 获取音乐数据
 * @param musicId
 */
function ajaxGetMusic(musicId) {
    $.ajax({
        //请求类型
        type: "GET",
        dataType: "json",
        url: "musics/" + musicId,
        success: function (msg) {
            if (msg.code == 200) {
                echoMusic(msg.data.music);
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
 * @param music
 */
function echoMusic(music) {
    $("#musicdetail_musicname span").text(music.name);
    if (music.lyrics.length > 0) {
        $("#musicdetail_lyric").val(music.lyrics[0].lyricText);
        let language = music.lyrics[0].language;
        console.log(language)
        if (language == "Chinese") {
            language = "中文"
        } else if (language == "English") {
            language = "英文";
        } else if (language == "Japanese") {
            language = "日语";
        }
        $("#musicdetail_language_box span").text(language);
    } else {
        $("#musicdetail_lyric").val("(无歌词)");
    }
    $("#musicdetail_date_box span").text(dateFormat(music.releaseDate, false));
    let singersName = "";
    console.log(music.singers)
    for (let singer of music.singers) {
        singersName += singer.singerName + " ";
    }
    $("#musicdetail_siger_box span").text(singersName)

    if (music.permission == 1) {
        $("#charge_radio_box span").text("免费");
        $("#add_music_price_box span").text("0元")
    } else {
        if (music.permission == 0) {
            $("#charge_radio_box span").text("收费");
        } else if (music.permission == 2) {
            $("#charge_radio_box span").text("会员免费");
        }
        $("#add_music_price_box span").text(music.price + "元")
    }
}