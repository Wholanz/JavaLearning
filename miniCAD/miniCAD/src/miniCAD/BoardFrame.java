package miniCAD;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BoardFrame extends JFrame implements ActionListener{
	
	public static final int WIDTH = 480;
	public static final int HEIGHT = 640;
	
	private Container contentPane = getContentPane();
	
	private ToolPanel toolPanel = new ToolPanel(contentPane);
	
	private JMenu startMenu = new JMenu("Start");
	private JMenuItem saveItem = new JMenuItem("Save file");
	private JMenuItem openItem = new JMenuItem("Open file");
	
	private JMenuBar menuBar = new JMenuBar();
	
	
	public static void main(String[] args) {
		BoardFrame board = new BoardFrame();
		board.setVisible(true);
		board.setSize(HEIGHT,WIDTH);
		board.setTitle("DrawBoard");
		board.setLocationRelativeTo(null);
		board.setVisible(true);
	}
	
	public BoardFrame(){
		
		final MouseAdapter maLine = new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent me){
				Line newLine = new Line();
				newLine.update(me.getX(), me.getY(), me.getX(), me.getY(), Color.BLACK);
				toolPanel.lines.add(newLine);
			}
			
			@Override
			public void mouseDragged(MouseEvent me){
				toolPanel.lines.get(toolPanel.lines.size() - 1).endX = me.getX();
				toolPanel.lines.get(toolPanel.lines.size() - 1).endY = me.getY();
				toolPanel.repaint();
			}
		};
		
		add(toolPanel);
		
		toolPanel.setBackground(Color.BLACK);
		toolPanel.setForeground(Color.WHITE);
		
		saveItem.addActionListener(this);
		startMenu.add(saveItem);
		startMenu.add(openItem);
		menuBar.add(startMenu);
		setJMenuBar(menuBar);
		
		saveItem.addActionListener(this);
		openItem.addActionListener(this);
		
		addWindowListener( new WindowDestroyer() );
		
		toolPanel.setFocusable(true);
		
	
	
	}
	
	
	


	@Override
	public void actionPerformed(ActionEvent action) {
		
		switch(action.getActionCommand()){
			case "Save file":
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.jpg", "jpg");
				JFileChooser dialog = new JFileChooser();
				dialog.setFileFilter(filter);
				dialog.setDialogTitle("Save jpg file");
				int result = dialog.showSaveDialog(null);
				if(result == JFileChooser.APPROVE_OPTION){
					File file = dialog.getSelectedFile();
					if(!file.getPath().endsWith(".jpg")){
						file = new File(file.getPath() + ".jpg");
					}
					BufferedImage image = new BufferedImage(toolPanel.getWidth(), toolPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
					Graphics2D g2 = image.createGraphics();
					toolPanel.paint(g2);
					try {
						ImageIO.write(image, "jpeg", file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				break;
			case "Open file":
				FileNameExtensionFilter openFilter = new FileNameExtensionFilter("*.jpg", "jpg");
				JFileChooser openDialog = new JFileChooser();
				openDialog.setFileFilter(openFilter);
				openDialog.setDialogTitle("Open jpg file");
				int openResult = openDialog.showOpenDialog(null);
				if(openResult == JFileChooser.APPROVE_OPTION){
					File file = openDialog.getSelectedFile();
					toolPanel.image = new ImageIcon(file.getPath());
					toolPanel.hasImage = true;
					toolPanel.repaint();
				}
				break;
		}
		
	}
	
	
	

}
