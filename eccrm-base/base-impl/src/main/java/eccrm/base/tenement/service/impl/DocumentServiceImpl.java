package eccrm.base.tenement.service.impl;

import eccrm.base.tenement.bo.DocumentBo;
import eccrm.base.tenement.dao.DocumentDao;
import eccrm.base.tenement.domain.Document;
import eccrm.base.tenement.service.DocumentService;
import eccrm.base.tenement.vo.DocumentVo;
import eccrm.core.VoHelper;
import eccrm.core.VoWrapper;
import com.ycrl.core.pager.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: miles
 * @datetime: 2014-07-01
 */
@Service("documentService")
public class DocumentServiceImpl implements DocumentService, VoWrapper<Document, DocumentVo> {
    @Resource
    private DocumentDao documentDao;

    @Override
    public String save(Document document) {
        return documentDao.save(document);
    }

    @Override
    public void update(Document document) {
        documentDao.update(document);
    }

    @Override
    public PageVo query(DocumentBo bo) {
        PageVo vo = new PageVo();
        Long total = documentDao.getTotal(bo);
        vo.setTotal(total);
        if (total == 0) return vo;
        List<Document> documents = documentDao.query(bo);
        vo.setData(VoHelper.wrapVos(documents, this));
        return vo;
    }

    @Override
    public DocumentVo findById(String id) {
        return wrap(documentDao.findById(id));
    }


    @Override
    public void deleteByIds(String... ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            documentDao.deleteById(id);
        }
    }


    public DocumentVo wrap(Document document) {
        if (document == null) return null;
        DocumentVo vo = new DocumentVo();
        BeanUtils.copyProperties(document, vo);
        return vo;
    }

    @Override
    public List<DocumentVo> queryByTenementId(String tenementId) {
        List<Document> docs = documentDao.queryByTenementId(tenementId);
        return VoHelper.wrapVos(docs, this);
    }
}
