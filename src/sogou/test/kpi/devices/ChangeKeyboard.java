package sogou.test.kpi.devices;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenjiongxin
 * 该类在kpi测试中用来切换各个键盘
 */
public class ChangeKeyboard {
	
	private static final String CHANGE_KEYBOARD_PATH="/KPITestDir/ChangeKeyboard/";
	private static final String NINE_PINYIN="9_PINYIN";
	private static final String NINE_ENGLISH="9_ENGLISH";
	private static final String TWSIX_PINYIN="26_PINYIN";
	private static final String TWSIX_ENGLISH="26_ENGLISH";
	private List<Point> changeList;
	private String filename;
	private static ChangeKeyboard _instance=null;
	
	public ChangeKeyboard(String filename){
		this.filename=filename;
	}
	
	public void setFileName(String name){
		this.filename=name;
	}
	
	public static ChangeKeyboard getInstance(String filename){
		if(_instance==null){
			_instance=new ChangeKeyboard(filename);
		}
		return _instance;
	}
	
	public List<Point> getNinePinyin(){
		return getPointList(NINE_PINYIN);
	}
	
	public List<Point> getNineEnglish(){
		return getPointList(NINE_ENGLISH);
	}
	
	public List<Point> getTwsixPinyin(){
		return getPointList(TWSIX_PINYIN);
	}
	
	public List<Point> getTwsixEnglish(){
		return getPointList(TWSIX_ENGLISH);
	}
	
	private List<Point> getPointList(String tag){
		if(filename==null) return null;
		File sdRoot=SdCard.getSDPath();
		if(sdRoot==null) return null;
		changeList=new ArrayList<Point>();
		try{
			String path=sdRoot.toString()+CHANGE_KEYBOARD_PATH+filename;
			File file=new File(path);
			if(!file.exists()) return null;
			FileInputStream fileInputStream=new FileInputStream(path);
			DataInputStream dataInputStream=new DataInputStream(fileInputStream);
			BufferedReader bufferedReader=new BufferedReader(
					new InputStreamReader(dataInputStream));
			String sLine=null;
			while(true){
				sLine=bufferedReader.readLine();
				if(sLine.equals(tag)){
					while((sLine=bufferedReader.readLine())!=null){
						if(sLine.length()==0) break;
						Point point=new Point();
						String array[]=sLine.split(",");
						if(array.length<2) continue;
						point.setX(Integer.parseInt(array[0]));
						point.setY(Integer.parseInt(array[1]));
						changeList.add(point);
					}
					break;
				}
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
		return changeList;
	}

}
