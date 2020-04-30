import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.KeyCode;

public class DatamodelDisplayCell extends TreeCell<Datamodel>{
	// TextField that serves as an editor
	private TextField textTF;
	
	// method switching cell into the editation state
	public void startEdit() {
		super.startEdit();
		
		// creates editor if it is not already available
		if (textTF == null) {
			createEditor();
		}
		
		// disabling renderer
		setText(null);
		// setting up editor - creatign content
		textTF.setText(createEditorContent());
		// displaying editor in the cell
		setGraphic(textTF);
	}
	
	// method switching cell into displaying state
	public void cancelEdit() {
		super.cancelEdit();

		// setting content of the renderer (label)
		setText(createContent());
		// removing editor from the cell
		setGraphic(null);
	}
	
	// method setting updated value to the cell
	public void updateItem(Datamodel item, boolean empty) {
		super.updateItem(item, empty);
		
		// nothing is displayed for empty cell
		if (empty) {
			setText(null);
			setGraphic(null);	
		} else {
			// in editation state
			if (isEditing()) {				
				if (textTF != null) {
					// setting content of the editor
					textTF.setText(createEditorContent());
					// disabling renderer
					setText(null);
					// adding editor to the scene graph
					setGraphic(textTF);
				}
			// in displaying state	
			} else {			
				// creating content of the renderer
				setText(createContent());
				// disabling editor
				setGraphic(null);
			}
		}
	}
	
	// creates content for the renderer
	private String createContent() {
		// content is composed from the symbol (file / directory) and name
		return getItem().getTyp().getSymbol() + " " + getItem().getName();
	}
	
	// creates content for the editor
	private String createEditorContent() {
		// editor displays only name
		return getItem().getName();
	}
	
	// creates editor itself
	private void createEditor() {
		// editor is based on the text field
		textTF = new TextField();		
		// adding reaction to the pressed key, in order to determine if the
		// new value was commited or if the editation was canceled
		textTF.setOnKeyReleased(event -> {
			// when editation is confirmed
			if (event.getCode() == KeyCode.ENTER) {
				// veryfying if new value was really provided  
				if (textTF.getText().length() == 0) {
					// when no value was provided, editation is canceled
					cancelEdit();
				// when new value was provided and confirmed	
				} else {					
					// getting acces to the element from the model, representing
					// edited value
					Datamodel item = getItem();
					// new name is set to the mode
					item.setName(textTF.getText());
					// commit event is fired
					commitEdit(item);
				}
			// when editation is canceled	
			} else if (event.getCode() == KeyCode.ESCAPE) {
				// cancel event is fired
				cancelEdit();
			}			
		});
	}
	
	
}
