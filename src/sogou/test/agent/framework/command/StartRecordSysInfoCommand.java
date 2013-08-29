package sogou.test.agent.framework.command;

public class StartRecordSysInfoCommand implements MonkeyCommand{

	private ImplementCommand implementCommand;
	
	public StartRecordSysInfoCommand(ImplementCommand ic){
		this.implementCommand=ic;
	}
	
	@Override
	public String execute(ValueObject valueObject) {
		return this.implementCommand.startRecordSysInfo();
	}

}
