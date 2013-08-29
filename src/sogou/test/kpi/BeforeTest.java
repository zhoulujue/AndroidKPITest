package sogou.test.kpi;

import sogou.test.agent.framework.command.RunScriptContext;
import sogou.test.agent.framework.root.CommonFunction;
import sogou.test.agent.framework.root.RebootTool;
import android.content.Context;
import android.util.Log;

public class BeforeTest extends Thread{
	
	private Context context;
	
	public BeforeTest(Context context){
		this.context=context;
	}
	
	@Override
	public void run(){
		int testNumber=KPITestApplication.getInstance().getTestNumber();

		InputMethodInfo info=new InsertInputMethodInfo(this.context.getContentResolver())
			.getInputMethodInfo(testNumber-1);
		if(info!=null){
			KPITestApplication.getInstance().unInstall(info.getPackageName());
			CommonFunction.sleepForWhile(3000);
		}
				
		info=new InsertInputMethodInfo(this.context.getContentResolver())
			.getInputMethodInfo(testNumber);
		if(info!=null){
			KPITestApplication.getInstance().initMonkeyPort();
			CommonFunction.sleepForWhile(3000);
			KPITestApplication.getInstance().unInstall(info.getPackageName());
			CommonFunction.sleepForWhile(3000);
			KPITestApplication.getInstance().install(CommonFunction.getSdDir()+SaveActivity.INSTALLER_PATH+
					info.getInstaller());
			CommonFunction.sleepForWhile(15000);
			new RunScriptContext(context).runScript(CommonFunction.getSdDir()+RunScriptContext.CHANGE_INPUT_METHOD
					+info.getChangeInputMethodScript());
			CommonFunction.sleepForWhile(3000);
			new RunScriptContext(context).runScript(CommonFunction.getSdDir()+SaveActivity.SETTING_PATH
					+info.getSettingScript());
			Log.i("kpi", "Before Thread finish!");
			
			new RebootTool(context).doAction(null, null);
		}
	}

}
