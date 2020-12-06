package org.VoituLex.view;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;

public class SimulationController {

    @FXML
    private AnchorPane canvas;

    @FXML
    private Group root;

    public SimulationController(){

    }

    public Group getGroup() {
        return root;
    }


}
