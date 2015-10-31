import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;




public class Analyzer {

	public static void main(String[] args) {

		try {
			
			LogMessage logMessage = new LogMessage();
			String logLine;
			long link404 = 0;
			long dataScale = 0;
			ArrayList<ForSort> ipList = new ArrayList<ForSort>();
			ArrayList<ForSort> dayList = new ArrayList<ForSort>();
			ArrayList<ForSort> monthList = new ArrayList<ForSort>();
			ArrayList<ForSort> pageList = new ArrayList<ForSort>();
			Scanner input = new Scanner(System.in);
			System.out.println("Welcome!");
			System.out.print("Please enter the log file name to be analysed:");
			String name = input.nextLine();
			BufferedReader reader = new BufferedReader(new FileReader(name));
			System.out.println("******************************************************");
			System.out.println("Analysing....It may take a long time to finish...");
			while((logLine = reader.readLine()) != null){
				logMessage = MessageSplit.messageSplit(logLine);
				ForSort.add(ipList, logMessage.ip);
				ForSort.add(dayList, logMessage.day);
				ForSort.add(monthList, logMessage.month);
				ForSort.add(pageList, logMessage.page);
				if(logMessage.linkState.equals("404"))
					link404 ++;
				if(!logMessage.dataSize.equals("-")){
					dataScale += Long.parseLong(logMessage.dataSize);
				}
			}
			
			System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
			Collections.sort(ipList, new SortByCount());
			Collections.sort(dayList, new SortByCount());
			Collections.sort(monthList, new SortByCount());
			Collections.sort(pageList, new SortByCount());
			
			
		
			
			System.out.println("Ip "+pageList.get(pageList.size()-1).name+" took the most pages with count "+pageList.get(pageList.size()-1).count);
			System.out.println("The most busy day is "+dayList.get(dayList.size()-1).name+" with count "+dayList.get(dayList.size()-1).count);
			System.out.println("The most busy month is "+monthList.get(monthList.size()-1).name+" with count "+monthList.get(monthList.size()-1).count);
			System.out.println("The most popular page is "+pageList.get(pageList.size()-1).name+" with count "+pageList.get(pageList.size()-1).count);
			System.out.println(link404+" clients disconnect during the period!");
			System.out.println(dataScale+" bytes of data is transfered during the period!");
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

}


class ForSort{
	public String name = null;
	public int count = 1;
	
	public ForSort(String name){
		this.name = name;
	}
	public ForSort(){
	}
	
	public static void add(ArrayList<ForSort> list,String check){
		
		ForSort name = new ForSort(check);
		if(!list.contains(name)){
			if(list.size()>50000){
			Collections.sort(list, new SortByCount());
			ForSort temp = new ForSort();
			temp.name = list.get(list.size()-1).name;
			temp.count = list.get(list.size()-1).count;
			list.clear();
			list.add(temp);
			}
			else
			list.add(name);
		}
		else list.get(list.indexOf(name)).count++;
	}
	
	
	
	@Override
	public boolean equals(Object obj){
		if(this == obj) return true;
		else if( obj != null && obj instanceof ForSort){
			ForSort t = (ForSort) obj;
			return t.name.equals(this.name);
		}
		else return false;
	}
	
}

class SortByCount implements Comparator<ForSort> {
	@Override
	 public int compare(ForSort o1, ForSort o2) {
	  ForSort s1 = (ForSort) o1;
	  ForSort s2 = (ForSort) o2;
	  if (s1.count > s2.count)
	   return 1;
	  return -1;
	 }
	}
