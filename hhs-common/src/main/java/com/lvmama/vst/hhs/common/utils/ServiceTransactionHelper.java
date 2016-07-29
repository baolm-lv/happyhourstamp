/**
 * 
 */
package com.lvmama.vst.hhs.common.utils;

/**
 * @author fengyonggang
 *
 */
public class ServiceTransactionHelper {

	private final static ThreadLocal<String> threadLocal = new ThreadLocal<String>();
	
	public static void setTransactionId(String transactionId) {
		threadLocal.set(transactionId);
	}
	
	public static String getTransactionId() {
		return threadLocal.get();
	}
}
