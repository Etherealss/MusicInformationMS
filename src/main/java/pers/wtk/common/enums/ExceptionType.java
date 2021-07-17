package pers.wtk.common.enums;

/**
 * @author wtk
 * @description
 * @date 2021-05-26
 */
public class ExceptionType {

    /** 用户请求的行为失败 */
    public static final String ACTION_FAIL = "Fail";

    /**
     * 没有记录
     */
    public static final String NOT_FOUND = "NotFound";

    public static final String WRONG_TARGET = "WrongTarget";

    /** 错误的类型，可以是多种类型，视具体场景而定 */
    public static final String ERROR_TYPE = "ErrorType";

    /** 错误的参数，可以是多种参数的错误，如数值小于0等 */
    public static final String ERROR_PARAM = "ErrorParam";

    /** 身份验证失败 */
    public static final String AUTHENTICATION_FAIL = "AuthenticationFail";

    /**
     * 不足 错误
     * 如余额不足
     */
    public static final String INSUFFICIENT = "Insufficient";

    /** 服务器出错 */
    public static final String SERVICE_ERROR = "ServerError";

}
