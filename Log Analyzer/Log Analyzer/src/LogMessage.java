
public class LogMessage {
	public String ip = null;
	public String day = null;
	public String month = null;
	public String page = null;
	public String dataSize = null;
	public String linkState = null;
	
	public LogMessage(){
		
	}
	
	public LogMessage(String ip,String day,String month,String page,String dataSize,String linkState){
		this.ip = ip;
		this.day = day;
		this.month = month;
		this.page = page;
		this.dataSize = dataSize;
		this.linkState = linkState;
	}
}
