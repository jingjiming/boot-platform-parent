package com.css.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * JSON转换工具类 依赖Jackson
 * Created by jiming.jing on 2020/3/28.
 */
public class JSONUtils {

	private static Logger logger = LoggerFactory.getLogger(JSONUtils.class);

	// 加载速度太慢，放入静态代码块
    //private static final ObjectMapper objectMapper = new ObjectMapper();
    private static ObjectMapper objectMapper;

    private JSONUtils() {}

    static {
        objectMapper = new ObjectMapper();
        // 允许字符串中存在回车换行控制符
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }
    
    public static ObjectMapper getInstance() {  
        return objectMapper;  
    }
    /**
     * Object --> JSONString
     * JSONUtils.getInstance().writeValueAsString(Object obj)
     * @param obj
     * @return
     */
    public static String toJSONString(Object obj) {
        return obj != null ? toJSONString(obj, () -> "") : "";
    }

    public static String toJSONString(Object obj, Supplier<String> defaultSupplier) {
        try {
            // 标准输出
            //return obj != null ? objectMapper.writeValueAsString(obj) : defaultSupplier.get();
            // 格式化输出
            return obj != null ? objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj) : defaultSupplier.get();
        } catch (Throwable throwable) {
            logger.error(String.format("toJSONString %s", obj != null ? obj.toString() : "null"), throwable);
        }
        return defaultSupplier.get();
    }

    /**
     * json --> JavaObject
     * JSONUtils.getInstance().readValue(String value, Class clazz)
     * @param obj
     * @param clazz
     * @return
     */
    public static <T> T toJavaObject(Object obj, Class<T> clazz) {
        return obj != null ? toJavaObject(toJSONString(obj), clazz, () -> null) : null;
    }

    public static <T> T toJavaObject(String value, Class<T> clazz, Supplier<T> defaultSupplier) {
        try {
            if (StringUtils.isBlank(value)) {
                return defaultSupplier.get();
            }
            return objectMapper.readValue(value, clazz);
        } catch (Throwable e) {
            logger.error(String.format("toJavaObject exception: \n %s\n %s", value, clazz), e);
        }
        return defaultSupplier.get();
    }

    /**
     * json --> List<T>
     * JSONUtils.getInstance().readValue(String content, new TypeReference<List<T>>(){})方式
     * @param value
     * @param clazz
     * @return
     */
    public static <T> List<T> toJavaObjectList(String value, Class<T> clazz) {
        return StringUtils.isNotBlank(value) ? toJavaObjectList(value, clazz, () -> null) : null;
    }

    public static <T> List<T> toJavaObjectList(Object obj, Class<T> clazz) {
        return obj != null ? toJavaObjectList(toJSONString(obj), clazz, () -> null) : null;
    }

    public static <T> List<T> toJavaObjectList(String value, Class<T> clazz, Supplier<List<T>> defaultSupplier) {
        try {
            if (StringUtils.isBlank(value)) {
                return defaultSupplier.get();
            }
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
            return objectMapper.readValue(value, javaType);
        } catch (Throwable throwable) {
            logger.error(String.format("toJavaObjectList exception \n%s\n%s", value, clazz), throwable);
        }
        return defaultSupplier.get();
    }

    public static <T> List<T> toJavaObjectList(String value, TypeReference<List<T>> typeReference, Supplier<List<T>> defaultSupplier) {
        try {
            if (StringUtils.isBlank(value)) {
                return defaultSupplier.get();
            }
            return objectMapper.readValue(value, typeReference);
        } catch (Throwable throwable) {
            logger.error(String.format("toJavaObjectList exception \n%s\n%s", value, typeReference), throwable);
        }
        return defaultSupplier.get();
    }

    // 简单地直接用json复制或者转换(Cloneable)
    public static <T> T jsonCopy(Object obj, Class<T> clazz) {
        return obj != null ? toJavaObject(toJSONString(obj), clazz) : null;
    }

    public static Map<String, Object> toMap(String value) {
        return StringUtils.isNotBlank(value) ? toMap(value, () -> null) : null;
    }

    public static Map<String, Object> toMap(Object obj) {
        return obj != null ? toMap(obj, () -> null) : null;
    }

    public static Map<String, Object> toMap(Object obj, Supplier<Map<String, Object>> defaultSupplier) {
        if (obj == null) {
            return defaultSupplier.get();
        }
        try {
            if (obj instanceof Map) {
                return (Map<String, Object>) obj;
            }
        } catch (Exception e) {
            logger.info("fail to convert" + toJSONString(obj), e);
        }
        return toMap(toJSONString(obj), defaultSupplier);
    }

    public static Map<String, Object> toMap(String value, Supplier<Map<String, Object>> defaultSupplier) {
        if (StringUtils.isBlank(value)) {
            return defaultSupplier.get();
        }
        try {
            return toJavaObject(value, LinkedHashMap.class);
        } catch (Exception e) {
            logger.error(String.format("toMap exception\n%s", value), e);
        }
        return defaultSupplier.get();
    }


    public static List<Object> toList(String value) {
        return StringUtils.isNotBlank(value) ? toList(value, () -> null) : null;
    }

    public static List<Object> toList(Object value) {
        return value != null ? toList(value, () -> null) : null;
    }

    public static List<Object> toList(String value, Supplier<List<Object>> defaultSuppler) {
        if (StringUtils.isBlank(value)) {
            return defaultSuppler.get();
        }
        try {
            return toJavaObject(value, List.class);
        } catch (Exception e) {
            logger.error("toList exception\n" + value, e);
        }
        return defaultSuppler.get();
    }

    public static List<Object> toList(Object value, Supplier<List<Object>> defaultSuppler) {
        if (value == null) {
            return defaultSuppler.get();
        }
        if (value instanceof List) {
            return (List<Object>) value;
        }
        return toList(toJSONString(value), defaultSuppler);
    }

}
