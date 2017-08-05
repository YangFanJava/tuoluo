package io.renren.common.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By YangF
 * Date: 2017/7/12
 * Time: 18:40
 */
public class FastJosnUtils {

    /**
     * 对象转化成json字符串
     * @param object
     * @return
     */
    public static String objectToJson(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     *  将json结果集转化为对象
     * @param jsonData
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> clazz) {
        return JSON.parseObject(jsonData, clazz);
    }

    /**
     * json字符串转LIST
     * @param jsonData
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> clazz){
        return JSON.parseArray(jsonData, clazz);
    }
}