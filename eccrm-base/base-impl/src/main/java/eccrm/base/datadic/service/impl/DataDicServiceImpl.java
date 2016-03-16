package eccrm.base.datadic.service.impl;

import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.pager.PageVo;
import eccrm.base.datadic.bo.DataDicBo;
import eccrm.base.datadic.dao.DataDicCondition;
import eccrm.base.datadic.dao.DataDicDao;
import eccrm.base.datadic.dao.DataDicItemDao;
import eccrm.base.datadic.domain.DataDic;
import eccrm.base.datadic.domain.DataDicItem;
import eccrm.base.datadic.service.DataDicService;
import eccrm.base.datadic.vo.DataDicItemVo;
import eccrm.base.datadic.vo.DataDicVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Michael
 */
@Service("dataDicService")
public class DataDicServiceImpl implements DataDicService {
    @Resource
    private DataDicDao dataDicDao;

    @Resource
    private DataDicItemDao dataDicItemDao;

    @Override
    public String save(DataDic dataDic) {
        // 保存数据字典
        String id = dataDicDao.save(dataDic);

        // 保存字典项
        saveDataDicItem(dataDic);
        return id;
    }

    private void saveDataDicItem(DataDic dataDic) {
        List<DataDicItem> items = dataDic.getItems();
        if (items != null) {
            for (DataDicItem item : items) {
                item.setDicId(dataDic.getId());
                item.setDicCode(dataDic.getCode());
                item.setDicName(dataDic.getName());
                dataDicItemDao.save(item);
            }
        }
    }

    @Override
    public void update(DataDic dataDic) {
        dataDicDao.update(dataDic);

        // 删掉原有关联关系
        dataDicItemDao.deleteByDicId(dataDic.getId());

        // 重建新的关系
        saveDataDicItem(dataDic);
    }

    @Override
    public PageVo pageQuery(DataDicBo bo) {
        PageVo vo = new PageVo();
        Long total = dataDicDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<DataDic> dataDicList = dataDicDao.query(bo);
        List<DataDicVo> vos = BeanWrapBuilder.newInstance()
                .wrapList(dataDicList, DataDicVo.class);
        vo.setData(vos);
        return vo;
    }

    @Override
    public DataDicVo findById(String id) {
        DataDic dataDic = dataDicDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(dataDic, DataDicVo.class);
    }

    @Override
    public DataDicVo detail(String id) {
        DataDicVo vo = findById(id);
        if (vo != null) {
            List<DataDicItem> items = dataDicItemDao.queryByDicCode(vo.getCode());
            vo.setItems(BeanWrapBuilder.newInstance()
                    .wrapList(items, DataDicItemVo.class));
        }
        return vo;
    }

    @Override
    public DataDicVo findByCode(String code) {
        DataDic dataDic = dataDicDao.findByCode(code);
        if (dataDic == null) {
            return null;
        }
        BeanWrapBuilder builder = BeanWrapBuilder.newInstance();
        DataDicVo vo = builder.wrap(dataDic, DataDicVo.class);
        List<DataDicItem> items = dataDicItemDao.queryByDicCode(code);
        vo.setItems(builder
                .wrapList(items, DataDicItemVo.class));

        return vo;
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            // 删除数据字典项
            dataDicItemDao.deleteByDicId(id);

            // 删除数据字典
            dataDicDao.deleteById(id);
        }
    }


    @Override
    public List queryData(DataDicCondition condition) {
        String className = condition.getClassName();
        if (className == null || "".equals(className)) {
            throw new RuntimeException("错误的数据字典配置：类名称不能为空!");
        }
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("错误的数据字典配置,类[" + className + "]不存在!");
        }
        return dataDicDao.queryData(clazz, condition);
    }
}
