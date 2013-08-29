package sogou.test.agent.framework.command;

public class UpCommand implements MonkeyCommand{

	private ImplementCommand implementCommand;
	
	public UpCommand(ImplementCommand ic){
		this.implementCommand=ic;
	}
	
	@Override
	public String execute(ValueObject valueObject) {
		return this.implementCommand.touchUpAction(valueObject.getX(), valueObject.getY());
	}

}
