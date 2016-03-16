package eccrm.core;

/**
 * 将实体类封装为对应的vo的接口
 * SRC 源对象：实体类
 * TARGET 目标对象：VO
 *
 * @author miles
 * @datetime 2014/4/14 16:46
 */
public interface VoWrapper<SRC, TARGET> {
    /**
     * 将一个实体源对象转成目标对象
     *
     * @param src 源对象
     * @return 目标对象
     */
    TARGET wrap(SRC src);
}
