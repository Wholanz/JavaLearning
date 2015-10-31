
public class MessageSplit {
	
	public static LogMessage messageSplit(String line){
		try {
		String[] broken1;
		broken1 = line.split("\"");
		broken1[0]=broken1[0].replace(broken1[0].split(" ")[2], "-");
		String[] broken2 = broken1[0].split(" - - ");
		String ip = broken2[0];
		broken2[1] = broken2[1].replace("[", "");
		broken2[1] = broken2[1].replace("]", "");
		String[] time = broken2[1].split(" ");
		String day = time[0].split("/")[0];
		String month = time[0].split("/")[1];
		String page = broken1[1].split(" ")[1];
		String dataSize = broken1[2].split(" ")[2];
		String linkState = broken1[2].split(" ")[1];
		return new LogMessage(ip, day, month, page, dataSize,linkState);
		}catch (ArrayIndexOutOfBoundsException e){
			System.out.println("ArrayIndexOutOfBoundsException!");
			System.out.println(line);
		}
		return null;
	}
	
	

}
