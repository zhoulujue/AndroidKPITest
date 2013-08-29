package sogou.test.agent.framework.command;

public class SleepCommand implements MonkeyCommand{
	private ImplementCommand implementCommand;
	
	public SleepCommand(ImplementCommand ic){
		this.implementCommand=ic;
	}
	
	@Override
	public String execute(ValueObject valueObject) {
		return this.implementCommand.sleepForWhile(valueObject.getTime());
	}
}
