package rough.preparation.utils;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 配置管理工具.
 * <p>
 * 尚需完善. 目前为从配置文件中取得信息缓存之.
 * 配置文件的取得顺序为先从 CLASS_PATH 中取得, 若没有, 则从文件中取.
 *
 * @author rough
 */
public class UtilsConfig {
    protected static final Log logger = LogFactory.getLog(UtilsConfig.class);

    private static final String[] CONFIG_NAMES = {"utils.properties"};
    private static final Properties config = new Properties();

    static {
        init();
    }

    private static Properties loadFromFile(String fileName) {
        Properties result = new Properties();
        try {
            result.load(new FileInputStream(fileName));
        } catch (Throwable e) {
            logger.error(e, e);
        }
        return result;
    }

    private static Properties loadFormClassPath(String name) {
        Properties result = new Properties();
        try {
            result.load(ClassLoader.getSystemResourceAsStream(name));
        } catch (Throwable e) {
            logger.error(e, e);
        }
        return result;
    }

    private static void init() {
        for (String name : UtilsConfig.CONFIG_NAMES) {
            Properties properties = loadFormClassPath(name);
            if (properties.keySet().isEmpty()) {
                properties = loadFromFile(name);
            }
            config.putAll(properties);
        }
    }

    /**
     * 取得 String 类型的配置信息
     *
     * @param key 配置 key
     * @return 配置值
     */
    public static String getString(String key) {
        return config.getProperty(key);
    }
}
