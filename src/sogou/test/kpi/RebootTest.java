package sogou.test.kpi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import sogou.test.agent.framework.root.CommonFunction;
import sogou.test.kpi.database.Constants;

public class RebootTest extends Thread implements Constants{
	
	private Context context;
	
	public RebootTest(Context context){
		this.context=context;
	}
	
	@Override
	public void run(){
		CommonFunction.sleepForWhile(180000);
		int testNumber=KPITestApplication.getInstance().getTestNumber();
		InputMethodInfo info=new InsertInputMethodInfo(context.getContentResolver())
				.getInputMethodInfo(testNumber);
    	if(info!=null){
    		Intent intent=new Intent(context,TestActivity.class);
    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS); 
    		Bundle bundle=new Bundle();
			bundle.putInt(_ID, info.getId());
			bundle.putString(IMI_INPUT_METHOD_NAME, info.getName());
			bundle.putString(IMI_KEYBOARD_CHANGE_SCRIPT, info.getScript());
			intent.putExtras(bundle);
    		context.startActivity(intent);
    		
    		//KPITestApplication.getInstance().setTestNumber(testNumber+1);
    		
    	}
		Log.i("kpi", "reboot!");
	}

}
