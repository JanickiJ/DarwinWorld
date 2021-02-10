package agh.cs.project.view;

import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Grass;
import agh.cs.project.elements.Vector2d;
import agh.cs.project.engine.IEngine;
import agh.cs.project.engine.SimulationEngine;
import agh.cs.project.map.MapStatistics;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

import static java.lang.Integer.min;


public class Visualisation {


    private final int rows;
    private final int columns;
    private final int squareSize;
    private final Image[] animalsImage;
    private final String[] animalsName;
    private final Image grassImage;

    private boolean showDominateGenotype;
    private Timeline timeline;
    private final IEngine engine;
    private final VBox statisticPanel;
    private final HBox topPanel;
    private final GridPane gridPane;
    private final BorderPane bPane;
    private HBox bottomPanel;
    private final VBox rightPanel;
    MapStatistics statistics;
    String resourcesPath = "resources/agh/cs/project/";


    public Visualisation(SimulationEngine engine, int squareSize, int columns, int rows, BorderPane bPane) {
        animalsName = new String[] {"chick", "chicken","rabbit","parrot", "snake", "pig" , "cow","bear", "narwhal"};
        animalsImage = new Image[animalsName.length];
        for(int i =0; i< animalsName.length; i++){
            animalsImage[i] = new Image(resourcesPath + animalsName[i] + ".png");
        }
        grassImage = new Image(resourcesPath + "grass.png");

        this.engine = engine;
        this.squareSize = squareSize;
        this.rows = rows;
        this.columns = columns;
        this.bPane = bPane;
        this.showDominateGenotype = false;

        topPanel = new HBox();
        statisticPanel = new VBox();
        gridPane = new GridPane();
        bottomPanel = new HBox();
        statistics = engine.getIWorldMap().getMapStatistics();
        rightPanel = new VBox();
    }


    public void drawBorderPane() {
        drawBackground();
        drawIMapElements();
        drawStatisticsPanel();
        drawTopPanel();
        bPane.getChildren().clear();
        bPane.setLeft(statisticPanel);
        bPane.setCenter(gridPane);
        bPane.setTop(topPanel);
        bPane.setBottom(bottomPanel);
    }

    public void setBottomPanel(HBox bottomPanel){
        this.bottomPanel = bottomPanel;
    }

    private void drawTopPanel() {
        topPanel.getChildren().clear();
        topPanel.setSpacing(10);
        topPanel.setAlignment(Pos.TOP_LEFT);

        Rectangle start = new Rectangle(40,40);
        Rectangle stop = new Rectangle(40,40);
        start.setFill(new ImagePattern(new Image(resourcesPath + "start.png")));
        stop.setFill(new ImagePattern(new Image(resourcesPath + "stop.png")));

        start.setOnMouseClicked((MouseEvent e)-> timeline.play());
        stop.setOnMouseClicked((MouseEvent e)-> timeline.pause());

        topPanel.getChildren().addAll(start,stop);

        Text text = new Text(" "+statistics.getDominantGenotype().getKey().getGenom());
        Rectangle show = new Rectangle(40,40);
        show.setOnMouseClicked((MouseEvent e)-> showDominateGenotype = !showDominateGenotype);
        show.setFill(new ImagePattern(new Image(resourcesPath + "dominative.png")));

        topPanel.getChildren().addAll(show, text);
    }


