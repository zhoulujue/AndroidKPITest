package sogou.test.kpi;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import sogou.test.agent.framework.root.CommonFunction;
import sogou.test.kpi.database.Constants;
import sogou.test.kpi.devices.DeviceInfo;
import sogou.test.kpi.devices.DeviceResolution;
import sogou.test.kpi.devices.ExecuteContext;
import sogou.test.kpi.devices.Point;
import sogou.test.kpi.devices.RunScript;
import sogou.test.kpi.devices.SdCard;
import sougou.test.monkeyagent.MonkeyAgent;
import sougou.test.monkeyagent.MonkeyMotionEventAgent;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends Activity implements OnClickListener,Constants{
	
	private static final int SET_EDITTEXT_WITH_BLACK=0;
	private static final int SET_TEXTVIEW_WITH_RESULT=1;
	private static final String NINE_SCRIPT_PATH="/KPITestDir/Source/9key.txt";
	private static final String TWSIX_SCRIPT_PATH="/KPITestDir/Source/26key.txt";
	private static final String KPI_RESULT_PATH="/KPITestDir/TestResult/kpi_result.txt";
	private static final String STATE_RESULT_PATH="/KPITestDir/TestResult/state_result.txt";
	private static final int CHANGE_SLEEP_TIME=5000;
	private static final String NINE_SPACE="space9";
	private static final String TWSIX_SPACE="space26";
	private static final int ENGLISH_MODEL=1;
	private static final int CHINESE_MODEL=2;
	private Button title_btn;
	private TextView title_tv;
	private ImageView title_iv;
	//private TalkToMonkey talkToMonkey;
	private MonkeyAgent ma;
	private EditText etMessage;
	private boolean ifSuccessToMonkey=false;
	private DeviceResolution deviceResolution;
	private String word;
	private String yinjie;
	private String quanzhi="0";
	private long startTime,endTime;
	private double keyTime;
	private TextView tvResult;
	private int countTotal = 0;
	private int  pass = 0;
	private int fail = 0;
	
	//电池相关数据：
	public static String batteryScale = "";
	public static String batteryTemprature = "";
	
	private double everyChangeKeyTime=0;
	private double fourChangeKeyTime[];
	private int fourChangeWordNum[];
	
	private BatteryBroadcastReceiver myBroadcastReciver = BatteryBroadcastReceiver.getInstance();
	
	private DeviceInfo deviceInfo;
	private static boolean runTag=true;
	private InputMethodInfo inputMethodInfo;
	private Point spacePoint;
	private static boolean editChange=false;
	private static int nowModel;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.test_layout);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.i_title);
        TestActivity.runTag=true;
        
        Bundle bundle=this.getIntent().getExtras();
        inputMethodInfo=new InputMethodInfo();
        if(bundle!=null){      	
        	inputMethodInfo.setId(bundle.getInt(_ID));
        	inputMethodInfo.setName(bundle.getString(IMI_INPUT_METHOD_NAME));
        	inputMethodInfo.setScript(bundle.getString(IMI_KEYBOARD_CHANGE_SCRIPT));
        }
        
        findWidget();
        initWidget();
        
        RunScript runScript=RunScript.getInstance(this);
        runScript.startRunScript();
        
        //new Thread(){
		//	public void run(){
		//		talkToMonkey = TalkToMonkey.getInstance();
		//		ifSuccessToMonkey=true;
		//	}
		//}.start();
        ma=new MonkeyAgent(2,0);
        ma.startMonkey();
        ifSuccessToMonkey=true;
		
		
		new MyThread(handlerMessage).start();
		//deviceResolution=DeviceResolution.getInstance(this);
		deviceResolution=new DeviceResolution(this);
		etMessage.requestFocus();		
		registerReceiver(myBroadcastReciver, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));
		
		deviceInfo=DeviceInfo.getInstance((ActivityManager)this.getSystemService(ACTIVITY_SERVICE));
		
		fourChangeKeyTime=new double[4];
		fourChangeWordNum=new int[4];
		
	}
	
	private void findWidget(){
		title_btn=(Button)this.findViewById(R.id.title_right_btn);
        title_tv=(TextView)this.findViewById(R.id.title);
        title_iv=(ImageView)this.findViewById(R.id.title_img);
        etMessage=(EditText)this.findViewById(R.id.et_test_message);
        tvResult=(TextView)this.findViewById(R.id.result_area_id);
        TextWatcher mTextWatcher = new TextWatcher() {
        	int sysCalCount = 0;
        	String before = "";
        	String on = "";
        	//上屏词
        	String text = "";
        	String result="";
			@Override
			public void afterTextChanged(Editable arg0) {

			}			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				before = arg0.toString();				
				//System.out.println("before="+before+"+++===");
			}
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				on = arg0.toString();	
				//System.out.println("on="+on+"+++===");
				//if(on.equals(" ")) etMessage.setText("");
				//if(on.endsWith(" ")) System.out.println("=====");
				//else System.out.println("!!!!!!");
				if(editChange&&on.endsWith(" ")){
					endTime=System.currentTimeMillis();
					if(nowModel==ENGLISH_MODEL)
						keyTime=(double)(endTime-startTime)/(double)(yinjie.length()+1);
					else if(nowModel==CHINESE_MODEL)
						keyTime=(double)(endTime-startTime)/(double)(yinjie.length()+2);
					text = etMessage.getText().toString().trim();
					//计算首先按键数据
					if (text.equals(word.trim())) {		
						countTotal = countTotal + 1;
						pass = pass + 1;
						result = "Pass";
					} else {
						countTotal = countTotal + 1;
						fail = fail + 1;
						result = "Err";
					}
					everyChangeKeyTime+=(double)keyTime;
					String kpiLog = getCurrentTime().toString() + " >> "
							+ "Total:" + countTotal + ", " 
							+ "Pass:" + pass+ ", " + "Fail:" + fail + ", " 
							+ "keyTime:"+ (float) keyTime + "ms" + " >> " 
							+ yinjie
							+ " >> weight:" + quanzhi + ">> Expect:"
							+ word + ", Real:" + text
							+ ", Result:<" + result + ">";
					tvResult.setText(kpiLog);
	    			writeLogFiles(KPI_RESULT_PATH,kpiLog);
	    			
	    			String uss = deviceInfo.getUss();
	                String rss = deviceInfo.getRss();        
	                String pss=deviceInfo.getPss();
					String cpuUsage = deviceInfo.getCpuUsage();
					String stateLog = "Scale:" + TestActivity.batteryScale  + " >> "  
	                	+ "Temprature:" + TestActivity.batteryTemprature + " >> "
	                	+ "Rss:" + rss + " >> "
	                	+ "Pss:" + pss + " >> "
	                	+ "Uss:" + uss  + " >> " 
	                	+ "Cpu:" + cpuUsage + "%";	
					
					writeLogFiles(STATE_RESULT_PATH,stateLog);			
					editChange=false;
					//etMessage.setText("");
				}
			}
        };
        etMessage.addTextChangedListener(mTextWatcher);
	}
	
	private void initWidget(){
		title_btn.setText("返回");
		title_btn.setOnClickListener(this);
		title_tv.setText(inputMethodInfo.getName()+"测试界面");
		title_iv.setImageResource(R.drawable.set_date_32);
	}
	
	@Override
	public void finish(){
		super.finish();
		TestActivity.runTag=false;
		unregisterReceiver(myBroadcastReciver);
		overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_right);
	}

	@Override
	public void onClick(View view) {
		if(view==title_btn){
			finish();
		}
		
	}
	
	@Override 
	public void onRestart(){
		super.onRestart();
		runTag=true;
	}
	
	class MyThread extends Thread{
		private Handler handler;
		public MyThread(Handler handler){
			this.handler=handler;
		}
		public void run(){
			while(!ifSuccessToMonkey){
				sleepForMoment(100);
			}
			sleepForMoment(CHANGE_SLEEP_TIME);
			int location[]=new int[2];
			etMessage.getLocationOnScreen(location);
			//talkToMonkey.touch(""+(location[0]+etMessage.getWidth()/2), ""+(location[1]+etMessage.getHeight()/2));
			MonkeyMotionEventAgent event=new MonkeyMotionEventAgent(
					KeyEvent.ACTION_DOWN,(location[0]+etMessage.getWidth()/2),(location[1]+etMessage.getHeight()/2));
			ma.runCmd(event);
			event=new MonkeyMotionEventAgent(
					KeyEvent.ACTION_UP,(location[0]+etMessage.getWidth()/2),(location[1]+etMessage.getHeight()/2));
			ma.runCmd(event);
			sleepForMoment(CHANGE_SLEEP_TIME);
			Looper.prepare();
			//ExecuteContext executeContext=new ExecuteContext(talkToMonkey,inputMethodInfo.getScript());
			ExecuteContext executeContext=new ExecuteContext(ma,inputMethodInfo.getScript());
			if(TestActivity.runTag&&executeContext.changeToNinePinyin()){
				nowModel=CHINESE_MODEL;
				spacePoint=deviceResolution.getPoint(NINE_SPACE);
				executeScript(NINE_SCRIPT_PATH,handler);
				fourChangeWordNum[0]=countTotal;
				if(fourChangeWordNum[0]!=0){
					fourChangeKeyTime[0]=everyChangeKeyTime/(double)fourChangeWordNum[0];
				}
				else
					fourChangeKeyTime[0]=0;
				everyChangeKeyTime=0;
				sleepForMoment(CHANGE_SLEEP_TIME);
			}
			
			if(TestActivity.runTag&&executeContext.changeToNineEnglish()){
				nowModel=ENGLISH_MODEL;
				spacePoint=deviceResolution.getPoint(NINE_SPACE);
				executeScript(NINE_SCRIPT_PATH,handler);
				fourChangeWordNum[1]=countTotal-fourChangeWordNum[0];
				if(fourChangeWordNum[1]!=0){
					fourChangeKeyTime[1]=everyChangeKeyTime/(double)fourChangeWordNum[1];
				}
				else
					fourChangeKeyTime[1]=0;
				everyChangeKeyTime=0;
				sleepForMoment(CHANGE_SLEEP_TIME);
			}
			
			if(TestActivity.runTag&&executeContext.changeToTwsixPinyin()){
				nowModel=CHINESE_MODEL;
				spacePoint=deviceResolution.getPoint(TWSIX_SPACE);
				executeScript(TWSIX_SCRIPT_PATH,handler);
				fourChangeWordNum[2]=countTotal-fourChangeWordNum[0]-fourChangeWordNum[1];
				if(fourChangeWordNum[2]!=0){
					fourChangeKeyTime[2]=everyChangeKeyTime/(double)fourChangeWordNum[2];
				}
				else
					fourChangeKeyTime[2]=0;
				everyChangeKeyTime=0;
				sleepForMoment(CHANGE_SLEEP_TIME);
			}
			
			if(TestActivity.runTag&&executeContext.changeToTwsixEnglish()){
				nowModel=ENGLISH_MODEL;
				spacePoint=deviceResolution.getPoint(TWSIX_SPACE);
				executeScript(TWSIX_SCRIPT_PATH,handler);
				fourChangeWordNum[3]=countTotal-fourChangeWordNum[0]-fourChangeWordNum[1]-fourChangeWordNum[2];
				if(fourChangeWordNum[3]!=0){
					fourChangeKeyTime[3]=everyChangeKeyTime/(double)fourChangeWordNum[3];
				}
				else
					fourChangeKeyTime[3]=0;
				everyChangeKeyTime=0;
				
				writeLogFiles(KPI_RESULT_PATH,getCurrentTime().toString()+"\t九键拼音平均按键时间\t"+fourChangeKeyTime[0]+"\n");
				writeLogFiles(KPI_RESULT_PATH,getCurrentTime().toString()+"\t九键英文平均按键时间\t"+fourChangeKeyTime[1]+"\n");
				writeLogFiles(KPI_RESULT_PATH,getCurrentTime().toString()+"\t二十六键拼音平均按键时间\t"+fourChangeKeyTime[2]+"\n");
				writeLogFiles(KPI_RESULT_PATH,getCurrentTime().toString()+"\t二十六键英文平均按键时间\t"+fourChangeKeyTime[3]+"\n");
				
				sleepForMoment(CHANGE_SLEEP_TIME);
				//CommonFunction.sleepForWhile(3000);
				int testNumber=KPITestApplication.getInstance().getTestNumber();
				KPITestApplication.getInstance().setTestNumber(testNumber+1);
	    		new BeforeTest(getBaseContext()).start(); 
	    		finish();
			}
			
			 
			
		}
	}
	
	private void executeScript(String name,Handler handler){
		File sdRoot=SdCard.getSDPath();
		if(sdRoot==null) return;
		try{
			String path=sdRoot.toString()+name;
			FileInputStream fileInputStream=new FileInputStream(path);
			DataInputStream dataInputStream=new DataInputStream(fileInputStream);
			BufferedReader bufferedReader=new BufferedReader(
					new InputStreamReader(dataInputStream));
			String sLine=null;
			
			while((sLine=bufferedReader.readLine())!=null&&TestActivity.runTag){
				processLine(sLine,handler,name);						
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
	
	private void processLine(String line,Handler handler,String name){
		if(line.indexOf('=')>=0){
			String array[]=line.split(";");
			if(array[0].split("=").length==2)
				word=array[0].split("=")[1];
			else
				word="";
			yinjie=word;
			editChange=true;
			startTime=System.currentTimeMillis();
			for(int i=1;i<array.length;i++){
				String temp[]=array[i].split(",");
				if(temp.length==2){
					if(CommonFunction.isNumber(temp[0])&&CommonFunction.isNumber(temp[1])){
						MonkeyMotionEventAgent event=new MonkeyMotionEventAgent(
								KeyEvent.ACTION_DOWN,Integer.valueOf(temp[0]),Integer.valueOf(temp[1]));
						ma.runCmd(event);
						event=new MonkeyMotionEventAgent(
								KeyEvent.ACTION_UP,Integer.valueOf(temp[0]),Integer.valueOf(temp[1]));
						ma.runCmd(event);
					}
				}
			}
		}
		else{
		
			String array[]=line.split("\t");
			word=array[1];
			yinjie=array[0];
			Point point=null,point2=null,point3=null;
			//talkToMonkey.press("del");
			editChange=true;
			startTime=System.currentTimeMillis();
			for(int i=0;i<yinjie.length();i++){
				if(i==0) point2=deviceResolution.getPoint(""+yinjie.charAt(i));
				if(i==1) point3=deviceResolution.getPoint(""+yinjie.charAt(i));
				point=deviceResolution.getPoint(""+yinjie.charAt(i));
				//talkToMonkey.touch(""+point.getX(), ""+point.getY());
				MonkeyMotionEventAgent event=new MonkeyMotionEventAgent(
					KeyEvent.ACTION_DOWN,point.getX(),point.getY());
				ma.runCmd(event);
				event=new MonkeyMotionEventAgent(
					KeyEvent.ACTION_UP,point.getX(),point.getY());
				ma.runCmd(event);
				point=null;
			}	
		}
		for(int j=0;j<4;j++){
			//point=deviceResolution.getPoint()
			//talkToMonkey.press("space");
			//talkToMonkey.touch(""+spacePoint.getX(), ""+spacePoint.getY());
			MonkeyMotionEventAgent event=new MonkeyMotionEventAgent(
					KeyEvent.ACTION_DOWN,spacePoint.getX(),spacePoint.getY());
			ma.runCmd(event);
			event=new MonkeyMotionEventAgent(
					KeyEvent.ACTION_UP,spacePoint.getX(),spacePoint.getY());
			ma.runCmd(event);
		}		
		//Message message=handler.obtainMessage();
		//message.what=SET_TEXTVIEW_WITH_RESULT;
		//handler.sendMessage(message);
		sleepForMoment(1000);
		
		//System.out.println(point.getX());
		Message message=handler.obtainMessage();
		message.what=SET_EDITTEXT_WITH_BLACK;
		handler.sendMessage(message);
		sleepForMoment(800);
		
		
	}
	
	
	
	private void sleepForMoment(int time){
		try{
			Thread.sleep(time);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	private Handler handlerMessage = new Handler(Looper.getMainLooper()) {
    	public void handleMessage(Message msg) { 
    		if(msg.what==SET_EDITTEXT_WITH_BLACK){
    			etMessage.setText("");
    			if(nowModel==ENGLISH_MODEL&&inputMethodInfo.getScript().startsWith("wi")){
    				MonkeyMotionEventAgent event=new MonkeyMotionEventAgent(
    						KeyEvent.ACTION_DOWN,110,760);
    				ma.runCmd(event);
    				event=new MonkeyMotionEventAgent(
    						KeyEvent.ACTION_UP,110,760);
    				ma.runCmd(event);
    			}
    		}
    		else if(msg.what==SET_TEXTVIEW_WITH_RESULT){
    			/*String text=etMessage.getText().toString().trim();
    			String result="";
    			if (text.equals(word.trim())) {		
					countTotal ++;
					pass  ++;
					result = "Pass";
				} else {
					countTotal ++;
					fail++;
					result = "Err";
				}
    			String kpiLog = getCurrentTime().toString() + " >> "
						+ "Total:" + countTotal + ", " 
						+ "Pass:" + pass+ ", " + "Fail:" + fail + ", " 
						+ "keyTime:"+ (float) keyTime + "ms" + " >> " 
						+ yinjie + ">>"+
						" >> weight:" + quanzhi + ">> Expect:"
						+ word + ", Real:" + text
						+ ", Result:<" + result + ">";
    			tvResult.setText(kpiLog);
    			writeLogFiles(KPI_RESULT_PATH,kpiLog);
    			
    			String uss = deviceInfo.getUss();
                String rss = deviceInfo.getRss();               
				float cpuUsage = deviceInfo.getCpuUsage();
				String stateLog = "Scale:" + TestActivity.batteryScale  + " >> "  
                	+ "Temprature:" + TestActivity.batteryTemprature + " >> "
                	+ "Rss:" + rss + " >> "
                	+ "Uss:" + uss  + " >> " 
                	+ "Cpu:" + cpuUsage + "%";	
				
				writeLogFiles(STATE_RESULT_PATH,stateLog);*/
    		}
    	}
	};
	
	private String getCurrentTime() {
		SimpleDateFormat timeFormat = new SimpleDateFormat(
				"yyyy-MM-dd_HH-mm-ss");
		return timeFormat.format(new Date());
	}
	 
	 private void writeLogFiles(String testFileName, String log) {
		 File sdRoot = Environment.getExternalStorageDirectory();
		 String testSourcePath = sdRoot + testFileName;
		 try {
			 writeTestLog(testSourcePath, log);
		 } catch (IOException e) {		
			 e.printStackTrace();
		 }
	 }
	 
	 private void writeTestLog(String testFilePath, String log) throws IOException {
		 File file = new File(testFilePath);
		 try {
			 file.createNewFile();
		 } catch (IOException e) {
			 Toast.makeText(this, "Failed to create logfile in sd!", Toast.LENGTH_LONG).show();
			 e.printStackTrace();
		 }

		 try {
			 OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8");
			 osw.write(log + "\r\n");
			 osw.flush();
			 osw.close();
		} catch (IOException e) {
			 e.printStackTrace();
		}
	 }	
}
