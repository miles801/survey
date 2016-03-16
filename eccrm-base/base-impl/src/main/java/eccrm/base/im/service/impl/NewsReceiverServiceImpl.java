package eccrm.base.im.service.impl;

import com.ycrl.core.pager.PageVo;
import com.ycrl.base.common.CommonStatus;
import eccrm.base.im.bo.NewsReceiverBo;
import eccrm.base.im.domain.NewsReceiver;
import eccrm.base.im.vo.NewsReceiverVo;
import eccrm.base.im.dao.NewsReceiverDao;
import eccrm.base.im.service.NewsReceiverService;
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
@Service("newsReceiverService")
public class NewsReceiverServiceImpl implements NewsReceiverService {
    @Resource
    private NewsReceiverDao newsReceiverDao;

    @Override
    public String save(NewsReceiver newsReceiver) {
        String id = newsReceiverDao.save(newsReceiver);
        return id;
    }

    @Override
    public void update(NewsReceiver newsReceiver) {
        newsReceiverDao.update(newsReceiver);
    }

    @Override
    public PageVo pageQuery(NewsReceiverBo bo) {
        PageVo vo = new PageVo();
        Long total = newsReceiverDao.getTotal(bo);
        vo.setTotal(total);
        if (total==null || total == 0) return vo;
        List<NewsReceiver> newsReceiverList = newsReceiverDao.query(bo);
        List<NewsReceiverVo> vos = BeanWrapBuilder.newInstance()
            .wrapList(newsReceiverList,NewsReceiverVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public NewsReceiverVo findById(String id) {
        NewsReceiver newsReceiver = newsReceiverDao.findById(id);
        return BeanWrapBuilder.newInstance()
            .wrap(newsReceiver, NewsReceiverVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            newsReceiverDao.deleteById(id);
        }
    }


}
