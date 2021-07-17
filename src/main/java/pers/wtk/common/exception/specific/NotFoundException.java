package pers.wtk.common.exception.specific;

import pers.wtk.common.enums.ExceptionType;
import pers.wtk.common.exception.BadRequestException;

/**
 * @author wtk
 * @description
 * @date 2021-05-28
 */
public class NotFoundException extends BadRequestException {
    public NotFoundException() {
        super(ExceptionType.NOT_FOUND, "目标不存在");
    }

    public NotFoundException(String message) {
        super(ExceptionType.NOT_FOUND, message);
    }
}
