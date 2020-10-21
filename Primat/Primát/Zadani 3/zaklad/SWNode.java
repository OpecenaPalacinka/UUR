public class SWNode {
	private NodeType type;
	
	private String name;
	private String description;
	private Image photo;
		
	public SWNode(NodeType type, String name, String description, Image photo) {
		super();
		this.type = type;
		this.name = name;
		this.description = description;
		this.photo = photo;
	}

	public NodeType getType() {
		return type;
	}

	public void setType(NodeType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Image getPhoto() {
		return photo;
	}

	public void setPhoto(Image photo) {
		this.photo = photo;
	}

	public boolean isHierarchy() {
		return type.isHierarchy();
	}
}
