package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;

public class ManagerVo implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 75391579150202594L;
    private Long managerId;
    private String managerName;
    public Long getManagerId() {
        return managerId;
    }
    public void setManagerId(Long long1) {
        this.managerId = long1;
    }
    public String getManagerName() {
        return managerName;
    }
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
    
    

}
