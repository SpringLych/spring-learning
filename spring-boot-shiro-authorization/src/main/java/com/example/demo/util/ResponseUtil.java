package com.example.demo.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LiYingChun
 * @date 2019/8/1
 */
public class ResponseUtil extends HashMap<String, Object> {
    /**
     * Constructs an empty <tt>HashMap</tt> with the default initial capacity
     * (16) and the default load factor (0.75).
     */
    public ResponseUtil() {
        put("code", 0);
        put("msg", "操作成功");
    }

    public static ResponseUtil error() {
        return error(1, "操作失败");
    }

    public static ResponseUtil error(String msg) {
        return error(500, msg);
    }

    public static ResponseUtil error(int code, String msg) {
        ResponseUtil response = new ResponseUtil();
        response.put("code", code);
        response.put("msg", msg);
        return response;
    }

    public static ResponseUtil ok(String msg) {
        ResponseUtil response = new ResponseUtil();
        response.put("msg", msg);
        return response;
    }

    public static ResponseUtil ok(Map<String, Object> map) {
        ResponseUtil response = new ResponseUtil();
        response.putAll(map);
        return response;
    }

    public static ResponseUtil ok() {
        return new ResponseUtil();
    }

    @Override
    public ResponseUtil put(String key, Object value) {
        super.put(key, value);
        return this;
    }


}
