package com.yhl.ros.common.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: MapUtils
 * @Package: com.yhl.ros.common.utils
 * @Description: 描述
 * @Date: 2020-02-12 01:18
 */
@Slf4j
public class MapUtils {
    // 235fe9aca7ac6d98c60c95981a09b854,fc18e8c4181cb9620c2625cca0e9927a,218d529ce14fa2eb19d691b00a1c726c
    private static String[] keys = {"38e0dff65d068d0ac04993c61e66687c", "9a7d90423142a1a35048f934a4caa619", "916b32662484b98f84a3938684d4fc54", "221fb2e7a8341a3ef9ffdc259263aee3",
            "d409b80e01694874c47963047d3ed7e9", "2194b717e34a25a9bb3312622b99bf4f", "7c522c2e0d862240c84ccd9be7985c33", "218d529ce14fa2eb19d691b00a1c726c",
            "4c8eb2e5de4a5f63dd6de53c41c74c76", "fc18e8c4181cb9620c2625cca0e9927a", "218d529ce14fa2eb19d691b00a1c726c", "8dfdb8ddda399257e25d5231b98eab00",
            "235fe9aca7ac6d98c60c95981a09b854"};
    private static String key = "218d529ce14fa2eb19d691b00a1c726c";
//    private static String danoneKey = "e003db9c35b251158b61311c40ed3650";

    /**
     * 阿里云api 根据经纬度获取地址
     *
     * @param longitude
     * @param latitude
     * @return
     */
    public static String getAdd(String longitude, String latitude) {
        StringBuffer sb = new StringBuffer();
        sb.append("key=").append(key).append("&location=").append(longitude).append(",").append(latitude);
        String res = sendPost("http://restapi.amap.com/v3/geocode/regeo", sb.toString());
        // log.info(res);
        JSONObject jsonObject = JSONObject.parseObject(res);
        JSONObject jsonObject1 = JSONObject.parseObject(jsonObject.getString("regeocode"));
        String add = jsonObject1.get("formatted_address").toString();
        return add;
    }

