package com.yiping.gao.common.utils.map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yiping.gao.common.utils.http.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 高一平
 * @date 2020/10/9
 * @description 百度地图相关工具
 */
@Slf4j
public class BaiduMapUtils extends MapUtils {

    private HttpClientUtils http = new HttpClientUtils();

    /**
     * 百度地图参数
     */
    private static String ak = "z65o0tIB2T29ubrzLYonYB13wgHIKTMS";
    private static String baiduMapUrl = "http://api.map.baidu.com/geocoder/v2/?ak=" + ak + "&output=json&";

    /**
     * 经纬度获取地址
     *
     * @param location
     * @return
     * @throws Exception
     */
    @Override
    public Address getAddress(String location) throws Exception {
        if (location != null && !location.trim().isEmpty()) {
            String url = baiduMapUrl + "location=" + location;
            String response = http.get(url);
            JSONObject resultJson = getBaiduResult(response);
            if (resultJson != null) {
                Address address = new Address();
                address.setAddress(resultJson.getString("formatted_address"));
                address.setCountry(resultJson.getJSONObject("addressComponent").getString("country"));
                address.setProvince(resultJson.getJSONObject("addressComponent").getString("province"));
                address.setCity(resultJson.getJSONObject("addressComponent").getString("city"));
                address.setDistrict(resultJson.getJSONObject("addressComponent").getString("district"));
                address.setTown(resultJson.getJSONObject("addressComponent").getString("town"));
                address.setStreet(resultJson.getJSONObject("addressComponent").getString("street"));
                address.setAreaCode(resultJson.getJSONObject("addressComponent").getString("adcode"));
                address.setLongitude(resultJson.getJSONObject("location").getDoubleValue("lng"));
                address.setLatitude(resultJson.getJSONObject("location").getDoubleValue("lat"));
                return address;
            }
        }
        return null;
    }

    /**
     * 地址转经纬度
     *
     * @param address
     * @return
     */
    @Override
    public Position getPosition(String address) throws Exception {
        if (address != null && !address.trim().isEmpty()) {
            // 处理地址中的特殊字符
            address = dealAddress(address);
            String url = baiduMapUrl + "address=" + address;
            String response = http.get(url);
            JSONObject resultJson = getBaiduResult(response);
            if (resultJson != null) {
                Position pos = new Position();
                pos.setLongitude(resultJson.getJSONObject("location").getDoubleValue("lng"));
                pos.setLatitude(resultJson.getJSONObject("location").getDoubleValue("lat"));
                return pos;
            }
        }
        return null;
    }

    /**
     * 解析百度地址返回结果
     *
     * @param response 原始返回信息
     * @return
     */
    private JSONObject getBaiduResult(String response) {
        if (response != null && !response.isEmpty()) {
            JSONObject responseJson = JSON.parseObject(response);
            if (responseJson.getInteger("status").equals(0)) {
                return responseJson.getJSONObject("result");
            }
        }
        return null;
    }

    /**
     * 处理地址中的特殊字符
     *
     * @param address
     * @return
     */
    private String dealAddress(String address) {
        address = address.replace(" ", "");
        address = address.replace("，", "");
        address = address.replace("。", "");
        address = address.replace("、", "");
        address = address.replace("：", "");
        address = address.replace("；", "");
        address = address.replace("（", "");
        address = address.replace("）", "");
        address = address.replace("【", "");
        address = address.replace("】", "");
        address = address.replace("《", "");
        address = address.replace("》", "");
        address = address.replace("”", "");
        return address;
    }

}
