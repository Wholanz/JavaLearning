import java.util.ArrayList;


public class Option {
	private char option;
	private ArrayList<String> parameter = new ArrayList<String>();
	
	public Option(char option){
		this.option = option;
	}
	
	public char getOption(){
		return option;
	}
	
	public ArrayList<String> getParameter(){
		return parameter;
	}
	
	public void setOption(char option){
		this.option = option;
	}
	public void addParameter(String parameter){
		this.parameter.add(parameter);
	}
	
	public boolean matchParameter(String parameter){
		for(int i=0;i<this.parameter.size();i++)
		if (parameter.equals(this.parameter.get(i)))
			return true;
		
		return false;
	}
}
