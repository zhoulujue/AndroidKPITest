package sogou.test.agent.framework.command;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory implements ActionConstants {

	private MonkeyCommand command;
	public Map<String, SoftReference<MonkeyCommand>> commandCache;
	private static CommandFactory instance;

	public CommandFactory() {
		commandCache = new HashMap<String, SoftReference<MonkeyCommand>>();
	}

	public static CommandFactory getInstance() {
		if (instance == null) {
			instance = new CommandFactory();
		}
		return instance;
	}

	public MonkeyCommand createCommand(String key,
			ImplementCommand implementCommand) {
		if (commandCache.containsKey(key)) {
			SoftReference<MonkeyCommand> softReference = commandCache.get(key);
			if (softReference.get() != null) {
				return softReference.get();
			} else {
				commandCache.remove(key);
			}
		}

		if (key.equals(DOWN_AND_UP_ACTION)) {
			command = new DownUpCommand(implementCommand);
		} else if (key.equals(KEY_DOWN_AND_UP_ACTION)) {
			command = new KeyDownUpCommand(implementCommand);
		} else if (key.equals(TOUCH_DOWN_ACTION)) {
			command = new DownCommand(implementCommand);
		} else if (key.equals(TOUCH_UP_ACTION)) {
			command = new UpCommand(implementCommand);
		} else if (key.equals(DRAG_ACTION)) {
			command = new MoveCommand(implementCommand);
		} else if (key.equals(KEY_DOWN_ACTION)) {
			command = new KeyDownCommand(implementCommand);
		} else if (key.equals(KEY_UP_ACTION)) {
			command = new KeyUpCommand(implementCommand);
		} else if (key.equals(SLEEP_ACTION)) {
			command = new SleepCommand(implementCommand);
		} else if (key.equals(TEST_SCRIPT_ACTION)) {
			command = new TestScriptCommand(implementCommand);
		} else if (key.equals(LUNCH_APP_ACTION)||key.equals(START_APP_TIME)) {
			command = new LunchAppCommand(implementCommand);
		} else if (key.equals(START_RECORD_SYSTEM_INFO)) {
			command = new StartRecordSysInfoCommand(implementCommand);
		} else if (key.equals(STOP_RECORD_SYSTEM_INFO)) {
			command = new StopRecordSysInfoCommand(implementCommand);
		} 
		else {
			return null;
		}

		commandCache.put(key, new SoftReference<MonkeyCommand>(command));
		return command;
	}

}
