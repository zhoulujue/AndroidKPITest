package sogou.test.kpi.database;

import android.net.Uri;
import android.provider.BaseColumns;

public interface Constants extends BaseColumns {
	
	public static final String TB_INPUT_METHOD_INFO = "tb_input_method_info";
	public static final String IMI_INPUT_METHOD_NAME = "imi_input_method_name";
	public static final String IMI_KEYBOARD_CHANGE_SCRIPT= "imi_keyboard_change_script";
	public static final String IMI_INPUT_METHOD_INSTALLER= "imi_input_method_installer";
	public static final String IMI_INPUT_METHOD_PACKAGE= "imi_input_method_package";
	public static final String IMI_CHANGE_INPUT_METHOD_SCRIPT= "imi_change_input_method_script";
	public static final String IMI_INPUT_METHOD_SETTING_SCRIPT= "imi_input_method_setting_script";
	
	public static final String ANTHORITY = "sogou.test.kpi";
    public static final Uri CONTENT_URI_TB_IMI = Uri.parse("content://"+ANTHORITY+"/"+TB_INPUT_METHOD_INFO);
    
    public static final int START_ACTIVITY_FOR_RESULT_SAVE=1;
    public static final int START_ACTIVITY_FOR_RESULT_DELETE=2;
    
    public static final String RIGHT_VALUE="ok";
    public static final String ERROR_VALUE="error"; 
}
