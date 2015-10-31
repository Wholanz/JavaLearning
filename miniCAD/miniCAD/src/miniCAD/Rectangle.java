package miniCAD;
import java.awt.Color;

public class Rectangle{
	int x;
	int y;
	int h;
	int w;
	Color color = Color.WHITE;
	
	public void update(int newX, int newY, int newW, int newH, Color c){
		
		x = newX;
		y = newY;
		h = newH;
		w = newW;
		color = c;
	}
}