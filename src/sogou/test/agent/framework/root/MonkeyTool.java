package sogou.test.agent.framework.root;

import android.content.Context;

public class MonkeyTool extends AbstractTool implements ToolsConstants{

	private Context context;

	public MonkeyTool(Context context) {
		if (context == null)
			return;
		this.context = context;
		createFileFromAsset(MONKEYSCRIPT, context);
		//succTag=chmodToolbox(MONKEYSCRIPT,context);
	}
	
	@Override
	public boolean doAction(String tag, String value) {
		try {
			String cmd[] = { "su","sh /data/data/" + context.getPackageName()
					+ "/files/"+MONKEYSCRIPT+"" };
			consoleExec(cmd);
		} catch (Exception ex) {

		}
		return false;
	}

}
