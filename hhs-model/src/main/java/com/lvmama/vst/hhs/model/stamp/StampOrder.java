package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.lvmama.vst.hhs.model.common.StampDuration;

public class StampOrder implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String orderId;  //订单ID
	private String productId;  //产品ID
	private String productName; //产品名称
	private String goodsId;
	private String goodsName;
	private String stampId;   
	private String stampName;
	private List<StampDuration> availTimeSlot;	//可兑换的产品时间
	private int amount; 						//数量
	private Long price;						    //单价
	private Long paidPrice; 					//已支付金额
	private String orderStatus;					//订单状态 
	private Date createDate;                     //下单时间
	private Date latestPayTime;					//最后支付时间
    private StampDuration  stampRedeemableDate;//兑换时间段  
    private Long totalAmount;                   //应付金额
    private String departId;                    //多出发地
    
    private int unuseCodeNum;  // 可用券数量
	
	public int getUnuseCodeNum() {
		return unuseCodeNum;
	}
	public void setUnuseCodeNum(int unuseCodeNum) {
		this.unuseCodeNum = unuseCodeNum;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getStampId() {
		return stampId;
	}
	public void setStampId(String stampId) {
		this.stampId = stampId;
	}
	public String getStampName() {
		return stampName;
	}
	public void setStampName(String stampName) {
		this.stampName = stampName;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getLatestPayTime() {
		return latestPayTime;
	}
	public void setLatestPayTime(Date latestPayTime) {
		this.latestPayTime = latestPayTime;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public List<StampDuration> getAvailTimeSlot() {
		return availTimeSlot;
	}
	public void setAvailTimeSlot(List<StampDuration> availTimeSlot) {
		this.availTimeSlot = availTimeSlot;
	}
	public double getPrice() {
		return price;
	}
	
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public Long getPaidPrice() {
        return paidPrice;
    }
    public void setPaidPrice(Long paidPrice) {
        this.paidPrice = paidPrice;
    }
    public StampDuration getStampRedeemableDate() {
        return stampRedeemableDate;
    }
    public void setStampRedeemableDate(StampDuration stampRedeemableDate) {
        this.stampRedeemableDate = stampRedeemableDate;
    }
    public void setPrice(Long price) {
        this.price = price;
    }    
    
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Long getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public String getDepartId() {
        return departId;
    }
    public void setDepartId(String departId) {
        this.departId = departId;
    }
    public static StampOrder build() {
		StampOrder so = new StampOrder();
		so.setAmount(new Random().nextInt(10) + 1);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, so.getAmount());
		so.setLatestPayTime(c.getTime());
		so.setOrderId(String.valueOf(new Random().nextInt(1000000)));
		//so.setOrderNo(UUID.randomUUID().toString());
		so.setProductId(String.valueOf(new Random().nextInt(1000000)));
		so.setProductName("测试产品名称");
		so.setGoodsId(String.valueOf(new Random().nextInt(1000000)));
		so.setGoodsName("测试商品名称");
		so.setStampId(UUID.randomUUID().toString());
		so.setStampName("测试预约券");
		so.setOrderStatus("PAYED");
		so.setPaidPrice(2000L);
		so.setPrice(3000L);
	    c.add(Calendar.DATE, 30);
		so.setLatestPayTime(c.getTime());

		Date start = c.getTime();
		List<StampDuration> duration = new ArrayList<StampDuration>();
		c.add(Calendar.DATE, 10);
		duration.add(new StampDuration(start, c.getTime()));
	    so.setStampRedeemableDate(new StampDuration(start, c.getTime()));
		c.add(Calendar.DATE, 4);
		duration.add(new StampDuration(c.getTime(), c.getTime()));
		c.add(Calendar.DATE, 2);
		duration.add(new StampDuration(c.getTime(), c.getTime()));
		c.add(Calendar.DATE, 4);
		duration.add(new StampDuration(c.getTime(), c.getTime()));
		so.setAvailTimeSlot(duration);
		return so;
	}
	
}
