import java.util.Scanner;
/**
 * Thing describes a thing object that has an index, name and parent. constructor takes in scanner for string name, int index and int parent.
 * @author dan
 *
 */
public class Thing implements Comparable<Thing>{

	private int index;
	private String name;
	protected int parent;
	
	/**
	 * default constructor sets thing to overall parent of world
	 */
	public Thing() {
		this.index = -1;
		this.name = "world";
		this.parent = -1;
	}
	
	/**
	 * takes in scanner for string name, int index and int parent
	 * @param sc
	 */
	public Thing (Scanner sc) {
		if (sc.hasNext()) this.name = sc.next();
		if (sc.hasNextInt())this.index = sc.nextInt();
		if (sc.hasNextInt())this.parent = sc.nextInt();
	}
	
	/**
	 * compares objects index to other thing objects index returns zero if true
	 */
	@Override
	public int compareTo(Thing thing) {
		return this.name.compareTo(thing.name);
	}

	/**
	 * gives parent of thing
	 * @return parent
	 */
	public int getParent() {
		return parent;
	}
	
	/**
	 * Gives index of object
	 * @return index
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Gives name of thing
	 * @return Name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * returns name index
	 */
	public String toString() {
		return name + " " + index;
	}
}
