package pers.wtk.common.exception;

/**
 * @author wtk
 * @description 服务器非检查型异常
 * @date 2021-05-26
 */
public class ServerRuntimeException extends RuntimeException {

    /** 异常类型 */
    private String type;

    /** 结果码的文字描述 */
    private String msg;

    public ServerRuntimeException() {
    }

    public ServerRuntimeException(String msg) {
        this.msg = msg;
    }

    public ServerRuntimeException(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
