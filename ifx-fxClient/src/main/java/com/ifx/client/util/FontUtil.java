package com.ifx.client.util;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * @author pengpeng
 * @description
 * @date 2023/7/22
 */
public interface FontUtil {

    Font ArialFont = Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 20);

    public static Label defaultLabel(int size,String label){
        Label l = new Label(label);

        l.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 20));
        l.setStyle("-fx-text-fill: white;");
        return l ;
    }



    public static Font bigFont(){
        return defaultFont(30);
    }


    public static Font defaultFont(Integer size)  {
       return Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, size);
    }
//    public static <T extends Region>  T defaultLabel(int size, String label, Class<T> tClass) throws ClassNotFoundException {
//        Label l = new Label(label);
//        return Class.forName(tClass.getName());
//
//        l.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 20));
//        l.setStyle("-fx-text-fill: white;");
//        return l ;
//    }


}
