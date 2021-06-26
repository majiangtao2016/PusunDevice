package com.health.pusun.device.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 集合工具类
 */
public class CollectionUtil {

    public static String divide100(Long data) {
        BigDecimal a = new BigDecimal(data);
        BigDecimal b = new BigDecimal(100);
        return a.divide(b, 2, RoundingMode.HALF_UP).toString();
    }

    public static float divide100byFloat(Long data) {
        BigDecimal a = new BigDecimal(data);
        BigDecimal b = new BigDecimal(100);
        return a.divide(b, 2, RoundingMode.HALF_UP).floatValue();
    }


    public static DecimalFormat df  = new DecimalFormat("###0.00");

    /**
     * 获取集合的值，以','隔开
     *
     * @param collection 集合
     * @return
     */
    public static String getCollectionValue(Collection collection) {
        if (collection == null || collection.isEmpty()) {
            return "";
        }
        StringBuilder buffer = new StringBuilder(collection.size() * 16);

        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next != collection) {
                buffer.append(next);
            }
            if (it.hasNext()) {
                buffer.append(",");
            }
        }
        return buffer.toString();
    }


    public static String getCollectionValue2(HashMap map) {

        if (map == null || map.isEmpty()) {
            return "";
        }
        StringBuilder buffer = new StringBuilder();

        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            buffer.append(entry.getKey() + "=" + entry.getValue());
            if (iterator.hasNext()) {
                buffer.append("&");
            }
        }

        return buffer.toString();
    }
    /**
     * 去除重复的数据
     *
     * @param list
     * @return
     */
    public  static <T> ArrayList<T> removeDuplicate(List<T> list) {
        return new ArrayList<>(new HashSet<>(list));
    }

}
