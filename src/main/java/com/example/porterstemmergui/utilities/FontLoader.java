package com.example.porterstemmergui.utilities;

import com.example.porterstemmergui.HelloApplication;
import javafx.scene.text.Font;

public class FontLoader {
    private static Font f;

    private static void loadFont (int fontSize, String style) {

        try {

            f = Font.loadFont(HelloApplication.class.getResource(style).toExternalForm()
                    ,fontSize);

            // use this font with our label
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String poppinsFile = "Poppins-SemiBold.ttf";
    private static String rubikFile = "RubikBrokenFax-Regular.ttf";

    public static Font getFont (int fontSize) {
        loadFont(fontSize, rubikFile);
        return f;
    }

    public static Font getPoppinsFont (int fontSize) {
        loadFont(fontSize, poppinsFile);
        return f;
    }



}
