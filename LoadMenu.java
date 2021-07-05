import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

class LoadMenu {
    private final Text heading = new Text();
    private Scene scene;
    private final Text show = new Text();
    private Button button;
    private final TextField textField = new TextField();

    public Button getButton() {
        return button;
    }

    public TextField getTextField() {
        return textField;
    }

    public Scene getScene() {
        return scene;
    }

    public boolean update(){
        heading.setText("LOAD GAME");
        heading.setFont(Font.font("Blissful Thinking",120));
        heading.setFill(Color.WHITE);
        heading.setX(640);
        heading.setY(360);

        int y = -1;
        FileInputStream x;
        try {
            x = new FileInputStream("load.txt");
            y = x.read();
            x.close();
        } catch (Exception e) {
//            e.printStackTrace();
        }
        String s;
        boolean flag;
        if (y == -1)
            y = 0;
        else
            y -= 48;
        if (y > 0){
            s = "You have " + y + " saved games. Enter the number (1 - " + y + ") to load: ";
            flag = true;
        }
        else {
            s = "You have no saved games :(";
            flag = false;
        }
        show.setText(s);
        show.setFont(Font.font("Blissful Thinking",35));
        show.setFill(Color.WHITE);

        Image i = new Image("LoadGame.png");
        button = new Button();
        button.setPrefSize(120, 100);
        button.setStyle("-fx-background-color: #272727");
        ImageView img = new ImageView(i);
        img.setFitHeight(120);
        img.setPreserveRatio(true);
        button.setGraphic(img);

        VBox layout = new VBox();
        layout.setSpacing(100);
        layout.setStyle("-fx-background-color: #272727");
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.CENTER);
        layout.setMinHeight(720);
        layout.setMinWidth(1280);
        layout.getChildren().addAll(heading, show, textField, button);
        scene = new Scene(layout);
        return flag;
    }
}