package com.example.project1maxledlighting;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller1 implements Initializable {
    public Button openBtn;
    public Button startBtn;
    public Text lengthText;
    public Text maxLightText;

    private FileChooser fileChooser = new FileChooser();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        startBtn.setDisable(true);
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));

    }


    public void openFile() {

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            PowerManagement pm  = PowerManagement.getInstance(selectedFile);
            if(pm.isValid()){//checks  (if the file have the duplicate)
                lengthText.setFill(Color.BLACK);
                lengthText.setText("The Length of the Maximum Leds : "+pm.getOnList().size());
                maxLightText.setText("The maximum leds light is : "+pm.getOnList().toString().replaceAll("\\W"," "));
                startBtn.setDisable(false);
            }else {
                lengthText.setText("The file contains duplicates || number of led less than number of power.please choose another file");
                lengthText.setFill(Color.RED);
                pm.reset();
            }

        } else {

            System.out.println("No file selected.");
        }

    }

    public void switchToView2() throws IOException {
        App.loadScene("view2.fxml");
    }
}