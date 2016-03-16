package eccrm.base.notebook.domain;

import com.ycrl.base.common.CommonDomain;

/**
 * @author shenbb
 * @datetime 2014-03-22
 */
public class NoteBook extends CommonDomain {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
