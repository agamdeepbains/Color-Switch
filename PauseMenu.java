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

class PauseMenu {
    private final Text heading = new Text();
    private Button resume;
    private Button save;
    private Scene scene;

    public Button getResumeButton() {
        return resume;
    }

    public Button getSaveButton() {
        return save;
    }

    public Scene getScene() {
        return scene;
    }

    private Button createButton(Image i) {
        Button button = new Button();
        button.setPrefSize(120, 100);
        button.setStyle("-fx-background-color: #272727");
        ImageView x = new ImageView(i);
        x.setFitHeight(120);
        x.setPreserveRatio(true);
        button.setGraphic(x);
        return button;
    }

    public void update(){
        heading.setText("GAME PAUSED");
        heading.setFont(Font.font("Blissful Thinking",120));
        heading.setFill(Color.WHITE);
        heading.setX(640);
        heading.setY(360);

        Image i1 = new Image("ResumeGame.png");
        resume = createButton(i1);

        Image i2 = new Image("SaveGame.png");
        save=createButton(i2);

        VBox layout = new VBox();
        layout.setSpacing(100);
        layout.setStyle("-fx-background-color: #272727");
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.CENTER);
        layout.setMinHeight(720);
        layout.setMinWidth(1280);
        layout.getChildren().addAll(heading,resume,save);
        scene = new Scene(layout);
    }
}