import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
/**
 * Graphical interface for the ingest of a file containing a port and all of the dock, ships and workers for the port.
 * @author Daniel Russell CMSC 335
 *
 */
public class SeaPortProgram extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final JFileChooser jfc = new JFileChooser(".");
	private World world;
	final private String[] searchTerms= {"", "Name", "Index","Skill"};
	final private String[] sortTerms= {"","Name", "Weight","Length","Width","Draft"};
	JPanel jobs;
	
	/**
	 * sets size and frame of GUI calls method to create interface objects
	 */
	public SeaPortProgram () {
		this.setSize(870,600);
		this.setMinimumSize(new Dimension(550,600));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("SeaPort");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainFrame();
		this.setVisible(true);
	}

	/**
	 * creates text objects and interface objects for GUI. Also handles button actions.
	 */
	private void mainFrame() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		
		
		JPanel filechooser = new JPanel(new FlowLayout());
		JButton fileChoice = new JButton("Open a File");
		
		JLabel mainLabel = new JLabel("Select a file to enter Port information");
		filechooser.add(mainLabel);
		filechooser.add(fileChoice);
		
		JTextArea ta = new JTextArea(30,30);
		ta.setFont(new java.awt.Font ("Monospaced", 0, 12));
		JScrollPane sp = new JScrollPane(ta); 
		
		JPanel search = new JPanel(new FlowLayout());
		JTextField searchbar = new JTextField(15);
		JButton searchbtn = new JButton("Search");
		JButton resetbtn = new JButton("Reset");
		JButton sortbtn = new JButton("Sort");
		JComboBox<String> searchList = new JComboBox<String>(searchTerms);

		search.add(searchbar);
		search.add(searchList);
		search.add(searchbtn);	
		
		JComboBox<String> sortList = new JComboBox<String>(sortTerms);
				
		JPanel sortName = new JPanel(new FlowLayout());
		sortName.add(sortList);
		sortName.add(sortbtn);
		sortName.add(resetbtn);
		
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Border sortborder = BorderFactory.createTitledBorder(loweredetched, "Sort");
		Border searchborder = BorderFactory.createTitledBorder(loweredetched, "Search");
		search.setBorder(searchborder);
		sortName.setBorder(sortborder);
		
		JPanel bottom = new JPanel(new FlowLayout());
		bottom.add(search);
		bottom.add(sortName);
		
		JPanel bottom2 = new JPanel(new GridLayout(2,0,5,5));
		bottom2.add(filechooser);
		bottom2.add(bottom);
		
		JTabbedPane tb = new JTabbedPane();
		
				
		
		fileChoice.addActionListener((ActionEvent e) -> {
			int returnVal = jfc.showOpenDialog(SeaPortProgram.this);
			
			try {
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = jfc.getSelectedFile();
					jobs = new JPanel();
					
					JTable table = new JTable(new DefaultTableModel(new Object[]{"Job", "Duration", "Skills Needed", "Workers needed", "Workers", "Status"},0));
					DefaultTableModel model = (DefaultTableModel) table.getModel();


					Scanner sc = new Scanner(file);
					world = new World(model);
					world.readFile(sc);
					ta.setText(world.toString());
					ta.setCaretPosition(0);
					jobs = world.getPanel();
					jobs.setLayout (new GridLayout (0,4));	
					tb.addTab("Text", sp);
					
					JScrollPane nsp = new JScrollPane(jobs);
					tb.addTab("Jobs", nsp);
					
					sc.close();
					System.out.println(world.numOfData());
					
					//Start of tree
					JTree jt = new JTree(world.buildTree(new DefaultMutableTreeNode("World")));
					JScrollPane jsp = new JScrollPane(jt);
					tb.addTab("Port Tree", jt);
					
					
					
					JScrollPane resources = new JScrollPane(table);
					tb.addTab("Resources", resources);
	
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		});
		
		searchbtn.addActionListener((ActionEvent e) -> {
			if (world != null) {
				String selected = (String) searchList.getSelectedItem();
				
				
				if (selected.equals("Skill")) {
					String data = world.searchSkill(searchbar.getText());
					if (!data.equals("")) {
						ta.setText(data);
					} else {
						ta.setText("No Results Found");
					}
						
				}
				if (selected.equals("Name")) {
					String data = world.searchName(searchbar.getText());
					if (!data.equals("")) {
						ta.setText(data);
					} else {
						ta.setText("No Results Found");
					}
				}
				if (selected.equals("Index")) {
					String data = world.searchIndex(searchbar.getText());
					if (!data.equals("")) {
						ta.setText(data);
					} else {
						ta.setText("No Results Found");
					}
				}
				
				ta.setCaretPosition(0);
			} else {
				ta.setText("Select File to begin");
			}
		});
		
		resetbtn.addActionListener((ActionEvent e) -> {
			if (world != null) 
				ta.setText(world.toString());
			ta.setCaretPosition(0);
		});
		
		sortbtn.addActionListener((ActionEvent e) -> {
			String selected = (String) sortList.getSelectedItem();
			//System.out.println(Thread.activeCount());
			if (world != null && Thread.activeCount() < 3) {
				if (selected.equals("Name")) {
					world.sortName();
					ta.setText(world.toString());
					ta.setCaretPosition(0);
				} 
				if (selected.equals("Weight")) {
					System.out.println(world.sortWeight());
					ta.setText(world.toString());
					ta.setCaretPosition(0);
				}
				if (selected.equals("Length")) {
					System.out.println(world.sortLength());
					ta.setText(world.toString());
					ta.setCaretPosition(0);
				}
				if (selected.equals("Width")) {
					System.out.println(world.sortWidth());
					ta.setText(world.toString());
					ta.setCaretPosition(0);
				}
				if (selected.equals("Draft")) {
					System.out.println(world.sortDraft());
					ta.setText(world.toString());
					ta.setCaretPosition(0);
				}
			} else
				ta.setText("Threads are still running or \nworld is not created \nselect Reset to bring back world info");
			
		});
		
		//mainPanel.add(filechooser, BorderLayout.NORTH);
		mainPanel.add(tb, BorderLayout.CENTER);
		mainPanel.add(bottom2, BorderLayout.NORTH);
		this.add(mainPanel);
	}
	
	/**
	 * starts the program
	 * @param args
	 */
	public static void main(String[] args) {
		new SeaPortProgram();
	}


}
