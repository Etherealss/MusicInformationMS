package pers.wtk.common.exception.resolver;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import pers.wtk.common.enums.ResultCode;
import pers.wtk.common.exception.BusinessRuntimeException;
import pers.wtk.common.exception.specific.ActionFailException;
import pers.wtk.common.exception.specific.NotFoundException;
import pers.wtk.common.exception.specific.ParametersErrorException;
import pers.wtk.common.exception.specific.TypeErrorException;
import pers.wtk.pojo.vo.Msg;

/**
 * @author wtk
 * @description
 * @date 2021-05-06
 */
@ControllerAdvice
@ResponseBody
public class ExceptionResolver {

    private static Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    /**
     * 不可读
     * @return 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Msg handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        if (e.getCause() instanceof MismatchedInputException) {
            logger.warn("pojo对象参数类型不匹配", e);
            return Msg.exception("pojo对象参数类型不匹配");
        }
        logger.warn("没有读取权限", e);
        return Msg.exception("没有读取权限");
    }

    /**
     * HttpRequestMethodNotSupportedException 前后端交接的接口参数缺失
     * @return 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Msg handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.warn("请求方式错误:" + e.getMessage(), e);
        return Msg.paramsError("请求方式错误：" + e.getMessage());
    }

    /**
     * MissingServletRequestParameterException 前后端交接的接口参数缺失
     * @return 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Msg handleHttpMessageNotReadableException(MissingServletRequestParameterException e) {
        logger.warn("前后端交接的接口参数缺失:" + e.getMessage(), e);
        return Msg.paramsError("前后端交接的接口参数缺失：" + e.getMessage());
    }

    /**
     * @param e ParametersErrorException 参数错误
     * @return 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ParametersErrorException.class, TypeErrorException.class, MethodArgumentTypeMismatchException.class})
    public Msg handleParametersErrorException(Exception e) {
        logger.info("用户输入的参数错误", e);
        if (e instanceof ParametersErrorException) {
            return Msg.paramsError("参数错误：" + ((ParametersErrorException) e).getMsg());
        } else if (e instanceof TypeErrorException) {
            return new Msg(ResultCode.ERROR_TYPE, "类型错误：" + ((TypeErrorException) e).getMsg());
        } else {
            return Msg.paramsError("参数错误：" + e.getMessage());
        }
    }

    /**
     * NotFoundException 目标不存在
     * 访问成功，而且响应流程在正常流程之内，只是结果不是用户期望的结果，所以返回200
     * @return 200 SUCCESS
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NotFoundException.class)
    public Msg handleNotFoundException(NotFoundException e) {
        logger.info("目标不存在：" + e.getMsg());
        return Msg.notFound("目标不存在：" + e.getMsg());
    }

    /**
     * ActionFailException 请求执行失败
     * @param e 访问成功，而且响应流程在正常流程之内，只是结果不是用户期望的结果，所以返回200
     * @return 200 SUCCESS
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ActionFailException.class)
    public Msg handleActionFailException(ActionFailException e) {
        logger.info("请求执行失败：" + e.getMsg());
        return new Msg(ResultCode.ACTION_FAIL, e.getMsg());
    }

    /**
     * @return 500 其他未知异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public Msg handleException(Throwable e) {
        if (e instanceof BusinessRuntimeException) {
            logger.warn("接口异常：{}", e.getMessage());
            return Msg.exception("接口异常" + ((BusinessRuntimeException) e).getMsg());
        }

        logger.error("服务运行异常：\n{}", e.getMessage());
        return Msg.exception("服务器异常：" + e.getMessage());
    }
}