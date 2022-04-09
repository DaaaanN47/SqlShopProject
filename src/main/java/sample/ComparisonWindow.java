package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ComparisonWindow implements Initializable {

    @FXML
    private HBox comparisonHbox;

    GraphicCardModel firstGraphicCard;
    GraphicCardModel secondGraphicCard;

    Controller mainController;

    ArrayList<GraphicCardModel> graphicCardModels = new ArrayList<>();
    private EventHandler<ActionEvent> eventHandler;

    public void setGraphicCardModels(ArrayList<GraphicCardModel> graphicCardModels) {
        this.graphicCardModels = graphicCardModels;
    }

    ArrayList<VBox> vBoxes = new ArrayList<>();

    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                deleteCardFromComp((Button)actionEvent.getSource());

            }
        };
    }

    public void initGCardsColumns(ArrayList<GraphicCardModel> graphicCardModels){

        for(int i = 0; graphicCardModels.size() > i; i++){
            VBox vBox = new VBox();
            TextField nameField = new TextField();
            nameField.setEditable(true);
            nameField.setText(graphicCardModels.get(i).name);
            TextField brandNameField = new TextField();
            brandNameField.setEditable(true);
            brandNameField.setText(graphicCardModels.get(i).brandName);
            TextField memoryField = new TextField();
            memoryField.setEditable(true);
            memoryField.setText(String.valueOf((graphicCardModels.get(i).memory)));
            TextField gpuFreqField = new TextField();
            gpuFreqField.setEditable(true);
            gpuFreqField.setText(String.valueOf(graphicCardModels.get(i).gpuFreq));
            TextField priceField = new TextField();
            priceField.setEditable(true);
            priceField.setText(String.valueOf(graphicCardModels.get(i).price));

            Label nameLabel = new Label("Название");
            Label brandLabel = new Label("Производитель");
            Label memoryLabel = new Label("Объем памяти");
            Label gpuFreqLabel = new Label("Частота процессора");
            Label priceLabel = new Label("Цена");

            vBox.setSpacing(10);
            Button button = new Button("Удалить");
            button.setId(String.valueOf(i));
            button.setOnAction(eventHandler);
            vBox.setStyle("-fx-background-color: grey");
            vBox.getChildren().addAll(nameLabel,nameField,brandLabel,brandNameField,memoryLabel,memoryField,gpuFreqLabel,gpuFreqField,priceLabel,priceField, button);
            vBox.setPadding(new Insets(5,10,0,5));
            comparisonHbox.getChildren().add(vBox);

            vBoxes.add(vBox);
        }

    }

    private void deleteCardFromComp(Button button) {
        for (int i = 0; i < vBoxes.size(); i++){
            if(vBoxes.get(i).getChildren().get(10).equals(button)){
                vBoxes.remove(i);
                graphicCardModels.remove(i);
                refresh();
                break;
            }
        }
    }

    private void refresh() {
        comparisonHbox.getChildren().clear();
        for (int i = 0; i < graphicCardModels.size(); i++){
            comparisonHbox.getChildren().add(vBoxes.get(i));
        }
    }
}
