/*
 * Created on 2019年9月15日
 */
package rough.preparation.batchjob;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractBatchJobExecutor implements BatchJobExecutor {

    protected static final Log logger = LogFactory.getLog(AbstractBatchJobExecutor.class);

    /**
     * 将存储成字符串形式参数转换成形式，字符串参数的格式为：PARAM1_NAME=PARAM1_VALUE;PARAM2_NAME=PARAM2_VALUE
     * @param batchParams 以字符串形式保存的参数
     * @return 以Map形式保存的参数
     */
    protected static Map<String, String> transferParams(String batchParams) {
        Map<String, String> result = new HashMap<>();

        try {
            String[] params = StringUtils.split(batchParams, ";");
            for (String param : params) {
                String[] paramInfo = StringUtils.split(param, "=", 2);
                if (paramInfo.length == 2)
                    result.put(paramInfo[0], paramInfo[1]);
                else if (paramInfo.length == 1)
                    result.put(paramInfo[0], null);
            }
        } catch (Exception e) {
            logger.warn("Can not transfer batchParams: " + batchParams);
            logger.error(e, e);
        }

        return result;
    }

}
