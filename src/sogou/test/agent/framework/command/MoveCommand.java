package sogou.test.agent.framework.command;

public class MoveCommand implements MonkeyCommand{
	private ImplementCommand implementCommand;
	
	public MoveCommand(ImplementCommand ic){
		this.implementCommand=ic;
	}
	
	@Override
	public String execute(ValueObject valueObject) {
		return this.implementCommand.moveAction(valueObject.getX(), valueObject.getY());
	}
}
