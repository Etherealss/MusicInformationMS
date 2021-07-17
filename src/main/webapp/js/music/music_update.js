/**
 * @fileoverview
 * @author  wtk
 * @date 2021-06-16
 */
$(function () {
    let params = getUrlParams();

    if (params.id != null) {
        ajaxGetMusic(params.id);
    }

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
    $("#add_music_name").val(music.name);
    if (music.lyrics.length > 0) {
        $("#add_music_lyric").val(music.lyrics[0].lyricText);
        let language = music.lyrics[0].language;
        if (language == "Chinese") {
            language = "中文"
        } else if (language == "English") {
            language = "英文";
        } else if (language == "Japanese") {
            language = "日语";
        }
        $("#add_music_language_box input").val(language);
    }
    $("#add_music_date").val(music.releaseDate);
    $("#add_music_price_box input").val(music.price)
    let singersName = "";
    for (let singer of music.singers) {
        singersName += singer.singerName + " ";
    }
    $("#add_music_siger_box input").val(singersName)

    // 选择单选框
    $("#charge_type_box label").removeClass("active")
    $($("#charge_type_box label")[music.permission]).addClass("active")
}