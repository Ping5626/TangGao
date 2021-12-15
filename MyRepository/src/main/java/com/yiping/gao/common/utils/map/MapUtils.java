package com.yiping.gao.common.utils.map;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 高一平
 * @date 2021/4/13
 * @description 地图工具类
 */
public abstract class MapUtils {

    @Data
    public class Position {
        private Double longitude;
        private Double latitude;
    }

    @Data
    public class Address {
        private String address;
        private String country;
        private String province;
        private String city;
        private String district;
        private String town;
        private String street;
        private String areaCode;
        private Double longitude;
        private Double latitude;
    }

    /**
     * 圆周率
     */
    private static final double PI = 3.14159265;
    /**
     * 赤道半径(单位m)
     */
    private static final double EARTH_RADIUS = 6378137;
    /**
     * 弧度
     */
    private static final double RAD = Math.PI / 180.0;

    /**
     * 根据经纬度获取地址
     *
     * @param location 经纬度
     * @return 地址信息
     * @throws Exception
     */
    public abstract Address getAddress(String location) throws Exception;

    /**
     * 根据地址获取经纬度
     *
     * @param address 地址信息
     * @return 经纬度
     * @throws Exception
     */
    public abstract Position getPosition(String address) throws Exception;

    /**
     * 获取距离
     *
     * @param mainPosition
     * @param position
     * @return
     */
    public Double getDistance(Position mainPosition, Position position) {
        if (mainPosition != null && position != null) {
            return getDistance(mainPosition.getLongitude(), mainPosition.getLatitude(),
                    position.getLongitude(), position.getLatitude());
        } else {
            return null;
        }
    }

    /**
     * 基于余弦定理求两经纬度距离
     *
     * @param lon1 第一点的精度
     * @param lat1 第一点的纬度
     * @param lon2 第二点的精度
     * @param lat2 第二点的纬度
     * @return 返回的距离，单位km
     */
    public double getDistance(double lon1, double lat1, double lon2, double lat2) {
        double radLat1 = lat1 * RAD;
        double radLat2 = lat2 * RAD;

        double radLon1 = lon1 * RAD;
        double radLon2 = lon2 * RAD;

        /**
         * south
         */
        if (radLat1 < 0) {
            radLat1 = Math.PI / 2 + Math.abs(radLat1);
        }
        /**
         * north
         */
        if (radLat1 > 0) {
            radLat1 = Math.PI / 2 - Math.abs(radLat1);
        }
        /**
         * west
         */
        if (radLon1 < 0) {
            radLon1 = Math.PI * 2 - Math.abs(radLon1);
        }
        /**
         * south
         */
        if (radLat2 < 0) {
            radLat2 = Math.PI / 2 + Math.abs(radLat2);
        }
        /**
         * north
         */
        if (radLat2 > 0) {
            radLat2 = Math.PI / 2 - Math.abs(radLat2);
        }
        /**
         * west
         */
        if (radLon2 < 0) {
            radLon2 = Math.PI * 2 - Math.abs(radLon2);
        }
        double x1 = EARTH_RADIUS * Math.cos(radLon1) * Math.sin(radLat1);
        double y1 = EARTH_RADIUS * Math.sin(radLon1) * Math.sin(radLat1);
        double z1 = EARTH_RADIUS * Math.cos(radLat1);

        double x2 = EARTH_RADIUS * Math.cos(radLon2) * Math.sin(radLat2);
        double y2 = EARTH_RADIUS * Math.sin(radLon2) * Math.sin(radLat2);
        double z2 = EARTH_RADIUS * Math.cos(radLat2);

        double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
        //余弦定理求夹角
        double theta = Math.acos((EARTH_RADIUS * EARTH_RADIUS + EARTH_RADIUS * EARTH_RADIUS - d * d) / (2 * EARTH_RADIUS * EARTH_RADIUS));
        double dist = theta * EARTH_RADIUS;
        return dist;
    }

    /**
     * 根据提供的经度和纬度、以及半径，取得此半径内的最大最小经纬度
     *
     * @param radius 单位米 *
     * @return minLat, minLng, maxLat, maxLng
     */
    public Double[] getAround(Double lon, Double lat, Double radius) {
        Double longitude = lon;
        Double latitude = lat;
        Double degree = (24901 * 1609) / 360.0;
        Double raidusMile = radius;
        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        Double minLat = latitude - radiusLat;
        Double maxLat = latitude + radiusLat;
        Double mpdLng = degree * Math.cos(latitude * (PI / 180));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        Double minLng = longitude - radiusLng;
        Double maxLng = longitude + radiusLng;
        return new Double[]{minLat, maxLat, minLng, maxLng};
    }

    /**
     * 解析地址信息
     *
     * @param address 地址信息（需包含省市县/区等字符用于分割地址）
     * @return
     */
    public Address formatAddress(String address) {
        if (address != null && !address.isEmpty()) {
            Address result = new Address();
            String regex = "(?<province>[^特别行政区]+特别行政区|[^自治区]+自治区|[^省]+省|[^市]+市)(?<city>省直辖行政单位|省属虚拟市|市辖县|市辖区|县|自治州|[^地区]+地区|[^州]+州|[^盟]+盟|[^市]+市|[^区]+区|)?(?<district>[^旗]+旗|[^市]+市|[^区]+区|[^县]+县)?(?<town>[^县]+县|[^区]+区|[^乡]+乡|[^村]+村|[^镇]+镇|[^街道]+街道)?(?<village>.*)";
            Matcher matcher = Pattern.compile(regex).matcher(address);
            while (matcher.find()) {
                result.setAddress(address);
                result.setProvince(matcher.group("province"));
                if (result.getProvince() != null
                        && ("北京市".equals(result.getProvince()) || "上海市".equals(result.getProvince()) || "天津市".equals(result.getProvince()) || "重庆市".equals(result.getProvince()))) {
                    result.setCity(result.getProvince());
                    result.setDistrict(matcher.group("city"));
                } else {
                    result.setCity(matcher.group("city"));
                    result.setDistrict(matcher.group("district"));
                }
                result.setTown(matcher.group("town"));
                result.setStreet(matcher.group("village"));
            }
            return result;
        }
        return null;
    }

}
