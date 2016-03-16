package eccrm.base.user.domain;

/**
 * 用户与组的关联中间表
 *
 * @author miles
 * @datetime 2014-07-03
 */
public class UserGroup {
    private String id;
    private User user;
    private Group group;
    private Integer sequenceNo;

    public UserGroup() {
    }

    public UserGroup(String userId, String groupId, Integer sequenceNo) {
        user = new User();
        user.setId(userId);
        group = new Group();
        group.setId(groupId);
        this.sequenceNo = sequenceNo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }
}
