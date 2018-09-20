import java.util.ArrayList;
/**
 * builds a morse code tree used to convert morsse code to letters
 * @author Daniel Russell CMSC 204
 *
 */
public class MorseCodeTree implements LinkedConverterTreeInterface<String>{

	ArrayList<String> list;
	TreeNode<String> root;
	String letter = " ";
	
	/**
	 * constructor - builds tree and inits root
	 */
	public MorseCodeTree() {
		root = new TreeNode<String>("");
		root.lc = null;
		root.rc = null;
		buildTree();
	}
	
	@Override
	public TreeNode<String> getRoot() {
		return root;
	}

	@Override
	public void setRoot(TreeNode<String> newNode) {
		root = newNode;		
	}

	@Override
	public LinkedConverterTreeInterface<String> insert(String code, String result) {
		addNode(getRoot(),code, result);
		return this;
	}

	@Override
	public void addNode(TreeNode<String> root, String code, String letter) {

		if (!code.equals(null)){
			if (code.charAt(0) == '.'){
				if (root.lc != null) {
					addNode(root.lc, code.substring(1), letter);
				} else
					root.lc = new TreeNode<String>(letter);
			} else {
				if (root.rc != null) {
					addNode(root.rc, code.substring(1), letter);
				} else
					root.rc = new TreeNode<String>(letter);
			}
		} 

	}

	@Override
	public String fetch(java.lang.String code) {
		return fetchNode(getRoot(), code);
	}

	@Override
	public String fetchNode(TreeNode<String> root, String code) {
		
		if (code.length() > 0) {	
			if (code.charAt(0) == '.'){
				if (root.lc != null) {
					fetchNode(root.lc, code.substring(1));
				} 
			} else {
				if (root.rc != null) {
					fetchNode(root.rc, code.substring(1));
				}
			}		
		} else
			letter = root.getData();
		
		return letter;
			
	}

	@Override
	public LinkedConverterTreeInterface<String> delete(String data) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("operation not supported.");
	}

	@Override
	public LinkedConverterTreeInterface<String> update() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("operation not supported.");
	}

	@Override
	public void buildTree() {
		insert(".","e");
		insert("-","t");
		insert("..","i");
		insert(".-","a");
		insert("-.","n");
		insert("--","m");
		insert("...","s");
		insert("..-","u");
		insert(".-.","r");
		insert(".--","w");
		insert("-..","d");
		insert("-.-","k");
		insert("--.","g");
		insert("---","o");
		insert("....","h");
		insert("...-","v");
		insert("..-.","f");
		insert(".-..","l");
		insert(".--.","p");
		insert(".---","j");
		insert("-...","b");
		insert("-..-","x");
		insert("-.-.","c");
		insert("-.--","y");
		insert("--..","z");
		insert("--.-","q");		
	}

	@Override
	public ArrayList<String> toArrayList() {
		list = new ArrayList<String>();
		LNRoutputTraversal(getRoot(), list);
		return list;
	}

	@Override
	public void LNRoutputTraversal(TreeNode<String> root, ArrayList<String> list) {
		if (root.lc!=null){
			LNRoutputTraversal(root.lc, list);
		}
		list.add(root.getData() + " ");
		if (root.rc != null) {
			LNRoutputTraversal(root.rc, list);
		}
	}

}
