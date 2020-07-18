package rough.preparation.utils;

import java.util.List;

/**
 * 由于 ObjectMatcher 只能返回一个具体的实例，在应用场景，返回列表，
 * 之后再进行循环匹配会更加有效一些，因此做了这个缓存费率列表的工具类。
 * 这个类缓存和返回的的对象都是列表，不同的是，缓存时只缓存一次全局列表，
 * 返回则返回匹配后的列表。
 * @param <BusiObject> 用来进行匹配的类
 * @param <Rate> 作为匹配结果返回列表中的类
 * @param <Key> 本次缓存的匹配字段，在子类中使用
 */
public interface RateListCache<BusiObject, Rate, Key> {
    /**
     * 匹配需要取得的结果列表类
     * @param busiObject 业务对象
     * @return 结果费率列表
     */
    List<Rate> get(BusiObject busiObject);

    /**
     * 缓存费率列表，所有要缓存的列表仅需要缓存一次，不需要循环缓存
     * @param rates 所有要缓存的费率列表
     */
    void put(List<Rate> rates);
}