    /**
     * 阿里云api 根据经纬度获取所在城市
     *
     * @param longitude
     * @param latitude
     * @return
     */
    public static String getCity(String longitude, String latitude) {
        // log 大 lat 小
        // 参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)
        String urlString = "http://gc.ditu.aliyun.com/regeocoding?l=" + longitude + "," + latitude + "&type=010";
        String res = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                res += line + "\n";
            }
            in.close();
            JSONObject jsonObject = JSONObject.parseObject(res);
            JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("addrList"));
            JSONObject j_2 = JSONObject.parseObject(jsonArray.get(0).toString());
            String allAdd = j_2.getString("admName");
            String arr[] = allAdd.split(",");
            res = arr[1];
        } catch (Exception e) {
            log.error("error in wapaction,and e is " + e.getMessage());
        }
        // log.info(res);
        return res;
    }

    /**
     * 高德api 根据地址获取经纬度
     *
     * @param name
     * @return
     */
    public static Map<String, String> getLatAndLogByName(String name) {
        List<String> keyList = shuffle(keys);
        if (CollectionUtils.isEmpty(keyList)) {
            return new HashMap<>();
        }
        StringBuffer s = new StringBuffer();
        int count = 0;
        if (org.apache.commons.lang3.StringUtils.isNotBlank(name)) {
            name = name.replaceAll(" ", "");
        }
        s.append("key=" + keyList.get(count) + "&keywords=" + name);
        String res = sendGet("https://restapi.amap.com/v3/place/text", s.toString());
        JSONObject jsonObject = JSONObject.parseObject(res);
        JSONArray jsonArray = new JSONArray();
        String info = jsonObject.getString("info");
        if (!StringUtils.equals("OK", info)) {
            for (int i = 0; i < keyList.size(); i++) {
                count++;
                if (count > keyList.size() - 1) {
                    return new HashMap<>();
                } else {
                    StringBuffer s1 = new StringBuffer();
                    s1.append("key=" + keyList.get(count) + "&keywords=" + name);
                    res = sendGet("https://restapi.amap.com/v3/place/text", s1.toString());
                    jsonObject = JSONObject.parseObject(res);
                    info = jsonObject.getString("info");
                    if (StringUtils.equals("OK", info)) {
                        jsonArray = JSONArray.parseArray(jsonObject.getString("pois"));
                        break;
                    }
                }
            }
        } else {
            jsonArray = JSONArray.parseArray(jsonObject.getString("pois"));
        }

        if (jsonArray == null || jsonArray.size() == 0) {
            return new HashMap<>();
        } else {
            JSONObject location = (JSONObject) jsonArray.get(0);
            String add = location.get("location").toString();
            String address = location.get("name").toString();

            Map<String, String> data = new HashMap<>();
            data.put("location", add);
            data.put("address", address);
            return data;
        }
    }

    /**
     * 高德api 根据地址获取经纬度
     *
     * @param name
     * @return
     */
    /*
     * public static Map<String,String> getLatAndLogByName(String name) {
     * StringBuffer s = new StringBuffer(); s.append("key=" + key + "&address=" +
     * name); String res = sendPost("http://restapi.amap.com/v3/geocode/geo",
     * s.toString()); log.info(res); JSONObject jsonObject =
     * JSONObject.parseObject(res); JSONArray jsonArray =
     * JSONArray.parseArray(jsonObject.getString("geocodes"));
     * if(jsonArray.size()==0){ return new HashMap<>(); } else { JSONObject location
     * = (JSONObject) jsonArray.get(0); String add =
     * location.get("location").toString(); String address =
     * location.get("formatted_address").toString(); Map<String,String> data = new
     * HashMap<>(); data.put("location",add); data.put("address",address); return
     * data; } }
     *
     * /** 高德api 根据地址获取经纬度
     *
     * @return
     */
    public static String getAddByAMAP(String longitude, String latitude) {
        StringBuffer sb1 = new StringBuffer();
        sb1.append("key=").append(key).append("&location=").append(longitude).append(",").append(latitude);
        String res = sendPost("http://restapi.amap.com/v3/geocode/regeo", sb1.toString());
        JSONObject jsonObject = JSONObject.parseObject(res);
        JSONObject jsonObject1 = JSONObject.parseObject(jsonObject.getString("regeocode"));
        String add = jsonObject1.get("formatted_address").toString();
        return add;
    }

    /**
     * 高德api 坐标转换---转换至高德经纬度
     *
     * @return
     */
    public static String convertLocations(String longitude, String latitude, String type) {
        StringBuffer sb2 = new StringBuffer();
        sb2.append("key=").append(key).append("&locations=").append(longitude).append(",").append(latitude)
                .append("&coordsys=");
        if (type == null) {
            sb2.append("gps");
        } else {
            sb2.append(type);
        }
        String res = sendPost("http://restapi.amap.com/v3/assistant/coordinate/convert", sb2.toString());
        JSONObject jsonObject = JSONObject.parseObject(res);
        String add = jsonObject.get("locations").toString();
        return add;
    }

    public static String getAddByName(String name) {
        // log 大 lat 小
        // 参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)
        String urlString = "http://gc.ditu.aliyun.com/geocoding?a=" + name;
        String res = "";
        try {
            URL url1 = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                res += line + "\n";
            }
            in.close();
            JSONObject jsonObject = JSONObject.parseObject(res);
            String longitude = jsonObject.getString("lon");
            String latitude = jsonObject.getString("lat");
            System.err.println(jsonObject);
            res = getNearbyAdd(longitude, latitude);
        } catch (Exception e) {
            log.info("error in wapaction,and e is " + e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    public static String getNearbyAdd(String longitude, String latitude) {

        String add = sendGet("http://ditu.amap.com/service/regeo",
                "longitude=" + longitude + "&latitude=" + latitude + "&type=010");

        return add;
    }

    /**
     * 高德api 关键字模糊查询
     *
     * @param keyWord
     * @param city
     * @return
     */
    public static String getKeywordsAddByLbs(String keyWord, String city) {
        StringBuffer sb3 = new StringBuffer();
        sb3.append("key=" + key + "&keywords=");
        if (keyWord.contains(" ")) {
            String[] str = keyWord.split(" ");
            for (int i = 0; i < str.length; i++) {
                if (i == 0) {
                    sb3.append(str[i]);
                } else {
                    sb3.append("+" + str[i]);
                }
            }
        } else {
            sb3.append(keyWord);
        }
        sb3.append("&city=" + city);
        sb3.append("offset=10&page=1");
        String around = sendPost("http://restapi.amap.com/v3/place/text", sb3.toString());
        return around;
    }

    /**
     * 高德api 经纬度/关键字 附近地标建筑及地点查询
     *
     * @param longitude
     * @param latitude
     * @param keyWord
     * @return
     */
    public static String getAroundAddByLbs(String longitude, String latitude, String keyWord) {
        String around = sendPost("http://restapi.amap.com/v3/place/around", "key=" + key + "&location=" + longitude
                + "," + latitude + "&keywords=" + keyWord + "&radius=2000&offset=10&page=1");
        return around;
    }

    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                // log.info(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.info("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.info("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * GET请求数据
     *
     * @param get_url url地址
     * @param content key=value形式
     * @return 返回结果
     * @throws Exception
     */
    public static String sendGetData(String get_url, String content) throws Exception {
        String result = "";
        URL getUrl = null;
        BufferedReader reader = null;
        String lines = "";
        HttpURLConnection connection = null;
        try {
            if (content != null && !content.equals(""))
                get_url = get_url + "?" + content;
            // get_url = get_url + "?" + URLEncoder.encode(content, "utf-8");
            getUrl = new URL(get_url);
            connection = (HttpURLConnection) getUrl.openConnection();
            connection.connect();
            // 取得输入流，并使用Reader读取
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 设置编码
            while ((lines = reader.readLine()) != null) {
                result = result + lines;
            }
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            if (reader != null) {
                reader.close();
                reader = null;
            }
            connection.disconnect();
        }
    }

    /**
     * @param POST_URL url地址
     * @param content  key=value形式
     * @return 返回结果
     * @throws Exception
     */
    public static String sendPostData(String POST_URL, String content) throws Exception {
        HttpURLConnection connection = null;
        DataOutputStream out = null;
        BufferedReader reader = null;
        String line = "";
        String result = "";
        try {
            URL postUrl = new URL(POST_URL);
            connection = (HttpURLConnection) postUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            // Post 请求不能使用缓存
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.connect();

            out = new DataOutputStream(connection.getOutputStream());
            // content = URLEncoder.encode(content, "utf-8");
            // DataOutputStream.writeBytes将字符串中的16位的unicode字符�?8位的字符形式写道流里�?
            out.writeBytes(content);
            out.flush();
            out.close();
            // 获取结果
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 设置编码
            while ((line = reader.readLine()) != null) {
                result = result + line;
            }
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            if (out != null) {
                out.close();
                out = null;
            }
            if (reader != null) {
                reader.close();
                reader = null;
            }
            connection.disconnect();
        }
    }

    /*
     * 过滤掉html里不安全的标签，不允许用户输入这些标�?
     */
    public static String htmlFilter(String inputString) {
        // return inputString;
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        /*
         * Pattern p_script; java.util.regex.Matcher m_script;
         */

        try {
            String regEx_script = "<[\\s]*?(script|style)[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?(script|style)[\\s]*?>";
            String regEx_onevent = "on[^\\s]+=\\s*";
            String regEx_hrefjs = "href=javascript:";
            String regEx_iframe = "<[\\s]*?(iframe|frameset)[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?(iframe|frameset)"
                    + "[\\s]*?>";
            String regEx_link = "<[\\s]*?link[^>]*?/>";

            htmlStr = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE).matcher(htmlStr).replaceAll("");
            htmlStr = Pattern.compile(regEx_onevent, Pattern.CASE_INSENSITIVE).matcher(htmlStr).replaceAll("");
            htmlStr = Pattern.compile(regEx_hrefjs, Pattern.CASE_INSENSITIVE).matcher(htmlStr).replaceAll("");
            htmlStr = Pattern.compile(regEx_iframe, Pattern.CASE_INSENSITIVE).matcher(htmlStr).replaceAll("");
            htmlStr = Pattern.compile(regEx_link, Pattern.CASE_INSENSITIVE).matcher(htmlStr).replaceAll("");

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;
    }

    private static List<String> shuffle(String[] arr) {
        List<String> keyList = new ArrayList<>();
        if (arr.length > 1) {
            for (int i = 0; i < keys.length; i++) {
                keyList.add(keys[i]);
            }
            Collections.shuffle(keyList);
        }
        return keyList;
    }

    /**
     * 货车路径规划
     *
     * @param origins
     * @param destination
     * @return
     */
    public static Map<String, Double> truckDirection(String origins, String destination, String size,String danoneKey) {
        String urlString = "https://restapi.amap.com/v4/direction/truck";
        StringBuilder params = new StringBuilder();
        Map<String, Double> map = new HashMap<>();
        params.append("origin=").append(origins).append("&destination=").append(destination).append("&strategy=10")
                .append("&size=").append(size).append("&nosteps=1").append("&key=").append(danoneKey);
        try {
            String res = sendGet(urlString, params.toString());
            JSONObject jsonObject = JSONObject.parseObject(res);
            Integer status = (Integer) jsonObject.get("errcode");
            if (status == 0) {
                JSONObject data = (JSONObject) jsonObject.get("data");
                JSONObject route = (JSONObject) data.get("route");
                JSONArray paths = (JSONArray) route.get("paths");
                JSONObject result = (JSONObject) paths.get(0);
                Integer distance = (Integer) result.get("distance");
                Integer duration = (Integer) result.get("duration");
                map.put("distance", distance.doubleValue());
                map.put("time", duration.doubleValue());
            } else {
                log.info("请求失败rul:{}", params.toString());
                log.info("请求失败信息message:{}", res);
            }
        } catch (Exception e) {
            log.info("error in wapaction,and e is " + e.getMessage());
        }
        return map;
    }

    public static void main(String[] args) {
        Map<String, Double> map = truckDirection("107.921385,32.510652", "110.824882,39.135157", "2","");
        if (org.apache.commons.collections4.MapUtils.isNotEmpty(map)) {
            System.out.println("distacne:" + map.get("distance") / 1000);
            System.out.println("time:" + map.get("time") / 3600);
        } else {
        }
    }
}
