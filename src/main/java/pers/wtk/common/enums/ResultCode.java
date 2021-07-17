package pers.wtk.common.enums;

/**
 * @author wtk
 * @description
 * @date 2021-05-26
 */
public class ResultCode {

    public static final int SUCCESS = 200;
    /**
     * 请求要求用户的身份认证
     */
    public static final int UNAUTHORIZED = 401;
    public static final int NOT_FOUND = 404;
    /**
     * 客户端请求中的方法被禁止
     */
    public static final int METHOD_NOT_ALLOWED = 405;

    /**
     * 服务器在验证在请求的头字段中给出先决条件时，没能满足其中的一个或多个
     */
    public static final int PRECONDITION_FAILED = 412;

    /** 错误的类型 */
    public static final int ERROR_TYPE = 1002;

    /** 错误的参数类型 */
    public static final int ERROR_PARAM = 1003;

    /** 用户请求的行为执行失败 */
    public static final int ACTION_FAIL = 400;
    /** 用户请求的行为不允许执行 */
    public static final int ACTION_NOT_ALLOW = 1004;

}
