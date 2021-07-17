/**
 * @fileoverview
 * @author  wtk
 * @date 2021-06-18
 */
function addSinger() {
    let singer = {}
    singer.singerName = $("#add_singer_name").val();

    if (singer.singerName.length == 0) {
        toastr.warning("请输入歌手名");
        return false;
    }

    let disc = $("#add_singer_disc").val();
    if (disc == null || disc.length == 0) {
        toastr.warning("请输入歌手描述");
        return false;
    } else if (disc.length > 200) {
        toastr.warning("歌手描述不能超过200字");
        return false;
    }
    singer.disc = disc;

    singer.birthday = $("#add_singer_birthday_box input").val()
    let dateCheck = reasonalDate4Tip(singer.birthday);
    // 日期检查
    if (dateCheck == "null") {
        toastr.warning("请选择生日");
        return false;
    } else if (dateCheck == "big") {
        toastr.warning("生日不能晚于当前时间！");
        return false;
    }

    let tel = $("#add_singer_tel_box input").val();
    if (tel == null || tel.length == 0) {
        toastr.warning("请输入歌手的联系方式！");
        return false;
    } else if (tel.length != 11) {
        toastr.warning("联系方式要求为11位手机号码！");
        return false;
    }
    singer.tel = tel;

    let params = getUrlParams();
    if (params.id == null) {
        // 添加新歌手
        ajaxAddSinger(singer);
    } else {
        // 修改已有歌手
        singer.id = params.id;
        ajaxPutSinger(singer);
    }
    return true;
}

/**
 * 提交添加歌手
 * @param {Object} singer
 */
function ajaxAddSinger(singer) {
    $.ajax({
        type: "POST",
        url: "/singers",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify({
            "singerName": singer.singerName,
            "birthday": new Date(singer.birthday).getTime(),
            "sex": singer.sex,
            "tel": singer.tel,
            "discription": singer.description
        }),
        success: function (msg) {
            console.log("新增歌手成功：", msg)
            if (msg.code == 200) {
                window.location.replace("singerlist.html");
            } else  {
                console.log("新增歌手失败：", msg)
                toastr("新增歌手失败")
            }
        },
        error: function (msg) {
            console.log("新增歌手失败：", msg)
            toastr.error("新增歌手出事了！")
        }
    });
}

/**
 * 请求修改歌手
 * @param {Object} singer
 */
function ajaxPutSinger(singer) {
    $.ajax({
        type: "PUT",
        url: "/singers/" + singer.id,
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify({
            "singerName": singer.singerName,
            "birthday": new Date(singer.birthday).getTime(),
            "sex": singer.sex,
            "tel": singer.tel,
            "discription": singer.description
        }),
        success: function (msg) {
            console.log("修改歌手成功：", msg)
            if (msg.code == 200) {
                window.location.replace("singerlist.html");
            } else  {
                console.log("修改歌手失败：", msg)
                toastr("修改歌手失败")
            }
        },
        error: function (msg) {
            console.log("修改歌手失败：", msg)
            toastr.error("修改歌手出事了！")
        }
    });
}


function cancelAddSinger() {
    window.location.replace("./singerlist.html");
}