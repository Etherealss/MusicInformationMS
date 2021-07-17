package pers.wtk.common.exception;

/**
 * @author wtk
 * @description 用户请求异常
 * @date 2021-05-26
 */
public class BadRequestException extends ServerException {
    public BadRequestException(String type, String msg) {
        super(type, msg);
    }
}
