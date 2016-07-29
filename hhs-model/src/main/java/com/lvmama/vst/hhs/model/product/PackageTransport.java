/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

import java.io.Serializable;

/**
 * @author fengyonggang
 *
 */
public class PackageTransport implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long groupId;
	private Long toStartPoint;
	private String toStartPointName;
	private Long toDestination;
	private String toDestinationName;
	private Long backStartPoint;
	private String backStartPointName;
	private Long backDestination;
	private String backDestinationName;
	private String toStartDate;
	private String backStartDate;
	private String transportType;
	private ProdTrafficVO traffic;
	private SuppGoods goods;
	
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
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
	public String getToStartDate() {
		return toStartDate;
	}
	public void setToStartDate(String toStartDate) {
		this.toStartDate = toStartDate;
	}
	public String getBackStartDate() {
		return backStartDate;
	}
	public void setBackStartDate(String backStartDate) {
		this.backStartDate = backStartDate;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public SuppGoods getGoods() {
		return goods;
	}
	public void setGoods(SuppGoods goods) {
		this.goods = goods;
	}
	public ProdTrafficVO getTraffic() {
		return traffic;
	}
	public void setTraffic(ProdTrafficVO traffic) {
		this.traffic = traffic;
	}
}
