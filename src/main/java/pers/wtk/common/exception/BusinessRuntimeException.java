package pers.wtk.common.exception;

/**
 * @author wtk
 * @description 业务非检查型异常
 * @date 2021-05-26
 */
public class BusinessRuntimeException extends ServerRuntimeException {

    public BusinessRuntimeException() {
    }

    public BusinessRuntimeException(String type, String msg) {
        super(type, msg);
    }
}
