package com.wlg.Dao;

import com.wlg.Model.annotation.Key;
import com.wlg.Model.annotation.SqlTable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.Column;
import javax.swing.text.html.parser.Entity;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 利用java反射处理@注解声明的工具类
 * 目前处理的标签有：@SqlTable,@SqlColumn,@Key
 * Created by chenqi on 2016/4/20.
 */
public class ReflectUtils {
    private static final Log log = LogFactory.getLog(ReflectUtils.class);

    private static final String IS = "is";
    private static final String GET = "get";
    private static final String SET = "set";
    private static final String SEPARATOR = ",";

    /**
     * 获取指定class对象在数据库对应的数据库表名称，
     * 未定义@SqlTable标签 默认返回类名
     * @param entityClass
     * @return
     */
    public static String getTableNameByClass(Class entityClass) throws SystemException{
        String tablename = null;
        //通过isAnnotationPresent判断是否存在注解
        if(entityClass.isAnnotationPresent(SqlTable.class)){
            //获取类的注解
            SqlTable sqlTable = (SqlTable) entityClass.getAnnotation(SqlTable.class);
            //获取注解参数值
            if(StringUtil.isEmptyStr(sqlTable.name())) {
                tablename =entityClass.getSimpleName();
            }else{
                tablename = sqlTable.name();
            }
        }else{
            //试图获取一个未经@SqlTable声明的对象所对应的数据库表名，唯有@SqlTable声明的对象才能获取数据库对应表名
            throw new SystemException(SystemException.ISNOT_ANOTATION_SQLTABLE);
        }
        return tablename;
    }

    /**
     * 根据class以及它的属性名查询数据库表对应字段名
     * @param entityClass
     * @param fieldName
     * @return
     * @throws SystemException
     * @throws NoSuchFieldException
     */
    public static String getSqlColumnNameByFieldName(Class entityClass, String fieldName) throws SystemException, NoSuchFieldException {
        if(StringUtil.isEmptyStr(fieldName)) {
            return null;
        }
        Field field = entityClass.getDeclaredField(fieldName);
        if (field.isAnnotationPresent(Column.class)) {
            Column sqlColumn = field.getAnnotation(Column.class);
            String columnName = sqlColumn.name();
            if (StringUtil.isEmptyStr(columnName)) {
                columnName = field.getName();
            }
            return columnName;
        }else{
            throw new SystemException(SystemException.ISNOT_ANOTATION_SQLCOLUMN);
        }
    }

    /**
     * 通过反射获取对象指定属性字段的值
     * @param obj 需要获取属性值的对象
     * @param fieldName 需要获取的属性名
     * @return
     * @throws NoSuchFieldException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object getFieldValueByFieldName(Object obj, String fieldName) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(StringUtil.isEmptyStr(fieldName) || obj == null){
            return null;
        }
        Field field = obj.getClass().getDeclaredField(fieldName);
        //获取属性的get或is方法获取对象属性值
        StringBuffer sb = new StringBuffer(15);
        if(boolean.class == field.getType()){
            sb.append(IS);
        }else{
            sb.append(GET);
        }
        sb.append(String.valueOf(fieldName.charAt(0)).toUpperCase()).append(fieldName.substring(1));
        Method method = obj.getClass().getDeclaredMethod(sb.toString());
        return method.invoke(obj);
    }

    /**
     *利用反射将指定对象的属性字段值修改为指定值
     * @param obj 需要设置属性值的对象
     * @param  methodName2MethodMap entity类的所有方法名与方法的映射
     * @param fieldName 需要设置的属性名
     * @param fieldValue 需要设置的属性值
     * @throws NoSuchFieldException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void setFieldValueByFieldName(Object obj, Map<String,Method> methodName2MethodMap, String fieldName, Object fieldValue) throws InvocationTargetException, IllegalAccessException {
        //获取属性的get或is方法获取对象属性值
        StringBuffer sb = new StringBuffer(15);
        sb.append(SET).append(String.valueOf(fieldName.charAt(0)).toUpperCase()).append(fieldName.substring(1));
//        log.debug(sb.toString() + " value="+fieldValue);
        methodName2MethodMap.get(sb.toString()).invoke(obj,fieldValue);
    }



    /**
     * 加载class对应对象中所有用@SqlColumn标记的属性与对应数据库字段的映射关系
     * 只有用@SqlColumn标记的属性才返回
     * @param entityClass
     * @return
     */
    public static Map<String,String> getAllClassField2SqlColumnFieldMap(Class entityClass){
        Map<String,String> map = null;
        //获取全部字段
        Field[] fields = entityClass.getDeclaredFields();
        if(fields != null && fields.length > 0){
            map = new HashMap<String, String>();
            for(Field field:fields) {
                //获取注解
                if (field.isAnnotationPresent(Column.class)) {
                    Column sqlColumn = field.getAnnotation(Column.class);
                    String columnName = sqlColumn.name();
                    if (StringUtil.isEmptyStr(columnName)) {
                        columnName = field.getName();
                    }
                    map.put(field.getName(),columnName);
                }
            }
        }
        return map;
    }

