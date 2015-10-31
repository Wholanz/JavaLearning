package miniCAD;
import java.awt.Color;


public class Text {
	String text;
	int x;
	int y;
	Color color = Color.WHITE;
	
	public void update(int newX, int newY, String newText, Color c){
		
		x = newX;
		y = newY;
		text = newText;
		color = c;
	}
}
