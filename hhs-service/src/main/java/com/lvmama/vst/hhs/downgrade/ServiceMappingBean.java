/**
 * 
 */
package com.lvmama.vst.hhs.downgrade;

import java.io.Serializable;

/**
 * @author fengyonggang
 *
 */
public class ServiceMappingBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String [] paths;
	private String [] methods;
	private String [] produces;
	private String [] consumes;
	private String beanName;
	private String methodName;
	private String disabled;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[] getPaths() {
		return paths;
	}
	public void setPaths(String[] paths) {
		this.paths = paths;
	}
	public String[] getMethods() {
		return methods;
	}
	public void setMethods(String[] methods) {
		this.methods = methods;
	}
	public String[] getProduces() {
		return produces;
	}
	public void setProduces(String[] produces) {
		this.produces = produces;
	}
	public String[] getConsumes() {
		return consumes;
	}
	public void setConsumes(String[] consumes) {
		this.consumes = consumes;
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
}
