import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Command {
	
	private ArrayList<Option> options;
	private String command = null;
	
	public Command(){
		options = new ArrayList<Option>();
	}
	
	public void ExecCommand(){
		if(this.command.equals("select"))
			this.commandSel();
		else if(this.command.equals("add"))
			this.commandAdd();
		else if(this.command.equals("exit"))
			this.commandExit();
		else if(this.command.equals("help"))
			this.commandHelp();
		else {
			System.out.println("Please input the correct command! Input help if you have any doubt!");
		}
	}
	
	private void commandExit() {
		System.out.println("Thanks for using!");
		System.exit(0);
	}

	private void commandHelp() {
		
		System.out.println("You can use the system with the following commands:");
		System.out.println("add [student name] [course name] [grade]");
		System.out.println("This command is used for adding information!");
		System.out.println("select");
		System.out.println("If no option is chosen, print all items!");
		System.out.println("-s [student name]");
		System.out.println("The command with this option is used for selecting the information of an exact student!");
		System.out.println("-c [course name]");
		System.out.println("The command with this option is used for selection the information of an exact course!");
		System.out.println("exit");
		System.out.println("This command is used for quiting the system!");
	}

	private void commandAdd() {
		try {
			
			if(options.get(0).getParameter().size() != 3){
				System.out.println("Invalid Command!");
				return;
			}
			String data;
			String[] item = null;
			BufferedReader reader = new BufferedReader(new FileReader("grade.csv"));
			BufferedWriter writer = new BufferedWriter(new FileWriter("gradetemp.csv"));
			
			while((data = reader.readLine()) != null){
				String dataTemp = data.replace(" ", "");
				item = dataTemp.split(",");
				if(item[0].equals(options.get(0).getParameter().get(0)) && 
						item[1].equals(options.get(0).getParameter().get(1))){
					
					
				}
				else {
					writer.write(data);
					writer.newLine();

				}
			}
			if(data != null)
			data = data.replace(item[2], options.get(0).getParameter().get(2));
			else data = options.get(0).getParameter().get(0) +","+options.get(0).getParameter().get(1)+","+options.get(0).getParameter().get(2);
			writer.write(data);
			writer.newLine();
			writer.close();
			reader.close();
			
			reader = new BufferedReader(new FileReader("gradetemp.csv"));
			writer = new BufferedWriter(new FileWriter("grade.csv"));
			while((data = reader.readLine()) != null){
				writer.write(data);
				writer.newLine();
				}
			reader.close();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void commandSel() {
		try {
			String data;
			int total = 0;
			int count = 0;
			BufferedReader reader = new BufferedReader(new FileReader("grade.csv"));
			if(options.size() == 0)
			while((data = reader.readLine()) != null){
				data = data.replace(" ", "");
				String[] item = data.split(",");
				System.out.println(item[0]+" get "+item[2]+" in "+ item[1]);
			}
			else if(options.size() == 1){
				if(options.get(0).getParameter().size()>1 || options.get(0).getParameter().size()<1){
					System.out.println("you can only input one parameter!");
					reader.close();
					command = null;
					return;
				}
				if(options.get(0).getOption() == 's'){
				while((data = reader.readLine()) != null){
					data = data.replace(" ", "");
					String[] item = data.split(",");
					if(options.get(0).matchParameter(item[0])){
						System.out.println(item[0]+" get "+item[2]+" in "+ item[1]);
						total += Integer.parseInt(item[2]);
						count ++;
						}
				}
				if(count==0){
					System.out.println("No student named "+ options.get(0).getParameter().get(0));
					reader.close();
					return;
				}
				}
				if(options.get(0).getOption() == 'c'){
					while((data = reader.readLine()) != null){
						data = data.replace(" ", "");
						String[] item = data.split(",");
						if(options.get(0).matchParameter(item[1])){
							System.out.println(item[0]+" get "+item[2]+" in "+ item[1]);
							total += Integer.parseInt(item[2]);
							count ++;
							}
					}
					if(count==0){
						System.out.println("No course named "+ options.get(0).getParameter().get(0));
						reader.close();
						return;
					}
					}
				System.out.println("The total grade is: "+ total);
				System.out.println("The average grade is: "+ total*1.0/count);
			}
			else if(options.size() == 2){
				if(options.get(0).getParameter().size()>1||options.get(1).getParameter().size()>1){
					System.out.println("you can only input one parameter!");
					reader.close();
					return;
				}
					if(options.get(0).getOption() == 's' && options.get(1).getOption() == 'c'){
						while((data = reader.readLine()) != null){
							data = data.replace(" ", "");
							String[] item = data.split(",");
							if(options.get(0).matchParameter(item[0]) && options.get(1).matchParameter(item[1])){
								System.out.println(item[0]+" get "+item[2]+" in "+ item[1]);
								count++;
							}
							
						}
					if(count == 0)
						System.out.println("No such message!");
					}
					else if(options.get(0).getOption() == 'c' && options.get(1).getOption() == 's'){
						while((data = reader.readLine()) != null){
							data = data.replace(" ", "");
							String[] item = data.split(",");
							if(options.get(0).matchParameter(item[1]) && options.get(1).matchParameter(item[0])){
								System.out.println(item[0]+" get "+item[2]+" in "+ item[1]);
								count++;
							}
							
						}
					if(count == 0)
						System.out.println("No such message!");
					}
					else {
						System.out.println("Invalid command! Use help to get infomation!");
						reader.close();
						return;
					}
			}
			reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void commandInfoGet(String commandLine){
		String[] SplitCommandLine = commandLine.split(" ");
		
		try{
			int optionCount = 0;
		for(int i = 0; i < SplitCommandLine.length; i++){
			
			if(SplitCommandLine[i].equals("")) 
				continue;
			else if(this.command == null) 
				this.command = SplitCommandLine[i];
			else if(SplitCommandLine[i].charAt(0) == '-'){
				
				if((optionCount = SplitCommandLine[i].length() - 1)<1) throw new WrongCommandLineOptionException("Wrong option input!");
				for(int j = 1; j < SplitCommandLine[i].length(); j++){
					this.options.add(new Option(SplitCommandLine[i].charAt(j)));
				}
			}
			else {
				if(optionCount==0){
					options.add(new Option('d'));
					optionCount = 1;
				}
				for(int k = this.options.size() - optionCount; k < this.options.size(); k++){
					this.options.get(k).addParameter(SplitCommandLine[i]);
				}
			}
		}
	}catch(WrongCommandLineOptionException e){
		System.out.println(e.getMessage());
	}
	
		
	}
	
	public String getCommand(){
		return command;
	}
	
	public void initialize(){
		options.clear();
		command = null;
	}
	
}
