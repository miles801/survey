package eccrm.base.auth.service;

import eccrm.base.auth.domain.AccreditFunc;

import java.util.List;

/**
 * @author Michael
 */
public interface AccreditFuncService {

    /**
     * 给指定岗位授权新的数据资源
     * 首先删除原有被授权的资源，然后重新保存新的资源集合
     *
     * @param positionId    岗位id
     * @param accreditFuncs 操作授权集合
     */
    void accreditToPosition(String positionId, List<AccreditFunc> accreditFuncs);

    /**
     * 查询指定岗位被授权的所有数据资源的编号集合
     * 注意：这个方法不适合参数超过2000的情况
     *
     * @param positionIds 岗位id列表
     * @return 数据资源的编号集合
     */
    List<String> queryResourceCodeByPosition(String[] positionIds);


    /**
     * 查询个人被授予的全部操作资源
     *
     * @return 返回操作资源的编号
     */
    List<String> queryPersonalResourceCode();

}
