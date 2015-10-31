import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;



public class SpreadSheet extends JFrame implements ActionListener,TableModelListener{
	
	public static final int WIDTH = 480;
	public static final int HEIGHT = 640;
	
	public JButton deleteLine = new JButton("DeleteLine");
	public JButton addLine = new JButton("AddLine");
	
	private JMenu startMenu = new JMenu("Start");
	private JMenuItem saveItem = new JMenuItem("Save file");
	private JMenuItem openItem = new JMenuItem("Open file");
	
	private JMenuBar menuBar = new JMenuBar();
	
	private SpreadSheet sheet ;
	private JTable table ;
	
	private JScrollPane scrollPane ;
	
	private DefaultTableModel tbmodel ;
	
	
	public static void main(String[] args) {
		
		
		SpreadSheet sheet = new SpreadSheet();
		
		
		sheet.setVisible(true);
		
	}
	
	
	public SpreadSheet(){
		
		startMenu.add(saveItem);
		startMenu.add(openItem);
		menuBar.add(startMenu);
		setJMenuBar(menuBar);
		
		saveItem.addActionListener(this);
		openItem.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		
		addLine.addActionListener(this);
		deleteLine.addActionListener(this);
		
		buttonPanel.add(addLine);
		buttonPanel.add(deleteLine);
		GridBagLayout layout = new GridBagLayout();
		buttonPanel.setLayout(layout);
		
		GridBagConstraints gridBag = new GridBagConstraints();
		gridBag.fill = GridBagConstraints.BOTH;
		
		gridBag.gridwidth = 0;
		gridBag.weightx = 0;
		gridBag.weighty = 0;
		
		layout.setConstraints(addLine, gridBag);
		layout.setConstraints(deleteLine,gridBag);
		
		
		getContentPane().add(buttonPanel,BorderLayout.EAST);
		
		setTitle("Sheet");
		setSize(HEIGHT,WIDTH);
		setVisible(true);
		
		
		
		
		addWindowListener( new WindowDestroyer() );
		
		
	}
	
	public void saveFile(File file){
		
		BufferedWriter writer;
		String data = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			
			
			int rowCount = tbmodel.getRowCount();
			int colCount = tbmodel.getColumnCount();
			
			data = "";
			for(int i = 0; i < colCount; i++){
				data += tbmodel.getColumnName(i);
				data += ",";
			}
			
			data = data.substring(0,data.length()-1);
			writer.write(data);
			writer.newLine();
			
			
			for(int i = 0; i < rowCount; i++){
				data = "";
				for(int j = 0; j < colCount; j++){
					data = data + (String)tbmodel.getValueAt(i, j);
					data += ",";
				}
				data = data.substring(0,data.length()-1);
				writer.write(data);
				writer.newLine();
			}
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			
		}catch(NullPointerException e){
			System.out.println("Empty table");
		}
		
		
	}
		
	
	public void openFile(File file){
		
		
		tbmodel = new DefaultTableModel();
		
		BufferedReader reader;
		
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String data;
			String[] columns = null;
			
			if((data = reader.readLine())!=null){
				String dataTemp = data.replace(" ", "");
				columns = dataTemp.split(",");
				
				for(int i =0; i<columns.length;i++){
					tbmodel.addColumn(columns[i]);
				}
				
			}
			while((data = reader.readLine())!=null){
				String dataTemp = data.replace(" ", "");
				columns = dataTemp.split(",");
				tbmodel.addRow(columns);
			}
			
			
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find the file! Please enter again!");
			return;
		} catch (IOException e) {
			System.out.println("File read error!");
			e.printStackTrace();
		}
		
		
		table = new JTable(tbmodel);
		
		if(scrollPane!=null)
		getContentPane().remove(scrollPane);
		
		scrollPane = new JScrollPane(table);
		
		getContentPane().add(scrollPane,BorderLayout.WEST);
		
		table.setFillsViewportHeight(true);
		this.setVisible(true);
	}

	
	
	@Override
	public void actionPerformed(ActionEvent action) {
		
		switch(action.getActionCommand()){
		case "Save file":
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.csv", "csv");
			JFileChooser dialog = new JFileChooser();
			dialog.setFileFilter(filter);
			dialog.setDialogTitle("Save .CSV file");
			int result = dialog.showSaveDialog(null);
			if(result == JFileChooser.APPROVE_OPTION){
				File file = dialog.getSelectedFile();
				if(!file.getPath().endsWith(".csv")){
					file = new File(file.getPath() + ".csv");
				}
				saveFile(file);
			}
			break;
		case "Open file":
			FileNameExtensionFilter openFilter = new FileNameExtensionFilter("*.csv", "csv");
			JFileChooser openDialog = new JFileChooser();
			openDialog.setFileFilter(openFilter);
			openDialog.setDialogTitle("Open .CSV file");
			int openResult = openDialog.showOpenDialog(null);
			if(openResult == JFileChooser.APPROVE_OPTION){
				File file = openDialog.getSelectedFile();
				
				openFile(file);
				
			}
			break;
		case "AddLine":
			tbmodel.addRow(new Object[]{});
			break;
		case "DeleteLine":
			
			int[] selRows = table.getSelectedRows();
			
			for(int k: selRows){
				tbmodel.removeRow(k);
			}
			
			break;
	}
		
	}


	@Override
	public void tableChanged(TableModelEvent table) {
		
		int column = table.getColumn();
		int row = table.getFirstRow();
		tbmodel.fireTableCellUpdated(row, column);
		
	}

}
