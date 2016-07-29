package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;
import java.util.List;

public class PackageChangeHotelGroupVo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Long groupId;
    
    private String start;//入住时间
    private String end;//离店时间
    
    private List<ChangeHotelVo> changeHotelVoes;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

   

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List<ChangeHotelVo> getChangeHotelVoes() {
        return changeHotelVoes;
    }

    public void setChangeHotelVoes(List<ChangeHotelVo> changeHotelVoes) {
        this.changeHotelVoes = changeHotelVoes;
    }
    
    

}
