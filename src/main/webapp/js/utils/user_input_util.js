/**
 * @fileoverview 用户注册、修改信息时的通用操作
 * @author  wtk
 * @date 2021-06-09
 */
/**
 * 检查id的字符和长度：
 * 1、仅包含数字
 * 2、6~20个字符
 * @param id
 * @returns {boolean} 符合标准返回true
 */
function standardId(id) {
    let reg = new RegExp("^\\d{6,20}$");
    //符合规定返回true
    return reg.test(id);
}

/**
 * 检查密码的字符和长度
 * 1、仅包含大小写英文字母、数字和下划线
 * 2、6~20个字符
 * @param password
 * @returns {boolean} 符合标准返回true
 */
function standardPw(password) {
    let reg = new RegExp("^\\w{6,20}$");
    //符合规定返回true
    return reg.test(password);
}

/**
 * 检查昵称的字符和长度
 * 1~8个汉字、英文、数字，可以包含下划线
 * @param nickname
 * @returns {boolean} 符合标准返回true
 */
function standardNickname(nickname) {
    let reg = new RegExp("^[\u4e00-\u9fa5_a-zA-Z0-9]{1,8}$");
    //符合规定返回true
    return reg.test(nickname);

}

/**
 * 检查两次密码：
 *      1、是否有输入
 *      2、输入是否一致
 *      3、密码是否符合规定
 * 会通过提示框给出对应提示
 * @param tipObj 提示框
 * @param pw1Val 第一次输入密码的<strong>值</strong>
 * @param pw2Val 重复输入密码的<strong>值</strong>
 * @returns {boolean} 所有数据均符合要求返回true，否则为false
 */
function validatePw(tipObj, pw1Val, pw2Val) {
    //判断第一次密码有没有输入
    if (pw1Val == null || pw1Val === "") {
        setTip(tipObj, "请输入你的账号密码", "warning");
        return false;
    } else {
        //检查密码规范
        if (!standardPw(pw1Val)) {
            //密码已输入，但不符合规定
            setTip(tipObj, "密码由6~20个大小写英文字母、数字或者下划线组成，请检查哦", "warning");
            return false;
        }
    }
    //对比两次输入的密码
    if (pw2Val != null && pw2Val !== "") {
        //已经第二次输入了密码
        if (pw1Val === pw2Val) {
            //密码相同
            setTip(tipObj, "两次密码相同~", "success");
            return true;
        } else {
            //两次密码不相同
            setTip(tipObj, "两次密码<strong>不相同</strong>", "danger");
            return false;
        }
    } else {
        //没有再次输入密码
        setTip(tipObj, "啊嘞？你没有再次输入密码", "warning");
        return false;
    }
}

/**
 * 当两个密码输入框之一输入改变时，
 * 判断判断两次密码是否相同，并检查其输入规范
 * @param tip 提示框
 * @param pw1Obj 首次输入密码的<strong>输入框对象</strong>
 * @param pw2Obj 再次输入密码的<strong>输入框对象</strong>
 */
function pwOnchangeCheck(tip, pw1Obj, pw2Obj) {
    //自动检测两次密码是否相同，同时会检测密码的输入规范
    //两个输入框都绑定
    pw1Obj.on("change", function () {
        //在输入改变时才获取值，而不能再事件外获取
        validatePw(tip, pw1Obj.val(), pw2Obj.val());
    });
    pw2Obj.on("change", function () {
        validatePw(tip, pw1Obj.val(), pw2Obj.val());
    });
}

/**
 * 判断用户ID是否可用
 * @returns {boolean} 如果为空、包含非数字字符或者已被注册则返回false，表示不可用
 */
function validateUserId(tipObj, userId) {
    //判断用户id是否可用的变量
    let bool;
    //检查是否为空
    if (userId == null || userId === "") {
        setTip(tipObj, "请选择一个账号ID", "warning");
        bool = false;
    } else if (!standardId(userId)) {
        //检查是否为标准id
        //进入if语句则不是标准id
        //用户名不存在，可以使用
        setTip(tipObj, "账号ID仅能包含数字且在6~20个字符内！", "warning");
        bool = false;
    } else {
        /*
         * 发送ajax请求
         * 因为ajax请求是异步的，所以不抽出函数了
         */
        $.ajax({
            type: "GET",
            //同步
            async: false,
            url: "/user/" + userId,
            dataType: "json",
            success: function (msg) {
                if (msg.code == 200) {
                    //用户名存在，提示用户换一个ID
                    setTip(tipObj, msg.message, "danger");
                    bool = false;
                } else if (msg.code == 404) {
                    //用户名不存在，可以使用
                    setTip(tipObj, msg.message, "success");
                    bool = true;
                } else {
                    toastr.error("检查id是否重复时发生错误，请稍后重试");
                    bool = false;
                }
            },
            error: function (msg) {
                toastr.error("检查id是否重复时发生错误！请稍后重试");
                bool = false;
            }
        });
    }
    return bool;
}

/**
 * 昵称非空时检查用户昵称是否可用，并给出提示
 * 昵称由3~10个汉字、英文或数字组成，可以包含下划线
 * @param nickVal
 * @param tipObj
 * @returns {boolean}
 */
function validateNickname(tipObj, nickVal) {
    if (!standardNickname(nickVal)) {
        // 密码已输入，且不符合规定
        setTip(tipObj, "昵称由3~10个汉字、英文或数字组成，可以包含下划线", "warning");
        return false;
    }
    return true;
}

/**
 * 得到当前时间
 * @returns {string} 日期格式为yyyy-MM-dd
 */
function getCurrentDate() {
    //得到当前时间
    let date_now = new Date();
    //得到当前年份
    let year = date_now.getFullYear();
    //得到当前月份
    //  注意！js中获取Date中的month时结果为0~11
    //  判断当前月份是否小于10，如果小于，那么就在月份的前面补0
    let month = date_now.getMonth() + 1 < 10 ? "0" + (date_now.getMonth() + 1) : (date_now.getMonth() + 1);
    //得到当前几号
    let day = date_now.getDate() < 10 ? "0" + date_now.getDate() : date_now.getDate();
    return year + "-" + month + "-" + day;
}

/**
 * 比较日期是否超出范围
 * @param date 生日日期
 * @param compareDate 被比较的日期
 * @returns {boolean} 大于等于返回true，小于返回false
 */
function compareDate(date, compareDate) {
    //初始化数组
    let arr1 = [];
    let arr2 = [];
    if (date != null && compareDate != null) {
        //切割字符串
        arr1 = date.split('-');
        let date1 = new Date(arr1[0], parseInt(arr1[1]) - 1, arr1[2]);
        arr2 = compareDate.split('-');
        let date2 = new Date(arr2[0], parseInt(arr2[1]) - 1, arr2[2]);
        //大于等于返回true，小于返回false
        return (date1 >= date2);
    }
}

/**
 * 判断是否为合理的日期。
 * @param birthday 日期
 * @returns {String} 如果日期为空返回null
 * 早于1970-01-01返回small
 * 晚于当前时间返回big
 */
function reasonalDate4Tip(birthday) {
    if (birthday == null || birthday === "") {
        return "null";
    }
    if (compareDate(birthday, "1970-01-01")) {
        //大于等于最小日期，合理
        if (compareDate(birthday, getCurrentDate())) {
            //大于等于当前日期，不合理
            return "big";
        }

        return "ok";
    } else {
        //小于最小日期
        return "small";
    }
}