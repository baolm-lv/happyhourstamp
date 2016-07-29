/**
 * 
 */
package com.lvmama.vst.hhs.downgrade.eureka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lvmama.vst.hhs.downgrade.IPUtil;
import com.lvmama.vst.hhs.downgrade.eureka.generated.StatusType;

/**
 * @author fengyonggang
 *
 */
@Component
public class EurekaRestOperations {

	@Value("${eureka.client.serviceUrl.defaultZone}")
	private String eurekaService;
	
	@Value("${spring.application.name}")
	private String applicationName;
	
	public String getInstanceUrl() {
		return eurekaService + "apps/" + applicationName + "/" + IPUtil.getIpAddress();
	}
	
	public String getStatusUrl(StatusType status) {
		return eurekaService + "apps/" + applicationName + "/" + IPUtil.getIpAddress() + "/status?value=" + status.name();
	}
	
}
