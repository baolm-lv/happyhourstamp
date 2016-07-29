package com.lvmama.vst.hhs.stamp.service;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.lvmama.vst.hhs.common.utils.HhsUtils;
import com.lvmama.vst.hhs.model.admin.StampCreationRequest;
import com.lvmama.vst.hhs.model.common.Constant.ActivityStatus;
import com.lvmama.vst.hhs.model.common.StampDuration;

public class StampManagementServiceTest {
	
	private StampCreationRequest createRequest1() {
		StampCreationRequest request = new StampCreationRequest();
		request.setStampName("stampName02");
		request.setPrintPrice(2000.2);
		request.setDownpayment(200.1);
		request.setSalePrice(1889.88);
		request.setSetPrice(1777.09);
		request.setInventoryLevel(200);
		request.setActivityStatus(ActivityStatus.N);
		request.setStampOnsaleDuration(new StampDuration(HhsUtils.stringDateToDate("2016-08-31"), HhsUtils.stringDateToDate("1988-09-31")));
 		request.setStampRedeemableDuration(new StampDuration(HhsUtils.stringDateToDate("2016-08-31"), HhsUtils.stringDateToDate("1988-09-31")));
		request.setRemindCustomerDate(HhsUtils.stringDateToDate("2016-08-31"));
		request.setBalancesDueInHour(301);
		List<StampDuration> slots = new ArrayList<>();
		slots.add(new StampDuration(HhsUtils.stringDateToDate("2016-04-12"), HhsUtils.stringDateToDate("1988-04-17")));
		slots.add(new StampDuration(HhsUtils.stringDateToDate("2016-05-12"), HhsUtils.stringDateToDate("1988-05-17")));
		slots.add(new StampDuration(HhsUtils.stringDateToDate("2016-06-12"), HhsUtils.stringDateToDate("1988-06-17")));
		request.setAssociatedProdAvailTimeSlot(slots);
		request.setRemarks("remarks");
		request.setProductManagerId("dskjkjdskjs-111");
		request.setRuleRestrict("ruleRestrict");
		
		return request;
	}
	
	@Test
	public void testCreateStamp1() {

		StampCreationRequest request = this.createRequest1();
		String jsonStr = HhsUtils.objectToJsonString(request);
		System.out.println(jsonStr);
	}
	
	@Test
    public void queryStamp() {
/*
        StampDetails details = new  StampDetails();
        String url ="http://localhost:8181/hhs/v1/stamp/definition/queryStamp";
        RestTemplate template = new RestTemplate();
        details.setName("stamp");
        StampDetails[]  detail =   template.getForObject(url,  StampDetails[].class,  details);
      //  template.get
        String jsonStr = HhsUtils.objectToJsonString(detail);
        System.out.println(jsonStr);*/
    }
}
