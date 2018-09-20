import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Scanner;

import javax.swing.tree.DefaultMutableTreeNode;
/**
 * A SeaPort holding lists of all docks ships and persons of the port
 * @author Daniel Russell
 *
 */
public class SeaPort extends Thing{

	protected ArrayList<Dock> docks;
	protected ArrayList<Ship> que;
	protected ArrayList<Ship> ships;
	protected ArrayList<Person> persons;
	private ListIterator<Ship> li = null;
	
	/**
	 * Creates docks ships persons and que of ship lists
	 * @param sc scanner takes line to initializes port name and index 
	 */
	public SeaPort(Scanner sc) {
		super(sc);
		docks = new ArrayList<Dock>();
		que = new ArrayList<Ship>();
		ships = new ArrayList<Ship>();
		persons = new ArrayList<Person>();
		li = que.listIterator();
	}
	
	/**
	 * adds dock to this ports list
	 * @param dock
	 */
	public void addDock(Dock dock) {
		docks.add(dock);
	}

	/**
	 * adds a ship to this port
	 * @param ship to be added
	 * 
	 */
	public void addShip(Ship ship, HashMap<Integer, Dock> hm) {
		ships.add(ship);
		if (hm.get(ship.getParent()) == null) {
			que.add(ship);
		} else {
			hm.get(ship.getParent()).addShip(ship);
		}
	}
	
	/**
	 * adds person to port
	 * @param person
	 */
	public void addPerson(Person person) {
		persons.add(person);
	}
	
	public String toString() {
		String st = "SeaPort: " + super.toString() + "\n";
		for (Dock md:docks) st += "\n > " + md;
		st += "\n\n --- List of all ships in que:";
		for (Ship ms:que) st+= "\n >" + ms;
		st += "\n\n --- List of all ships:";
		for(Ship ms : ships) st += "\n >" + ms;
		st += "\n\n --- List of all persons:";
		for (Person mp: persons) st += "\n > " + mp;
		st += "\n\n";
		return st;
	}
	
	/**
	 * Sorts all Seaport Lists by name
	 */
	public void sortName() {
		Collections.sort(que);
		Collections.sort(docks);
		Collections.sort(ships);
		Collections.sort(persons);
	}
	
	/**
	 * Sorts all ships in que by weight
	 * @return Arraylist of sorted que
	 */
	public ArrayList<Ship> sortWeight() {
		Collections.sort(que, new CompareShipWeight());
		return que;		
	}
	
	/**
	 * Sorts all ships in que by Length
	 * @return Arraylist of sorted que
	 */
	public ArrayList<Ship> sortLength() {
		Collections.sort(que, new CompareShipLength());
		return que;		
	}
	
	/**
	 * Sorts all ships in que by width
	 * @return Arraylist of sorted que
	 */
	public ArrayList<Ship> sortWidth() {
		Collections.sort(que, new CompareShipWidth());
		return que;		
	}
	
	/**
	 * Sorts all ships in que by draft
	 * @return Arraylist of sorted que
	 */
	public ArrayList<Ship> sortDraft() {
		Collections.sort(que, new CompareShipDraft());
		return que;		
	}

	/**
	 * searches persons for skill
	 * @param st
	 * @return
	 */
	public String searchSkill(String st) {
		String result = "";
		for (Person p: persons) {
			if (p.getSkill().equals(st)) {
				result += p.toString() + "\n";
			}
		}
		return result;
		
	}

	/**
	 * searches port for index
	 * @param st
	 * @return
	 */
	public String searchIndex(int st) {
		String result = "";
		for (Dock d: docks) {
			if (d.getIndex() == st) {
				result += d.toString() + "\n";
			}
		}
		for (Ship s: ships) {
			if (s.getIndex() == st) {
				result += s.toString() + "\n";
			}
		}
		for (Person p: persons) {
			if (p.getIndex() == st) {
				result += p.toString() + "\n";
			}
		}
		return result;
	}
	
	/**
	 * searches port for name
	 * @param st
	 * @return
	 */
	public String searchName(String st) {
		String result = "";
		for (Dock d: docks) {
			if (d.getName().equals(st)) {
				result += d.toString() + "\n";
			}
		}
		for (Ship s: ships) {
			if (s.getName().equals(st)) {
				result += s.toString() + "\n";
			}
		}
		for (Person p: persons) {
			if (p.getName().equals(st)) {
				result += p.toString() + "\n";
			}
		}
		return result;
	}
	
	/**
	 * Gives port data for num of docks ships and persons
	 * @return
	 */
	public int [] getnumData() {
		int[] data = new int[4];
		data[0] = docks.size();
		data[1] = que.size();
		data[2] = ships.size();
		data[3] = persons.size();
		return data;
	}
	
	
	public DefaultMutableTreeNode buildTree(DefaultMutableTreeNode root) {
		DefaultMutableTreeNode dockRoot = new DefaultMutableTreeNode("Docks");
		for (Dock d: docks) {
			DefaultMutableTreeNode dockNode = new DefaultMutableTreeNode(d.getName());
			if (d.getShip() != null) {
			dockNode.add(new DefaultMutableTreeNode(d.getShip().getName()));
			}
			dockRoot.add(dockNode);			
		}
		root.add(dockRoot);
		
		DefaultMutableTreeNode queRoot = new DefaultMutableTreeNode("Que");
		for (Ship s: que) {
			DefaultMutableTreeNode shipNode = new DefaultMutableTreeNode(s.getName());
			for (Job j: s.jobs) {
				shipNode.add(new DefaultMutableTreeNode(j.getName()));
			}
			queRoot.add(shipNode);	
			
		}
		root.add(queRoot);
		
		DefaultMutableTreeNode shipRoot = new DefaultMutableTreeNode("Ships");
		for (Ship s: ships) {
			DefaultMutableTreeNode shipNode = new DefaultMutableTreeNode(s.getName());
			for (Job j: s.jobs) {
				shipNode.add(new DefaultMutableTreeNode(j.getName()));
			}
			shipRoot.add(shipNode);			
		}
		root.add(shipRoot);
		
		DefaultMutableTreeNode personRoot = new DefaultMutableTreeNode("Persons");
		for (Person p: persons) {
			DefaultMutableTreeNode personNode = new DefaultMutableTreeNode(p.getName());
			personRoot.add(personNode);			
		}
		root.add(personRoot);
		
		return root;
	}
	
}
