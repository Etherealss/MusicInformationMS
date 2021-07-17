/**
 * @fileoverview
 * @author  wtk
 * @date 2021-06-17
 */
/**
 * 删除歌手记录
 * @param singerId
 */
function deleteSinger(singerId) {
    let confimeDelete = confirm("确定要删除ID为【" + singerId + "】的歌手吗？");
    if (confimeDelete) {
        ajaxDeleteSinger(singerId);
    }
}

function toAddSinger() {
    window.location.href = "./addsinger.html";
}

/**
 * 请求删除歌手
 * @param singerId
 */
function ajaxDeleteSinger(singerId) {
    $.ajax({
        //请求类型
        type: "DELETE",
        dataType: "json",
        url: "singers/" + singerId,
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

function deleteMultiSinger() {
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
        let singerIdsStr = deleteIds[0]
        for (let i = 1; i < deleteIds.length; i++) {
            singerIdsStr += "," + deleteIds[i];
        }
        ajaxDeleteMultiSinger(singerIdsStr);
    }
}

/**
 * 多选删除
 * @param singerIdsStr
 */
function ajaxDeleteMultiSinger(singerIdsStr) {
    $.ajax({
        //请求类型
        type: "DELETE",
        dataType: "json",
        url: "singers?ids=" + singerIdsStr,
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