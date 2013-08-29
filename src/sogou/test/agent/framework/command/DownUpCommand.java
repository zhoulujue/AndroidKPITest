package sogou.test.agent.framework.command;

public class DownUpCommand implements MonkeyCommand{
	private ImplementCommand implementCommand;
	
	public DownUpCommand(ImplementCommand ic){
		this.implementCommand=ic;
	}
	
	@Override
	public String execute(ValueObject valueObject) {
		return this.implementCommand.touchDownUpAction(valueObject.getX(), valueObject.getY());
	}
}
