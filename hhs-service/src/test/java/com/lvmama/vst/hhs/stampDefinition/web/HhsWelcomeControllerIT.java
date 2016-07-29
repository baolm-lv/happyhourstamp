package com.lvmama.vst.hhs.stampDefinition.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.lvmama.vst.hhs.HappyHourStampServiceTest;

@Test
public class HhsWelcomeControllerIT extends HappyHourStampServiceTest {
    
    @Autowired
    @Qualifier("hhsClient")
    private RestTemplate client;
    
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeClass
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    public void testWelcomeController() throws Exception {
        MvcResult results = this.mockMvc.perform(get("http://localhost:8181/hhs/v1/message")).andReturn();
        String content = results.getResponse().getContentAsString();
        Assert.assertTrue(content.contains("\"message\":\"message\""));
    }
    
    public void test() throws Exception {
       MvcResult results = this.mockMvc.perform(get("http://localhost:8181/hhs/v1/stamp/definition/name/name")).andReturn();
    //   List list = this.client.getForObject("http://localhost:8181/hhs/v1/stamp/definition/name/name", List.class);
       System.out.println("start test stampDefinition get by name");
    }
}
