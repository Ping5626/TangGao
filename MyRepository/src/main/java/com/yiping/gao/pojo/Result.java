package com.yiping.gao.pojo;

import lombok.Getter;

/**
 * @Author: 高一平
 * @Date: 2019/9/2 9:34
 * @Description: 通用返回结果
 **/
@Getter
public class Result {
    /**
     * 返回码
     */
    private String code;
    /**
     * 返回提示信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private Object result;

    public void success() {
        success(null);
    }

    public void success(Object object) {
        this.code = "0";
        this.msg = "请求成功";
        this.result = object;
    }
}
