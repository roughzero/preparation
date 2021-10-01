package rough.preparation.utils.beanutils;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@CommonsLog
public class BeanUtils {
    public static Object getProperty(Object bean, String name) {
        if (bean == null || StringUtils.isEmpty(name)) {
            return null;
        }
        String methodName = "get" + StringUtils.capitalize(name);
        try {
            Method getMethod = bean.getClass().getMethod(methodName);
            return getMethod.invoke(bean);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.warn(e, e);
            return null;
        }
    }
}
