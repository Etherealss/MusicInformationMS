/**
 * @fileoverview
 * @author  wtk
 * @date 2021-06-14
 */
/**
 * 修改音乐收费模式时，如果是免费，则禁用价格输入框
 * @param {boolean} lock
 */
function changeMusicCharge(lock) {
    if (lock) {
        $("#add_music_price_box input").attr("disabled", "disabled");
    } else {
        $("#add_music_price_box input").removeAttr("disabled");
    }
}

function addMusic() {
    let music = {}
    music.musicName = $("#add_music_name").val();

    if (music.musicName.length == 0) {
        toastr.warning("请输入音乐名");
        return false;
    }

    music.lyric = {}
    music.lyric.lyricText = $("#add_music_lyric").val();
    if (music.lyric.length == 0) {
        toastr.warning("请输入歌词");
        return false;
    }

    let language = $("#add_music_language_box input").val();
    if (language == "中文") {
        music.lyric.language = "Chinese";
    } else if (language == "英文") {
        music.lyric.language = "English";
    } else if (language == "日语") {
        music.lyric.language = "Japanese";
    } else if (language == "其他") {
        music.lyric.language = "others";
    }

    music.permission = $("input[name='music_permission']:checked").val();
    console.log(music.permission)

    if (music.permission != 1) {
        // 收费
        music.price = $("#add_music_price_box input").val();
        if (music.price <= 0) {
            toastr.warning("请输入正确的价格");
            return false;
        }
    } else {
        music.price = 0;
    }

    music.releaseDate = $("#add_music_date").val()
    let dateCheck = reasonalDate4Tip(music.releaseDate);
    // 日期检查，可以添加发行时间在未来的音乐，但不能添加早于1970年1月1日发行的音乐
    if (dateCheck == "null") {
        toastr.warning("请选择发现日期");
        return false;
    } else if (dateCheck == "small") {
        toastr.warning("发行时间不能早于1970年！");
        return false;
    }

    let singerInput = $("#add_music_siger_box input").val();
    if (singerInput == null || singerInput.length == 0) {
        toastr.warning("请输入歌手姓名！");
        return false;
    }
    // 如果有多个歌手，按空格分隔
    let singerNames = singerInput.split(" ");
    // 拼接多个歌手的json
    let singerJson = [];
    for (let singerName of singerNames) {
        singerJson.push({"singerName": singerName});
    }
    music.singerNames = singerJson;

    let params = getUrlParams();
    if (params.id == null) {
        // 添加新音乐
        ajaxAddMusic(music);
    } else {
        // 修改已有音乐
        music.id = params.id;
        ajaxPutMusic(music);
    }
    return true;
}

/**
 * 提交添加音乐
 * @param {Object} music
 */
function ajaxAddMusic(music) {
    $.ajax({
        type: "POST",
        url: "/musics",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify({
            "name": music.musicName,
            "lyrics": [{
                "musicId": -1,
                "lyricText": music.lyric.lyricText,
                "language": music.lyric.language
            }],
            "singers": music.singerNames,
            "permission": music.permission,
            "price": music.price,
            "releaseDate": new Date(music.releaseDate).getTime()
        }),
        success: function (msg) {
            console.log("新增音乐成功：", msg)
            if (msg.code == 200) {
                window.location.replace("musiclist.html");
            } else if (msg.code == 1002) {
                toastr("歌词语言错误！")
            } else  {
                console.log("新增音乐失败：", msg)
                toastr("新增音乐失败")
            }
        },
        error: function (msg) {
            console.log("新增音乐失败：", msg)
            toastr.error("新增音乐出事了！")
        }
    });
}

/**
 * 请求修改音乐
 * @param {Object} music
 */
function ajaxPutMusic(music) {
    $.ajax({
        type: "PUT",
        url: "/musics/" + music.id,
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify({
            "id": music.id,
            "name": music.musicName,
            "lyrics": [{
                "musicId": -1,
                "lyricText": music.lyric.lyricText,
                "language": music.lyric.language
            }],
            "singers": music.singerNames,
            "permission": music.permission,
            "price": music.price,
            "releaseDate": new Date(music.releaseDate).getTime()
        }),
        success: function (msg) {
            console.log("修改音乐成功：", msg)
            if (msg.code == 200) {
                window.location.replace("musiclist.html");
            } else if (msg.code == 1002) {
                toastr("歌词语言错误！")
            } else  {
                console.log("修改音乐失败：", msg)
                toastr("修改音乐失败")
            }
        },
        error: function (msg) {
            console.log("修改音乐失败：", msg)
            toastr.error("修改音乐出事了！")
        }
    });
}


function cancelAddMusic() {
    window.location.replace("./musiclist.html");
}