/**
 * 
 */
package com.lvmama.vst.hhs.model.admin;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author fengyonggang
 *
 */
public class PackageTransportVo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long groupTransportId;
	private Long groupId;
	private Long toStartPoint;
	private String toStartPointName;
	private Long toDestination;
	private String toDestinationName;
	private Long backStartPoint;
	private String backStartPointName;
	private Long backDestination;
	private String backDestinationName;
	private int toStartDay;
	private int backStartDay;
	private String transportType;
	private List<SuppGoodsVo> goods;
	private List<PackageProductVo> products;
	public Long getGroupTransportId() {
		return groupTransportId;
	}
	public void setGroupTransportId(Long groupTransportId) {
		this.groupTransportId = groupTransportId;
	}
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
	public int getToStartDay() {
		return toStartDay;
	}
	public void setToStartDay(int toStartDay) {
		this.toStartDay = toStartDay;
	}
	public int getBackStartDay() {
		return backStartDay;
	}
	public void setBackStartDay(int backStartDay) {
		this.backStartDay = backStartDay;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public List<SuppGoodsVo> getGoods() {
		return goods;
	}
	public void setGoods(List<SuppGoodsVo> goods) {
		this.goods = goods;
	}
   
    public List<PackageProductVo> getProducts() {
        return products;
    }
    public void setProducts(List<PackageProductVo> products) {
        this.products = products;
    }
    
    
	
}
