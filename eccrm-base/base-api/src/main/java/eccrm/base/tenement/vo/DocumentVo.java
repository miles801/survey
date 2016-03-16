package eccrm.base.tenement.vo;

/**
 * @author miles
 * @datetime 2014-07-01
 */

public class DocumentVo extends CrmBaseVo {

    private String name;
    private String docType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }
}
