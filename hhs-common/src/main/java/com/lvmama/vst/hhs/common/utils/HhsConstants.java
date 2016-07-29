/**
 * 
 */
package com.lvmama.vst.hhs.common.utils;

/**
 * @author fengyonggang
 *
 */
public interface HhsConstants {

	String ON_REDEEM_FLAG = "2";
	String ON_SALE_FLAG = "1";
	
	String AUDLT = "AUDLT";
	String CHILD = "CHILD";
	
	String BRANCH_FLAG_PRIMARY = "PRIMARY";
	String BRANCH_FLAG_PRIMARY_AUDLT = BRANCH_FLAG_PRIMARY + "_" + AUDLT;
	String BRANCH_FLAG_PRIMARY_CHILD = BRANCH_FLAG_PRIMARY + "_" + CHILD;
	String BRANCH_FLAG_GROUP_PREFIX = "GROUP_";
	String BRANCH_FLAG_CHANGE_PREFIX = "CHANGE_";
	
	String VALID = "VALID";
	String INVALID = "INVALID";
	
	String LINE = "LINE";
	String LINE_TICKET = "LINE_TICKET";
	String HOTEL = "HOTEL";
	String TRANSPORT = "TRANSPORT";
	String CHANGE = "CHANGE";
	
	Long CONTRACT_ID = 6196L;
	Long SUPPLIER_ID = 12141L;
}
