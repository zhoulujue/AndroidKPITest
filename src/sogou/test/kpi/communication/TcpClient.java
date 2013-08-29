package sogou.test.kpi.communication;

import java.net.*;
import java.io.*;

import android.util.Log;

public class TcpClient{
	private String serverAddress = "127.0.0.1";
	private int serverPort = 8005;
	private Talking talking = null;
	private Socket socket = null;

	public TcpClient() {
	}
	
	public TcpClient(String destAddr, int destPort) {
		setAddr(destAddr);
		setPort(destPort);
	}
	
	public void setAddr(String destAddr) {
		serverAddress = destAddr;
	}

	public void setPort(int destPort) {
		serverPort = destPort;
	}

	public boolean connect() throws UnknownHostException {
		try {
			socket = new Socket(serverAddress, serverPort);
			//socket.setSoTimeout(60000);
			talking = new Talking(socket);
			if(talking == null){ 
				Log.v("msg", "talk is null");				
			}else{
				Log.v("msg", "talk is not null");
			}

			return true;
		} catch(IOException e) {
			Log.v("msg", "wrong");
			e.printStackTrace(); 
			return false;
		}
	}

	public void close() {
		try{
			if ((talking!=null)&&(!talking.isClosed())){
				talking.close();
				talking=null;
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void write(byte[] data) throws IOException {
		byte[] test = data;
		if(talking == null){
			Log.v("msg", "talking: == null");
		}
		talking.write(test);
	}
	
	public void writeLine(byte[] data) throws IOException {
		talking.writeLine(data);
	}

	public String readLine() throws IOException {
		return talking.readLine();
	}
}
