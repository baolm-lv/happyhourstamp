/**
 * 
 */
package com.lvmama.vst.hhs;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

/**
 * @author fengyonggang
 *
 */
@ActiveProfiles({ "integration" })
@WebAppConfiguration
@SpringApplicationConfiguration(AppTest.class)
@Test
public class HappyHourStampServiceTest extends AbstractTestNGSpringContextTests {

//	@Configuration
//	static class ContextConfiguration {
//		@Bean
//		public DataSource dataSource() {
//			DriverManagerDataSource ds = new DriverManagerDataSource();
//			ds.setDriverClassName("com.mysql.jdbc.Driver");
//			ds.setUrl("jdbc:mysql://10.200.3.178/hhs");
//			ds.setUsername("hhs");
//			ds.setPassword("123456");
//			return ds;
//		}
//	}
}
