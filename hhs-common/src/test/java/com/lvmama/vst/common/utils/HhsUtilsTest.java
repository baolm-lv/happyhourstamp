package com.lvmama.vst.common.utils;

import org.testng.annotations.Test;

import com.lvmama.vst.hhs.common.utils.HhsUtils;

@Test
public class HhsUtilsTest {

	@Test
	public void stringToObjectTest() {
		String str = "{\"user\" : \"alex\", \"id\" : 1}";
		Uid obj = (Uid)HhsUtils.jsonToObject(str, new Uid().getClass());
		System.out.println(obj.getId() + " " + obj.getUser());
	}
	
	@Test
	public void objectToStringTest() {
		Uid uid = new Uid(2, "jennider");
		String str = HhsUtils.objectToJsonString(uid);
		System.out.println(str);
	}
}
