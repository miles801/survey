package eccrm.base.notebook.service.impl;

import com.ycrl.core.pager.PageVo;
import eccrm.base.notebook.bo.NoteBookBo;
import eccrm.base.notebook.dao.NoteBookDao;
import eccrm.base.notebook.domain.NoteBook;
import eccrm.base.notebook.service.NoteBookService;
import eccrm.base.notebook.vo.NoteBookVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: shenbb
 * @datetime: 2014-03-22
 */
@Service("noteBookService")
public class NoteBookServiceImpl implements NoteBookService {
    @Resource
    private NoteBookDao noteBookDao;

    @Override
    public String save(NoteBook noteBook) {
        String id = noteBookDao.save(noteBook);
        return id;
    }

    @Override
    public void update(NoteBook noteBook) {
        noteBookDao.update(noteBook);
    }

    @Override
    public PageVo query(NoteBookBo bo) {
        PageVo vo = new PageVo();
        Long total = noteBookDao.getTotal(bo);
        if (total == null || total == 0) return vo;
        vo.setTotal(total);
        List<NoteBook> noteBooks = noteBookDao.query(bo);
        vo.setData(wrapVos(noteBooks));
        return vo;
    }

    @Override
    public NoteBookVo findById(String id) {
        return wrapVo(noteBookDao.findById(id));
    }

    @Override
    public void deleteByIds(String... ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            if (StringUtils.isNotBlank(id)) {
                noteBookDao.deleteById(id);
            }
        }


    }


    private NoteBookVo wrapVo(NoteBook noteBook) {
        if (noteBook == null) return null;
        NoteBookVo vo = new NoteBookVo();
        BeanUtils.copyProperties(noteBook, vo);

        return vo;
    }

    private List<NoteBookVo> wrapVos(List<NoteBook> noteBooks) {
        List<NoteBookVo> vos = new ArrayList<NoteBookVo>();
        if (noteBooks == null) return vos;
        for (NoteBook noteBook : noteBooks) {
            NoteBookVo foo = wrapVo(noteBook);
            if (foo == null) continue;
            vos.add(foo);
        }
        return vos;
    }
}
