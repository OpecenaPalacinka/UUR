/**
 * Enum defining types of ship modules
 * 
 * @author Richard Lipka
 *
 */
public enum ModuleType {	
	POD("Pod", 0), 
	PAYLOAD("Payload", 10),
	FUEL_TANK("Fuel tank", 20),	 
	ENGINE("Engine", 30),
	AERODYNAMICS("Aerodynamics", 40),
	UNKNOWN("Unknown", 10000);
	
	private final String typeName;
	private final int order;
	
	private ModuleType(String name, int order) {
		this.typeName = name;
		this.order = order;
	}
	
	public String toString() {
		return typeName;
	}
}
