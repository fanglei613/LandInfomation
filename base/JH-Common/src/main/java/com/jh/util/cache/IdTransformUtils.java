package com.jh.util.cache;

import com.jh.entity.BaseEntity;
import com.jh.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description:统计占比解析类：
 * 1.遍历所有使用了@IdToName注解的属性
 * 4.查找到注解上配置的ID属性，反射赋值
 *
 * @version <1> 2018-10-16 14:54 lijie: Created.
 */

public class IdTransformUtils {

    private static Logger logger = LoggerFactory.getLogger(IdTransformUtils.class);

    public static void idTransForObj(Object cobj){
        try {
            Class clazz = cobj.getClass();
            //获取所有属性值
            Field[] fields = getAllFields(clazz);
            for (Field field : fields) {
                //找出有需要转化数据的值
                IdTransform r = field.getAnnotation(IdTransform.class);
                if (r != null) {
                    //获取缓存类型
                    String cacheType = (String)r.type();
                    //获取转化类型
                    String transType=(String)r.transType();
                    //获取id属性值
                    Field field_prop = getFieldByName(clazz,r.propName());
                    field_prop.setAccessible(true);
                    Object obj=field_prop.get(cobj);
                    if(obj ==null){
                        //logger.info("id值为空：{}");
                        continue;
                    }
                    String idStr =field_prop.get(cobj).toString();
                    if(StringUtils.isBlank(idStr)){
                        //logger.info("id值为空：{}",idStr);
                        continue;
                    }
                    //根据ID转化
                    String cachePrefix=cacheType+transType;
                    String name=RedisUtil.get(cachePrefix+idStr);
                    //如果name为空，取值ID本身
                    name=StringUtils.isNotBlank(name)?name:idStr;
                    //给当前属性赋值
                    field.setAccessible(true);
                    field.set(cobj, name);
                }
            }

        }catch(Exception e){
            logger.error("通过反射根据ID设置名称或编码失败："+e.getMessage());
        }
    }

    public static void idTransForList(List list){
        for(Object obj:list){
            idTransForObj(obj);
        }
    }

    public static void idTransForBean(Object obj){
        idTransForObj(obj);
    }

    public static void main(String [] args)throws Exception{
        BaseEntity p=new BaseEntity();
        p.setDelFlag("1702");
        idTransForObj(p);
        System.out.println(p.getCreatorName());
        List<BaseEntity> list=new ArrayList<BaseEntity>();
        list.add(p);
        idTransForList(list);
        System.out.println(p.getCreatorName());
    }
    /**
     * 获取所有属性值，包括私有属性和继承属性
     * @param clazz
     * @return
     */
    public static Field[] getAllFields(Class clazz){
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null){
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    /**
     * 根据属性名称获取属性对象，包括私有属性和继承属性
     * @param clazz
     * @return
     */
    public static Field getFieldByName(Class clazz,String fieldName){
        if(StringUtils.isBlank(fieldName)){
            return null;
        }
        Field[] fields=getAllFields(clazz);
        for(Field f:fields){
            if(fieldName.equals(f.getName())){
                return f;
            }
        }
        return null;
    }

}


