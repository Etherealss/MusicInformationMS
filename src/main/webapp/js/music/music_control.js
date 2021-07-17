/**
 * @fileoverview
 * @author  wtk
 * @date 2021-06-11
 */
/**
 * 删除音乐记录
 * @param musicId
 */
function deleteMusic(musicId) {
    let confimeDelete = confirm("确定要删除ID为【" + musicId + "】的音乐吗？");
    if (confimeDelete) {
        ajaxDeleteMusic(musicId);
    }
}

function toAddMusic() {
    window.location.href = "./addmusic.html";
}

/**
 * 请求删除音乐
 * @param musicId
 */
function ajaxDeleteMusic(musicId) {
    $.ajax({
        //请求类型
        type: "DELETE",
        dataType: "json",
        url: "musics/" + musicId,
        success: function (msg) {
            console.log(msg);
            if (msg.code == 200) {
                toastr.success("删除成功");
                // 刷新页面
                window.setTimeout(function () {
                    window.location.reload();
                },1000)
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

function deleteMultiMusic() {
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
        let musicIdsStr = deleteIds[0]
        for (let i = 1; i < deleteIds.length; i++) {
            musicIdsStr += "," + deleteIds[i];
        }
        ajaxDeleteMultiMusic(musicIdsStr);
    }
}

/**
 * 多选删除
 * @param musicIdsStr
 */
function ajaxDeleteMultiMusic(musicIdsStr) {
    $.ajax({
        //请求类型
        type: "DELETE",
        dataType: "json",
        url: "musics?ids=" + musicIdsStr,
        success: function (msg) {
            console.log(msg);
            if (msg.code == 200) {
                toastr.success("删除成功");
                // 刷新页面
                window.setTimeout(function () {
                    window.location.reload();
                },500)
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