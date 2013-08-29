package sogou.test.kpi.devices;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.app.ActivityManager;
import android.os.Debug.MemoryInfo;
import android.util.Log;

public class DeviceInfo {
	
	private static DeviceInfo _instance=null;
	private ActivityManager  activityManager;
	
	public DeviceInfo(ActivityManager am){
		this.activityManager=am;
	}
	
	public static DeviceInfo getInstance(ActivityManager am){
		if(_instance==null){
			_instance=new DeviceInfo(am);
		}
		return _instance;
	}
	
	public String getUss(){
		 MemoryInfo  mi = getAppMemoInfo();
		 return mi.getTotalPrivateDirty() + "KB";		
	}
	
	public String getRss(){
		 MemoryInfo  mi = getAppMemoInfo();
		 return mi.getTotalSharedDirty() + "KB";	
	}
	
	public String getPss(){
		MemoryInfo mi=getAppMemoInfo();
		return mi.getTotalPss()+"KB";
	}
	
	public MemoryInfo getAppMemoInfo(){		
		List<ActivityManager.RunningAppProcessInfo> mRunningProcess = activityManager.getRunningAppProcesses();
		int i = 1;
		int pid = 0;
		for (ActivityManager.RunningAppProcessInfo amProcess : mRunningProcess){
			if(amProcess.processName.equalsIgnoreCase("com.sohu.inputmethod.sogou")){
				Log.v("msg", "get sogou memoinfo");
				pid = amProcess.pid;
				break;
			}else if(amProcess.processName.equalsIgnoreCase("com.baidu.input")){
				Log.v("msg", "get baidu memoinfo");
				pid = amProcess.pid;
				break;				
			}else if(amProcess.processName.equalsIgnoreCase("com.tencent.qqpinyin.service")){
				Log.v("msg", "get qq memoinfo");
				pid = amProcess.pid;
				break;				
			}else if(amProcess.processName.equalsIgnoreCase("com.iflytek.inputmethod")){
				Log.v("msg", "get ifly memoinfo");
				pid = amProcess.pid;
				break;				
			}else if(amProcess.processName.equalsIgnoreCase("com.cootek.smartinputv5")){
				Log.v("msg", "get cootek memoinfo");
				pid = amProcess.pid;
				break;				
			}
			else if(amProcess.processName.equalsIgnoreCase("com.hit.wi")){
				Log.v("msg", "get wi memoinfo");
				pid = amProcess.pid;
				break;				
			}
			else if(amProcess.processName.equalsIgnoreCase("com.google.android.inputmethod.pinyin")){
				Log.v("msg", "get google memoinfo");
				pid = amProcess.pid;
				break;				
			}
			else if(amProcess.processName.equalsIgnoreCase("com.snda.input")){
				Log.v("msg", "get snda memoinfo");
				pid = amProcess.pid;
				break;				
			}
		}		
		int[] pids = new int[1];
		pids[0] = pid;
		MemoryInfo[] memoryInfoArray = activityManager.getProcessMemoryInfo(pids); 
		MemoryInfo pidMemoryInfo=memoryInfoArray[0];		
		return pidMemoryInfo;			
	}
	
	public float getCpuUsage() {
		ProcessBuilder cmd;
		String result = "";
		String content = "";
		try {
			String[] args = { "/system/bin/cat", "/proc/stat" };
			cmd = new ProcessBuilder(args);
			Process process = cmd.start();
			InputStream in = process.getInputStream();
			byte[] re = new byte[1024];
			while (in.read(re) != -1) {
				content = content + new String(re);
			}
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		result = content.split("\n")[0].trim().split("cpu")[1].trim();
		String[] temp = result.split(" ");
		// (User)5215 (nice)170 (System)3640 (Idle)5001 (iowait)63 (Irq)5 (sotfirq)18 0 0
		// CPU利用率   =   100   *（user   +   nice   +   system）/（user   +   nice   +   system   +   idle）
        float user = Float.valueOf(temp[0]);
        float nice = Float.valueOf(temp[1]);
        float system = Float.valueOf(temp[2]);
        float idle = Float.valueOf(temp[3]);
		return 100*(user + nice + system)/(user + nice + system + idle);
	}
}
