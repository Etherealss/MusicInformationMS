/**
 * @fileoverview 搜索相关
 * @author  wtk
 * @date 2021-06-18
 */
/**
 * 修改搜索的类型值
 * @param type
 */
function changeSingerSearchType(type) {
    $("#singers_search_type_menu").attr("data-search-type", type);
    if (type === "singerName") {
        $("#singers_search_input").attr("placeholder", "请输入歌手名");
    } else {
        $("#singers_search_input").attr("placeholder", "请输入歌手ID");
    }
}

/**
 * 进行搜索
 */
function searchSinger() {
    let inputValue = $("#singers_search_input").val();
    let searchType = $("#singers_search_type_menu").attr("data-search-type");

    let searchUrl = "singerlist.html";
    if (searchType == "singerName") {
        // 按歌手名搜索
        searchUrl += "?name=";
    } else if (searchType == "singerId") {
        // 按歌手ID搜索
        searchUrl += "?id=";
    }
    searchUrl += encodeURIComponent(inputValue);

    window.location.href = searchUrl;
}