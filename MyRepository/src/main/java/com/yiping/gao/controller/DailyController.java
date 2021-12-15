package com.yiping.gao.controller;

import com.yiping.gao.pojo.Result;
import com.yiping.gao.service.base.TestBaseService;
import com.yiping.gao.service.sys.ServiceHandle;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author: 高一平
 * @Date: 2019/9/2 9:30
 * @Description:
 **/
@RestController
public class DailyController {

    /**
     * 统一访问入口
     *
     * @param SID    请求所属
     * @param action 请求动作
     * @param object 请求参数 JSON对象
     * @return 返回结果
     */
    @PostMapping("/json/{SID}/{action}")
    public Result execute(@PathVariable(value = "SID") String SID,
                          @PathVariable(value = "action") String action,
                          @RequestBody Object object) {
        Result result = new Result();
        try {
            result = ServiceHandle.invoke("success", object, TestBaseService.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

}
