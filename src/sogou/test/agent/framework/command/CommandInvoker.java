package sogou.test.agent.framework.command;

public class CommandInvoker {
	
	private MonkeyCommand monkeyCommand;
	
	public CommandInvoker(MonkeyCommand monkeyCommand){
		this.monkeyCommand=monkeyCommand;
	}
	
	public String action(ValueObject valueObject){
		return monkeyCommand.execute(valueObject);
	}

}
