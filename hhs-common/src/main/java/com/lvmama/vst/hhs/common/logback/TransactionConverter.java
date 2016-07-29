/**
 * 
 */
package com.lvmama.vst.hhs.common.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import com.lvmama.vst.hhs.common.utils.ServiceTransactionHelper;

/**
 * @author fengyonggang
 *
 */
public class TransactionConverter extends ClassicConverter {

	private static final String TRANSACTIONID = "TransactionId";
	
	@Override
	public String convert(ILoggingEvent event) {
		String transactionId = ServiceTransactionHelper.getTransactionId();
		if(transactionId == null) 
			transactionId = TRANSACTIONID;
		return transactionId;
	}
	
}
