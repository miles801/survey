package eccrm.base.attachment.domain;

import eccrm.base.tenement.domain.CrmBaseDomain;
import eccrm.core.enums.EnumClass;
import eccrm.core.enums.EnumSymbol;

/**
 * @author miles
 * @datetime 14-2-28 下午10:40
 * 附件
 */

public class Attachment extends CrmBaseDomain implements EnumSymbol {

    public static final String STATUS_TEMP = "TEMP";
    public static final String STATUS_ACTIVE = "ACTIVE";
    /**
     * 文件的真实名称
     */
    private String fileName;

    private String fileType;

    private String contentType;

    private Long size;

    private Long uploadTime;
    /**
     * 业务id
     */
    private String businessId;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 业务类
     */
    private String businessClass;

    /**
     * 附件类型：本地附件、外部链接
     */
    @EnumClass(AttachmentType.class)
    private Integer attachmentType;
    /**
     * 访问路径
     */
    private String path;
    /**
     * 状态
     */
    private String status;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessClass() {
        return businessClass;
    }

    public void setBusinessClass(String businessClass) {
        this.businessClass = businessClass;
    }


    public Integer getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(Integer attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
