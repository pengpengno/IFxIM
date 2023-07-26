package com.ifx.client;

import com.jfoenix.svg.SVGGlyphLoader;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.util.Set;

public class BoostrapApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {                   //(1)
        Panel panel = new Panel("This is the title");
        panel.getStyleClass().add("panel-primary");                            //(2)
        BorderPane content = new BorderPane();
        content.setPadding(new Insets(20));
//        Button button = new Button("Hello BootstrapFX");
        panel.setBody(content);

        Scene scene = new Scene(panel);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());       //(3)
//        URL url = UrlBuilder.of("icon/menu/add.svg").toURL();
//        URL url = UrlBuilder.of("icon/menu/icomoon.svg").toURL();
        SVGGlyphLoader.loadGlyphsFont(this.getClass().getResourceAsStream("icon/menu/icomoon.svg"),"icomoonn.svg");
        Set<String> allGlyphsIDs = SVGGlyphLoader.getAllGlyphsIDs();
//        SVGGlyph svgGlyph = SVGGlyphLoader(url);
        Button button1 = new Button("6666666");
//        Image image = new Image();
//        button1.getStylesheets().add("css/button.css");
        content.setBottom(button1);

//        button1.getStyleClass().setAll("btn","gg-add");
//        panel.getChildren().add(svgGlyph1);

        primaryStage.setTitle("BootstrapFX");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}