    /**
     * 加载对象所有用@SqlColumn标注的属性与值的映射关系
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String,Object> getField2ValueMap(Object obj) throws SystemException {
        if(obj == null){
            return null;
        }
        Map<String,Object> map = null;
        Class c = obj.getClass();
        //获取全部字段
        Field[] fields = c.getDeclaredFields();
        if(fields != null && fields.length > 0) {
            map = new HashMap<String, Object>();
            try {
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Column.class)) {
                        //获取属性的get或is方法获取对象属性值
                        StringBuffer sb = new StringBuffer(15);
                        if(boolean.class == field.getType()){
                            sb.append(IS);
                        }else{
                            sb.append(GET);
                        }
                        sb.append(String.valueOf(field.getName().charAt(0)).toUpperCase()).append(field.getName().substring(1));
                        Method method = c.getDeclaredMethod(sb.toString());
                        log.debug(method.getName());
                        map.put(field.getName(), method.invoke(obj));
                    }
                }
            }catch(Exception e){
                log.error(e.getMessage(),e);
                throw new SystemException(SystemException.REFLECT_GET_FIELDVALUE_ERROR);
            }
        }
        return map;
    }

    /**
     * 加载对象所有值不为null的属性与值的映射关系
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String,Object> getAllNoNullValueField2ValueMap(Object obj) throws SystemException {
        if(obj == null){
            return null;
        }
//        log.debug("getAllNoNullValueField2ValueMap  obj.getClass()======"+obj.getClass());
        Map<String,Object> map = null;
        Class c = obj.getClass();
        //获取全部字段
        Field[] fields = c.getDeclaredFields();
        if(fields != null && fields.length > 0) {
            map = new HashMap<String, Object>();
            try {
                for (Field field : fields) {
                    //获取属性的get或is方法获取对象属性值
                    StringBuffer sb = new StringBuffer();
                    if(boolean.class == field.getType()){
                        sb.append(IS);
                    }else{
                        sb.append(GET);
                    }
                    sb.append(String.valueOf(field.getName().charAt(0)).toUpperCase()).append(field.getName().substring(1));
                    Method method = c.getDeclaredMethod(sb.toString());
                    log.debug(method.getName());
                    Object value = method.invoke(obj);
                    if(value != null) {
                        map.put(field.getName(), value);
                    }
                }
            }catch(Exception e){
                log.error(e.getMessage(),e);
                throw new SystemException(SystemException.REFLECT_GET_FIELDVALUE_ERROR);
            }
        }
        return map;
    }

    /**
     * 加载对象所有用@SqlColumn标注且值不为null的属性与值的映射关系
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String,Object> getNoNullValueField2ValueMap(Object obj) throws SystemException {
        if(obj == null){
            return null;
        }
        Map<String,Object> map = null;
        Class c = obj.getClass();
        //获取全部字段
        Field[] fields = c.getDeclaredFields();
        if(fields != null && fields.length > 0) {
            map = new HashMap<String, Object>();
            try {
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Column.class)) {
                        //获取属性的get或is方法获取对象属性值
                        StringBuffer sb = new StringBuffer();
                        if(boolean.class == field.getType()){
                            sb.append(IS);
                        }else{
                            sb.append(GET);
                        }
                        sb.append(String.valueOf(field.getName().charAt(0)).toUpperCase()).append(field.getName().substring(1));
                        Method method = c.getDeclaredMethod(sb.toString());
                        log.debug(method.getName());
                        Object value = method.invoke(obj);
                        if(value != null) {
                            map.put(field.getName(), value);
                        }
                    }
                }
            }catch(Exception e){
                log.error(e.getMessage(),e);
                throw new SystemException(SystemException.REFLECT_GET_FIELDVALUE_ERROR);
            }
        }
        return map;
    }

    /**
     * 查询entity对应表的的所有字段
     * @param entityClass
     * @param alias 设定表别名
     * @return 如：alias.ID,alias.NAME
     */
    public static String getAllColumnsByEntityClass(Class entityClass, String alias){
        boolean flag = StringUtil.isEmptyStr(alias);
        StringBuffer sb = new StringBuffer();
        //获取全部字段
        Field[] fields = entityClass.getDeclaredFields();
        if(fields != null && fields.length > 0){
            for(Field field:fields) {
                //获取注解
                if (field.isAnnotationPresent(Column.class)) {
                    Column sqlColumn = field.getAnnotation(Column.class);
                    String columnName = sqlColumn.name();
                    if (StringUtil.isEmptyStr(columnName)) {
                        columnName = field.getName();
                    }
                    if (flag){
                        sb.append(columnName)
                                .append(",");
                    }else{
                        sb.append(alias)
                                .append(".")
                                .append(columnName)
                                .append(",");
                    }
                }
            }
            sb.deleteCharAt(sb.toString().length() -1)
                    .append(" ");

        }
        return sb.toString();
    }
    /**
     * 返回查询entity对应表的查询全表sql语句
     * @param entityClass
     * @return 如： alias.ID,alias.NAME
     */
    public static String getAllColumnsByEntityClass(Class entityClass){
        return getAllColumnsByEntityClass(entityClass,null);
    }


