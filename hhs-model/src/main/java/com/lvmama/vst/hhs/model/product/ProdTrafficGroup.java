/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

import java.util.List;

/**
 * @author fengyonggang
 *
 */
public class ProdTrafficGroup {
	
	private Long groupId;
    private Long productId;
    private String groupName;
    private List<ProdTrafficBus> prodTrafficBusList;//交通组下面的汽车信息
    private List<ProdTrafficTrain> prodTrafficTrainList;//交通组下面的火车信息
    private List<ProdTrafficFlight> prodTrafficFlightList;//交通组下面的飞机信息
    private List<ProdTrafficShip> prodTrafficShipList;//交通组下面的轮船信息
    
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<ProdTrafficBus> getProdTrafficBusList() {
		return prodTrafficBusList;
	}
	public void setProdTrafficBusList(List<ProdTrafficBus> prodTrafficBusList) {
		this.prodTrafficBusList = prodTrafficBusList;
	}
	public List<ProdTrafficTrain> getProdTrafficTrainList() {
		return prodTrafficTrainList;
	}
	public void setProdTrafficTrainList(List<ProdTrafficTrain> prodTrafficTrainList) {
		this.prodTrafficTrainList = prodTrafficTrainList;
	}
	public List<ProdTrafficFlight> getProdTrafficFlightList() {
		return prodTrafficFlightList;
	}
	public void setProdTrafficFlightList(List<ProdTrafficFlight> prodTrafficFlightList) {
		this.prodTrafficFlightList = prodTrafficFlightList;
	}
	public List<ProdTrafficShip> getProdTrafficShipList() {
		return prodTrafficShipList;
	}
	public void setProdTrafficShipList(List<ProdTrafficShip> prodTrafficShipList) {
		this.prodTrafficShipList = prodTrafficShipList;
	}
}
