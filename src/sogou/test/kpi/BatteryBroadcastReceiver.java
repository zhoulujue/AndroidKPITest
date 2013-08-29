package sogou.test.kpi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class BatteryBroadcastReceiver extends BroadcastReceiver {

	private static BatteryBroadcastReceiver _instance=null;
	
	public BatteryBroadcastReceiver () { 
	}
	
	public static BatteryBroadcastReceiver getInstance(){
		if(_instance==null){
			_instance=new BatteryBroadcastReceiver();
		}
		return _instance;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		int level = (int) (intent
				.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
				/ (float) intent.getIntExtra(BatteryManager.EXTRA_SCALE,100) * 100);
		TestActivity.batteryScale = level + "%";
		TestActivity.batteryTemprature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 1)/10.0 +"℃";

	}	
}