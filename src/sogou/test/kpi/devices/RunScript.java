package sogou.test.kpi.devices;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import sogou.test.kpi.R;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class RunScript {
	
	private static RunScript _instance=null;
	private static final String PRIVATE_AGENT_SCRIPT_LOCATION= "/data/data/sogou.test.kpi/run.sh";
	private static final String TAG="screen";
	private Context myContext;
	
	public RunScript(Context myContext){
		this.myContext=myContext;
	}
	
	public static RunScript getInstance(Context myContext){
		if(_instance==null){
			_instance=new RunScript(myContext);
		}
		return _instance;
	}
	
	public void startRunScript(){
		installScript();
        toRunScript();
	}
	
	private boolean  installScript(){

	    InputStream autoStreamTemp =  myContext.getResources().openRawResource(R.raw.run);			
	    try{
			byte[] bytes = new byte[autoStreamTemp.available()];
			DataInputStream dis = new DataInputStream(autoStreamTemp);
			dis.readFully(bytes);
			File temp = new File(PRIVATE_AGENT_SCRIPT_LOCATION);
			if(temp.exists()){
				temp.delete();
			}
			temp.createNewFile();

			FileOutputStream suOutStream = new FileOutputStream(PRIVATE_AGENT_SCRIPT_LOCATION);
			suOutStream.write(bytes);
			suOutStream.close(); 
			return true;
	    }catch(Exception e){
	    	Toast.makeText(myContext, "Root", Toast.LENGTH_SHORT).show();
	    	e.printStackTrace();
	    	return false;
	    }	
	}
	
	private void toRunScript(){
		try {
			Log.v(TAG, "runScript");
			//String[] cmd = {". " + PRIVATE_AGENT_SCRIPT_LOCATION};
			//consoleExc(cmd);
			String cmd1[] = {"su"};
			consoleExc(cmd1);
		} catch (IOException e) {
			Toast.makeText(myContext, "Root", Toast.LENGTH_SHORT).show();
			Log.v(TAG, "runScript failed");
			e.printStackTrace();
		}
	}
			
	public void consoleExc(String[] cmd) throws IOException{
		Process process = Runtime.getRuntime().exec("su");
		DataOutputStream os = new DataOutputStream(process.getOutputStream());		
		for(int i=0; i< cmd.length; i++){
			os.writeBytes(cmd[i] + "\n");	
		}
		os.writeBytes("exit\n");
		os.flush();        
	}

}
