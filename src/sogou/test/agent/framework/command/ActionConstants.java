package sogou.test.agent.framework.command;

import sogou.test.kpi.database.Constants;


public interface ActionConstants extends Constants{
	
	public static final String TOUCH_DOWN_ACTION="down";
	public static final String TOUCH_UP_ACTION="up";
	public static final String DRAG_ACTION="drag";
	public static final String KEY_DOWN_ACTION="keydown";
	public static final String KEY_UP_ACTION="keyup";
	public static final String SLEEP_ACTION="sleep";
	public static final String SCRIPT_ACTION="script";
	public static final String STOP_SCRIPT_ACTION="stopscript";
	public static final String OPEN_IDENTIFY_ACTION="openidentify";
	public static final String TEST_SCRIPT_ACTION="testscript";
	public static final String REPEAT_COMMAND="repeatcmd";
	public static final String DOWN_AND_UP_ACTION="down_and_up";
	public static final String KEY_DOWN_AND_UP_ACTION="key_down_and_up";
	
	
	public static final String LUNCH_APP_ACTION="lunchapp";
	public static final String START_APP_TIME="startapptime";
	public static final String RECORD_SYSINFO_ACTION="sysinfo";
	
	public static final String START_RECORD_SYSTEM_INFO="startRecordSysInfo";
	public static final String STOP_RECORD_SYSTEM_INFO="stopRecordSysInfo";
	
	public static final String WRITE_TEST_NUMBER="testnum";
	
	public static final String GET_DEVICE_INFO="getdevice";
	public static final String SEND_MESSAGE="sendMessage";
	public static final String GET_SYS_INFO="getSysInfo";
	
	public static final String CLOSE_TEST_ACTION="success";
	
	public static final String REBOOT_ACTION="reboot";
	public static final String INIT_MONKEY_PORT="monkey";

}
