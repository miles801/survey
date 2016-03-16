package eccrm.base.tenement.domain;

import java.io.Serializable;

/**
 * <p>租户标识接口，该系统中所有模块的实体类均需要实现该接口</p>
 *
 * @author miles
 */
public interface TenementSymbol extends Serializable {
    /**
     * 获得租户的唯一ID
     */
    String getTenementId();

    /**
     * 设置租户的ID
     */
    void setTenementId(String tenementId);
}
