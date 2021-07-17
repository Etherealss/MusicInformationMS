package pers.wtk.pojo.vo;

import lombok.Data;
import pers.wtk.common.enums.ResultCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wtk
 * @description 接口结果
 * @date 2021-05-26
 */
@Data
public class Msg {
    /** 结果码 */
    private int code;

    /** 结果描述，对结果进行描述，如“用户密码错误” */
    private String message;

    /** 接口返回的结果 */
    private Map<String, Object> data = new HashMap<>(2);

    public static Msg success() {
        return new Msg(200, "请求执行成功");
    }

    public static Msg success(String message) {
        return new Msg(200, message);
    }

    public static Msg success(String key, Object metadata) {
        Msg msg = new Msg(200, "success");
        msg.addData(key, metadata);
        return msg;
    }

    public static Msg success(String message, String key, Object metadata) {
        Msg msg = success(key, metadata);
        msg.setMessage(message);
        return msg;
    }

    public static Msg userUnlogged() {
        return new Msg(ResultCode.UNAUTHORIZED, "用户未登录");
    }

    public static Msg exception() {
        return new Msg(500, "exception");
    }

    public static Msg exception(String message) {
        return new Msg(500, message);
    }

    public static Msg exception(Throwable e) {
        return new Msg(500, e.getMessage());
    }

    public static Msg exception(String key, Object metadata) {
        Msg msg = new Msg(500, "exception");
        msg.addData(key, metadata);
        return msg;
    }

    public static Msg notFound(String message) {
        return new Msg(ResultCode.NOT_FOUND, message);
    }

    public static Msg notReadable(String message) {
        return new Msg(ResultCode.ACTION_FAIL, message);
    }

    public static Msg paramsError(String message) {
        return new Msg(ResultCode.ERROR_PARAM, message);
    }

    public void addData(String key, Object metadata) {
        data.put(key, metadata);
    }

    public Msg() {}

    public Msg(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
