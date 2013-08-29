package sogou.test.agent.framework.root;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.util.Log;

public class InstallTool extends AbstractTool implements ToolsConstants{

	private static final String UNINSTALL_ACTION_CMD = " exec app_process /system/bin sogou.installpackage.jar.StartMain uninstall ";
	private static final String INSTALL_ACTION_CMD = " exec app_process /system/bin sogou.installpackage.jar.StartMain reinstall ";
	private static final String CLEAR_ACTION_CMD = " exec app_process /system/bin sogou.installpackage.jar.StartMain clear ";
	private String exportClassPath;
	//private Context context;

	public InstallTool(Context context) {
		if (context == null) {
			return;
		}

		Log.i("file", "install file 2");
		this.createFileFromAsset(INSTALLER, context);
		exportClassPath = "export CLASSPATH=/data/data/"
				+ context.getPackageName() + "/files/installpackagejar.jar";
	}

	@Override
	public boolean doAction(String tag, String value) {
		if (tag == null)
			return false;
		//Log.i("install", "tag->"+tag);
		if (tag.equals(INSTALL)) {
			File temp = new File(value);
			//Log.i("install", "value->"+value);
			if (!temp.exists()){
				//Log.i("install", "->file not exist");
				return false;
			}
				
			Log.i("install", "->install app");
			String cmd[] = { exportClassPath, INSTALL_ACTION_CMD +"\""+ value +"\""};
			try {
				consoleExec(cmd);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		} else if (tag.equals(UNINSTALL)) {
			String cmd[] = { exportClassPath, UNINSTALL_ACTION_CMD + value };
			try {
				consoleExec(cmd);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		else if(tag.equals(CLEAR)){
			String cmd[] = { exportClassPath, CLEAR_ACTION_CMD + value };
			try {
				consoleExec(cmd);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}

}
