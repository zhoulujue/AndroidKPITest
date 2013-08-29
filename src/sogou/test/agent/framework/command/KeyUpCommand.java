package sogou.test.agent.framework.command;

public class KeyUpCommand implements MonkeyCommand{

	private ImplementCommand implementCommand;
	
	public KeyUpCommand(ImplementCommand ic){
		this.implementCommand=ic;
	}
	
	@Override
	public String execute(ValueObject valueObject) {
		return this.implementCommand.keyUpAction(valueObject.getValue());
	}
	
}
