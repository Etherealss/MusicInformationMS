/**
 * @fileoverview 常用的js操作
 * @author  wtk
 * @date 2021-06-09
 */
/**
 * 获取已经清空样式的提示框
 * @param tip 提示框对象
 * @returns {*}
 */
getPreparedTip = function (tip) {
    //先移除原来的提示内容和样式
    tip.empty();
    tip.removeClass();
    //这个属性要加回去不能清除。
    tip.prop("role", "alert");
    return tip;
}
/**
 * 获取提示框对象，设置提示信息和样式
 * @param tipObj 提示框对象
 * @param message 提示信息
 * @param type 提示框样式
 */
setTip = function (tipObj, message, type) {
    //获取提示框对象
    let tip = getPreparedTip(tipObj);
    //根据type添加不同的样式，如alert-warning
    tip.addClass("alert alert-" + type);
    tip.html("<p>" + message + "</p>");
}

/**
 * 获取url中的参数，返回参数包对象
 * @returns {{}} params
 */
getUrlParams = function () {
    let params = {};
    //获取当前界面url中的参数
    let url = location.search;

    //如果存在“？”则说明存在参数
    if (url.indexOf("?") != -1) {
        //去除url中的“?”，保留之后的所有字符
        let str = url.substr(1);
        //切割字符串，把每个参数及参数值分成数组
        let paramArr = str.split("&");
        for (let i = 0; i < paramArr.length; i++) {
            //前者为参数名称，后台为参数值
            params[paramArr[i].split("=")[0]] = paramArr[i].split("=")[1];
        }
    }
    return params;
}

/**
 * 日期毫秒数转为字符串
 * @param {Number} dateMs 日期毫秒数
 * @param {Boolean} containTime 是否包含时间
 * @returns {string} 格式化日期yyyy-MM-dd HH:mm:ss
 */
dateFormat = function (dateMs, containTime) {
    /*
    实践证明，从后端获取到的日期对象时以毫秒数发送的
    但是此处直接使用接收到的数据调用日期对象的方法会报错
    应该用获得到的毫秒数创建js日期对象
     */
    let date = new Date(dateMs);
    let year = date.getFullYear();
    let month = date.getMonth();
    let day = date.getDate();
    if (month < 10) {
        month = '0' + month;
    }
    if (day < 10) {
        day = '0' + day;
    }
    let dateStr = year + "-" + month + "-" + day;
    if (containTime) {
        let hour = date.getHours();
        let mintue = date.getMinutes();
        let second = date.getSeconds();
        dateStr += " " + hour + ":" + mintue + ":" + second;
    }
    return dateStr;
}