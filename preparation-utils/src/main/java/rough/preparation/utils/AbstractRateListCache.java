package rough.preparation.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RateListCache 默认实现基类，使用递归子匹配器的方式进行实现
 * @param <BusiObject>
 * @param <Rate>
 * @param <Key>
 */
@SuppressWarnings({"unchecked", "unused"})
public abstract class AbstractRateListCache<BusiObject, Rate, Key>
        implements RateListCache<BusiObject, Rate, Key> {

    /** 本级缓存 */
    protected final Map<Key, Object> cache = new HashMap<>();

    /** 使用使用子匹配器 */
    protected abstract boolean isUsingChildMatcher();

    /** 新的子匹配器实例 */
    protected abstract RateListCache<BusiObject, Rate, Key> childMatcher();

    /**
     * 根据业务对象来决定进行匹配的字段值，匹配时使用
     * @param busiObject 用来进行匹配的类
     * @return 进行匹配的字段值
     */
    protected abstract Key getKeyByBusiObject(BusiObject busiObject);

    /**
     * 将需缓存的费率列表按匹配字段转换为 Map 形式
     * @param rates 需要缓存的费率列表
     * @return 按匹配字段分类的 Map 形式
     */
    protected abstract Map<Key, List<Rate>> getCacheOfRates(List<Rate> rates);

    @Override
    public void put(List<Rate> rates) {
        Map<Key, List<Rate>> tmpCache = getCacheOfRates(rates);
        if (isUsingChildMatcher()) {
            for (Key key : tmpCache.keySet()) {
                if (cache.containsKey(key)) {
                    RateListCache<BusiObject, Rate, Key> childMatcher = (RateListCache<BusiObject, Rate, Key>) cache.get(key);
                    childMatcher.put(tmpCache.get(key));
                } else {
                    RateListCache<BusiObject, Rate, Key> childMatcher = childMatcher();
                    childMatcher.put(tmpCache.get(key));
                    cache.put(key, childMatcher);
                }
            }
        } else {
            cache.putAll(tmpCache);
        }
    }

    @Override
    public List<Rate> get(BusiObject busiObject) {
        Key key = getKeyByBusiObject(busiObject);
        if (isUsingChildMatcher()) {
            RateListCache<BusiObject, Rate, Key> childMatcher = (RateListCache<BusiObject, Rate, Key>) cache.get(key);
            if (childMatcher == null) {
                return new ArrayList<>();
            } else {
                return childMatcher.get(busiObject);
            }
        } else {
            return (List<Rate>) cache.get(key);
        }
    }
}