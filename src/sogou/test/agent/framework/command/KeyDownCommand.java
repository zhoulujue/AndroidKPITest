package sogou.test.agent.framework.command;

public class KeyDownCommand implements MonkeyCommand{

	private ImplementCommand implementCommand;
	
	public KeyDownCommand(ImplementCommand ic){
		this.implementCommand=ic;
	}
	
	@Override
	public String execute(ValueObject valueObject) {
		return this.implementCommand.keyDownAction(valueObject.getValue());
	}
}
