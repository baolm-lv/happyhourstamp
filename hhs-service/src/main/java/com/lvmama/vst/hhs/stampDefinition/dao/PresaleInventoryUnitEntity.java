package com.lvmama.vst.hhs.stampDefinition.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the presale_inventory_unit database table.
 * 
 */
@Entity
@Table(name="presale_inventory_unit")
@NamedQuery(name="PresaleInventoryUnit.findAll", query="SELECT p FROM PresaleInventoryUnitEntity p")
public class PresaleInventoryUnitEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="CREATE_DATE")
	private Timestamp createDate;

	@Column(name="INVENTORY_LEVEL")
	private int inventoryLevel;

	@Column(name="STAMP_DEFINITION_ID")
	private String stampDefinitionId;

	@Column(name="UPDATE_DATE")
	private Timestamp updateDate;
	
	@Column(name="INIT_INVENTORY_LEVEL")
	private int initInventoryLevel;

	public PresaleInventoryUnitEntity() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public int getInventoryLevel() {
		return this.inventoryLevel;
	}

	public void setInventoryLevel(int inventoryLevel) {
		this.inventoryLevel = inventoryLevel;
	}

	public String getStampDefinitionId() {
		return this.stampDefinitionId;
	}

	public void setStampDefinitionId(String stampDefinitionId) {
		this.stampDefinitionId = stampDefinitionId;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

    public int getInitInventoryLevel() {
        return initInventoryLevel;
    }

    public void setInitInventoryLevel(int initInventoryLevel) {
        this.initInventoryLevel = initInventoryLevel;
    }
	
	

}