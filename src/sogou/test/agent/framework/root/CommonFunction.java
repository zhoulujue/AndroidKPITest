package sogou.test.agent.framework.root;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.widget.Toast;

public class CommonFunction{
	
	public static byte[] compressBmp(int piex[], int width, int height,
			int quality) {
		Bitmap bitmap = Bitmap.createBitmap(piex, width, height,
				Bitmap.Config.RGB_565);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, quality,
				byteArrayOutputStream);
		bitmap.recycle();
		bitmap = null;
		byte[] result = byteArrayOutputStream.toByteArray();
		try {
			byteArrayOutputStream.close();
			byteArrayOutputStream = null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	public static byte[] intToByte(int temp) {
		byte[] array = new byte[4];
		array[0] = (byte) (temp >> 24 & 0xFF);
		array[1] = (byte) (temp >> 16 & 0xFF);
		array[2] = (byte) (temp >> 8 & 0xFF);
		array[3] = (byte) (temp >> 0 & 0xFF);
		return array;
	}

	public static boolean isNumber(String str) {
		if(str==null) return false;
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	public static File getSdDir() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();
		}
		return sdDir;
	}

	public static boolean isServiceRunning(Context context, String serviceName) {
		if (serviceName == null)
			return false;
		ActivityManager myManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
				.getRunningServices(30);
		for (int i = 0; i < runningService.size(); i++) {
			// TestLog.info(CONTROL_LABEL,runningService.get(i).service.getClassName().toString());
			if (runningService.get(i).service.getClassName().toString()
					.equals(serviceName)) {
				return true;
			}
		}
		return false;
	}

	public static void writeLogFiles(String testFileName, String log) {
		File sdRoot = getSdDir();
		if (sdRoot == null)
			return;
		String testSourcePath = sdRoot + testFileName;
		try {
			writeTestLog(testSourcePath, log);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeTestLog(String testFilePath, String log)
			throws IOException {
		File file = new File(testFilePath);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		OutputStreamWriter osw;
		try {
			osw = new OutputStreamWriter(new FileOutputStream(file, true),
					"UTF-8");
			osw.write(log + "\r\n");
			osw.flush();
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressLint("SimpleDateFormat")
	public static String getCurrentTime() {
		SimpleDateFormat timeFormat = new SimpleDateFormat(
				"yyyy-MM-dd_HH-mm-ss");
		return timeFormat.format(new Date());
	}

	@SuppressLint("SimpleDateFormat")
	public static String getCurrentDate() {
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
		return timeFormat.format(new Date());
	}

	public static String getTopActivity(Context myContext) {
		ActivityManager manager = (ActivityManager) myContext
				.getSystemService(android.content.Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
		String cmpNameTemp = null;
		if (null != runningTaskInfos) {
			cmpNameTemp = (runningTaskInfos.get(0).topActivity
					.flattenToShortString());
			return cmpNameTemp;
		}
		return null;
	}

	public static boolean portIsUsing(int port) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException ex) {
			return true;
		}
		try {
			serverSocket.close();
		} catch (IOException ex) {
		}
		return false;
	}

	public static boolean ipIsSucc(String address, int port) {
		boolean result = true;
		InetSocketAddress inetSocketAddress = new InetSocketAddress(address,
				port);
		Socket socket = new Socket();
		try {
			socket.connect(inetSocketAddress);
			socket.close();
		} catch (IOException e) {
			result = false;
			e.printStackTrace();
			return result;
		}
		return result;

	}

	public static boolean getNetWorkState(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		//TestLog.info("chen", "type="+networkInfo.getType()+",wifi type="+ConnectivityManager.TYPE_WIFI+
				//",ip="+networkInfo.getDetailedState());
		
		if (networkInfo != null && networkInfo.isConnected()&&networkInfo.getType()==ConnectivityManager.TYPE_WIFI) {
			return true;
		} else {
			return false;
		}
	}

	//该函数有点问题，不知道是哪个网络的ip地址
	public static String getIp(Context context) {
		WifiManager wifimanage=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiinfo= wifimanage.getConnectionInfo();
		return intToIp(wifiinfo.getIpAddress()); 
	}
	
	public static String intToIp(int i)  {
		return (i&0xFF)+"."+((i>>8)&0xFF)+"." +((i>>16)&0xFF)+"."+((i>> 24)&0xFF);
	}  
	
	
	
	public static boolean getScreenDir(Context context){
		boolean tag=context.getResources().getConfiguration().orientation == 
			Configuration.ORIENTATION_PORTRAIT?true:false;
		return tag;
	}

	
	public static void disLock(Context context){
		KeyguardManager keyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
		KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("");
		keyguardLock.disableKeyguard();
		
	}
	
	public static void sleepForWhile(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
