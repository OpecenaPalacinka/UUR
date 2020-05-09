import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * Vytvořil jsem metodu tryParse, která by měla pomocí try-catch bloku převést String na Int a když vznikne vyjímka
 * tak vrátit starou hodnotu. Ovšem tato metoda mi nefunguje a nevím proč. Zkusil jsem dát řádku 73-75 do try-catch bloku
 * předtím než jsem vytvořil metodu tryParse a to také nefungovalo. Mohl by jste mi prosím říct, proč to takto nefunguje?
 * Děkuji za odpověd :)
 * @author Jan Pelikán
 */

public class Main extends Application {
    TableView<DataModel> table = new TableView<DataModel>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Tabulka");
        primaryStage.setScene(getScene());
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(500);
        primaryStage.show();
    }

    private Scene getScene() throws Exception {
        return new Scene(getRoot(), 490, 300);
    }

    private Parent getRoot() throws Exception {
        VBox vBox = new VBox();

        table.setEditable(true);
        table.setItems(createData());
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<DataModel, String> nameCol = new TableColumn<>("Jméno fontu");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(column -> new ConsumingTextFieldTableCell<DataModel, String>());

        TableColumn<DataModel, Barvy> colorCol = new TableColumn<>("Barva fontu");
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
        colorCol.setCellFactory(ComboBoxTableCell.forTableColumn(Barvy.values()));
        colorCol.setOnEditCommit(event -> event.getRowValue().setColor(event.getNewValue()));

        TableColumn<DataModel, Rez> rezCol = new TableColumn<>("Řez fontu");
        rezCol.setCellValueFactory(new PropertyValueFactory<>("rez"));
        rezCol.setCellFactory(ComboBoxTableCell.forTableColumn(Rez.values()));

        TableColumn<DataModel, Integer> sizeCol = new TableColumn<>("Velikost");
        sizeCol.setCellValueFactory(new PropertyValueFactory<DataModel, Integer>("size"));
        sizeCol.setCellFactory(column -> new ConsumingTextFieldTableCell<>(new IntegerStringConverter()));
        sizeCol.setOnEditCommit(event -> {
                event.getRowValue().setSize(tryParse(String.valueOf(event.getNewValue()),event.getOldValue()));
        });


        TableColumn<DataModel, Boolean> visibCol = new TableColumn<>("Viditelnost");
        visibCol.setCellValueFactory(new PropertyValueFactory<DataModel, Boolean>("visibility"));
        visibCol.setCellFactory(CheckBoxTableCell.forTableColumn(visibCol));

        TableColumn<DataModel, String> nahledCol = new TableColumn<>("Náhled");
        nahledCol.setMinWidth(105);
        nahledCol.setEditable(false);
        nahledCol.setCellFactory(column -> new TableCell<DataModel, String>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if(getTableRow() != null){
                            DataModel dataModel = getTableRow().getItem();
                            if (dataModel != null) {
                                if (dataModel.isVisibility()) {

                                    setText(dataModel.getName());
                                    setTextFill(Color.valueOf(dataModel.getColor().toString()));
                                    switch (dataModel.getRez()) {
                                        case bold:
                                            setStyle("-fx-font-weight: bold;");
                                            setStyle("-fx-font-size: "+dataModel.getSize()+";");
                                            break;
                                        case italic:
                                            setStyle("-fx-font-style: italic;");
                                            setStyle("-fx-font-size: "+dataModel.getSize()+";");
                                            break;
                                        case normal:
                                            setStyle("-fx-font-style: normal;");
                                            setStyle("-fx-font-size: "+dataModel.getSize()+";");
                                            break;
                                    }
                                } else {
                                    setText(null);
                                }
                            }
                        }
                    }
        });

        nahledCol.setCellValueFactory(value -> new StringBinding() {
            {
                super.bind(value.getValue().nameProperty(),value.getValue().colorProperty(),value.getValue().rezProperty(),
                        value.getValue().sizeProperty(),value.getValue().visibilityProperty());
            }
            @Override
            protected String computeValue() {
                return value.getValue().nameProperty().get();
            }
        });

        table.getColumns().addAll(nameCol, colorCol, rezCol, sizeCol, visibCol, nahledCol);

        TextField info1 = new TextField();
        Button info = new Button("Info");
        info.setOnAction(p -> show(table.getSelectionModel().getSelectedItem(), info1));
        Button delete = new Button("Smazat");
        delete.setOnAction(p -> delete());
        Button add = new Button("Přidat");
        add.setOnAction(p -> table.getItems().add(new DataModel()));

        HBox hBox = new HBox();
        hBox.setSpacing(35);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(info, delete, add);
        vBox.getChildren().addAll(table, info1, hBox);


        return vBox;
    }

    public int tryParse(String value, int defaultValue){
        try {
            int retur = Integer.parseInt(value);
            return retur;
        } catch (NumberFormatException e){
            System.out.println("Zadali jste neplatný znak, vracím defaultní hodnotu.");
            return defaultValue;
        }
    }

    private void show(DataModel selectedItem, TextField lInfo) {
        if(table.getSelectionModel().getSelectedItem() != null){
            lInfo.setText(selectedItem.toString());
        }
    }

    private void delete() {
        ObservableList<DataModel> selection = table.getSelectionModel().getSelectedItems();
        table.getItems().removeAll(selection);
        table.getSelectionModel().clearSelection();
    }

    private ObservableList<DataModel> createData() {
        ObservableList<DataModel> data = FXCollections.observableArrayList();
        data.add(new DataModel());
        data.add(new DataModel());
        data.add(new DataModel());
        return data;
    }

    public static class ConsumingTextFieldTableCell<S, T> extends TextFieldTableCell<S, T> {
        public ConsumingTextFieldTableCell(StringConverter<T> converter) {
            super(converter);

            setOnKeyReleased(event -> {
                if (event.getCode() == KeyCode.DELETE) {
                    event.consume();
                }
            });
        }

        public ConsumingTextFieldTableCell() {
            this((StringConverter<T>) new DefaultStringConverter());
        }
    }


}
