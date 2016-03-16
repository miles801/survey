package eccrm.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miles
 * @datetime 2014/4/14 16:44
 * VO的工具类
 */
public class VoHelper {
    /**
     * 将一组源对象集合通过转化器转换为目标集合
     *
     * @param srcData 源对象
     * @param wrapper 目标对象
     * @param <T>     源对象类型
     * @param <K>     目标对象类型
     */
    public static <T, K> List<K> wrapVos(List<T> srcData, VoWrapper<T, K> wrapper) {
        List<K> vos = new ArrayList<K>();
        if (srcData == null) return vos;
        for (T o : srcData) {
            K foo = wrapper.wrap(o);
            if (foo == null) continue;
            vos.add(foo);
        }
        return vos;
    }
}
