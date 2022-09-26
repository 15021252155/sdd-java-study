package cn.com.sdd.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author suidd
 * @name Result
 * @description HTTP响应状态
 * @date 2020/5/7 14:34
 * Version 1.0
 **/
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Result<T> implements Serializable {
    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String msg;
    /**
     * 响应数据
     */
    private Object data;

    public static Result call(Integer code, String msg, Object data) {
        return new Result().setCode(code).setMsg(msg).setData(data);
    }

    public static Result ok(String msg, Object data) {
        return call(ResultConstant.SUCCESS_CODE, msg, data);
    }

    public static Result ok(String msg) {
        return ok(msg, null);
    }

    public static Result ok(Object data) {
        return ok(ResultConstant.SUCCESS_MSG, data);
    }

    public static Result error(Integer code, String msg, Object data) {

        return call(code, msg, data);
    }

    public static Result error(Integer code, String msg) {
        return call(code, msg, null);
    }

    public static Result error(Integer code, Object data) {
        return call(code, null, data);
    }

    /**
     * 请求失败
     *
     * @param msg 返回信息
     * @param <T> 类型
     * @return ResultVo
     */
    public static <T> Result failed(String msg) {
        return new Result().setCode(ResultConstant.FAIL_CODE).setMsg(msg);
    }

    /**
     * 请求失败
     *
     * @param msg  返回信息
     * @param data 返回数据
     * @param <T>  类型
     * @return ResultVo
     */
    public static <T> Result failed(String msg, T data) {
        return  new Result().setCode(ResultConstant.FAIL_CODE).setMsg(msg).setData(data);
    }
    /**
     * 未查询到相关数据-但是仍需要返回给前端数据
     *
     * @param <T> 类型
     * @return ResultVo
     */
    public static <T> Result noData() {
        return new Result().setCode(ResultConstant.SUCCESS_NO_DATA_CODE).setMsg(ResultConstant.SUCCESS_NO_DATA_MSG);
    }

    /**
     * 参数异常
     *
     * @param <T>
     * @return
     */
    public static <T> Result paramError() {
        return new Result().setCode(ResultConstant.FAIL_PARAM_CODE).setMsg(ResultConstant.FAIL_PARAM_MSG);
    }

    /**
     * 参数异常
     *
     * @param <T>
     * @return
     */
    public static <T> Result paramError(String msg) {
        return new Result().setCode(ResultConstant.FAIL_PARAM_CODE).setMsg(msg);
    }
}