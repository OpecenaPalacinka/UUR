import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Airplane {
	private String name;
	private AirplaneType type;
	private double weight;
	private double range;
	private LocalDate firstFlight;
	
	public Airplane(String name, AirplaneType type, double weight, double range, LocalDate firstFlight) {
		this.name = name;
		this.type = type;
		this.weight = weight;
		this.range = range;
		this.firstFlight = firstFlight;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public AirplaneType getType() {
		return type;
	}
	
	public void setType(AirplaneType type) {
		this.type = type;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public double getRange() {
		return range;
	}
	
	public void setRange(double range) {
		this.range = range;
	}	
	
	public LocalDate getFirstFlight() {
		return firstFlight;
	}
	
	public void setFirstFlight(LocalDate firstFlight) {
		this.firstFlight = firstFlight;
	}
	
	public String toString() {
		return name;
	}
	
	public static ObservableList<Airplane> getDefaultData() {
		ObservableList<Airplane> airplanes = FXCollections.observableArrayList();
		
		airplanes.add(new Airplane("Boeing 747", AirplaneType.PASSENGER, 112000, 13450, LocalDate.of(1969, 2, 9)));
		airplanes.add(new Airplane("Boeing 707", AirplaneType.PASSENGER, 333400, 7300, LocalDate.of(1957, 12, 20)));
		airplanes.add(new Airplane("Airbus A300", AirplaneType.PASSENGER, 132000, 9600, LocalDate.of(1972, 10, 28)));
		
		airplanes.add(new Airplane("Ilyushin Il-76", AirplaneType.CARGO, 92500, 4300, LocalDate.of(1971, 4, 25)));
		airplanes.add(new Airplane("Lockheed C-5 Galaxy", AirplaneType.CARGO, 249000, 4440, LocalDate.of(1968, 6, 30)));
		
		airplanes.add(new Airplane("Vickers F.B.5", AirplaneType.INTERCEPTOR, 555, 403, LocalDate.of(1914, 7, 17)));
		airplanes.add(new Airplane("F-22 Raptor", AirplaneType.INTERCEPTOR, 19700, 2960, LocalDate.of(1997, 9, 7)));
		airplanes.add(new Airplane("Sukhoi Su-15", AirplaneType.INTERCEPTOR, 23973, 1700, LocalDate.of(1962, 5, 30)));
		
		airplanes.add(new Airplane("Tupolev Tu-160", AirplaneType.BOMBER, 110000, 12300, LocalDate.of(1981, 12, 19)));
		airplanes.add(new Airplane("B-17 Flying Fortress", AirplaneType.BOMBER, 16391, 3219, LocalDate.of(1935, 6, 28)));
		
		airplanes.add(new Airplane("Lockheed HC-130", AirplaneType.TANKER, 34826, 8334, LocalDate.of(2010, 7, 29)));		
		
		return airplanes;
	}
}
