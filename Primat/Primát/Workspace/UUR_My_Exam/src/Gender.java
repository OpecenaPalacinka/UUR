public enum Gender {
	MALE("Male"), FEMALE("Female");	
	
	private String genderName;
	
	private Gender(String name) {
		this.genderName = name;
	}
	
	public String toString() {
		return genderName;
	}
}
