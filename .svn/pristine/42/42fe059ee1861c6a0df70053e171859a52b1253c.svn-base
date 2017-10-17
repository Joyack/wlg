import com.wlg.Model.User;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/6/14.
 */
public class getField {

    @Test
    public void getFields() throws NoSuchFieldException, IllegalAccessException {
        User user =  new User();
//        user.setId(1);
        user.setUsername("2");
        user.setPassword("3");
        user.setUname("4");
        System.out.println((getFieldValue(user)));

    }

    public Object getFieldValue(Object object) throws NoSuchFieldException, IllegalAccessException {
        Field[] field = object.getClass().getDeclaredFields();
        StringBuffer stringBuffer = new StringBuffer();
        Field fi = null;
        for(Field fd : field){
            System.out.println(fd.getName());
            fi = object.getClass().getDeclaredField(fd.getName());//获取属性
            fi.setAccessible(true);//设置当前对象对model私有属性的访问权限
            Object fieldValue = fi.get(object);
            if(fieldValue!=null){
                stringBuffer.append(fd.getName()+"='"+fieldValue+"'");
            }
        }
        //输出属性值
        return stringBuffer.toString();
    }
}
