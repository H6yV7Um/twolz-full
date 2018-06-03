package com.twolz.qiyi.common.constant;

/**
 * @author liuwei
 * 车辆相关的常量
 */
public class CarConst extends SysConst{

	public static final String CAR_INFO = "crtinfo:";

	public static final String LAST_CAR_INFO = "lastCrtinfo:";

	/**
	 * 指令
	 * 1：开门，2：关门，3：寻车，4：开启座椅，5：获取车辆最新信息
	 */
	 public static final int DEVICE_OPEN = 1;
	 public static final int DEVICE_CLOSE = 2;
	 public static final int DEVICE_FIND = 3;
	 public static final int DEVICE_OPEN_SITCUSHION =4;
	 public static final int DEVICE_INFO=5;

	/**
	 * 满电压-ROKY
	 */
	public static Float VOLTAGE_MAX_ROKY=54f;
	
	/**
	 * 最低电压ver1-50 ROKY
	 */
	public static Float VOLTAGE_MIN_ROKY=42f;


	/**
	 * 车辆命令类型
	 */
	public enum CmdType {
	    OPEN_DOOR("01","开门"), 
	    LOCK("02","锁车"),
	    ALIGHT("03","寻车");
	 
		private String key;
	    
	    private String text;

	    private CmdType(String key,String text) {
	        this.key=key;
	        this.text=text;
	    }

		public String getKey() {
			return key;
		}


		public String getText() {
			return text;
		}
	}
	
	
	/**
	 * 车辆状态
	 * 1未发布，2在库待租，3已出租，4维修保养，5出售报废，6下线
	 */
	public enum CarState {
		NOT_PUBLISH(1,"未发布"), 
	    WAIT_RENT(2,"在库待租"),
	    HAD_RENT(3,"已出租"),
	    MAINTAIN(4,"维修保养"),
	    USELESS(5,"出售报废"),
	    OFFLINE(6,"下线");
	 
		private int key;
	    
	    private String text;
	    
	 
	    private CarState(int key,String text) {
	        this.key=key;
	        this.text=text;
	    }


		public int getKey() {
			return key;
		}


		public String getText() {
			return text;
		}
	 
	   
	}
	

	/**
	 * 车辆厂商枚举标记
	 */
	public enum CarFactory {
		ROKY("roky","锐祺智能科技");

		private String key;
	    
	    private String text;
	 
	    private CarFactory(String key,String text) {
	        this.key=key;
	        this.text=text;
	    }

		public String getKey() {
			return key;
		}

		public String getText() {
			return text;
		}
	}

	/**
	 * 车辆状态
	 */
	public enum CarStatusE {
		SF((short)0,"设防"),
		CF((short)1,"撤防"),
		ZC((short)2,"驻车"),
		FAULT((short)3,"骑行");

		private short key;

		private String text;

		private CarStatusE(short key,String text) {
			this.key=key;
			this.text=text;
		}

		public short getKey() {
			return key;
		}

		public String getText() {
			return text;
		}

	}
}
