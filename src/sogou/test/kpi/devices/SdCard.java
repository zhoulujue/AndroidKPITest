package sogou.test.kpi.devices;

import java.io.File;

import android.os.Environment;

public class SdCard {
	public static File getSDPath(){ 
        File sdDir = null; 
        boolean sdCardExist = Environment.getExternalStorageState()   
                              .equals(android.os.Environment.MEDIA_MOUNTED);  
        if(sdCardExist){                                  
            sdDir = Environment.getExternalStorageDirectory();
        }
        return sdDir;          
    } 
}
