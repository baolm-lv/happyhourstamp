package com.lvmama.vst.hhs;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lvmama.vst.hhs.common.web.HhsApiUriTemplates;
import com.lvmama.vst.hhs.common.web.HhsWebConstants;

@RestController
@Api(value = HhsWebConstants.APP_ROOT_PATH)
public class WelcomeController {

	@ApiOperation(value = "Welcome message, can be used to verify service running.")
	@RequestMapping(method = RequestMethod.GET, value = {
			HhsApiUriTemplates.V1 + "/{message}" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> getStampById(@PathVariable("message") final String message) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("message", message);
		
		return map;
	}
}
