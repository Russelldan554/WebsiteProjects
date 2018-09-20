import java.util.Scanner;
/**
 * Creates a Dock that has a Ship 
 * @author Daniel Russell
 *
 */
public class Dock extends Thing {
	
	private Ship ship;
	private int shipIndex;
	
	/**
	 * Takes dock name index parent and ship index
	 * @param sc
	 */
	public Dock(Scanner sc) {
		super(sc);
		if (sc.hasNextInt()) this.shipIndex = sc.nextInt();
		ship = null;
	}

	/**
	 * adds ship object to dock
	 * @param ship
	 */
	public void addShip(Ship ship) {
		this.ship = ship;
	}
	
	/**
	 * gives current docked ship index
	 * @return
	 */
	public int getCurrentShip() {
		return this.shipIndex;
	}
	
	public Ship getShip() {
		return ship;
	}
	
	public String toString() {
		String st = "Dock: " + super.toString() + "\n";
		if (!(ship == null)) {
			st += "  Ship:" + ship.toString() + "\n";
		}
		
		return st;
	}
}
