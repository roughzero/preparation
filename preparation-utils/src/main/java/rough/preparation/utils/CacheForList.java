package rough.preparation.utils;

import java.util.List;

/**
 * 由于 ObjectMatcher 只能返回一个具体的实例，在应用场景，返回列表，
 * 之后再进行循环匹配会更加有效一些，因此做了这个缓存费率列表的工具类。
 * 这个类缓存和返回的的对象都是列表，不同的是，缓存时只缓存一次全局列表，
 * 返回则返回匹配后的列表。
 * 2021-05-22 修改成缓存与匹配使用同一种类。
 *
 * @param <T> 需要缓存的类
 */
public interface CacheForList<T> {
    /**
     * 匹配需要取得的结果列表类
     *
     * @param key 用来进行匹配的类
     * @return 结果列表
     */
    List<T> get(T key);

    /**
     * 缓存费率列表，所有要缓存的列表仅需要缓存一次，不需要循环缓存
     *
     * @param values 所有要缓存的列表
     */
    void put(List<T> values);
}
