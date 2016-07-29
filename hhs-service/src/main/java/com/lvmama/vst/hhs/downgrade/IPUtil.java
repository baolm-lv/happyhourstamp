/**
 * 
 */
package com.lvmama.vst.hhs.downgrade;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author fengyonggang
 *
 */
public class IPUtil {

	private static String IP_ADDRESS;
	
	static {
		// shoud be passed by command line
		IP_ADDRESS = System.getProperty("HOST_NAME");
		if (null == IP_ADDRESS || IP_ADDRESS.isEmpty()) {
			try {
				IP_ADDRESS = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
			}
		}
	}
	
	public static String getIpAddress() {
		return IP_ADDRESS;
	}
}
