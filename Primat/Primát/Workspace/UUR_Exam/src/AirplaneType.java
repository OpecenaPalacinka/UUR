public enum AirplaneType {
	UNKNOWN("Unknown"), 
	PASSENGER("Passenger plane"),
	CARGO("Cargo plane"),
	INTERCEPTOR("Interceptor"),
	BOMBER("Bomber"),
	TANKER("Tanker");
	
	private String typeName;
	
	private AirplaneType(String typeName) {
		this.typeName = typeName;
	}
	
	public String toString() {
		return typeName;
	}

}
