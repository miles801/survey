package eccrm.base.auth.web;

import eccrm.base.auth.domain.AccreditFunc;

import java.util.List;

/**
 * @author Michael
 */
public class AccreditFunTemp {
    private String deptId;
    private List<AccreditFunc> resources;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public List<AccreditFunc> getResources() {
        return resources;
    }

    public void setResources(List<AccreditFunc> resources) {
        this.resources = resources;
    }
}
