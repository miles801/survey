package eccrm.base.im.service.impl;

import com.ycrl.core.pager.PageVo;
import com.ycrl.base.common.CommonStatus;
import eccrm.base.im.bo.NewsCollectBo;
import eccrm.base.im.domain.NewsCollect;
import eccrm.base.im.vo.NewsCollectVo;
import eccrm.base.im.dao.NewsCollectDao;
import eccrm.base.im.service.NewsCollectService;
import org.springframework.stereotype.Service;
import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.beans.BeanWrapCallback;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Michael
 */
@Service("newsCollectService")
public class NewsCollectServiceImpl implements NewsCollectService {
    @Resource
    private NewsCollectDao newsCollectDao;

    @Override
    public String save(NewsCollect newsCollect) {
        String id = newsCollectDao.save(newsCollect);
        return id;
    }

    @Override
    public void update(NewsCollect newsCollect) {
        newsCollectDao.update(newsCollect);
    }

    @Override
    public PageVo pageQuery(NewsCollectBo bo) {
        PageVo vo = new PageVo();
        Long total = newsCollectDao.getTotal(bo);
        vo.setTotal(total);
        if (total==null || total == 0) return vo;
        List<NewsCollect> newsCollectList = newsCollectDao.query(bo);
        List<NewsCollectVo> vos = BeanWrapBuilder.newInstance()
            .wrapList(newsCollectList,NewsCollectVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public NewsCollectVo findById(String id) {
        NewsCollect newsCollect = newsCollectDao.findById(id);
        return BeanWrapBuilder.newInstance()
            .wrap(newsCollect, NewsCollectVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            newsCollectDao.deleteById(id);
        }
    }


}
