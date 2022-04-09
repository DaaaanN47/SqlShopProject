package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    private AnchorPane mainPanel;

    @FXML
    private TabPane two_tabs;

    @FXML
    private Tab firstTab;

    @FXML
    private TableView<GraphicCardModel> shopTable;

    @FXML
    private TableColumn<GraphicCardModel, String > idCol;

    @FXML
    private TableColumn<GraphicCardModel, String > nameCol;

    @FXML
    private TableColumn<GraphicCardModel, String > brandCol;

    @FXML
    private TableColumn<GraphicCardModel, String > memoryCol;

    @FXML
    private TableColumn<GraphicCardModel, String > gpuFreq;

    @FXML
    private TableColumn<GraphicCardModel, String > price;

    @FXML
    private Tab secondTab;

    @FXML
    private TextField nameField;

    @FXML
    private TextField memoryFIeld;

    @FXML
    private TextField brandField;

    @FXML
    private Button addCardBtn;

    @FXML
    private ChoiceBox<String> brandFilter;

    @FXML
    private Button refreshTabel;

    @FXML
    private ChoiceBox<String> memoryFIlter;

    @FXML
    private ChoiceBox<String> brandContructor;

    @FXML
    private TextArea graphiccardInfoField;

    @FXML
    private TableView<BrandModel> brandsTable;

    @FXML
    private TableColumn<BrandModel, String > brandIdCol;

    @FXML
    private TableColumn<BrandModel, String > brandNameCol;

    @FXML
    private TableColumn<BrandModel, String > brandStatusCol;

    @FXML
    private Button disableBtn;

    @FXML
    private Button enableBtn;

    ArrayList<GraphicCardModel> graphicCardModels = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idCol = new TableColumn<>("id");
        shopTable.getColumns().add(idCol);
        nameCol = new TableColumn<>("name");
        shopTable.getColumns().add(nameCol);
        brandCol = new TableColumn<>("brandName");
        shopTable.getColumns().add(brandCol);
        memoryCol = new TableColumn<>("memory");
        shopTable.getColumns().add(memoryCol);
        gpuFreq = new TableColumn<>("gpuFreq");
        shopTable.getColumns().add(gpuFreq);
        price = new TableColumn<>("price");
        shopTable.getColumns().add(price);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brandName"));
        memoryCol.setCellValueFactory(new PropertyValueFactory<>("memory"));
        gpuFreq.setCellValueFactory(new PropertyValueFactory<>("gpuFreq"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        shopTable.setItems(DBConnector.getNewList());
        setFilters();
        shopTable.setOnMouseClicked(e -> { FillGCardInfo(shopTable.getSelectionModel().getSelectedItem());
        });

        brandIdCol = new TableColumn<>("id");
        brandsTable.getColumns().add(brandIdCol);
        brandNameCol = new TableColumn<>("name");
        brandsTable.getColumns().add(brandNameCol);
        brandStatusCol = new TableColumn<>("status");
        brandsTable.getColumns().add(brandStatusCol);

        brandIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        brandNameCol.setCellValueFactory(new PropertyValueFactory<>("brandName"));
        brandStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        brandsTable.setItems(DBConnector.getBrandList());
        //изменение цветов
        nameField.setStyle("-fx-background-color: F27878; -fx-text-fill: black;");
        nameField.setText("Привет");
    }

    public void clickCreateRow(ActionEvent actionEvent) {
        if(!nameField.getText().isEmpty() && !brandContructor.getSelectionModel().isEmpty() && !memoryFIeld.getText().isEmpty()){
            DBConnector.addNewInfo(nameField.getText(), brandContructor.getSelectionModel().getSelectedItem(), Integer.parseInt(memoryFIeld.getText()));
            System.out.println("все класно, добавили!");
        }
    }

    public void FillGCardInfo(GraphicCardModel graphicCardModel){
        if(graphicCardModel == null){

        }else {
            graphiccardInfoField.setText("Id: " + graphicCardModel.id + "\n" +
                    "name: " + graphicCardModel.name + "\n" +
                    "brand Id: " + graphicCardModel.brandId + "\n" +
                    "brand Name " + graphicCardModel.brandName + "\n" +
                    "memory: " + graphicCardModel.memory + "\n");
        }
    }
    public void setFilters(){
        brandFilter.setItems(DBConnector.createBrandListFilter(true));
        brandFilter.getSelectionModel().selectFirst();
        brandContructor.setItems(DBConnector.createBrandListFilter(false));
        memoryFIlter.setItems(DBConnector.createMemoryList());
        memoryFIlter.getSelectionModel().selectFirst();
    }

    public void clickRefreshTable(ActionEvent actionEvent) {
        shopTable.setItems(DBConnector.getNewList());
        setFilters();
    }

    public void clickUseFilter(ActionEvent actionEvent) {
        shopTable.setItems(DBConnector.UseFilter(brandFilter.getSelectionModel().getSelectedItem(),memoryFIlter.getSelectionModel().getSelectedItem()));
    }

    public void clickDisableBrand(ActionEvent actionEvent) {
        if(!brandsTable.getSelectionModel().isEmpty()) {
            brandsTable.setItems(DBConnector.changeStatus(0, brandsTable.getSelectionModel().getSelectedItem().id));
        }
        shopTable.setItems(DBConnector.getNewList());
    }

    public void clickEnableBrand(ActionEvent actionEvent) {
        if(!brandsTable.getSelectionModel().isEmpty()){
            brandsTable.setItems(DBConnector.changeStatus(1,brandsTable.getSelectionModel().getSelectedItem().id));
        }
        shopTable.setItems(DBConnector.getNewList());
    }

    public void clickAddToComparison(ActionEvent actionEvent) {
        if(shopTable.getSelectionModel().isEmpty()){

        }
        else{
            graphicCardModels.add(shopTable.getSelectionModel().getSelectedItem());
        }

    }

    public void clickOpenComparisonWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/comparisonWindow.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        ComparisonWindow comparisonWindow = fxmlLoader.getController();
        comparisonWindow.setMainController(this);
        comparisonWindow.setGraphicCardModels(this.graphicCardModels);
        comparisonWindow.initGCardsColumns(comparisonWindow.graphicCardModels);
        Stage stage = new Stage();
        stage.setTitle("Сравнение");
        stage.setScene(new Scene(root1));
        stage.show();;
    }
}

