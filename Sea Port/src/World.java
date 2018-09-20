import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
/**
 * Handler for reading of port file. Creates SeaPort object and calls ports to add objects based on the files specified.
 * @author Daniel Russell
 *
 */
public class World extends Thing{

	protected ArrayList<SeaPort> ports;
	private PortTime time;
	JPanel jobFrame; 
	protected ArrayList<Job> jobs;
	DefaultTableModel model;
	
	/**
	 * Takes scanner object containing file and separates it into lines for process method to handle
	 * @param sc scanner of file to be ingested
	 */
	public World(DefaultTableModel model) {
		super();
		ports = new ArrayList<SeaPort>();
		jobFrame = new JPanel();
		jobs = new ArrayList<Job>();
		this.model = model;
	}
	
	
	
	/**
	 * Takes in lines from scanner and adds them to SeaPort object.
	 * @param st line with qualifying content from scanner
	 */
	public void readFile(Scanner sc) {
		HashMap<Integer, SeaPort> portMap = new HashMap<Integer, SeaPort>();
		HashMap<Integer, Dock> dockMap = new HashMap<Integer, Dock>();
		while (sc.hasNext()) {
			String st = sc.nextLine();
			if (st.contains("//")) {
				
			} else
		
				System.out.println("Processing >" + st + "<");
				Scanner line = new Scanner(st);
				
				if(!line.hasNext()) {
					
				} else {					
					try {
						switch (line.next()) {
						case "port"  : SeaPort port = new SeaPort(line);
									   ports.add(port);
									   portMap.put(port.getIndex(), port);
									   break;
									   
						case "dock"	 : Dock dock  = new Dock(line);
									   portMap.get(dock.getParent()).addDock(dock);;
									   dockMap.put(dock.getIndex(), dock);
									   break;
									   
						case "pship" : PassengerShip pship  = new PassengerShip(line);
									   if (portMap.get(pship.getParent()) != null) {
										   portMap.get(pship.getParent()).addShip(pship, dockMap);
									   } else
										   portMap.get(dockMap.get(pship.getParent()).getParent()).addShip(pship, dockMap);
									   break;
									   
						case "cship" : CargoShip cship  = new CargoShip(line);
									   if (portMap.get(cship.getParent()) != null) {
										   portMap.get(cship.getParent()).addShip(cship, dockMap);
									   } else
										   portMap.get(dockMap.get(cship.getParent()).getParent()).addShip(cship, dockMap);
									   break;
									   
						case "person": Person person = new Person(line);
									   portMap.get(person.getParent()).addPerson(person);
									   break;
						case "job"   : Job job = new Job(line, jobFrame, this, model);
									   Ship s = job.ship;
									   s.addJob(job);
									   jobs.add(job);
									   break;
						}
					} catch (NullPointerException e) {
						System.out.println("null");
					}
				}
				line.close();
		}
		sc.close();
	}
	
	public JPanel getPanel() {
		return jobFrame;
	}
	
	/**
	 * 
	 * @param index jobs parent ship index
	 * @return seaport object
	 */
	public SeaPort getJobsPort(Ship ship) {
		for (SeaPort p : ports) {
			for (Ship s : p.ships) {
				if (s.getIndex() == ship.getIndex()) {
					return p;
				}
			}
		}
		return null;
	}
	
	public Ship getShip (int index) {
		for (SeaPort p : ports) {
			for(Ship s: p.ships) {
				if (s.getIndex() == index) {
					return s;
				}
			}
		}
		return null;
	}
	
	public Dock getDock (int index) {
		for (SeaPort p : ports) {
			for(Dock d: p.docks) {
				if (d.getIndex() == index) {
					return d;
				}
			}
		}
		return null;
	}
	
	/**
	 * Sorts all port lists by name
	 */
	public void sortName() {
		for (SeaPort port : ports) {
			port.sortName();
		}
	}
	
