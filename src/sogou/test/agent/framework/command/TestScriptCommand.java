package sogou.test.agent.framework.command;

public class TestScriptCommand implements MonkeyCommand{

	private ImplementCommand implementCommand;
	
	public TestScriptCommand(ImplementCommand ic){
		this.implementCommand=ic;
	}
	
	@Override
	public String execute(ValueObject valueObject) {
		// TODO Auto-generated method stub
		return this.implementCommand.testScript();
	}

}
