import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
/**
 * creates a ship with weight, length, width, and draft 
 * @author Daniel Russell
 * CMSC 335
 *
 */
public class Ship extends Thing{

	private PortTime arrivalTime, dockTime;
	private double draft, length, weight, width;
	protected ArrayList<Job> jobs;
	
	/**
	 * takes in weight, length, width, and draft
	 * @param sc
	 */
	public Ship(Scanner sc) {
		super(sc);
		if (sc.hasNextDouble()) this.weight = sc.nextDouble();
		if (sc.hasNextDouble())this.length = sc.nextDouble();
		if (sc.hasNextDouble())this.width = sc.nextDouble();
		if (sc.hasNextDouble())this.draft = sc.nextDouble();
		jobs = new ArrayList<Job>();
	}
	
	public double getWeight() {	return weight;}
	
	public double getLength() {	return length;}
	
	public double getWidth() {	return width;}
	
	public double getDraft() {	return weight;}
	
	public void addJob (Job j) {
		jobs.add(j);
	}
	
	
}

/**
 * Implements comparator to sort by weight for Ship objects.
 * @author Daniel Russell
 * CMSC 335
 *
 */
class CompareShipWeight implements Comparator<Ship> {

	@Override
	public int compare(Ship s1, Ship s2) {
		if (s1.getWeight() == s2.getWeight()) {
			return s1.compareTo(s2);
		}
		return (int) s1.getWeight() - (int) s2.getWeight();
	}
	
}

/**
 * Implements comparator to sort by Length for Ship objects.
 * @author Daniel Russell
 * CMSC 335
 *
 */
class CompareShipLength implements Comparator<Ship> {

	@Override
	public int compare(Ship s1, Ship s2) {
		if (s1.getLength() == s2.getLength()) {
			return s1.compareTo(s2);
		}
		return (int) s1.getLength() - (int) s2.getLength();
	}
	
}

/**
 * Implements comparator to sort by Width for Ship objects.
 * @author Daniel Russell
 * CMSC 335
 *
 */
class CompareShipWidth implements Comparator<Ship> {

	@Override
	public int compare(Ship s1, Ship s2) {
		if (s1.getWidth() == s2.getWidth()) {
			return s1.compareTo(s2);
		}
		return (int) s1.getWidth() - (int) s2.getWidth();
	}
	
}

/**
 * Implements comparator to sort by Draft for Ship objects.
 * @author Daniel Russell
 * CMSC 335
 *
 */
class CompareShipDraft implements Comparator<Ship> {

	@Override
	public int compare(Ship s1, Ship s2) {
		if (s1.getDraft() == s2.getDraft()) {
			return s1.compareTo(s2);
		}
		return (int) s1.getDraft() - (int) s2.getDraft();
	}
	
}