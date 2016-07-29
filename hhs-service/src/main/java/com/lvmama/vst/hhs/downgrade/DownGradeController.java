/**
 * 
 */
package com.lvmama.vst.hhs.downgrade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.lvmama.vst.hhs.common.web.ResponseEntityBuilder;

/**
 * @author fengyonggang
 *
 */
@RestController
@RequestMapping("/mgt/service")
public class DownGradeController implements ApplicationContextAware {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DownGradeController.class);

	@Value("${info.app.name:hhs}")
	private String serviceName;
	
	@Autowired
	private DownGradeService downGradeService;

	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ServiceMappingResponse> list() {
		
		try {
			String startPath = "/" + serviceName;
			Map<String, Map<String, String>> downGradeMap = downGradeService.getDownGradeMap(serviceName);
			
			List<ServiceMappingBean> mappings = new ArrayList<ServiceMappingBean>();
			if (applicationContext != null) {
				for (Entry<String, RequestMappingHandlerMapping> bean : applicationContext
						.getBeansOfType(RequestMappingHandlerMapping.class).entrySet()) {
					
					if(bean.getValue() instanceof EndpointHandlerMapping) {
						continue;
					}
					
					Map<?, HandlerMethod> methods = bean.getValue().getHandlerMethods();
					for (Entry<?, HandlerMethod> method : methods.entrySet()) {
						ServiceMappingBean mapping = new ServiceMappingBean();
						mapping.setBeanName(bean.getKey());
						mapping.setMethodName(method.getValue().toString());
						
						String mappingKey = method.getKey().toString();
						mappingKey = mappingKey.substring("{".length(), mappingKey.length() - 1);
						String [] keyArray = mappingKey.split(",");
						for(String key : keyArray) {
							if(key.startsWith("methods=")) {
								key = key.substring("methods=".length());
								mapping.setMethods(parseMappingKey(key));
							} else if(key.startsWith("produces=")) {
								key = key.substring("produces=".length());
								mapping.setProduces(parseMappingKey(key));
							} else if(key.startsWith("consumes=")) {
								key = key.substring("consumes=".length());
								mapping.setConsumes(parseMappingKey(key));
							} else {
								mapping.setPaths(parseMappingKey(key));
							}
						}
						if(!mapping.getPaths()[0].startsWith(startPath)) {
							continue;
						}
						
						if(mapping.getMethods() != null && mapping.getMethods().length > 0) {
							String key = mapping.getPaths()[0] + "_" + mapping.getMethods()[0];
							if(downGradeMap.containsKey(key)) {
								Map<String, String> map = downGradeMap.get(key);
								mapping.setId(map.get("id"));
								mapping.setDisabled(map.get("disabled"));
							}
						}
						mappings.add(mapping);
					}
				}
			}
			
			ServiceMappingResponse resp = new ServiceMappingResponse();
			resp.setData(mappings);
			return ResponseEntityBuilder.ok(resp);
		} catch(Exception e) {
			LOGGER.error("error when load service list", e);
			return new ResponseEntity<ServiceMappingResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	private String[] parseMappingKey(String key) {
		return key.substring("[".length(), key.length() - 1).split("\\|\\|");
	}
	
	@RequestMapping(value = "/downgrade", method = RequestMethod.POST)
	public ResponseEntity<String> downGrade(@RequestBody DownGradeRequest request) {
		try {
			if(CollectionUtils.isNotEmpty(request.getIds())) {
				downGradeService.downGradeByIds(request.getIds(), request.isLocal());
			}
			if(CollectionUtils.isNotEmpty(request.getMethods())) {
				downGradeService.downGradeByMethods(request.getMethods(), serviceName, request.isLocal());
			}
			return ResponseEntityBuilder.ok();
		} catch(Exception e) {
			LOGGER.error("error when downgrade services", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/recover", method = RequestMethod.POST)
	public ResponseEntity<String> recover(@RequestBody DownGradeRequest request) {
		try {
			if(CollectionUtils.isNotEmpty(request.getIds())) {
				downGradeService.recoverByIds(request.getIds(), request.isLocal());
			}
			return ResponseEntityBuilder.ok();
		} catch (Exception e) {
			LOGGER.error("error when recover services", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/recover-all", method = RequestMethod.POST)
	public ResponseEntity<String> recoverAll() {
		try {
			downGradeService.recoverByService(serviceName);
			return ResponseEntityBuilder.ok();
		} catch (Exception e) {
			LOGGER.error("error when recover all services", e);
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
