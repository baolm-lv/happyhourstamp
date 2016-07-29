/**
 * 
 */
package com.lvmama.vst.hhs.downgrade.eureka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.lvmama.vst.hhs.common.web.ResponseEntityBuilder;
import com.lvmama.vst.hhs.downgrade.eureka.generated.Instance;
import com.lvmama.vst.hhs.downgrade.eureka.generated.StatusType;

/**
 * @author fengyonggang
 *
 */
@RestController
@RequestMapping("/mgt/eureka")
public class EurekaController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EurekaController.class);
	
	@Autowired
	private EurekaRestOperations oper;
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value = "/instance", method = RequestMethod.GET)
	public ResponseEntity<Instance> instance() {
		try {
			String url = oper.getInstanceUrl();
			LOGGER.info("url to be called for instance: {}", url);
			Instance instance = restTemplate.getForObject(url, Instance.class);
			return ResponseEntityBuilder.ok(instance);
		} catch(Exception e) {
			LOGGER.error("error when downgrade services", e);
			return new ResponseEntity<Instance>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/status", method = RequestMethod.POST)
	public ResponseEntity<Instance> status(@RequestBody StatusRequest request) {
		try {
			StatusType status = StatusType.fromValue(request.getStatus());
			if(status == null) 
				return new ResponseEntity<Instance>(HttpStatus.INTERNAL_SERVER_ERROR);
			
			String url = oper.getStatusUrl(status);
			LOGGER.info("url to be called for instance: {}", url);
			restTemplate.put(url, null);
			
			return instance();
			
		} catch(Exception e) {
			LOGGER.error("error when downgrade services", e);
			return new ResponseEntity<Instance>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
