package sogou.test.agent.framework.command;

public class ValueObject {
	
	private String x;
	private String y;
	private String value;
	private int time;
	
	public ValueObject(){
		x="";
		y="";
		value="";
		time=0;
	}
	
	public ValueObject(String x,String y){
		this.x=x;
		this.y=y;
	}
	
	public ValueObject(String value){
		this.value=value;
	}
	
	public ValueObject(int time){
		this.time=time;
	}
	
	public String getX(){
		return this.x;
	}
	
	public String getY(){
		return this.y;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public int getTime(){
		return this.time;
	}

}
