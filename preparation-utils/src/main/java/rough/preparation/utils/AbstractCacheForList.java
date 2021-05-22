package rough.preparation.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RateListCache 默认实现基类，使用递归子匹配器的方式进行实现
 */
@SuppressWarnings({"unchecked", "unused"})
public abstract class AbstractCacheForList<T>
        implements CacheForList<T> {

    /**
     * 本级缓存
     */
    protected final Map<Object, Object> cache = new HashMap<>();

    /**
     * 使用使用子缓存
     */
    protected abstract boolean isUsingChildCache();

    /**
     * 新的子匹配器实例
     */
    protected abstract CacheForList<T> childCache();

    /**
     * 根据业务对象来决定进行匹配的字段值，匹配时使用
     *
     * @param value 用来缓存与匹配的类
     * @return 进行匹配的字段值
     */
    protected abstract Object getKeyByBusiObject(T value);

    /**
     * 将需缓存的费率列表按匹配字段转换为 Map 形式
     *
     * @param valueObjs 需要缓存的费率列表
     * @return 按匹配字段分类的 Map 形式
     */
    protected Map<Object, List<T>> getCacheOfRates(List<T> valueObjs) {
        Map<Object, List<T>> tmpCache = new HashMap<>();
        valueObjs.forEach(t -> {
            Object key = getKeyByBusiObject(t);
            List<T> list = tmpCache.computeIfAbsent(key, k -> new ArrayList<>());
            list.add(t);
        });
        return tmpCache;
    }

    @Override
    public void put(List<T> valueObjs) {
        Map<Object, List<T>> tmpCache = getCacheOfRates(valueObjs);
        if (isUsingChildCache()) {
            for (Object key : tmpCache.keySet()) {
                CacheForList<T> childMatcher = (CacheForList<T>) cache.computeIfAbsent(key, k -> childCache());
                childMatcher.put(tmpCache.get(key));
            }
        } else {
            cache.putAll(tmpCache);
        }
    }

    @Override
    public List<T> get(T queryObj) {
        Object key = getKeyByBusiObject(queryObj);
        if (isUsingChildCache()) {
            CacheForList<T> childMatcher = (CacheForList<T>) cache.get(key);
            if (childMatcher == null) {
                return new ArrayList<>();
            } else {
                return childMatcher.get(queryObj);
            }
        } else {
            return (List<T>) cache.get(key);
        }
    }
}