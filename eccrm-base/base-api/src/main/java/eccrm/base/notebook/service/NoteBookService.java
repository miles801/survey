package eccrm.base.notebook.service;

import com.ycrl.core.pager.PageVo;
import eccrm.base.notebook.bo.NoteBookBo;
import eccrm.base.notebook.domain.NoteBook;
import eccrm.base.notebook.vo.NoteBookVo;

/**
 * @author shenbb
 * @datetime 2014-03-22
 */
public interface NoteBookService {

    String save(NoteBook noteBook);

    void update(NoteBook noteBook);

    PageVo query(NoteBookBo bo);

    NoteBookVo findById(String id);

    void deleteByIds(String... ids);

}
