import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
/**
 * uses MorseCodeTree to translate morse code to words
 * @author Daniel Russell CMSC 204
 *
 */
public class MorseCodeConverter {

	static MorseCodeTree tree;
	
	/**
	 * inits MorseCodeTree
	 */
	public MorseCodeConverter() {
		tree = new MorseCodeTree();
	}
	
	/**
	 * creates a string of tree in LNR order
	 * @return tree in LNR order
	 */
	public static String printTree(){
		tree = new MorseCodeTree();
		ArrayList<String> print = new ArrayList<String>();
		print = tree.toArrayList();
		String complete = "";
		for (String s : print) {
			complete += s;
		}
		return complete;
		
	}
	
	/**
	 * takes morse code gives back string with translation
	 * @param code morse code to be translated
	 * @return translated message
	 */
	public static String convertToEnglish(String code){
		tree = new MorseCodeTree();
		String complete = "";
		String split = " ";
		StringTokenizer token = new StringTokenizer(code,split);
		while (token.hasMoreTokens()){
			String tok = token.nextToken();
			if (tok.equals("/")){
				complete += " ";
			} else
				complete += tree.fetch(tok);
		}
		return complete;
		
	}
	
	/**
	 * takes file to convert code to message
	 * @param codeFile file containing morse code
	 * @return translated message
	 * @throws FileNotFoundException
	 */
	public static String convertToEnglish(File codeFile) throws FileNotFoundException{
		tree = new MorseCodeTree();
		String line = null;
		String complete = "";
		
		try {
			FileReader fileReader = 
					new FileReader(codeFile);

			BufferedReader bufferedReader = 
					new BufferedReader(fileReader);

			while((line = bufferedReader.readLine()) != null) {
				String split = " ";
				StringTokenizer seperate = new StringTokenizer(line, split);
				while (seperate.hasMoreTokens()) {
					String add = seperate.nextToken();
					if (add.equals("/")){
						complete += " ";
					} else
						complete += tree.fetch(add);
				}
			}
			bufferedReader.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return complete;

	}
	
	
	
	
	
}
