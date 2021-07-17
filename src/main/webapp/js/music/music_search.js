/**
 * @fileoverview 搜索相关
 * @author  wtk
 * @date 2021-06-13
 */
/**
 * 修改搜索的类型值
 * @param type
 */
function changeSearchType(type) {
    $("#musics_search_type_menu").attr("data-search-type", type);
    if (type === "musicName") {
        $("#musics_search_input").attr("placeholder", "请输入音乐名");
    } else if (type === "musicId") {
        $("#musics_search_input").attr("placeholder", "请输入音乐ID");
    } else {
        $("#musics_search_input").attr("placeholder", "请输入歌手名");
    }
}

/**
 * 进行搜索
 */
function searchMusic() {
    let inputValue = $("#musics_search_input").val();
    let searchType = $("#musics_search_type_menu").attr("data-search-type");

    let searchUrl = "musiclist.html";
    if (searchType == "musicName") {
        // 按音乐名搜索
        searchUrl += "?name=";
    } else if (searchType == "singer") {
        // 按歌手名搜索
        searchUrl += "?singer=";
    } else if (searchType == "musicId") {
        // 按音乐ID搜索
        searchUrl += "?id=";
    }
    searchUrl += encodeURIComponent(inputValue);

    window.location.href = searchUrl;
}