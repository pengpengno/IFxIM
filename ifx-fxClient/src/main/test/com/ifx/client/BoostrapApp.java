package com.ifx.client;

import cn.hutool.core.net.url.UrlBuilder;
import com.jfoenix.svg.SVGGlyph;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.net.URL;

public class BoostrapApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {                   //(1)
        Panel panel = new Panel("This is the title");
        panel.getStyleClass().add("panel-primary");                            //(2)
        BorderPane content = new BorderPane();
        content.setPadding(new Insets(20));
//        Button button = new Button("Hello BootstrapFX");
//        button.getStyleClass().setAll("btn","btn-danger");                     //(2)
//        content.setCenter(button);
        panel.setBody(content);

        Scene scene = new Scene(panel);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());       //(3)
//        scene.getStylesheets().add("css/button.css");       //(3)
        URL url = UrlBuilder.of("icon/menu/add.svg").toURL();
//        SVGGlyph svgGlyph = SVGGlyphLoader.loadGlyph(url);
//        SVGGlyph svgGlyph = SVGGlyphLoader(url);
        Button button1 = new Button("6666666");
//        Image image = new Image();
        button1.getStylesheets().add("css/button.css");
        content.setBottom(button1);

        button1.getStyleClass().setAll("btn","gg-add");
//        button1.getStyleClass().setAll("btn","gg-airplane");
        SVGGlyph svgGlyph1 = new SVGGlyph("M2 12C2 6.47715 6.47715 2 12 2C17.5228 2 22 6.47715 22 12C22 17.5228 17.5228 22 12 22C6.47715 22 2 17.5228 2 12ZM12 4C7.58172 4 4 7.58172 4 12C4 16.4183 7.58172 20 12 20C16.4183 20 20 16.4183 20 12C20 7.58172 16.4183 4 12 4Z");
//        new SVGGlyph("icon/menu/add.svg")
//        Image image = new Image(url.getPath());

//        pa
//        panel.getChildren().add(svgGlyph);
        panel.getChildren().add(svgGlyph1);

        primaryStage.setTitle("BootstrapFX");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}