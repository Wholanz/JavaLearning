import java.util.Scanner;


public class GradeSystem {

	public static void main(String[] args) {
		
		System.out.println("Welcome to the grade system!");
		System.out.println("Please input help for any guidance of using the system!");
		System.out.println("What do you want to do?");
		System.out.println("**************************************************************");
		
		Scanner input = new Scanner(System.in);
		String commandLine;
		Command commandIn = new Command();
		
		while(true){
			System.out.print("<User>");
			commandLine = input.nextLine();
			commandIn.initialize();
			commandIn.commandInfoGet(commandLine);
			commandIn.ExecCommand();
		}
		
		
	}

}
