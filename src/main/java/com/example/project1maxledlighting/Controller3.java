package com.example.project1maxledlighting;


import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller3 implements Initializable {


    public TextArea valuesTable;
    public TextArea movementsTable;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PowerManagement pm = PowerManagement.getInstance(new File("signal.txt"));

        valuesTable.appendText(pm.getValuesTable().replace("null",""));
        movementsTable.appendText(pm.getMovementsTable().replace("null",""));


    }
    public void switchToStart() throws IOException {
        PowerManagement.getInstance(null).reset();
        App.loadScene("view1.fxml");
    }
}
