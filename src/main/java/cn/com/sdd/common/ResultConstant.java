package cn.com.sdd.common;

/**
 * @author suidd
 * @name ResultConstant
 * @description 关于http响应的所有常量
 * @date 2020/5/7 14:35
 * Version 1.0
 **/
public class ResultConstant {
    /**
     * 正常操作成功
     */
    public static Integer SUCCESS_CODE = 200;
    public static String SUCCESS_MSG = "ok";

    /**
     * 执行成功，但未达到预期目的
     */
    public static Integer SUCCESS_NO_DATA_CODE = 201;
    public static String SUCCESS_NO_DATA_MSG = "暂无数据";

    /**
     * 通用错误
     */
    public static Integer FAIL_CODE = 500;
    public static String FAIL_MSG = "操作失败";

    /**
     * 参数校验未通过
     */
    public static Integer FAIL_PARAM_CODE = 501;
    public static String FAIL_PARAM_MSG = "无效参数";
}