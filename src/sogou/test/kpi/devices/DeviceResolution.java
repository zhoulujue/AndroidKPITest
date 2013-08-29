package sogou.test.kpi.devices;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * @author chenjiongxin
 * 该类根据屏幕分辨率选择不同分辨率的脚本
 */
public class DeviceResolution {

	private static final int RESOLUTION_480_800_WIDTH=480;
	private static final int RESOLUTION_480_800_HEIGHT=800;
	private static final String RESOLUTION_480_800_FILENAME="input_480_800.py";
	private static final String RESOLUTION_DIR="/KPITestDir/Resolution/";
	private static DeviceResolution _instance=null;
	private Activity activity;
	private HashMap<String,Point> hashMap=new HashMap<String,Point>();
	
	public DeviceResolution(Activity activity){
		this.activity=activity;
		DisplayMetrics dm=getScreenResolution();
		if(dm==null) return;
		if(dm.heightPixels==RESOLUTION_480_800_HEIGHT&&
				dm.widthPixels==RESOLUTION_480_800_WIDTH){
			initHashMap(RESOLUTION_480_800_FILENAME);
		}
	}
	
	public static DeviceResolution getInstance(Activity activity){
		if(_instance==null){
			_instance=new DeviceResolution(activity);
		}
		return _instance;
	}
	
	public Point getPoint(String key){
		return hashMap.get(key);
	}
	
	private void initHashMap(String filename){
		File rootFile=SdCard.getSDPath();
		if(rootFile==null) return;
		String path = rootFile.toString()+RESOLUTION_DIR+filename;
		try{
			FileInputStream fileInputStream=new FileInputStream(path);
			DataInputStream dataInputStream=new DataInputStream(fileInputStream);
			BufferedReader bufferedReader=new BufferedReader(
					new InputStreamReader(dataInputStream));
			String sLine=null;
			while((sLine=bufferedReader.readLine())!=null){
				processLine(sLine);
			}
			bufferedReader.close();
			dataInputStream.close();
			fileInputStream.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private void processLine(String line){
		if(line==null) return;
		String array[]=line.split("=");
		String subArray[]=array[1].split(",");
		Point point=new Point();
		point.setX(Integer.parseInt(subArray[0]));
		point.setY(Integer.parseInt(subArray[1]));
		hashMap.put(array[0], point);
	}
	
	public DisplayMetrics getScreenResolution(){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager=activity.getWindowManager();
        if(windowManager==null) return null;
        
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm;      
	}
}
