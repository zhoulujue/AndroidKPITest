package sogou.test.agent.framework.command;

public class StopRecordSysInfoCommand implements MonkeyCommand{

	private ImplementCommand implementCommand;
	
	public StopRecordSysInfoCommand(ImplementCommand ic){
		this.implementCommand=ic;
	}
	
	@Override
	public String execute(ValueObject valueObject) {
		return this.implementCommand.stopRecordSysInfo();
	}

}
