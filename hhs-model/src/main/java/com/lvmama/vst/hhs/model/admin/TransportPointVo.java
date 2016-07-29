package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;

public class TransportPointVo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Long toStartPoint;
    private String toStartPointName;
    private Long toDestination;
    private String toDestinationName;
    private Long backStartPoint;
    private String backStartPointName;
    private Long backDestination;
    private String backDestinationName;
    public Long getToStartPoint() {
        return toStartPoint;
    }
    public void setToStartPoint(Long toStartPoint) {
        this.toStartPoint = toStartPoint;
    }
    public String getToStartPointName() {
        return toStartPointName;
    }
    public void setToStartPointName(String toStartPointName) {
        this.toStartPointName = toStartPointName;
    }
    public Long getToDestination() {
        return toDestination;
    }
    public void setToDestination(Long toDestination) {
        this.toDestination = toDestination;
    }
    public String getToDestinationName() {
        return toDestinationName;
    }
    public void setToDestinationName(String toDestinationName) {
        this.toDestinationName = toDestinationName;
    }
    public Long getBackStartPoint() {
        return backStartPoint;
    }
    public void setBackStartPoint(Long backStartPoint) {
        this.backStartPoint = backStartPoint;
    }
    public String getBackStartPointName() {
        return backStartPointName;
    }
    public void setBackStartPointName(String backStartPointName) {
        this.backStartPointName = backStartPointName;
    }
    public Long getBackDestination() {
        return backDestination;
    }
    public void setBackDestination(Long backDestination) {
        this.backDestination = backDestination;
    }
    public String getBackDestinationName() {
        return backDestinationName;
    }
    public void setBackDestinationName(String backDestinationName) {
        this.backDestinationName = backDestinationName;
    }
    
    
    
    

}
