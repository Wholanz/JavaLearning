package miniCAD;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ToolPanel extends JPanel implements ActionListener{
	
	public static final int HORIZONTAL_STRUT_SIZE = 15;
	
	public boolean hasImage = false;
	public ImageIcon image;
	
	public ArrayList<Line> lines = new ArrayList<Line>();
	public ArrayList<Circle> circles = new ArrayList<Circle>();
	public ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
	public ArrayList<Text> texts = new ArrayList<Text>();
	
	
	private JButton colorWhite = new JButton("WHITE");
	private JButton colorBlue = new JButton("BLUE");
	private JButton colorRed = new JButton("RED");
	private JButton colorGreen = new JButton("GREEN");
	private JButton colorPink = new JButton("PINK");
	private JButton colorYellow = new JButton("YELLOW");
	
	private ButtonGroup buttonGroup = new ButtonGroup();
	
	private JRadioButton circle = new JRadioButton("Circle");
	private JRadioButton line = new JRadioButton("Line");
	private JRadioButton rectangle = new JRadioButton("Rectangle");
	private JRadioButton text = new JRadioButton("Text");
	private JRadioButton cursor = new JRadioButton("Cursor");
	
	private Container contentPane ;
	
	private JPanel  shapePanel = new JPanel();
	private JPanel colorPanel = new JPanel();
	
	private boolean textEdit = false;
	private int oldX, oldY;
	
	private String editingText;
	
	MouseAdapter mLine,mText,mRectangle,mCircle;
	
	public ToolPanel(Container contentPane){
		
		super();
		
		
		this.contentPane = contentPane; 
		
		setForeground(Color.WHITE);
		
		colorRed.setBackground(Color.RED);
		colorBlue.setBackground(Color.BLUE);
		colorGreen.setBackground(Color.GREEN);
		colorYellow.setBackground(Color.YELLOW);
		colorWhite.setBackground(Color.WHITE);
		colorPink.setBackground(Color.PINK);
		

		colorRed.addActionListener(this);
		colorBlue.addActionListener(this);
		colorGreen.addActionListener(this);
		colorYellow.addActionListener(this);
		colorWhite.addActionListener(this);
		colorPink.addActionListener(this);
		
		
		colorPanel.add(colorWhite);
		colorPanel.add(colorBlue);
		colorPanel.add(colorRed);
		colorPanel.add(colorPink);
		colorPanel.add(colorYellow);
		colorPanel.add(colorGreen);
		
		
		
		colorPanel.setLayout(new GridLayout(2,3));
		
		buttonGroup.add(circle);
		buttonGroup.add(line);
		buttonGroup.add(rectangle);
		buttonGroup.add(cursor);
		buttonGroup.add(text);
		
		
		
		line.addActionListener(this);
		circle.addActionListener(this);
		rectangle.addActionListener(this);
		text.addActionListener(this);
		cursor.addActionListener(this);
		
		
		shapePanel.add(line);
		shapePanel.add(rectangle);
		shapePanel.add(circle);
		shapePanel.add(text);
		shapePanel.add(cursor);
		
		shapePanel.setLayout(new GridLayout(2,2));
		
		
		add(shapePanel,BorderLayout.WEST);
		add(colorPanel,BorderLayout.NORTH);
		
		
		mLine = new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent me){
				Line newLine = new Line();
				newLine.update(me.getX(), me.getY(), me.getX(), me.getY(), getForeground());
				lines.add(newLine);
			}
			
			@Override
			public void mouseDragged(MouseEvent me){
				lines.get(lines.size() - 1).endX = me.getX();
				lines.get(lines.size() - 1).endY = me.getY();
				repaint();
			}
		};
		mCircle = new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent me){
				Circle newCircle = new Circle();
				oldX = me.getX();
				oldY= me.getY();
				newCircle.update(me.getX(), me.getY(), 0, 0, getForeground());
				circles.add(newCircle);
			}
			
			@Override
			public void mouseDragged(MouseEvent me){
				int x = me.getX() < oldX ? me.getX() : oldX;
				int y = me.getY() < oldY ? me.getY() : oldY;
				int h = Math.abs(oldY - me.getY());
				int w = Math.abs(oldX - me.getX());
				circles.get(circles.size()-1).update(x, y, w, h, getForeground());
				repaint();
			}
		};
		mRectangle = new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent me){
				Rectangle newRectangle = new Rectangle();
				oldX = me.getX();
				oldY = me.getY();
				newRectangle.update(me.getX(), me.getY(), 0, 0, getForeground());
				rectangles.add(newRectangle);
			}
			
			@Override
			public void mouseDragged(MouseEvent me){
				int x = me.getX() < oldX ? me.getX() : oldX;
				int y = me.getY() < oldY ? me.getY() : oldY;
				int h = Math.abs(oldY - me.getY());
				int w = Math.abs(oldX - me.getX());
				rectangles.get(rectangles.size()-1).update(x, y, w, h, getForeground());
				repaint();
			}
		};
		mText = new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent me){
				if(!selText()){
					Text newText = new Text();
					newText.update(me.getX(), me.getY(), "Enter the text.", Color.BLUE);
					texts.add(newText);
					textEdit = true;
					editingText = "";
					requestFocus();
				}
				else{
					texts.get(texts.size()-1).color = getForeground();
					textEdit = false;
				}
				repaint();
			}
		};
		
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(textEdit){
					if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
						if(editingText.length() > 0)
							editingText = editingText.substring(0, editingText.length()-1);
					}
					else
						editingText += e.getKeyChar();
					texts.get(texts.size()-1).text = editingText;
					repaint();
				}
			}
		});
		

		
	}

	@Override
	public void actionPerformed(ActionEvent action) {

		switch(action.getActionCommand()){
		case "RED":
			setForeground(Color.RED);
			break;
		case "WHITE":
			setForeground(Color.WHITE);
			break;
		case "YELLOW":	
			setForeground(Color.YELLOW);
			break;
		case "BLUE":	
			setForeground(Color.BLUE);
			break;
		case "GREEN":	
			setForeground(Color.GREEN);
			break;
		case "PINK":	
			setForeground(Color.PINK);
			break;
		default:
			
	}
		
		switch(action.getActionCommand()){
		case "Line":
			removeMouseListener(mCircle);
			removeMouseMotionListener(mCircle);
			removeMouseListener(mRectangle);
			removeMouseMotionListener(mRectangle);
			removeMouseListener(mText);
			removeMouseMotionListener(mText);
			addMouseListener(mLine);
			addMouseMotionListener(mLine);
			break;
		case "Circle":
			removeMouseListener(mLine);
			removeMouseMotionListener(mLine);
			removeMouseListener(mRectangle);
			removeMouseMotionListener(mRectangle);
			removeMouseListener(mText);
			removeMouseMotionListener(mText);
			addMouseListener(mCircle);
			addMouseMotionListener(mCircle);
			break;
		case "Rectangle":	
			removeMouseListener(mLine);
			removeMouseMotionListener(mLine);
			removeMouseListener(mCircle);
			removeMouseMotionListener(mCircle);
			removeMouseListener(mText);
			removeMouseMotionListener(mText);
			addMouseListener(mRectangle);
			addMouseMotionListener(mRectangle);
			break;
		case "Text":	
			removeMouseListener(mLine);
			removeMouseMotionListener(mLine);
			removeMouseListener(mCircle);
			removeMouseMotionListener(mCircle);
			removeMouseListener(mRectangle);
			removeMouseMotionListener(mRectangle);
			addMouseListener(mText);
			addMouseMotionListener(mText);
			break;
		case "Cursor":	
			break;
		default:
			
	}
		
	}

	
	public boolean selText(){
		return textEdit;
	}
	
	protected void paintComponent(Graphics graphics){
		super.paintComponent(graphics);
		
		if(hasImage){
			image.paintIcon(this, graphics, 0, 0);
		}
		
		Iterator<Line> allLine = lines.iterator();
		Iterator<Circle> allCircle = circles.iterator();
		Iterator<Rectangle> allRectangle = rectangles.iterator();
		Iterator<Text> allText = texts.iterator();
		
		Line nextLine;
		while(allLine.hasNext()){
			nextLine = allLine.next();
			graphics.setColor(nextLine.color);
			graphics.drawLine(nextLine.startX, nextLine.startY, nextLine.endX, nextLine.endY);
		}
		
		Circle nextCircle;
		while(allCircle.hasNext()){
			nextCircle = allCircle.next();
			graphics.setColor(nextCircle.color);
			graphics.drawOval(nextCircle.x, nextCircle.y, nextCircle.w, nextCircle.h);
		}
		
		Rectangle nextRectangle;
		while(allRectangle.hasNext()){
			nextRectangle = allRectangle.next();
			graphics.setColor(nextRectangle.color);
			graphics.drawRect(nextRectangle.x, nextRectangle.y, nextRectangle.w, nextRectangle.h);
		}
		
		Text nextText;
		while(allText.hasNext()){
			nextText = allText.next();
			graphics.setColor(nextText.color);
			graphics.drawString(nextText.text, nextText.x, nextText.y);
		}
	}
	

		
		
		
		
	

}