    /**
     * 加载class每一属性字段与java类型的映射
     * @param entityClass
     * @return
     */
    public static Map<String,Class> getAllClassFile2JavaTypeMap(Class entityClass){
        Map<String,Class> map = null;
        //获取全部字段
        Field[] fields = entityClass.getDeclaredFields();
        if(fields != null && fields.length > 0){
            map = new HashMap<String, Class>();
            for(Field field:fields) {
                //获取注解
                map.put(field.getName(),field.getType());
            }
        }
        return map;
    }

    /**
     * 查询一个class标记为主键的属性与数据库表主键的映射
     * class对应的类未标记@Key或fields属性为空，则抛出SystemException
     * @param entityClass
     * @return
     * @throws SystemException
     */
    public static Map<String,String> getKeyField2KeyColumnMapFromEntity(Class entityClass) throws SystemException{
        if(entityClass.isAnnotationPresent(Key.class)){
            //获取类的注解
            Key key = (Key)entityClass.getAnnotation(Key.class);
            if(StringUtil.isEmptyStr(key.fields())){
                //严重异常，@Key fields值为空
                throw new SystemException(SystemException.ISNOT_ANOTATION_KEY);
            }
            //获取注解参数值
            String[] keyFields = key.fields().split(SEPARATOR);
            Map<String,String> keyField2ColumnMap= new HashMap<String,String>();
            try {
                for(String keyField : keyFields){
                    String keyColumnField = getSqlColumnNameByFieldName(entityClass,keyField);
                    if(StringUtil.isEmptyStr(keyColumnField)){
                        StringBuffer sb = new StringBuffer();
                        sb.append("异常实体类：")
                                .append(entityClass.getName())
                                .append(",异常属性：")
                                .append(keyField)
                               .append(",定义的作为key的该属性不存在对应的数据库表字段，请检查属性名称是否正确");
                        throw new SystemException(sb.toString());
                    }
                    keyField2ColumnMap.put(keyField,keyColumnField);
                }
            }catch(NoSuchFieldException e){
                throw new SystemException(SystemException.FIELD_NODEF_IN_CLASS);
            }
            return keyField2ColumnMap;
        }
        //严重异常，未定义主键，即class未标记@Key
        throw new SystemException(SystemException.ISNOT_ANOTATION_KEY);
    }

