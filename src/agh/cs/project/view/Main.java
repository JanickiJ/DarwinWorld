package agh.cs.project.view;

import agh.cs.project.engine.OptionsParser;
import agh.cs.project.engine.SimulationEngine;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;

import static agh.cs.project.engine.OptionsParser.loadPropFromFile;

public class Main extends Application {

    private int rows;
    private int columns;
    private int squareSize;
    OptionsParser parameters;


    @Override
    public void start(Stage primaryStage) {
        try {
            parameters = loadPropFromFile("src\\agh\\cs\\project\\parameters.json");
        } catch (IllegalArgumentException | FileNotFoundException ex) {
            System.out.println(ex);
        }

        this.rows = parameters.getWidth();
        this.columns = parameters.getHeight();
        this.squareSize = 15;
        int width = 1100;
        int height = 650;

        primaryStage.setTitle("Darwin World");
        BorderPane borderPane1 = new BorderPane();
        BorderPane borderPane2 = new BorderPane();
        Visualisation map1 = createAnimation(borderPane1);
        Visualisation map2 = createAnimation(borderPane2);
        map1.getTimeline().play();
        map2.getTimeline().play();

        HBox twoMaps = new HBox(borderPane1, borderPane2);
        twoMaps.setSpacing(50);
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(twoMaps);
        primaryStage.setScene(new Scene(scroll, width, height));
        primaryStage.show();


    }

    private void run(Visualisation visualisation) throws FileNotFoundException {
        visualisation.drawBorderPane();
        visualisation.getEngine().run();
        if(visualisation.getStatistics().getEndStatisticDate() == visualisation.getEngine().getAge()){
            visualisation.getStatistics().writeTxt("statistics.txt");
            visualisation.getStatistics().setMakeStatistics(false);
            visualisation.addTrackingAnimalPanel();
            visualisation.getTimeline().pause();
        }
    }

    private Visualisation createAnimation(BorderPane borderPane) {
        SimulationEngine engine = new SimulationEngine(parameters);
        Visualisation visualisation = new Visualisation(engine,squareSize,columns,rows,borderPane);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(250), e -> {
            try {
                run(visualisation);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }));
        makeNStatistics(visualisation);
        timeline.setCycleCount(Animation.INDEFINITE);
        visualisation.setTimeline(timeline);
        return visualisation;
    }

    public void makeNStatistics(Visualisation visualisation){
        TextField textField = new TextField("5");
        Text text = new Text("Stop, click on Animal, insert n, click button to show animal and average statistics ");
        Button button = new Button("Button");
        button.setOnAction(e -> {
            visualisation.getStatistics().setMakeStatistics(true);
            visualisation.getStatistics().setEndStatisticDate((visualisation.getEngine().getAge())+ Integer.parseInt(textField.getText()));
        });
        HBox bottom = new HBox();
        bottom.setSpacing(20);
        bottom.getChildren().addAll(text,button,textField);
        visualisation.setBottomPanel(bottom);
    }

}