
package sogou.test.kpi.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


import android.util.Log;

public class Talking {

	private Socket socket = null;
    private OutputStream outputStream = null;
    private InputStream inputStream = null;
    private BufferedReader bufferedReader = null;
    
    public Talking(Socket s) throws IOException
    {
    	this.socket = s;
    	outputStream = s.getOutputStream();
    	inputStream = s.getInputStream();
    	bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }
    
    public void close() throws IOException
    {
    	socket.shutdownInput();
    	socket.shutdownOutput();
    	bufferedReader.close();
    	inputStream.close();
    	outputStream.close();
    	socket.close();
    	//Log.i(VIDEO_LABEL, "Talking closed");
    }

    public boolean isClosed()
    {
        return socket.isClosed();
    }
    
    public Socket getSocket(){
    	return this.socket;
    }

    public String getIpAddress(){
    	String ipStr;    	
    	ipStr = socket.getInetAddress().toString();
    	ipStr = ipStr.substring(ipStr.indexOf('/')+1);
    	return ipStr;
    }
    
    public void write(byte[] data) throws IOException
    {
    	outputStream.write(data);
    	outputStream.flush();
    }
    
    public void writeLine(byte[] data) throws IOException
    {
    	outputStream.write(data);
    	outputStream.write("\n".getBytes());
    	outputStream.flush();
    }
    
    public void write(byte[] data,int start,int offset) throws IOException
    {
    	outputStream.write(data,start,offset);
    	outputStream.flush();
    }
    
    public String readLine() throws IOException
    {
    	return bufferedReader.readLine();
    }
    
    public byte[] readOneByte() throws IOException
    {
    	byte tk[]=new byte[1];
    	inputStream.read(tk,0,1);
    	return tk;
    }
    
    public void flushOutput()throws IOException{
    	outputStream.flush();
    }
}
