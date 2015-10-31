package miniCAD;
import java.awt.Color;

public class Circle{
	int x;
	int y;
	int h;
	int w;
	Color color = Color.WHITE;
	
	public void update(int newX, int newY, int newW, int newH, Color c){
		x = newX;
		y = newY;
		h = newW;
		w = newH;
		color = c;
	}
}