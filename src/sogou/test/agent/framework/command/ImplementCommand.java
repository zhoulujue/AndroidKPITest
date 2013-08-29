package sogou.test.agent.framework.command;

import java.util.List;

import sogou.test.kpi.communication.TalkToMonkey;
import sogou.test.kpi.database.Constants;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class ImplementCommand implements Constants{
	
	private TalkToMonkey talkToMonkey;
	private Context context;
	
	public ImplementCommand(Context context){
		talkToMonkey=TalkToMonkey.getInstance(context);
		talkToMonkey.close();
		talkToMonkey.createMonkeyClient();
		this.context=context;
	}
	
	/**执行休眠事件
	 * @param time
	 */
	public String sleepForWhile(int time){
		try{
			Thread.sleep(time);
			return RIGHT_VALUE;
		}
		catch(InterruptedException e){
			e.printStackTrace();
			return ERROR_VALUE;
		}
	}
	
	public String touchDownAction(String x,String y){
		return talkToMonkey.touchDown(x, y);
	}
	
	public String touchUpAction(String x,String y){
		return talkToMonkey.touchUp(x, y);
	}
	
	public String touchDownUpAction(String x,String y){
		String result1=talkToMonkey.touchDown(x, y);
		sleepForWhile(10);
		String result2=talkToMonkey.touchUp(x, y);
		if(result1.equalsIgnoreCase(RIGHT_VALUE)&&result2.equalsIgnoreCase(RIGHT_VALUE))
			return RIGHT_VALUE;
		return ERROR_VALUE;
	}
	
	public String keyDownAction(String value){
		return talkToMonkey.keyDown(value);
	}
	
	public String keyUpAction(String value){
		return talkToMonkey.keyUp(value);
	}
	
	public String keyDownUpAction(String value){
		String result1=talkToMonkey.keyDown(value);
		sleepForWhile(10);
		String result2=talkToMonkey.keyUp(value);
		if(result1.equalsIgnoreCase(RIGHT_VALUE)&&result2.equalsIgnoreCase(RIGHT_VALUE))
			return RIGHT_VALUE;
		return ERROR_VALUE;
	}
	
	public String moveAction(String x,String y){
		return talkToMonkey.moveTo(x, y);
	}
	
	public String openApplication(String packageName){
		if(packageName==null) return ERROR_VALUE;
		PackageManager packageManager = context.getPackageManager(); 
		List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);

		boolean isPresence = false;
		for (int i = 0; i < packageInfoList.size(); i++) {
			PackageInfo pak = (PackageInfo) packageInfoList.get(i);
			if (pak.applicationInfo.packageName.equals(packageName)) {
		         isPresence = true;
		         break ; 
			}
		}        
		if(isPresence){
			Intent intent =packageManager.getLaunchIntentForPackage(packageName);
			context.startActivity(intent);
			return RIGHT_VALUE;
			
		}
		return ERROR_VALUE;
	}
	
	public String startRecordSysInfo(){
		//Intent intent=new Intent(context,sogou.test.agent.framework.sysinfo.RecordSystemInfoService.class);
		//context.startService(intent);
		return RIGHT_VALUE;
	}
	
	public String stopRecordSysInfo(){
		//Intent intent=new Intent(context,sogou.test.agent.framework.sysinfo.RecordSystemInfoService.class);
		//context.stopService(intent);
		return RIGHT_VALUE;
	}
	
	public String testScript(){
		//context.startInstrumentation(new ComponentName(context,
		//		RunTestScript.class), null, null);
		return RIGHT_VALUE;
	}

}
