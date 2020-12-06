package org.VoituLex.view;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.VoituLex.engine.App;
import org.VoituLex.model.Bot;
import org.VoituLex.model.Box;
import org.VoituLex.model.Context;
import org.VoituLex.physics.P2d;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class SimulationView {

    private  SimulationController controller;

    private static final int X_GAP = 50;
    private static final int Y_GAP = 50;
    private  Context context;
    private Scene scene;

    private  List<BotView> objects;
    private List<List<BoxView>> boxes;
    private Stage stage;
    private Group root;
    private Canvas canvas;
    private GraphicsContext gc;

    public SimulationView(Context context) {
        this.context = context;
        this.objects = new ArrayList<>();
        this.boxes = new ArrayList<>();
    }

    public void updateRender() {
        Platform.runLater(()-> {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            boxes.forEach(l -> l.forEach(b -> {

                if (b.getBox().getGuest().isPresent()
                        && b.getBox().getGuest().get().isArrived()) {
                    gc.setFill(Paint.valueOf("green"));
                    gc.fillRect(b.x, b.y,
                            BoxView.DEFAULT_BOX_SIZE, BoxView.DEFAULT_BOX_SIZE);
                } else if (!b.getBox().isOnCorridor()) {
                    gc.strokeRect(b.x, b.y,
                            BoxView.DEFAULT_BOX_SIZE, BoxView.DEFAULT_BOX_SIZE);
                } else {
                    gc.setFill(b.getFill());
                    gc.fillRect(b.x, b.y,
                            BoxView.DEFAULT_BOX_SIZE, BoxView.DEFAULT_BOX_SIZE);
                }
            }));
            objects.forEach(b -> {
                try {
                    //System.out.println("bot->" + b.getX() + "-" + b.getY());
                    gc.drawImage(b, b.getX(), b.getY());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }

    public void createRender()  {
        stage = new Stage();
        scene = new Scene(loadFXML("/simulationView"));
        scene.setFill(Color.TRANSPARENT);
        stage.setWidth(context.getColumnsNum() * BoxView.DEFAULT_BOX_SIZE +
                ((BoxView.DEFAULT_BORDER_SIZE*2)*context.getColumnsNum()));
        stage.setHeight(context.getBoxes().size() * BoxView.DEFAULT_BOX_SIZE +
                ((BoxView.DEFAULT_BORDER_SIZE*2)*context.getColumnsNum()));

        this.canvas = new Canvas();
        this.canvas.setWidth(context.getColumnsNum() * BoxView.DEFAULT_BOX_SIZE +
                ((BoxView.DEFAULT_BORDER_SIZE*2)*context.getColumnsNum()));
        this.canvas.setHeight(context.getBoxes().size() * BoxView.DEFAULT_BOX_SIZE +
                ((BoxView.DEFAULT_BORDER_SIZE*2)*context.getColumnsNum()));
        stage.setScene(scene);

     //   String css = this.getClass().getResource("style.css").toExternalForm();
        // scene.getStylesheets().add(css);
        stage.setResizable(true);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/botImage.png")));
        stage.show();
        this.root = controller.getGroup();
        root.getChildren().add( canvas );
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Paint.valueOf("red"));
        //gc.setGlobalBlendMode(BlendMode.DIFFERENCE);

    }

    public void fillBoxes(){
        for (int i = 0; i < context.getBoxes().size(); i++) {
            for (int j = 0; j < context.getBoxes().get(i).size(); j++) {
                context.getBoxes().get(i).get(j).setCenter(computeCenter(i, j));
            }
        }
         for(int i = 1 ; i <= context.getStartingQueue().size(); i++){
             context.getStartingQueue().get(i-1).setCenter(computeCenter(context.getCorridorIndex(), -i));
         }

        for (int i = 0; i < context.getBoxes().size(); i++) {
            boxes.add(new ArrayList<>());
            for (int j = 0; j < context.getBoxes().get(i).size(); j++) {
                boxes.get(i).add(new BoxView(context.getBoxes().get(i).get(j)));
            }
        }

        System.out.println(canvas.toString());
    }

    public void fillBots() {

        this.context.getBots().forEach(b -> {
          this.objects.add(new BotView(b));
        });
       // System.out.println("Bot da visualizzare :"+this.objects.size());

    }

    private P2d computeCenter(int i, int j) {

        return new P2d(X_GAP + (j * X_GAP * 2), Y_GAP + (i * Y_GAP * 2));
    }

     Parent loadFXML(final String fxml)  {
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
         Parent p = null;
         try {
             p =  fxmlLoader.load();
         } catch (IOException e) {
             e.printStackTrace();
         }
          controller = fxmlLoader.getController();
        return p;
    }
}
