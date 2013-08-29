package sogou.test.agent.framework.command;

public class DownCommand implements MonkeyCommand{

	private ImplementCommand implementCommand;
	
	public DownCommand(ImplementCommand ic){
		this.implementCommand=ic;
	}
	
	@Override
	public String execute(ValueObject valueObject) {
		return this.implementCommand.touchDownAction(valueObject.getX(), valueObject.getY());
	}

}
