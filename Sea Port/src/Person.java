import java.util.Scanner;
/**
 * Person describes a person with a skill
 * @author Daniel Russell
 * CMSC 335
 *
 */
public class Person extends Thing{

	private String skill;
	boolean working;
	
	/**
	 * initializes person with name index and parent as well as skill
	 * @param sc
	 */
	public Person(Scanner sc) {
		super(sc);
		if (sc.hasNext()) this.skill = sc.next();
		working = false;
	}	
	
	/**
	 * Gives skill of person
	 * @return string Skill
	 */
	public String getSkill() {
		return skill;
	}
	
	public String toString() {
		return "Person: " + super.toString() + " "+ skill;
	}
	
}
