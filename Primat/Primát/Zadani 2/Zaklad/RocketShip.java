import java.util.HashMap;
import java.util.Map;

/**
 * Class representing one ship, composed in the program.
 * Ship is composed from modules, each module type can be present only once (one 
 * engine, one payload, one pod, one fuel tank, one set of winglets).
 * 
 * @author Richard Lipka
 *
 */
public class RocketShip {
	private String name;
	
	private HashMap<ModuleType, Module> parts = new HashMap<ModuleType, Module>(5);
		
	// setting and obtaining a name of the ship 
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}	
	//-------------------------------------------------------------------------
	
	/**
	 * Removing a specified module (cleaning its position)
	 * 
	 * @param toRemove module that should be removed
	 * @return true if the position was occupied and module was actually removed, false otherwise
	 */
	public boolean remove(Module toRemove) {
		if (toRemove == null) {
			return false;
		}
		
		if (parts.put(toRemove.getType(), null) == null) {
			return false; 
		} else {
			return true;
		}
	}
	
	/**
	 * Test of the position of specified type is occupied (if the ship have an engine, a fuel tank
	 * and so on)
	 * 
	 * @param tested position that should be tested
	 * @return true if the position is occupied
	 */
	public boolean typeIsUsed(ModuleType tested) {
		if (tested == null) {
			return false;
		}
		
		if (parts.get(tested) != null) {
			return true;
		} else {
			return false; 
		}		
	}

	/**
	 * Testing if the specific module is used now
	 * 
	 * @param tested module that should be tested
	 * @return true if the module is present in the ship on appropriate position
	 */
	public boolean moduleIsUsed(Module tested) {
		if (tested == null) {
			return false;
		}
		
		if (parts.get(tested.getType()) == tested) {
			return true;
		} else {
			return false; 
		}		
	}
	
	/**
	 * Obtaining a module that is now used on the specified position
	 * 
	 * @param type position of the module
	 * @return reference on the module on specified position, null otherwise 
	 */
	public Module getModule(ModuleType type) {
		return parts.get(type); 
	}
	
	/**
	 * Setting the module on the appropriate position (each module have
	 * a specific position)
	 * 
	 * @param newModule module that should be placed on the position, the 
	 *        type (position) of the module will be automatically used from
	 *        module 
	 */
	public void setModule(Module newModule) {
		parts.put(newModule.getType(), newModule);
	}	
	
	/**
	 * Determine if the ship have all required modules
	 * 
	 * @return ture if all module positions are occupied
	 */
	public boolean getComplete() {
		if ((parts.get(ModuleType.POD) != null) && 
			(parts.get(ModuleType.PAYLOAD) != null) && 
			(parts.get(ModuleType.FUEL_TANK) != null) && 
			(parts.get(ModuleType.ENGINE) != null) && 
			(parts.get(ModuleType.AERODYNAMICS) != null)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Removes all modules - the ship will contain no module 
	 */
	public void setAllNull() {
		for (Map.Entry<ModuleType, Module> entry : parts.entrySet()) {			
			ModuleType key = entry.getKey();
			parts.put(key, null);
		}		
	}
	
	/**
	 * Removes module on specified position
	 * 
	 * @param type position that should be cleared
	 */
	public void setPartNull(ModuleType type) {
		parts.put(type, null);
	}
	
	/**
	 * Computes total price of the ship - iterates over all positions
	 * 
	 * @return total price of all modules currently in the ship
	 */
	public double getPrice() {
		double price = 0;
		
		for (Map.Entry<ModuleType, Module> entry : parts.entrySet()) {			
			Module value = entry.getValue();
		    if (value != null) {
		    	price += value.getPrice();
		    }
		}
		
		return price;
	}
	
	/**
	 * Computes total weight of the ship - iterates over all positions
	 * 
	 * @return total weight of all modules currently in the ship
	 */
	public double getWeight() {
		double weight = 0;
		
		for (Map.Entry<ModuleType, Module> entry : parts.entrySet()) {			
			Module value = entry.getValue();
		    if (value != null) {
		    	weight += value.getWeight();
		    }
		}
		
		return weight;
	}	
}
