import java.util.Scanner;
/**
 * Creates a Cargo ship with a cargo value, volume and weight.
 * @author Daniel Russell
 *
 */
public class CargoShip extends Ship{

	private double cargoValue, cargoVolume, cargoWeight;
	
	/**
	 * takes in name, int index, int parent, Double weight, double volume and double value
	 * @param sc
	 */
	public CargoShip(Scanner sc) {
		super(sc);
		if (sc.hasNextDouble()) this.cargoWeight = sc.nextDouble();
		if (sc.hasNextDouble()) this.cargoVolume = sc.nextDouble();
		if (sc.hasNextDouble()) this.cargoValue = sc.nextDouble();		
	}

	public String toString() {
		return "Cargo ship: " + super.toString();
	}
	
	
	
}
