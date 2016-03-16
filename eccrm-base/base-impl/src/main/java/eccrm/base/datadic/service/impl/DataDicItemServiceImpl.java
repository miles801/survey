package eccrm.base.datadic.service.impl;

import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.pager.PageVo;
import eccrm.base.datadic.bo.DataDicItemBo;
import eccrm.base.datadic.dao.DataDicItemDao;
import eccrm.base.datadic.domain.DataDicItem;
import eccrm.base.datadic.service.DataDicItemService;
import eccrm.base.datadic.vo.DataDicItemVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Michael
 */
@Service("dataDicItemService")
public class DataDicItemServiceImpl implements DataDicItemService {
    @Resource
    private DataDicItemDao dataDicItemDao;

    @Override
    public String save(DataDicItem dataDicItem) {
        String id = dataDicItemDao.save(dataDicItem);
        return id;
    }

    @Override
    public void update(DataDicItem dataDicItem) {
        dataDicItemDao.update(dataDicItem);
    }

    @Override
    public PageVo pageQuery(DataDicItemBo bo) {
        PageVo vo = new PageVo();
        Long total = dataDicItemDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<DataDicItem> dataDicItemList = dataDicItemDao.query(bo);
        List<DataDicItemVo> vos = BeanWrapBuilder.newInstance()
                .wrapList(dataDicItemList, DataDicItemVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public List<DataDicItemVo> queryByDicCode(String code) {
        if (code == null || "".equals(code.trim())) {
            return null;
        }
        List<DataDicItem> items = dataDicItemDao.queryByDicCode(code);
        return BeanWrapBuilder.newInstance()
                .wrapList(items, DataDicItemVo.class);
    }

    @Override
    public DataDicItemVo findById(String id) {
        DataDicItem dataDicItem = dataDicItemDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(dataDicItem, DataDicItemVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            dataDicItemDao.deleteById(id);
        }
    }


}
