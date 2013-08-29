package sogou.test.agent.framework.root;

import android.content.Context;

public class RebootTool extends AbstractTool implements ToolsConstants {

	private Context context;

	public RebootTool(Context context) {
		if (context == null)
			return;
		this.context = context;
		createFileFromAsset(REBOOT, context);
		//succTag=chmodToolbox(REBOOT,context);
	}

	@Override
	public boolean doAction(String tag, String value) {
		//TestLog.info(CONTROL_LABEL, "==========packagename="+context.getPackageName());
		try {
			String cmd[] = { "/data/data/" + context.getPackageName()
					+ "/files/toolbox reboot\n" };
			consoleExec(cmd);
		} catch (Exception ex) {

		}

		return false;
	}

}
