package eccrm.base.tenement.domain;

import eccrm.base.attachment.AttachmentSymbol;

/**
 * 租户文档
 *
 * @author miles
 * @datetime 2014-07-01
 */
public class Document extends CrmBaseDomain implements AttachmentSymbol {

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

    @Override
    public String businessId() {
        return id;
    }
}
