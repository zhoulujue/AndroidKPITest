package sogou.test.agent.framework.command;

public class LunchAppCommand implements MonkeyCommand{
	private ImplementCommand implementCommand;
	
	public LunchAppCommand(ImplementCommand ic){
		this.implementCommand=ic;
	}
	
	@Override
	public String execute(ValueObject valueObject) {
		return this.implementCommand.openApplication(valueObject.getValue());
	}

}
