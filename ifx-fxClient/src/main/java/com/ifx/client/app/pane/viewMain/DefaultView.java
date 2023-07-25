package com.ifx.client.app.pane.viewMain;

import com.ifx.client.app.enums.APPEnum;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/8
 */
@Component
public class DefaultView extends GridPane implements Initializable, ViewAction {

    private Label defaultLabel;


    public void initPane(){

        defaultLabel = new Label(" There is no view , try to click dashboard");

        defaultLabel.setAlignment(Pos.CENTER);

        Font customFontBoldItalic = Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 20);

        defaultLabel.setStyle("-fx-text-fill: white;");

        defaultLabel.setFont(customFontBoldItalic);

        this.setBackground(new Background(new BackgroundFill(Color.rgb(136,10,160),null,null)));


        this.getChildren().add(defaultLabel);
    }

    @Override
    public void size(Long width, Long height) {
        ViewAction.super.size(width, height);
        this.setPrefSize(width,height);
    }

    @Override
    public APPEnum viewType() {
        return APPEnum.DEFAULT;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPane();
    }
}
