package cz.zcu.kiv.lipka.uur.exercise12;

import java.util.ArrayList;
import java.util.List;

import cz.zcu.kiv.lipka.uur.exercise12.data.Port;
import cz.zcu.kiv.lipka.uur.exercise12.data.Ship;

public class DataSource {
	private ArrayList<Ship> ships = new ArrayList<Ship>();
	private ArrayList<Port> ports = new ArrayList<Port>();
	
	public DataSource() {
		generateShips();
	}
	
	private void generatePorts() {
		ports.add(new Port("Hamburg"));
		ports.add(new Port("London"));		
		ports.add(new Port("Lisabon"));
		ports.add(new Port("Gdansk"));
		ports.add(new Port("Murmansk"));
	}
	
	private void generateShips() {
		generatePorts();
		
		ships.add(new Ship("Bismarc", 2500, ports.get(0)));
		ships.add(new Ship("Prince Albert", 500, ports.get(0)));
		ships.add(new Ship("Germania", 750, ports.get(0)));
		ships.add(new Ship("Berlin", 370, ports.get(0)));		
		
		ships.add(new Ship("Lady Grace", 500, ports.get(1)));
		ships.add(new Ship("Savannah", 250, ports.get(1)));
		ships.add(new Ship("Victory", 1500, ports.get(1)));
		ships.add(new Ship("Alistair", 300, ports.get(1)));
		ships.add(new Ship("Enterprise", 2700, ports.get(1)));
		
		ships.add(new Ship("Vasco da Gama", 300, ports.get(2)));
		ships.add(new Ship("Christobal", 1200, ports.get(2)));
		ships.add(new Ship("Tristao da Cunha", 380, ports.get(2)));
		ships.add(new Ship("Albuquerque ", 1300, ports.get(2)));
		
		ships.add(new Ship("Chmelnicky", 1500, ports.get(3)));
		ships.add(new Ship("Krakow", 1250, ports.get(3)));
		ships.add(new Ship("Westerplate", 730, ports.get(3)));
		
		ships.add(new Ship("Kirov", 1500, ports.get(4)));
		ships.add(new Ship("Kiev", 620, ports.get(4)));
		ships.add(new Ship("Minsk", 1300, ports.get(4)));
		ships.add(new Ship("Leonov", 1700, ports.get(4)));
		ships.add(new Ship("Yuri", 2600, ports.get(4)));		
	}
	
	public List<Ship> getShips() {
		return ships;
	}
	
	public List<Port> getPorts() {
		return ports;
	}
	
	public List<Ship> getShipsByPort(Port port) {
		ArrayList<Ship> result = new ArrayList<Ship>();
		ships.stream()
		     .filter(ship -> ship.getPortRegistration().equals(port))
		     .sorted((Ship s1, Ship s2) -> {
		    	return s1.getName().compareTo(s2.getName()); 
		     })
		     .forEach(ship -> result.add(ship)); 
		
		return result;	
	}
	
	public int getPortTonnage(Port port) {
		return ships.stream()
		            .filter(ship -> ship.getPortRegistration().equals(port))
		            .mapToInt(ship -> ship.getTonnage())
		            .sum();
	}
	
	public int getPortID(String portName) {
		for (Port port : ports) {
			if (port.getName().compareTo(portName) == 0) return ports.indexOf(port);
		}
		return -1;
	}
	
	public Port getPortByName(String portName) {
		for (Port port : ports) {
			if (port.getName().compareTo(portName) == 0) return port;
		}
		return null;
	}	
	
	public int getShipID(String shipName) {
		for (Ship ship : ships) {
			if (ship.getName().compareTo(shipName) == 0) return ships.indexOf(ship);
		}
		return -1;
	}	
	
	public Ship getShipByName(String shipName) {
		for (Ship ship : ships) {
			if (ship.getName().compareTo(shipName) == 0) return ship;
		}
		return null;
	}		
}
