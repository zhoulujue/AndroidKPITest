package sogou.test.kpi.devices;

import java.util.Iterator;
import java.util.List;

import android.view.KeyEvent;

import sogou.test.kpi.communication.TalkToMonkey;
import sougou.test.monkeyagent.MonkeyAgent;
import sougou.test.monkeyagent.MonkeyMotionEventAgent;

public class ExecuteContext {
	private static final int CHANGE_KEYBOARD_TIME=3000;
	//private TalkToMonkey talkToMonkey;
	private MonkeyAgent ma;
	private String filename;
	
	public ExecuteContext(MonkeyAgent ttm,String filename){
		//this.talkToMonkey=ttm;
		this.filename=filename;
		this.ma=ttm;
	}
	
	public void executeScript(){
		if(changeToNinePinyin());
		if(changeToNineEnglish());
		if(changeToTwsixPinyin());
		if(changeToTwsixEnglish());
	}
	
	public boolean touchEvent(List<Point> list){
		if(list==null||list.size()<1) return false;
		Iterator<Point> iterator=list.iterator();
		while(iterator.hasNext()){
			Point point=iterator.next();
			//this.talkToMonkey.touch(""+point.getX(), ""+point.getY());
			MonkeyMotionEventAgent event=new MonkeyMotionEventAgent(
					KeyEvent.ACTION_DOWN,point.getX(),point.getY());
			ma.runCmd(event);
			event=new MonkeyMotionEventAgent(
					KeyEvent.ACTION_UP,point.getX(),point.getY());
			ma.runCmd(event);
			sleepForMoment(CHANGE_KEYBOARD_TIME);
		}
		return true;		
	}
	
	public boolean changeToNinePinyin(){
		ChangeKeyboard changeKeyboard=ChangeKeyboard.getInstance(filename);
		changeKeyboard.setFileName(filename);
		List<Point> list=changeKeyboard.getNinePinyin();
		return touchEvent(list);
	}
	public boolean changeToNineEnglish(){
		ChangeKeyboard changeKeyboard=ChangeKeyboard.getInstance(filename);
		changeKeyboard.setFileName(filename);
		List<Point> list=changeKeyboard.getNineEnglish();
		return touchEvent(list);
	}
	public boolean changeToTwsixPinyin(){
		ChangeKeyboard changeKeyboard=ChangeKeyboard.getInstance(filename);
		changeKeyboard.setFileName(filename);
		List<Point> list=changeKeyboard.getTwsixPinyin();
		return touchEvent(list);
	}
	public boolean changeToTwsixEnglish(){
		ChangeKeyboard changeKeyboard=ChangeKeyboard.getInstance(filename);
		changeKeyboard.setFileName(filename);
		List<Point> list=changeKeyboard.getTwsixEnglish();
		return touchEvent(list);
	}
	
	private void sleepForMoment(int time){
		try{
			Thread.sleep(time);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}

}
