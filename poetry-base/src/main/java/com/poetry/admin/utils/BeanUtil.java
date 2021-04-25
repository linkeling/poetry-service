package com.poetry.admin.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jinheng.li
 * @date 2018-10-11 16:18
 * @Description：对象的基础处理类
 */
public class BeanUtil {

    /**
     * 将对象装换为map
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map,T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    /**
     * 将List<T>转换为List<Map<String, Object>>
     * @param objList
     * @return
     */
    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = Lists.newArrayList();
        if (objList != null && objList.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0,size = objList.size(); i < size; i++) {
                bean = objList.get(i);
                map = beanToMap(bean);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     * @param maps
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps,Class<T> clazz) throws InstantiationException, IllegalAccessException {
        List<T> list = Lists.newArrayList();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0,size = maps.size(); i < size; i++) {
                map = maps.get(i);
                bean = clazz.newInstance();
                mapToBean(map, bean);
                list.add(bean);
            }
        }
        return list;
    }

    /**
     * 拷贝对象属性到目标对象
     * <p>
     * <b>示例</b>
     * BeanSource bs = new BeanSource();
     * bs.set(...);
     * ...
     * BeanTarget bt = BeanUtil.copyProperties(bs,BeanTarget.class);
     *
     * </p>
     *
     * @param sourceObj 源对象
     * @param targetClazz 目标类
     * @return 目标对象
     */
    public static <T> T copyProperties(Object sourceObj, Class<T> targetClazz) {
        return BeanUtil.copyProperties(sourceObj, targetClazz, new String[]{});
    }

    public static <T> T copyProperties(Object sourceObj, Class<T> targetClazz, String... ignoreProperties) {
        if (sourceObj == null){
            return null;
        }

        T targetObj = null;
        try {
            targetObj = (T) targetClazz.newInstance();
            BeanUtils.copyProperties(sourceObj, targetObj, ignoreProperties);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        } catch (InstantiationException e) {
            throw new RuntimeException(e.getMessage());
        }
        return targetObj;
    }

    /**
     * 拷贝源列表对象到目标列表
     *
     * @param sourceList 源列表
     * @param targetClazz 目标列表元类型
     * @return 目标列表
     */
    public static <T> List<T> copyBeans(List<?> sourceList, Class<T> targetClazz){
        return BeanUtil.copyBeans(sourceList, targetClazz, new String[]{});
    }

    public static <T> List<T> copyBeans(List<?> sourceList, Class<T> targetClazz, String... ignoreProperties){
        if (sourceList == null) {
            return null;
        }
        List<T> targetList = new ArrayList<T>();
        for (Object sourceObj : sourceList) {
            T targetObj = BeanUtil.copyProperties(sourceObj, targetClazz, ignoreProperties);
            targetList.add(targetObj);
        }
        return targetList;
    }
}
