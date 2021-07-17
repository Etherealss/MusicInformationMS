/**
 * @fileoverview
 * @author  wtk
 * @date 2021-06-09
 */
$(function () {
    /*
    抵达首页时不允许返回上一页
    这样可以避免登录和退出登录时返回上一页带来的麻烦
    而且首页本就应该作为“起点”
     */
    var search = window.location.search;
    if(window.history && window.history.pushState){
        $(window).on("popstate", function(){
            var search = window.location.search;
            window.history.pushState("forward", null, "index.html" + search);
            window.history.forward();
        });
    }
    //在IE中必须得有这两行
    window.history.pushState("forward", null, "index.html" + search);
    window.history.forward();
})