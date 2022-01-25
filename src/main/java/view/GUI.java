package view;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

/*
 * Name: Mikkel Bentsen && Oliver rasoli
 * Date: 25/16/2022
 */

public class GUI extends Application
{

    public static void main(String[] args)
    {
        assert args != null;
        launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        /* Variables*/
        final int WINDOW_WIDTH = 475, WINDOW_HEIGHT = 550;
        final AnchorPane root = createPane(stage);
        final Scene scene = new Scene(root);

        /*style sheet*/
        scene.getStylesheets().add(new File("src/main/resources/css/style.css").toURI().toString());

        /*Window setup*/
        stage.getIcons().add(new Image(new File("src/main/resources/images/icon.png").toURI().toString()));
        stage.setResizable(false);
        stage.setTitle("Fishing Friend");
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
    /*Returns AnchorPane for GUI*/
    public static AnchorPane createPane(Stage stage)
    {
        /*GUI elements*/
        final AnchorPane pane = new AnchorPane();
        final Button start = new Button("Start");
        final Button stop = new Button("Stop");
        final Button close = new Button("Close");
        final Text console = new Text("Console");
        final Text hotkeys = new Text("Hotkeys");
        final Text fishingAbilityHead = new Text("Fishing Ability");
        final Text startHead = new Text("Start");
        final Text stopHead = new Text("Stop");
        final TextArea textArea = Console.INSTANCE.getOutput();
        final TextArea hotkeyFishing = new TextArea("4");
        final TextArea hotkeyStart = new TextArea();
        final TextArea hotkeyStop = new TextArea();
        final ScrollPane scrollPane = new ScrollPane(textArea);
        final Label labelRelog = Time.INSTANCE.getLabel();

        /*Label for time to next relog*/
        labelRelog.setVisible(true);
        labelRelog.setLayoutX(55);
        labelRelog.setLayoutY(475);
        labelRelog.setTextFill(Color.rgb(226, 226, 226));

        /*Text for start hotkey*/
        startHead.setLayoutX(217);
        startHead.setLayoutY(380);
        startHead.setFill(Color.rgb(226, 226, 226));

        /*Text for stop hotkey*/
        stopHead.setLayoutX(367);
        stopHead.setLayoutY(380);
        stopHead.setFill(Color.rgb(226, 226, 226));

        /*Text for fishing hotkey*/
        fishingAbilityHead.setLayoutX(70);
        fishingAbilityHead.setLayoutY(380);
        fishingAbilityHead.setFill(Color.rgb(226, 226, 226));

        /*Text for hotkey header*/
        hotkeys.setLayoutX(180);
        hotkeys.setLayoutY(350);
        hotkeys.setStyle("-fx-font: 24 arial;");
        hotkeys.setFill(Color.rgb(226, 226, 226));

        /*Text Area for fishing hotkey*/
        hotkeyFishing.setId("hotkey");
        hotkeyFishing.setLayoutY(390);
        hotkeyFishing.setLayoutX(80);
        hotkeyFishing.setMaxHeight(1);
        hotkeyFishing.setMaxWidth(10);

        /*Text Area for start hotkey*/
        hotkeyStart.setLayoutY(390);
        hotkeyStart.setLayoutX(210);
        hotkeyStart.setMaxHeight(1);
        hotkeyStart.setMaxWidth(10);

        /*Text Area for stop hotkey*/
        hotkeyStop.setLayoutY(390);
        hotkeyStop.setLayoutX(360);
        hotkeyStop.setMaxHeight(1);
        hotkeyStop.setMaxWidth(10);

        /*Text console settings/styling. */
        console.setLayoutX(185);
        console.setLayoutY(40);
        console.setStyle("-fx-font: 24 arial;");
        console.setFill(Color.rgb(226, 226, 226));

        /*Scrollpane settings/styling. */
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefWidth(400);
        scrollPane.setPrefHeight(150);
        scrollPane.setLayoutX(30);
        scrollPane.setLayoutY(60);
        scrollPane.setStyle("-fx-vbar-policy: NEVER;");

        /*TextArea settings/styling. */
        textArea.setId("textArea");
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setStyle("-fx-control-inner-background:#242424; -fx-text-box-border: #242424; -fx-focus-color: #242424;");

        /*ImageView settings/styling. */
        Image image = new Image(new File("src/main/resources/images/spinningWheel.gif").toURI().toString());
        ImageView imageView = new ImageView();
        imageView.setId("wheelImageView");
        imageView.setImage(image);
        imageView.setFitHeight(21.0);
        imageView.setFitWidth(21.0);
        imageView.setLayoutX(15);
        imageView.setLayoutY(475);
        imageView.setVisible(false);

        /*Button(Start) settings/styling. */
        start.setCursor(Cursor.HAND);
        start.setMinHeight(40);
        start.setMinWidth(110);
        start.setMaxHeight(40);
        start.setMaxWidth(110);
        start.setLayoutX(70);
        start.setLayoutY(250);

        /*Button(Stop) settings/styling. */
        stop.setCursor(Cursor.HAND);
        stop.setMinHeight(40);
        stop.setMinWidth(110);
        stop.setMaxHeight(40);
        stop.setMaxWidth(110);
        stop.setLayoutX(70);
        stop.setLayoutY(250);
        stop.setVisible(false);

        /*Button(Close) settings/styling. */
        close.setCursor(Cursor.HAND);
        close.setMinHeight(40);
        close.setMinWidth(110);
        close.setMaxHeight(40);
        close.setMaxWidth(110);
        close.setLayoutX(280);
        close.setLayoutY(250);

        /*Lambda actions on button clicks*/
        start.setOnAction(e ->
        {Controller.INSTANCE.start(); imageView.setVisible(true); stop.setVisible(true); start.setVisible(false);});
        stop.setOnAction(e ->
        {Controller.INSTANCE.stop(); imageView.setVisible(false); start.setVisible(true); stop.setVisible(false);});
        close.setOnAction(e -> stage.close());

        /*Adding all elements to AnchorPane/GUI */
        pane.styleProperty().set("-fx-background-color: #181818");
        pane.getChildren().add(start);
        pane.getChildren().add(stop);
        pane.getChildren().add(close);
        pane.getChildren().add(imageView);
        pane.getChildren().add(scrollPane);
        pane.getChildren().add(console);
        pane.getChildren().add(hotkeyFishing);
        pane.getChildren().add(hotkeyStart);
        pane.getChildren().add(hotkeyStop);
        pane.getChildren().add(hotkeys);
        pane.getChildren().add(fishingAbilityHead);
        pane.getChildren().add(startHead);
        pane.getChildren().add(stopHead);
        pane.getChildren().add(labelRelog);
        return pane;
    }
}
