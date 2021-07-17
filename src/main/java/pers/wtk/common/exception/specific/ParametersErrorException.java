package pers.wtk.common.exception.specific;

import pers.wtk.common.enums.ExceptionType;
import pers.wtk.common.exception.BadRequestException;

/**
 * @author wtk
 * @description 接口参数异常
 * @date 2021-06-08
 */
public class ParametersErrorException extends BadRequestException {
    public ParametersErrorException(String msg) {
        super(ExceptionType.ERROR_PARAM, msg);
    }

    public ParametersErrorException() {
        super(ExceptionType.ERROR_PARAM, "参数异常");
    }
}
