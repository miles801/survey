package eccrm.base.tenement.service;

import eccrm.base.tenement.bo.DocumentBo;
import eccrm.base.tenement.domain.Document;
import eccrm.base.tenement.vo.DocumentVo;
import com.ycrl.core.pager.PageVo;

import java.util.List;

/**
 * @author miles
 * @datetime 2014-07-01
 */
public interface DocumentService {

    String save(Document document);

    void update(Document document);

    PageVo query(DocumentBo bo);

    DocumentVo findById(String id);

    void deleteByIds(String... ids);

    List<DocumentVo> queryByTenementId(String tenementId);
}
