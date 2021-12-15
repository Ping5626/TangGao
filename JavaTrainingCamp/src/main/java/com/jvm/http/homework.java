package com.jvm.http;

/**
 * @author 高一平
 * @date 2021/10/7
 * @description 写一段代码，使用 HttpClient 或 OkHttp 访问  http://localhost:8801 ，代码提交到 GitHub
 */
public class homework {

    public static void main(String[] args) throws Exception {
        HttpClientUtils httpUtils = new HttpClientUtils();
        httpUtils.get("http://localhost:8801");
    }

}
