/**
 * @fileoverview 搜索相关
 * @author  wtk
 * @date 2021-06-18
 */
/**
 * 修改搜索的类型值
 * @param type
 */
function changeUserSearchType(type) {
    $("#users_search_type_menu").attr("data-search-type", type);
    if (type === "userName") {
        $("#users_search_input").attr("placeholder", "请输入用户名");
    } else {
        $("#users_search_input").attr("placeholder", "请输入用户ID");
    }
}

/**
 * 进行搜索
 */
function searchUser() {
    let inputValue = $("#users_search_input").val();
    let searchType = $("#users_search_type_menu").attr("data-search-type");

    let searchUrl = "userlist.html?";
    if (searchType == "userName") {
        // 按用户名搜索
        searchUrl += "name=";
    } else if (searchType == "userId") {
        // 按用户ID搜索
        searchUrl += "id=";
    } else {
        console.log("类型错误")
        return;
    }
    searchUrl += encodeURIComponent(inputValue);

    window.location.href = searchUrl;
}