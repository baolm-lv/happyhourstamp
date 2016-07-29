package com.lvmama.vst.hhs.model.common;

public class Constant {
    
    public static enum PAT_TYPE {
        PART("定金"),LAST("尾款"), FULL("全额");

        private String cnName;

        PAT_TYPE(String name) {
            this.cnName = name;
        }

        public String getCode() {
            return this.name();
        }

        public String getCnName() {
            return this.cnName;
        }

        public static String getCnName(String code) {
            for (PAT_TYPE item : PAT_TYPE.values()) {
                if (item.getCode().equals(code)) {
                    return item.getCnName();
                }
            }
            return code;
        }

        @Override
        public String toString() {
            return this.name();
        }
    }

    public static enum OrderSubType {
    	STAMP("券订单"), STAMP_PROD("券兑换的商品订单");
    	
    	private String desc;
    	private OrderSubType(String desc)  {
    		this.desc = desc;
    	}
    	public String getDesc() {
    		return this.desc;
    	}
    }
    
    public static enum GroupType {
    	HOTEL, LINE, LINE_TICKET, TRANSPORT
    }
    
    public static enum StampPhase {
    	SALE, REDEEM, ALL
    }

    /**
     * 券可售状态
     */
	public static enum ActivityStatus {
		Y("可售"), N("不可售");
		private String cnName;

		private ActivityStatus(String cnName) {
			this.cnName = cnName;
		}

		public String getCnName() {
			return this.cnName;
		}
	}
    
    /**
     * 券操作类型
     */
	public static enum OperateType {
		CREATE, 
		PAYED, 
		USE, 
		UNBIND, 
		UNBIND_AND_REFUND, 
		REFUND_APPLY, 
		REFUND_SUCCESS, 
		REFUND_FAIL, 
		INVALID, 
		ROLLBACK_INVALID,
		EXPIRED,
		REFUND_CANCEL,
		UNBIND_CANCEL;
	}
	
	/**
	 * 券状态
	 */
	public static enum StampStatus {
		BALANCED_DUE, // 待支付
		UNUSE, // 未使用
		USED, // 已使用
		OUT_DATED, // 过期
		INVALID, // 失效
		PAID_INVALID, // 已支付失效
		REFUNDING, // 退款中
		REFUNDED, // 已退款
		REFUND_FAIL, // 退款失败
		UNUSE_LOCK; //临时状态（解绑并退券）
	}
	
	/**
	 * 退券状态
	 */
	public static enum RefundStatus {
		REFUNDING, // 退款中
		REFUNDED, // 已退款
		REFUND_FAIL, // 退款失败
		CANCEL; //临时状态（解绑并退券）
	}
	
	public static enum ORDER_STATUS {
		NORMAL("正常"), 
		COMPLETE("已完成"), 
		CANCEL("已取消");

		private final String cnName;

		ORDER_STATUS(String name) {
			this.cnName = name;
		}

		public static String getCnName(String code) {
			for (ORDER_STATUS item : ORDER_STATUS.values()) {
				if (item.name().equals(code)) {
					return item.cnName;
				}
			}
			return code;
		}

		public String getCnName() {
			return this.cnName;
		}
	}
	
	/**
     * 券解綁状态
     */
    public static enum StampUnbindStatus {
        INUSE, // 使用中
        UNBIND; // 已解綁
    }
    
	public static enum SaleType {
		PEOPLE, //按人卖 
		COPIES	//按份卖
	}
	
	public static enum HotelValidateMessage {
		NOT_ONSALE, //商品不可售
		OUT_OF_STOCK//库存不足
	}
}