	/**
	 * sorts all ships in port que by weight
	 * @return list of all ports sorted
	 */
	public String sortWeight() {
		String st = "Name                        Weight\n";
		for (SeaPort port : ports) {
			ArrayList<Ship> ls = port.sortWeight();
			st += port.getName() + "\n";
			for (Ship s : ls) {
				st += s.toString() + " " + s.getWeight() + "\n";
			}
			st += "\n";
		}
		return st;
	}
	
	/**
	 * sorts all ships in port que by length
	 * @return list of all ports sorted
	 */
	public String sortLength() {
		String st = "Name                        Length\n";
		for (SeaPort port : ports) {
			ArrayList<Ship> ls = port.sortLength();
			st += port.getName() + "\n";
			for (Ship s : ls) {
				st += s.toString() + " " + s.getLength() + "\n";
			}
			st += "\n";
		}
		return st;
	}
	
	/**
	 * sorts all ships in port que by Width
	 * @return list of all ports sorted
	 */
	public String sortWidth() {
		String st = "Name                        Width \n";
		for (SeaPort port : ports) {
			ArrayList<Ship> ls = port.sortWidth();
			st += port.getName() + "\n";
			for (Ship s : ls) {
				st += s.toString() + " " + s.getWidth() + "\n";
			}
			st += "\n";
		}
		return st;
	}
	
	/**
	 * sorts all ships in port que by Draft
	 * @return list of all ports sorted
	 */
	public String sortDraft() {
		String st = "Name                        Draft\n";
		for (SeaPort port : ports) {
			ArrayList<Ship> ls = port.sortDraft();
			st += port.getName() + "\n";
			for (Ship s : ls) {
				st += s.toString() + " " + s.getDraft() + "\n";
			}
			st += "\n";
		}
		return st;
	}
	
	
	/**
	 * Searches ports for specified name
	 * @param st name of object to be found
	 * @return all names matching search
	 */
	public String searchName(String st) {
		String result = "";
		for (SeaPort port : ports) {
			if (port.getName().equals(st)) 
				result += port.toString();
			result += port.searchName(st);
		}
		return result;
	}
	
	/**
	 * Searches ports for specified index
	 * @param st index of object to be found
	 * @return all objects matching index
	 */
	public String searchIndex(String st) {
		String result = "";
		try {
		int index = Integer.parseInt(st);
		
		for (SeaPort port : ports) {
			if (port.getIndex() == index) 
				result += port.toString();
			result += port.searchIndex(index);
		}
		} catch (NumberFormatException e) {
			result = "Not a valid index";
		}
		return result;
	}

	/**
	 * Searches persons for specified skill
	 * @param st skill to be found in persons
	 * @return all persons matching that skill
	 */
	public String searchSkill(String st) {
		String result = "";
		for (SeaPort port : ports) {
			result += port.searchSkill(st);
		}
		return result;
	}
	
	/**
	 * Gives number of docks, ships, ships in que and persons
	 * @return
	 */
	public String numOfData () {
		String st = "Number of Ports: " + ports.size();
		int docks, ships, que, persons;
		docks = ships = que = persons = 0;
		for (SeaPort port: ports) {
			int[] data = port.getnumData();
			docks += data[0];
			que += data [1];
			ships += data[2];
			persons += data[3];
		}
		st += " \nNumber of Docks: " + docks
				+ " \nNumber of Ships: " + ships
				+ " \nNumber of Ships in que: " + que
				+ " \nNumber of Persons: " + persons;
		
		return st;
	}
	
	public String toString() {
		String st = "";
		for (SeaPort port : ports) {
			st += port.toString();
		}
		return st;
	}
	
	public DefaultMutableTreeNode buildTree(DefaultMutableTreeNode root) {
		
		for (SeaPort port : ports) {
			DefaultMutableTreeNode portNode = new DefaultMutableTreeNode(port.getName());
			portNode = port.buildTree(portNode);
			root.add(portNode);
		}		
		return root;
	}



	
}
