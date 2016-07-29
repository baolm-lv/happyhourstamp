/**
 * 
 */
package com.lvmama.vst.hhs.model.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fengyonggang
 *
 */
public class PackageTicket implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long groupId;
	private List<String> dates;
	private List<TicketGoods> tickets;
	
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public List<String> getDates() {
		return dates;
	}
	public void setDates(List<String> dates) {
		this.dates = dates;
	}
	public List<TicketGoods> getTickets() {
		return tickets;
	}
	public void setTicket(List<TicketGoods> tickets) {
		this.tickets = tickets;
	}
	public void addTicketGoods(TicketGoods goods) {
		if(goods == null) 
			return ;
		if(tickets == null) 
			tickets = new ArrayList<TicketGoods>();
		tickets.add(goods);
	}
}
