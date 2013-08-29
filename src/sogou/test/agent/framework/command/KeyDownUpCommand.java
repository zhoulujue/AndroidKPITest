package sogou.test.agent.framework.command;

public class KeyDownUpCommand implements MonkeyCommand{
	private ImplementCommand implementCommand;
	
	public KeyDownUpCommand(ImplementCommand ic){
		this.implementCommand=ic;
	}
	
	@Override
	public String execute(ValueObject valueObject) {
		return this.implementCommand.keyDownUpAction(valueObject.getValue());
	}
}
