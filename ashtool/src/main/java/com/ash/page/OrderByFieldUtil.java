package com.ash.page;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jianshengfei
 * @version 1.0.0
 * @Description 自定义工具类
 * @ClassName OrderByFieldUtil.java
 * @createTime 2021年04月13日 13:02
 */
public class OrderByFieldUtil {

    /**
     * 私有化构造
     */
    private OrderByFieldUtil() {}

    public static Map<String, String> getOrderByFieldsMap(Class instance) {
        return getOrderByFieldsMap(instance, null, null);
    }

    public static Map<String, String> getOrderByFieldsMap(Class instance, List<String> ignoreFields) {
        return getOrderByFieldsMap(instance, ignoreFields, null);
    }

    public static Map<String, String> getOrderByFieldsMap(Class instance, Boolean isOpenHump) {
        return getOrderByFieldsMap(instance, null, isOpenHump);
    }

    /**
     * 动态获取对应实体对象 @TableField 注解的字段，用于排序获取对应数据库
     * <p><font color = #e60039>适用此方法时，请注意返回的实体对象 和 instance 一致, 避免前端传错</font></p>
     *
     * <p><font color = #e60039>使用到反射，请勿滥用</font></p>
     *
     * @param instance 需要获取字段的实体对象
     * @param ignoreFields 需要过滤的字段
     * @param isOpenHump 是否开启驼峰转下划线模式
     * @return
     * @throws NoSuchFieldException
     */
    public static Map<String, String> getOrderByFieldsMap(Class instance, List<String> ignoreFields, Boolean isOpenHump) {
        Field[] fields = instance.getDeclaredFields();
        if(fields.length == 0) {
            throw new NullPointerException("Your bean field is empty !");
        }

        Map<String, String> map = new HashMap(7);
        for (int i = 0; i < fields.length; i++) {
            if(CollectionUtil.isEmpty(ignoreFields)) {
                putOrIgnoreByTableField(map, fields[i], isOpenHump);
            }else{
                // 除过fieldMap中的属性，其他属性都获取
                if(!ignoreFields.contains(fields[i].getName())) {
                    putOrIgnoreByTableField(map, fields[i], isOpenHump);
                }
            }
        }
        return map;
    }

    /**
     * 根据 @TableField put or ignore
     * !优化 @TableField 注解可尝试传入自定义的
     * @param map
     * @param field
     * @param isOpenHump 是否开启驼峰转下划线模式
     */
    private static void putOrIgnoreByTableField(Map<String, String> map, Field field, Boolean isOpenHump){
        String name = null;
        boolean annotationPresent = field.isAnnotationPresent(TableField.class);
        if (annotationPresent) {
            // 获取注解值
            name = field.getAnnotation(TableField.class).value();
        } else if (isOpenHump!= null && isOpenHump) {
            name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
        }
        if(StringUtils.isNotEmpty(name)) {
            map.put(field.getName(), name);
        }
    }




}
