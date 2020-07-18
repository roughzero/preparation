package rough.preparation.utils;

/**
 * 使用一个含有多个属性的类匹配另一个含有多个属性的类的匹配器.
 * 典型的场景如匹配表定规则或者费率的情况.
 * @param <QueryObject> 用来进行匹配的类
 * @param <Result> 作为匹配结果返回的类
 */
public interface ObjectMatcher<QueryObject, Result> {
    /**
     * 匹配需要取得的结果类
     * @param queryObject 用来进行匹配的类示例
     * @return 匹配得到的结果，若无法匹配得到，则返回空
     */
    Result get(QueryObject queryObject);

    /**
     * 将一个匹配结果放入到缓存中
     * @param result 放入缓存的匹配结果
     */
    void put(Result result);
}
