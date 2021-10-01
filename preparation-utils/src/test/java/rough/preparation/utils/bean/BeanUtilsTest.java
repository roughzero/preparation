package rough.preparation.utils.bean;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;

@CommonsLog
public class BeanUtilsTest {
    @Test
    public void testBeanUtils() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        SampleBean bean = new SampleBean();
        Object name = rough.preparation.utils.beanutils.BeanUtils.getProperty(bean, "name");
        log.info(name == null);
        log.info(name instanceof String);
        log.info(BeanUtils.getProperty(bean, "name"));
        BeanUtils.copyProperty(bean, "name", "test");
        bean.setBirthday(new Date());
        log.info(bean);
        log.info(BeanUtils.getProperty(bean, "birthday"));
        Object birthday = rough.preparation.utils.beanutils.BeanUtils.getProperty(bean, "birthday");
        log.info(birthday);
        log.info(birthday.getClass().getName());
        BigDecimal salary = new BigDecimal(12);
        BeanUtils.setProperty(bean, "salary", salary);
        log.info(bean.getSalary());
        log.info(birthday == bean.getBirthday());
        log.info(salary == bean.getSalary());
        salary = salary.add(new BigDecimal(1));
        log.info(salary == bean.getSalary());
        name = rough.preparation.utils.beanutils.BeanUtils.getProperty(bean, "name");
        log.info(name == bean.getName());
    }
}
