import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;

public class Job extends Thing implements Runnable{
	
	private double duration;
	protected ArrayList<String> skills;
	JProgressBar pb;
	JPanel panel;
	World world;
	JButton suspend, stop;
	Boolean sus, sto;
	Dock dock;
	SeaPort port;
	Ship ship;
	ArrayList<Person> workers;
	ArrayList<Person> working;
	ArrayList<Person> waiting;
	DefaultTableModel model;
	String status = "";
	
	public Job(Scanner sc, JPanel pane, World w, DefaultTableModel model) {
		super(sc);
		this.model = model;
		workers = new ArrayList<Person>();
		working = new ArrayList<Person>();
		waiting = new ArrayList<Person>();
		skills = new ArrayList<String>();
		if (sc.hasNextDouble()) {duration = sc.nextDouble();}
		while (sc.hasNext()) {
			skills.add(sc.next());
		}
		panel = pane;
		pb = new JProgressBar();
		pb.setStringPainted(true);
		panel.add(pb);
		panel.add(new JLabel(this.getName()));	
		
		suspend = new JButton("suspend");
		stop = new JButton("Stop");
		panel.add(suspend);
		panel.add(stop);
		
		world = w;
		sus = sto = true;
		
		model.addRow(new Object [] {this.getName(), this.duration, skills ,working ,waiting});
		
		new Thread (this).start();
		ship = world.getShip(this.getParent());
		dock = world.getDock(ship.getParent());
		port = world.getJobsPort(ship);
	}

	public void showStatus() {
		
	}
	
	@Override
	public void run() {
		long time = System.currentTimeMillis();
		long startTime = time;
		long stopTime = (long) (time + 1000 * this.duration);
		double duration = stopTime - time;	

		status = "Starting";
		suspend.addActionListener((ActionEvent e) -> {
			sus = !sus;
			if (!sus) {
				suspend.setBackground(Color.ORANGE);
			} else
				suspend.setBackground(null);

		});

		stop.addActionListener((ActionEvent e) -> {
			sto = false;
			stop.setBackground(Color.RED);
		});
		
		waitForDock();

		boolean busy = true;
		
		synchronized(port.persons) {
			while (busy) {
				//adds workers to list
				if (fillWorkers()) {} else {
					sto = false;
					addRow();
					break;
				}
				
				//If no workers are needed, start Job
				if (workers.isEmpty()) {
					addRow();
					break;
				}
				
				//check if worker is busy and wait
				for (Person w : workers) {
					for (Person p: port.persons) {
						if (p.equals(w))
							if (p.working) {
								status = "Waiting";
								addRow();
								try {
									
									port.persons.wait();
								} catch (InterruptedException e) {

								}
							} else {
								working.add(p);
								waiting.remove(p);
								p.working = true;
								busy = false;
								addRow();
							}	

					}

				}
			}
			
		}
		//run job
		while (time < stopTime && sto) {
			status = "In Progress";
			addRow();
			if (sus) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
				time += 100;
				pb.setValue((int) (((time - startTime) / duration) * 100));
				addRow();
			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			} 
		}

		finishJob();

	}

	public void finishJob() {
		
		ship.jobs.remove(ship.jobs.indexOf(this));
		if (ship.jobs.isEmpty()) {
			port.que.add(ship);
			ship.parent = port.getIndex();
			dock.addShip(null);
		}
		if (sto) {
			suspend.setEnabled(false);
			stop.setEnabled(false);
			suspend.setBackground(Color.GREEN);
			stop.setBackground(Color.GREEN);
			status = "Finished";
		} else {
			suspend.setEnabled(false);
			stop.setEnabled(false);
			suspend.setBackground(Color.RED);
			stop.setBackground(Color.RED);
			status = "Failed";
		}
		

		synchronized(port.persons) {
				for (Person w: workers ) {
					for (Person p: port.persons) {
						if (p.equals(w))
						p.working = false;
						skills.remove(p.getSkill());
					}
				}
				this.workers.removeAll(workers);
			port.persons.notifyAll();
			working.removeAll(working);
			waiting.removeAll(waiting);
			addRow();
			
		}
	}

	/**
	 * Checks for open dock if current ship is not in dock
	 */
	public void waitForDock() {
		while (dock==null) {
			for (Dock d: port.docks) {
				if (d.getShip() == null) {
					this.dock = d;
					d.addShip(this.ship);
					port.que.remove(this.ship);
					break;
				}
			}				
		}
	}

	/**
	 * Adds the workers from the port
	 */
	public boolean fillWorkers() {
		//Get workers with skills from port
		for (String sk: skills) {
			boolean hasWorker = false;
			for (Person p : port.persons) {
				if (p.getSkill().equals(sk)) {
					if (workers.contains(p)) {
						hasWorker = true;
					} else { 
						workers.add(p);
						waiting.add(p);
						hasWorker = true;
					}
					
				}
			}
			//if no skill found
			if (hasWorker == false) {
				System.out.println(this.getName() + " has no " + sk + " for port " + this.dock.parent);
				return false;
			}
		}
		return true;
	}
	
	public void addRow() {
		String skills = "";
		for (String s: this.skills) {
			skills += s + " ";
		}
		String working = "";
		for (Person s: this.working) {
			working += s.getName() + " ";
		}
		String waiting = "";
		for (Person s: this.waiting) {
			waiting += s.getName() + " ";
		}
		
		Object [] data = {this.duration, skills, waiting, working, status};
		updateRow(data);
	}
	
	private void updateRow(Object[] data) {
	    for (int i = 0; i < model.getRowCount(); i++)
	        if (model.getValueAt(i, 0).equals(this.getName()))
	            for (int j = 1; j < data.length+1; j++)
	                model.setValueAt(data[j-1], i, j);
	}

}
