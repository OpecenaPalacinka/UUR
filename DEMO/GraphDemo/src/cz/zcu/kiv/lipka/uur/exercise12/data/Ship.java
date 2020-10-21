package cz.zcu.kiv.lipka.uur.exercise12.data;

public class Ship {
	private String name;
	private int tonnage;
	private Port portRegistration;
		
	public Ship(String name, int tonnage, Port portRegistration) {
		super();
		this.name = name;
		this.tonnage = tonnage;
		this.portRegistration = portRegistration;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getTonnage() {
		return tonnage;
	}
	
	public void setTonnage(int tonnage) {
		this.tonnage = tonnage;
	}
	
	public Port getPortRegistration() {
		return portRegistration;
	}
	
	public void setPortRegistration(Port portRegistration) {
		this.portRegistration = portRegistration;
	}	
}
