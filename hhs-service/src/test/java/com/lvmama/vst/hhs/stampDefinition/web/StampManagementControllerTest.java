package com.lvmama.vst.hhs.stampDefinition.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.lvmama.vst.back.client.pub.service.ComLogClientService;
import com.lvmama.vst.hhs.HappyHourStampServiceTest;
import com.lvmama.vst.hhs.common.utils.HhsUtils;
import com.lvmama.vst.hhs.model.admin.StampCreationRequest;
import com.lvmama.vst.hhs.model.common.Constant.ActivityStatus;
import com.lvmama.vst.hhs.model.common.StampDuration;
import com.lvmama.vst.hhs.stampDefinition.service.StampGoodsService;
import com.lvmama.vst.hhs.stampDefinition.service.StampManagementService;

@Test
public class StampManagementControllerTest extends HappyHourStampServiceTest {

	private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Mock
	private ComLogClientService comLogService;

	@Mock
	private StampGoodsService goodsService;

	@Autowired
	@Mock
	private StampManagementService stampService;

	@BeforeClass
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	public void testWelcomeController() throws Exception {
		MvcResult results = this.mockMvc.perform(get("http://localhost:8181/hhs/v1/message")).andReturn();
		String content = results.getResponse().getContentAsString();
		Assert.assertTrue(content.contains("\"message\":\"message\""));
	}

	public void testCreateStamp() throws Exception {

		URI uri = new URI("http://localhost:8181/hhs/v1/admin/stamp/definition/");
		StampCreationRequest stampRequest = createRequest1();
		System.out.println(this.objectWriter.writeValueAsString(stampRequest));
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(uri);
		MvcResult result = this.mockMvc.perform(builder.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectWriter.writeValueAsString(stampRequest))).andReturn();
		String str = result.getResponse().getContentAsString();
		System.out.println("response content: " + str);

	}

	public void testGetById() throws Exception {
		MvcResult result = this.mockMvc.perform(get("http://localhost:8181/hhs/v1//admin/stamp/definition/id-001"))
				.andReturn();
		String str = result.getResponse().getContentAsString();
		System.out.println("response content: " + str);
	}
	
	public static  StampCreationRequest createRequest1() {
		StampCreationRequest request = new StampCreationRequest();
		request.setStampName("stampName02");
		request.setPrintPrice(2000.2);
		request.setDownpayment(200.1);
		request.setSalePrice(1889.88);
		request.setSetPrice(1777.09);
		request.setInventoryLevel(200);
		request.setActivityStatus(ActivityStatus.N);
		request.setStampOnsaleDuration(
				new StampDuration(HhsUtils.stringDateToDate("2016-08-31"), HhsUtils.stringDateToDate("1988-09-31")));
		request.setStampRedeemableDuration(
				new StampDuration(HhsUtils.stringDateToDate("2016-08-31"), HhsUtils.stringDateToDate("1988-09-31")));
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
		request.setProductId(123);
		return request;
	}
	
}
