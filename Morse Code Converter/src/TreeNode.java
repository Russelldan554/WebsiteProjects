/**
 * holds data for object in tree in MorseCodeTree
 * @author Daniel Russell CMSC 204
 *
 * @param <T> generic type
 */
public class TreeNode<T> {

		protected TreeNode<T> lc;
		protected TreeNode<T> rc;
		protected T node;

		/**
		 * initializes node to hold objects information
		 * @param dataNode
		 */
		public TreeNode(T dataNode){
			node = dataNode;
			lc = null;
			rc = null;
		}
		
		/**
		 * used for making deep copies
		 * @param node
		 */
		public TreeNode(TreeNode<T> node){
			TreeNode<T> copy = new TreeNode<T>(node);
		}
		
		/**
		 * 
		 * @return the data within the tree
		 */
		public T getData(){
			return node;
		}
		
	}