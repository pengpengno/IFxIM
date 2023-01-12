//package com.ifx.videoTest;
//
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.image.Image;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Font;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//
//import javax.swing.text.html.ImageView;
//
///**
// * @author pengpeng
// * @description
// * @date 2023/1/10
// */
//public abstract class UI{
//    public static Stage get_video_ui(String title, ImageView video_image, EventHandler<ActionEvent> value) {
//        VBox vBox = new VBox(10);
//        Text text = new Text("视频通话中...");
//        text.setFont(Font.font("黑体",20));
//        String[] button_text = {"结束视频通话","通话录音"};
//        Button[] button = new Button[2];
//        for (int i = 0; i < button.length; i++) {
//            button[i] = new Button(button_text[i]);
//            button[i].setFont(Font.font("黑体", 20));
//            button[i].setPrefSize(360,40);
//            button[i].setOnAction(value);
//        }
//        video_image.setSize(360f,340f);
//        video_image.(new Image("/0.png"));
//        vBox.getChildren().addAll(text, video_image, button[0], button[1]);
//        vBox.setAlignment(Pos.CENTER);
//        Stage stage = new Stage(StageStyle.UTILITY);
//        stage.setTitle(title);
//        stage.setResizable(false);
//        stage.setScene(new Scene(vBox,400,500));
//        return stage;
//    }
//
//    public static Stage get_audio_ui(String title,ImageView audio_image, EventHandler<ActionEvent> value) {
//        VBox vBox = new VBox(10);
//        Text text = new Text("音频通话中...");
//        text.setFont(Font.font("黑体",20));
//        String[] button_text = {"结束音频通话","通话录音"};
//        Button[] button = new Button[2];
//        for (int i = 0; i < button.length; i++) {
//            button[i] = new Button(button_text[i]);
//            button[i].setFont(Font.font("黑体", 20));
//            button[i].setPrefSize(360,40);
//            button[i].setOnAction(value);
//        }
//        audio_image.setFitWidth(360);
//        audio_image.setFitHeight(340);
//        audio_image.setImage(new Image("/0.png"));
//        vBox.getChildren().addAll(text, audio_image, button[0], button[1]);
//        vBox.setAlignment(Pos.CENTER);
//        Stage stage = new Stage(StageStyle.UTILITY);
//        stage.setTitle(title);
//        stage.setResizable(false);
//        stage.setScene(new Scene(vBox, 400, 500));
//        return stage;
//    }
//}