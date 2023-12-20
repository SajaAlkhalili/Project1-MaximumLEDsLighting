package com.example.project1maxledlighting;


import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {

    public AnchorPane connectionPane;
    public Image powerImg = new Image(getClass().getResourceAsStream("img/ipower.png"));
    public Image ledOnImg = new Image(getClass().getResourceAsStream("img/ledon.png"));
    public Image ledoffImg = new Image(getClass().getResourceAsStream("img/ledoff.png"));
    public List<HBox> powerList = new ArrayList<>();
    public List<HBox> ledList = new ArrayList<>();
    public List<Line> lineList = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {
            PowerManagement pm = PowerManagement.getInstance(null);
            for (int i = 1; i <= pm.getPowerCount(); i++) {
                Text powerNumber = new Text("" + i);
                ImageView imageView = getImgView(powerImg);
                HBox hBox = new HBox(10);
                hBox.setLayoutX(50);
                hBox.setLayoutY(i * 50);
                hBox.getChildren().addAll(powerNumber, imageView);
                powerList.add(hBox);
                connectionPane.getChildren().add(hBox);
            }

            for (int i = 0; i < pm.getPowerCount(); i++) {// for loop for the leds
                ImageView imgView = getImgView(pm.getOnList().contains(pm.getLedsArray()[i]) ? ledOnImg : ledoffImg);
                Text lightNumber = new Text("" + pm.getLedsArray()[i]);
                HBox hBox = new HBox(10);
                hBox.setAlignment(Pos.CENTER);
                hBox.setLayoutX(App.primaryStage.getWidth() - 100);
                hBox.setLayoutY((i + 1) * 50);
                ledList.add(hBox);
                if (pm.getOnList().contains(pm.getLedsArray()[i])) {
                    setPowerLine(pm.getLedsArray()[i], i);//put number power and number led
                }
                hBox.getChildren().addAll(imgView, lightNumber);
                connectionPane.getChildren().add(hBox);
            }
        });

        App.primaryStage.widthProperty().addListener(((observable, oldValue, newValue) -> {
            double chnageInX = newValue.doubleValue() - oldValue.doubleValue();
            ledList.forEach(e -> e.setLayoutX(e.getLayoutX() + chnageInX));
            lineList.forEach(e -> e.setStartX(e.getStartX() + chnageInX));
        }));

    }

    private void setPowerLine(int powerPosition, int lightPosition) {

        Line line = new Line();
        line.setStroke(Color.BLUE);
        line.setStrokeWidth(3);
        HBox ledBox = ledList.get(lightPosition);
        HBox powerBox = powerList.get(powerPosition);
        line.setStartX(ledBox.getLayoutX());
        line.setStartY(ledBox.getLayoutY() + 15);
        line.setEndX(powerBox.getLayoutX() + 50);
        line.setEndY(powerBox.getLayoutY() - 30);
        lineList.add(line);
        connectionPane.getChildren().add(line);

    }


    private ImageView getImgView(Image img) {

        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(30);
        imgView.setFitHeight(30);
        imgView.setPreserveRatio(true);
        return imgView;

    }


    public void switchToTable() throws IOException {
        App.loadScene("view3.fxml");
    }

    public void switchToStart() throws IOException {
        PowerManagement.getInstance(null).reset();
        App.loadScene("view1.fxml");
    }
}