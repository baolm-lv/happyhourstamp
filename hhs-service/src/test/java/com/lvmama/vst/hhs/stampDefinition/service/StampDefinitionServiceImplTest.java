/**
 * 
 */
package com.lvmama.vst.hhs.stampDefinition.service;

import java.util.List;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.lvmama.vst.hhs.HappyHourStampServiceTest;
import com.lvmama.vst.hhs.model.admin.StampCreationRequest;
import com.lvmama.vst.hhs.model.admin.StampDetailsVo;
import com.lvmama.vst.hhs.stampDefinition.web.StampManagementControllerTest;

@Test
public class StampDefinitionServiceImplTest extends HappyHourStampServiceTest {

	@Autowired
	@Mock
	private StampManagementService stampManagementService;
	
	private String stampName = "";
	private String stampDefinitionId = "";
	
	@BeforeClass 
	public void setUp() {
		//StampCreationRequest createRequest = StampManagementControllerTest.createRequest1();
		//this.stampName = createRequest.getStampName();
		//this.stampDefinitionId = stampManagementService.createStampDefinition(createRequest);
	}
	
	@Test
	public void testGetStampByProductName() {
		/*List<StampDetailsVo> vos = stampManagementService.getStampDefinitionByName(this.stampName);
		if (null == vos) {
			return;
		}
		for (StampDetailsVo vo : vos) {
			System.out.println(vo.getId());
		}*/
	//	Assert.notEmpty(list);
	}
	 
	@Test
	public void testGetStampByProductId() {
	/*	StampDetailsVo vo = stampManagementService.getStampById(this.stampDefinitionId);
		if (null != vo) {
			System.out.println(vo.getId());
		}*/
	//	Assert.notEmpty(list);
	}
}