package eccrm.base.tenement.dao;

import eccrm.base.tenement.bo.DocumentBo;
import eccrm.base.tenement.domain.Document;

import java.util.List;

/**
 * @author miles
 * @datetime 2014-07-01
 */
public interface DocumentDao {

    String save(Document document);

    void update(Document document);

    List<Document> query(DocumentBo bo);

    long getTotal(DocumentBo bo);

    Document findById(String id);

    int deleteById(String id);

    public List<Document> queryByTenementId(String tenementId);
}
