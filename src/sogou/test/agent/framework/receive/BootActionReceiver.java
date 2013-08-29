package sogou.test.agent.framework.receive;

import sogou.test.agent.framework.root.CommonFunction;
import sogou.test.kpi.KPITestApplication;
import sogou.test.kpi.RebootTest;
import sogou.test.kpi.database.Constants;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootActionReceiver extends BroadcastReceiver implements Constants{

	@Override
	public void onReceive(Context context, Intent intent) {
		final Context myContext=context;
		final int testNumber=KPITestApplication.getInstance().getTestNumber();
		
		if(testNumber>=0){
			CommonFunction.disLock(myContext);
			new RebootTest(context).start();
			CommonFunction.disLock(myContext);
		}
		
	}

}
