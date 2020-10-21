package cz.zcu.kiv.lipka.uur.exams.tree2fx;

public enum NodeType {
	HIERARCHY("Hierarchy", true),
	CHARACTER("Must have a name", false);
	
	private String typeName;
	private boolean hierarchy;
	
	private NodeType(String name, boolean hierarchy) {
		this.typeName = name;
		this.hierarchy = hierarchy;
	}
	
	public String toString() {
		return typeName;
	}
	
	public boolean isHierarchy() {
		return hierarchy;
	}
}
