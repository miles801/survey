package eccrm.base.notebook.dao;

import com.ycrl.core.pager.PageVo;
import eccrm.base.notebook.bo.NoteBookBo;
import eccrm.base.notebook.domain.NoteBook;
import eccrm.base.notebook.vo.NoteBookVo;
import java.util.List;

/**
* @author shenbb
* @datetime 2014-03-22
*/
public interface NoteBookDao {

    String save(NoteBook noteBook);

    void update(NoteBook noteBook);

    List<NoteBook> query(NoteBookBo bo);

    Long getTotal(NoteBookBo bo);

    NoteBook findById(String id);

    void deleteById(String id);

}
