package com.lvmama.vst.hhs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;

/**
 *
 * @author Luocheng Tang
 */
@SpringBootApplication
@EnableDiscoveryClient
@ImportResource(value = {"classpath:spring/security-config.xml"})
public class HappyHourStampService {

    private String serviceId;

    public String getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(final String serviceId) {
        this.serviceId = serviceId;
    }

    public static void main(final String[] args) {
        SpringApplication.run(HappyHourStampService.class, args);
    }
}