    private void drawStatisticsPanel() {
        statisticPanel.getChildren().clear();
        statisticPanel.setSpacing(7);

        Text title = new Text("Statistics");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));


        Text age = new Text("Age: " + engine.getAge());
        Text title1 = new Text("Num of Animals: " + statistics.getNumOfAliveAnimals());
        Text title2 = new Text("Num of Grass: " + statistics.getNumOfGrass());
        Text title4 = new Text("Average energy: " + statistics.getAverageEnergy());
        Text title5 = new Text("Average life: " + statistics.getAverageLifeTime());
        Text title6 = new Text("Average num of children: " + statistics.getAverageNumOfChildren());
        Text title7 = new Text("");

        Text title8 = new Text("Levels of energy in ascending order: ");
        title8.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        statisticPanel.getChildren().addAll(title,age, title1,title2,title4,title5,title6,title7,title8);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        for (int i=0; i< animalsImage.length ;i++){
            Image image = animalsImage[i];
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            gridPane.add(imageView,0,i);
        }
        for (int i=0; i< animalsImage.length ;i++){
            Text text = new Text(" " + animalsName[i]);
            gridPane.add(text,1,i);
        }
        statisticPanel.getChildren().add(gridPane);
    }


    private void drawIMapElements() {
        List<Animal> animalsToDraw = engine.getAnimalsToDraw();
        List<Grass> grassToDraw = engine.getGrassToDraw();
        for(Grass g : grassToDraw){
            int x = g.getPosition().x;
            int y = g.getPosition().y;
            Rectangle rectangle = new Rectangle(x*squareSize,y*squareSize,squareSize,squareSize);
            rectangle.setFill(new ImagePattern(grassImage));
            gridPane.add(rectangle, x*squareSize,y*squareSize);
        }

        int minEnergy = engine.getIWorldMap().getMapStatistics().getMinEnergy();
        int maxEnergy = engine.getIWorldMap().getMapStatistics().getMaxEnergy();
        Image animalImage;
        for(Animal a : animalsToDraw){
            if(showDominateGenotype && statistics.getDominantGenotype().getValue().contains(a)){
                animalImage = new Image(resourcesPath + "dominative.png");
            }
            else if ((maxEnergy - minEnergy) / animalsImage.length == 0){
                animalImage = animalsImage[animalsImage.length/2];
            }
            else {
                int idx = min(a.getEnergy() / ((maxEnergy - minEnergy) / animalsImage.length +minEnergy), animalsImage.length-1);
                animalImage = animalsImage[idx];
            }
            int x = a.getPosition().x;
            int y = a.getPosition().y;
            Rectangle rectangle = new Rectangle(x*squareSize, y*squareSize, squareSize,squareSize);
            rectangle.setFill(new ImagePattern(animalImage));
            rectangle.setOnMouseClicked((MouseEvent e) ->{
                System.out.println(a.getGenes().getGenom());
                statistics.setTrackedAnimal(a);
            });
            gridPane.add(rectangle, x*squareSize,y*squareSize);
        }
    }

    private void drawBackground(){
        gridPane.getChildren().clear();
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                Rectangle rectangle = new Rectangle(i*squareSize,j*squareSize,squareSize,squareSize);
                if(engine.getIWorldMap().inJungle(new Vector2d(i,j))){
                    rectangle.setFill(Color.web("#008000"));
                }
                else if((i+j)%2 == 0){
                    rectangle.setFill(Color.web("AAD751"));
                }
                else{
                    rectangle.setFill(Color.web("A2D149"));
                }
                gridPane.add(rectangle,i*squareSize,j*squareSize);
            }
        }
    }

    public void addTrackingAnimalPanel(){
        Text text1 = new Text("Tracking Animal statistics");
        text1.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Text text2 = new Text("Num of Descendants: " + statistics.getEndNumOfDescendants());
        Text text3 = new Text("Num of Children: " + statistics.getEndNumOfChildren());
        Text text4 = new Text("Death time: " + statistics.getDeadTime());
        Text text5 = new Text("Genotype: " );
        Text text6 = new Text(""+statistics.getTrackedAnimal().getGenes().getGenom());
        text6.setFont(Font.font("Arial", FontWeight.BOLD, 8));

        rightPanel.getChildren().addAll(text1,text2,text3,text4,text5,text6);
        bPane.setRight(rightPanel);
    }


    public Timeline getTimeline() {
        return timeline;
    }

    public IEngine getEngine() {
        return engine;
    }
    public void setTimeline(Timeline timeline){
        this.timeline = timeline;
    }
    public MapStatistics getStatistics(){
        return statistics;
    }


}
