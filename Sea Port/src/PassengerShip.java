import java.util.Scanner;
/**
 * Passenger ship that holds the number of passengers, the number of rooms, and the number of occupied rooms
 * @author Daniel Russell
 * CMSC 335
 *
 */
public class PassengerShip extends Ship{
	
	private int numberOfOccupiedRooms, numberOfPassengers, numberOfRooms;
	
	/**
	 * takes in num of passengers, num of roms and num of occupied rooms
	 * @param sc
	 */
	public PassengerShip(Scanner sc) {
		super(sc);
		if (sc.hasNextInt())this.numberOfPassengers = sc.nextInt();
		if (sc.hasNextInt())this.numberOfRooms = sc.nextInt();
		if (sc.hasNextInt())this.numberOfOccupiedRooms = sc.nextInt();
	}

	
	
	@Override
	public String toString() {
		String st = "Passenger ship: " + super.toString();
		if (jobs.size() == 0)
			return st;
		for (Job mj: jobs) st+= "\n   -" + mj;
		return st;
	}
	
}
