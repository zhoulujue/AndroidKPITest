package sogou.test.kpi.communication;

import java.io.IOException;
import android.content.Context;
import android.util.Log;

public class TalkToMonkey {
	
	public TcpClient tcpClient = null;
	private KeyCode keyCode = KeyCode.getInstance();
	private static TalkToMonkey _instance=null;
	private Context myContext;
	private static final String RIGHT_VALUE="OK";
	private static final String ERROR_VALUE="error";

	public TalkToMonkey(Context context) {
		this.myContext=context; 
	}
	
	public static TalkToMonkey getInstance(Context context){
		if(_instance==null){
			_instance=new TalkToMonkey(context);
		}
		return _instance;
	}
	
	public void createMonkeyClient(){
		tcpClient = new TcpClient("127.0.0.1", 8005);
		try{
			tcpClient.connect();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	} 
	
	public void close(){
		if(tcpClient==null) return;
		try{
			tcpClient.close();
			tcpClient=null;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String keyDown(String keys){
		String result = RIGHT_VALUE;
		String key = keyCode.getKeyCode(keys);
		String cmd1 = "key down " + key;
		result = sendMonkeyEvent(cmd1);
		if(!result.equalsIgnoreCase(RIGHT_VALUE)){
			result = ERROR_VALUE;
		}
		return result;
	}
	
	public String keyUp(String keys){
		String result = RIGHT_VALUE;
		String key = keyCode.getKeyCode(keys);
		String cmd1 = "key up " + key;
		result = sendMonkeyEvent(cmd1);
		if(!result.equalsIgnoreCase(RIGHT_VALUE)){
			result = ERROR_VALUE;
		}
		return result;
	}
	
	public String press(String keys){ 
		String result = RIGHT_VALUE;
		String key = keyCode.getKeyCode(keys);
		String cmd1 = "key down " + key;
		String result1 = sendMonkeyEvent(cmd1);
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String cmd2 = "key up " + key;
		String result2 = sendMonkeyEvent(cmd2);
		if(!result1.equalsIgnoreCase(RIGHT_VALUE) || !result2.equalsIgnoreCase(RIGHT_VALUE)){
			result = ERROR_VALUE;
		}
		return result;
	}
	
	public String longPress(String keys){
		String result = RIGHT_VALUE;
		String key = keyCode.getKeyCode(keys);
		String cmd1 = "key down " + key;
		String result1 = sendMonkeyEvent(cmd1);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String cmd2 = "key up " + key;
		String result2 = sendMonkeyEvent(cmd2);
		if(!result1.equalsIgnoreCase(RIGHT_VALUE) || !result2.equalsIgnoreCase(RIGHT_VALUE)){
			result = ERROR_VALUE;
		}
		return result;
	}
	
	public String mutiPress(String[] keys){
		String result = RIGHT_VALUE;
		for(int i=0; i<keys.length; i++){
			String key = keyCode.getKeyCode(keys[i]);
			String cmd1 = "key down " + key;
			String result1 = sendMonkeyEvent(cmd1);
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String cmd2 = "key up " + key;
			String result2 = sendMonkeyEvent(cmd2);
			if(!result1.equalsIgnoreCase(RIGHT_VALUE) || !result2.equalsIgnoreCase(RIGHT_VALUE)){
				result = ERROR_VALUE;
			}
		}
		return result;
	}
	
	public String touchDown(String x,String y){
		String result = RIGHT_VALUE;
		String cmd1 = "touch down " + x + " " + y;
		String result1 = sendMonkeyEvent(cmd1);
		if(!result1.equalsIgnoreCase(RIGHT_VALUE)){
			result = ERROR_VALUE;
		}
		return result;
	}
	
	public String touchUp(String x,String y){
		String result = RIGHT_VALUE;
		String cmd2 = "touch up " + x + " " + y;;
		String result2 = sendMonkeyEvent(cmd2);
		if(!result2.equalsIgnoreCase(RIGHT_VALUE)){
			result = ERROR_VALUE;
		}
		return result;
	}
	
	public String moveTo(String x,String y){
		String result = RIGHT_VALUE;
		String command3 = "touch move " + x + " " + y;
		String result2=sendMonkeyEvent(command3);
		if(!result2.equalsIgnoreCase(RIGHT_VALUE)){
			result = ERROR_VALUE;
		}
		return result;
	}
	
	public String touch(String x, String y){
		String result = RIGHT_VALUE;
		String cmd1 = "touch down " + x + " " + y;
		String result1 = sendMonkeyEvent(cmd1);
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String cmd2 = "touch up " + x + " " + y;;
		String result2 = sendMonkeyEvent(cmd2);
		if(!result1.equalsIgnoreCase(RIGHT_VALUE) || !result2.equalsIgnoreCase(RIGHT_VALUE)){
			result = ERROR_VALUE;
		}
		return result;
	}
	
	public String longTouch(String x, String y){
		String result = RIGHT_VALUE;
		String cmd1 = "touch down " + x + " " + y;
		String result1 = sendMonkeyEvent(cmd1);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String cmd2 = "touch up " + x + " " + y;;
		String result2 = sendMonkeyEvent(cmd2);
		if(!result1.equalsIgnoreCase(RIGHT_VALUE) || !result2.equalsIgnoreCase(RIGHT_VALUE)){
			result = ERROR_VALUE;
		}
		return result;
	}
	
	
	public String input(String charactor){
		String result = RIGHT_VALUE; 
		char[] temp = charactor.toCharArray();
		for(int i=0; i<temp.length; i++){
			String key = keyCode.getKeyCode((String.valueOf(temp[i])));
			String cmd1 = "key down " + key;
			String result1 = sendMonkeyEvent(cmd1);
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String cmd2 = "key up " + key;
			String result2 = sendMonkeyEvent(cmd2);
			if(!result1.equalsIgnoreCase(RIGHT_VALUE) || !result2.equalsIgnoreCase(RIGHT_VALUE)){
				result = ERROR_VALUE;
			}
		}
		return result;
	}
	
	public boolean track(int x, int y, int x1, int y1){
		String command1 = "touch down " + x + " " + y;
		sendMonkeyEvent(command1);
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String command2 = "touch move " + ((x + x1) / 2) + " " + ((y + y1) / 2);
		sendMonkeyEvent(command2);
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String command3 = "touch move " + x1 + " " + y1;
		sendMonkeyEvent(command3);
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String command4 = "touch up " + x1 + " " + y1;
		sendMonkeyEvent(command4);
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public String sendMonkeyEvent(String cmd){
		String result = null;
		if(tcpClient == null){
			//Log.v(CONTROL_LABEL, "TalkToMonkey: tc == null");
		}
		
		try {
			tcpClient.writeLine(cmd.getBytes());
			result = tcpClient.readLine();
		} catch (IOException e) {
			
		}
		return result;
	}
	
	public void sleep(){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}