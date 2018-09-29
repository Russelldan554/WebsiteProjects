import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
* @author Daniel Russell
* 
* GUI for morse code converter
*/
public class MorseGUI extends JFrame {

	
	public static void main(String[] args) {		
		//.... . .-.. .-.. --- / .-- --- .-. .-.. -.. Hello World in morse code
		JFrame frame = new JFrame("Morse Code Converter");
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel input = new JPanel(new FlowLayout());
		JTextField morseString = new JTextField(".... . .-.. .-.. --- / .-- --- .-. .-.. -..");
		morseString.setPreferredSize(new Dimension(200,30));
		JLabel enterString = new JLabel("Enter Morse code to be converted:");
		
		JButton convert = new JButton("Convert");
		
		input.add(enterString);
		input.add(morseString);
		input.add(convert);
		
		JTextArea result = new JTextArea();
		result.setPreferredSize(new Dimension(200,100));
		
		convert.addActionListener((ActionEvent e) -> {
			result.setText(MorseCodeConverter.convertToEnglish(morseString.getText()));
		});
		
		JButton readFile = new JButton("Convert File");
		readFile.addActionListener((ActionEvent e) -> {
			File file = getFile();
			try {
				result.setText(MorseCodeConverter.convertToEnglish(file));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		});
		
		input.add(readFile);
		
		panel.add(input, BorderLayout.CENTER);
		panel.add(result, BorderLayout.SOUTH);
		frame.add(panel);
		frame.setVisible(true);
		frame.setSize(500, 250);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	public static File getFile(){
		JFileChooser chooser = new JFileChooser("./");
		int status;
		File file = null;

		chooser.setDialogTitle("Select Input File");
		status = chooser.showOpenDialog(null);

		if(status == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				file = chooser.getSelectedFile();
			}
			catch (Exception e) {

				JOptionPane.showMessageDialog(null, "There is a problem with this file", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		return file;
	}
}
