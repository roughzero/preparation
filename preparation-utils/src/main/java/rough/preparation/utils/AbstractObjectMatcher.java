package rough.preparation.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class AbstractObjectMatcher<QueryObject, Result> implements ObjectMatcher<QueryObject, Result> {
    protected static final Log logger = LogFactory.getLog(AbstractObjectMatcher.class);

    protected final Map<Object, Object> cache = new HashMap<>();

    /** 子匹配器，若为空则直接缓存结果，否则使用子匹配器的实例缓存结果 */
    final Class<AbstractObjectMatcher<QueryObject, Result>> childMatcherPrototype;

    /**
     * 根据返回结果来决定进行匹配的字段值，缓存时使用
     * @param result 返回结果
     * @return 进行匹配的字段值
     */
    protected abstract Object getKeyByResult(Result result);

    /**
     * 根据用来进行匹配的类来决定进行匹配的字段值，匹配时使用
     * @param queryObject 用来进行匹配的类
     * @return 进行匹配的字段值
     */
    protected abstract Object getKeyBuQuery(QueryObject queryObject);

    /**
     * 将返回结果缓存到缓存中
     * @param key 进行匹配的字段值
     * @param result 返回结果
     */
    protected void putInCache(Object key, Result result) {
        cache.put(key, result);
    }

    /**
     * 从缓存中取得返回结果
     * @param key 进行匹配的字段值
     * @return 返回结果
     */
    protected Result getFromCache(Object key) {
        return (Result) cache.get(key);
    }

    /**
     * Constrctor.
     * @param childMatcherPrototype 子匹配器原型
     */
    public AbstractObjectMatcher(Class childMatcherPrototype) {
        this.childMatcherPrototype = childMatcherPrototype;
    }

    /**
     * 匹配需要取得的结果类的默认实现，采用递归子匹配器的方式实现.
     * @param result 放入缓存的匹配结果
     */
    @Override
    public void put(Result result) {
        Object key = getKeyByResult(result);

        if (childMatcherPrototype == null) {
            putInCache(key, result);
        } else {
            ObjectMatcher<QueryObject, Result> childMather;
            if (cache.containsKey(key)) {
                childMather = (ObjectMatcher<QueryObject, Result>) cache.get(key);
                childMather.put(result);
            } else {
                try {
                    childMather = childMatcherPrototype.newInstance();
                } catch (Exception e) {
                    logger.error(e, e);
                    throw new RuntimeException("Can not create instance of child matcher: " + childMatcherPrototype.getName());
                }
                childMather.put(result);
                cache.put(key, childMather);
            }
        }
    }

    /**
     * Super.get 的默认实现，采用递归子匹配器的方式实现
     * @param queryObject 用来进行匹配的类示例
     * @return 匹配得到的结果，若无法匹配得到，则返回空
     */
    @Override
    public Result get(QueryObject queryObject) {
        Object keyOfCache = getKeyBuQuery(queryObject);
        if (childMatcherPrototype == null) {
            return getFromCache(keyOfCache);
        } else {
            ObjectMatcher<QueryObject, Result> childMather;
            if (cache.containsKey(keyOfCache)) {
                childMather = (ObjectMatcher<QueryObject, Result>) cache.get(keyOfCache);
                return childMather.get(queryObject);
            } else {
                return null;
            }
        }
    }

}
