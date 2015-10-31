package miniCAD;
import java.awt.Color;


public class Line{
	int startX;
	int startY;
	int endX;
	int endY;
	Color color = Color.WHITE;
	
	public void update(int newStartX, int newStartY, int newEndX, int newEndY, Color c){
		startX = newStartX;
		startY =newStartY ;
		endX = newEndX;
		endY = newStartY;
		color = c;
	}
}