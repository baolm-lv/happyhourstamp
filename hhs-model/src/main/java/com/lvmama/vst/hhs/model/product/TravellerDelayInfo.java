/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

/**
 * @author fengyonggang
 *
 */
public class TravellerDelayInfo {

	private String containPregnantWomen;		//是否包含孕妇,用'Y'和'N'表示
	private String containForeign;				//是否包含外籍人士,用'Y'和'N'表示
	private String containOldMan;				//是否包含老年人,用'Y'和'N'表示
	private String containBaby;					//是否包含婴儿,用'Y'和'N'表示
	
	public String getContainPregnantWomen() {
		return containPregnantWomen;
	}
	public void setContainPregnantWomen(String containPregnantWomen) {
		this.containPregnantWomen = containPregnantWomen;
	}
	public String getContainForeign() {
		return containForeign;
	}
	public void setContainForeign(String containForeign) {
		this.containForeign = containForeign;
	}
	public String getContainOldMan() {
		return containOldMan;
	}
	public void setContainOldMan(String containOldMan) {
		this.containOldMan = containOldMan;
	}
	public String getContainBaby() {
		return containBaby;
	}
	public void setContainBaby(String containBaby) {
		this.containBaby = containBaby;
	}
	
}
