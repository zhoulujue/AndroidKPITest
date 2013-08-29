package sogou.test.agent.framework.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import sogou.test.agent.framework.root.CommonFunction;

import android.content.Context;
import android.util.Log;

public class RunScriptContext implements ActionConstants{
	
	public static final String CHANGE_INPUT_METHOD="/KPITestDir/ChangeInputMethod/";
	
	private ImplementCommand implememtCommand;
	
	public RunScriptContext(Context context){
		implememtCommand=new ImplementCommand(context);
	}
	
	public void runScript(String path){
		if(path==null||path.length()==0) return;
		File file=new File(path);
		if(!file.exists())
			return;
		
		try {
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line=null;
			
			while((line=reader.readLine())!=null){
				Log.i("kpi", "line="+line);
				String cmdArray[]=line.split(",");
				
				MonkeyCommand command=CommandFactory.getInstance().createCommand(cmdArray[0],
						implememtCommand);
				if(command!=null){
					ValueObject valueObject = null;
					if (cmdArray.length == 2) {
						if (cmdArray[0].equals(SLEEP_ACTION)) {
							valueObject = new ValueObject(
									Integer.valueOf(cmdArray[1]));
						} else {
							valueObject = new ValueObject(cmdArray[1]);
						}
					} else if (cmdArray.length == 3) {
						valueObject = new ValueObject(cmdArray[1], cmdArray[2]);
					}
				
					CommandInvoker commandInvoker=new CommandInvoker(command);
					commandInvoker.action(valueObject);
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
