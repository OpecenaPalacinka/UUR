import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class representing one ship module
 * 
 * @author Richard Lipka
 *
 */
public class Module {
	private String name;
	private double weight;
	private double price;
	private String description;
	private ModuleType type;
	
	public Module(ModuleType type, String name, double weight, double price, String description) {
		super();
		this.name = name;
		this.weight = weight;
		this.price = price;
		this.description = description;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDecription() {
		return description;
	}

	public void setDecription(String decription) {
		this.description = decription;
	}

	public ModuleType getType() {
		return type;
	}

	public void setType(ModuleType type) {
		this.type = type;
	};	
	
	public static ObservableList<Module> getDefaultData() {
		ObservableList<Module> result = FXCollections.observableArrayList();
		
		result.add(new Module(ModuleType.POD, "Sputnik", 10, 100, "Basic automated pod"));
		result.add(new Module(ModuleType.POD, "Friendship 7", 500, 2000, "Basic pod with life support for one human"));
		result.add(new Module(ModuleType.POD, "Helios", 2000, 30000, "Pod with life support for 3 astronauts"));
		
		result.add(new Module(ModuleType.PAYLOAD, "Sun lab", 5, 1500, "Simple lab for sun observation"));
		result.add(new Module(ModuleType.PAYLOAD, "Gravity lab", 500, 2500, "Lab for experiments with gravity field"));
		result.add(new Module(ModuleType.PAYLOAD, "Lander", 500, 22000, "Lander for one atronaut"));
		
		result.add(new Module(ModuleType.FUEL_TANK, "LT-Mk1", 500, 800, "Small tank for liquid fuel"));
		result.add(new Module(ModuleType.FUEL_TANK, "LT-Mk2", 3500, 24000, "Medium tank for liquid fuel"));
		result.add(new Module(ModuleType.FUEL_TANK, "LT-Mk3", 10000, 80500, "Large tank for liquid fuel"));
		
		result.add(new Module(ModuleType.ENGINE, "Eagle 1", 350, 33500, "Simple liquid fuel engine"));
		result.add(new Module(ModuleType.ENGINE, "Eagle 2", 700, 45200, "Large liquid fuel engine"));
		result.add(new Module(ModuleType.ENGINE, "Orion", 2000, 132000, "Nuclear propulsion"));
		
		result.add(new Module(ModuleType.AERODYNAMICS, "Small winglet", 20, 300, "Small wings at the rocket tail"));
		result.add(new Module(ModuleType.AERODYNAMICS, "Large winglet", 50, 480, "Large wings at the rocket tail"));
		result.add(new Module(ModuleType.AERODYNAMICS, "Duplex winglet", 100, 600, "Wings on the tail and trunk of the rocket"));
		
		return result;
	}
}
