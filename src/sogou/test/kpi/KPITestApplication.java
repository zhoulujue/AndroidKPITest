package sogou.test.kpi;

import java.io.File;

import sogou.test.agent.framework.root.InstallTool;
import sogou.test.agent.framework.root.MonkeyTool;
import sogou.test.agent.framework.root.ToolsConstants;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class KPITestApplication extends Application implements ToolsConstants{
	
	private static final String TEST_NUMBER="test_number";
	
	private static KPITestApplication instance=null;
	
	public static KPITestApplication getInstance(){
		return instance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance=this;
		Log.i("kpi", "app oncreate");
	}
	
	public void setTestNumber(int number){
		SharedPreferences settingSharedPreferences = getSharedPreferences(
				TEST_NUMBER, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settingSharedPreferences.edit();
		editor.putInt(TEST_NUMBER, number);
		editor.commit();
	}
	
	public int getTestNumber(){
		SharedPreferences settingSharedPreferences = getSharedPreferences(
				TEST_NUMBER, Context.MODE_PRIVATE);
		return settingSharedPreferences.getInt(TEST_NUMBER, -1);
	}
	
	private boolean isIllegal(String str){
		return str==null||str.length()==0;
	}
	
	public boolean unInstall(String packageName){
		if(isIllegal(packageName)) return false;
		return new InstallTool(this).doAction(UNINSTALL, packageName);
	}
	
	public boolean install(String path){
		if(isIllegal(path)) return false;
		File file=new File(path);
		if(!file.exists()) return false;
		return new InstallTool(this).doAction(INSTALL, path);
	}
	
	public boolean initMonkeyPort(){
		return new MonkeyTool(this).doAction(null, null);
	}

}