    /**
     * 查询一个class标记为主键的属性集合
     * class对应的类未标记@Key或fields属性为空，则抛出SystemException
     * @param entityClass
     * @return
     * @throws SystemException
     */
    public static Set<String> getKeyFieldsFromEntity(Class entityClass) throws SystemException{
        if(entityClass.isAnnotationPresent(Key.class)){
            //获取类的注解
            Key key = (Key)entityClass.getAnnotation(Key.class);
            if(StringUtil.isEmptyStr(key.fields())){
                //严重异常，@Key fields值为空
                throw new SystemException(SystemException.ISNOT_ANOTATION_KEY);
            }
            //获取注解参数值
            String[] keyFields = key.fields().split(SEPARATOR);
            Set<String> keyFieldSet= new HashSet<String>();
            try {
                for(String keyField : keyFields){
                    String keyColumnField = getSqlColumnNameByFieldName(entityClass,keyField);
                    if(StringUtil.isEmptyStr(keyColumnField)){
                        StringBuffer sb = new StringBuffer();
                        sb.append("异常实体类：")
                                .append(entityClass.getName())
                                .append(",异常属性：")
                                .append(keyField)
                                .append(",定义的作为key的该属性不存在对应的数据库表字段，请检查属性名称是否正确");
                        throw new SystemException(sb.toString());
                    }
                    keyFieldSet.add(keyField);
                }
            }catch(NoSuchFieldException e){
                throw new SystemException(SystemException.FIELD_NODEF_IN_CLASS);
            }
            return keyFieldSet;
        }
        //严重异常，未定义主键，即class未标记@Key
        throw new SystemException(SystemException.ISNOT_ANOTATION_KEY);
    }

    /**
     * 加载entity类的所有方法名与方法的映射
     * @param entityClass
     * @return
     */
    public static Map<String,Method> getAllMethodName2MethodMap(Class entityClass){
        Map<String,Method> map= null;
        Method[] methods = entityClass.getDeclaredMethods();
        if(methods != null && methods.length > 0) {
            map = new HashMap<String,Method>();
            for (Method method : entityClass.getDeclaredMethods()) {
                map.put(method.getName(),method);
            }
        }
        return map;
    }

    /**
     * 将entity对象转化为类名:非null属性=属性值...的字符串格式
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> String changeEntityToKVString(T entity){
        if(entity == null){
            return "";
        }
        Map<String,Object> field2ValueMap = ReflectUtils.getAllNoNullValueField2ValueMap(entity);
        StringBuffer sb = new StringBuffer();
        sb.append(entity.getClass().getSimpleName())
                .append(":");
        //如果主键的值都存在，只返回主键的键值即可，若主键有null值存在，则还要取其他非null属性返回
        if(field2ValueMap != null && field2ValueMap.size() > 0) {
            for (Map.Entry<String, Object> entry : field2ValueMap.entrySet()) {
                sb.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append(",");
            }
            return sb.toString().substring(0,sb.toString().length() -1);
        }else{
            sb.append(String.valueOf(entity));
            return sb.toString();
        }
    }


//    /**
//     * 测试
//     * @param args
//     */
//    public static void main(String[] args) {
//
//        Object user = new User();
//        ((User)user).setName("chenqi");
//        try {
////            System.out.print(getAllClassField2FieldValueMap(user));
////            System.out.println(getAllClassField2TableFieldMap(User.class));
////            System.out.println(getAllClassFile2JavaTypeMap(User.class));
//
////            Field field = user.getClass().getField("name");
////            Method method = user.getClass().getDeclaredMethod("getName");
////            System.out.println(method.getName());
////            System.out.println( method.invoke(user));
//
//            int i = 0;
//            System.out.println(changeEntityToKVString(new Location()));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
