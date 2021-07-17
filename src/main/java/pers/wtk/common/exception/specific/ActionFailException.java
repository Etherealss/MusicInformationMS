package pers.wtk.common.exception.specific;

import pers.wtk.common.enums.ExceptionType;
import pers.wtk.common.exception.BadRequestException;

/**
 * @author wtk
 * @description 用户请求的行为失败
 * @date 2021-06-09
 */
public class ActionFailException extends BadRequestException {

    public ActionFailException(String message) {
        super(ExceptionType.ACTION_FAIL, message);
    }
}
