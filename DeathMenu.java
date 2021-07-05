import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DeathMenu {
    private final Text heading = new Text();
    private Button resume;
    private Button restart;
    private Button exit;
    private Scene scene;

    public Button getResumeButton() {
        return resume;
    }

    public Button getRestartButton() {
        return restart;
    }

    public Button getExitButton() {
        return exit;
    }

    public Scene getScene() {
        return scene;
    }

    private Button createButton(Image i, int fh) {
        Button button = new Button();
        button.setPrefSize(120, 100);
        button.setStyle("-fx-background-color: #272727");
        ImageView x = new ImageView(i);
        x.setFitHeight(fh);
        x.setPreserveRatio(true);
        button.setGraphic(x);
        return button;
    }

    public void update(){
        heading.setText("Oops, You died :(");
        heading.setFont(Font.font("Blissful Thinking",120));
        heading.setFill(Color.WHITE);
        heading.setX(640);
        heading.setY(360);

        Image i1 = new Image("ResumeGame.png");
        resume = createButton(i1, 120);

        Image i2 = new Image("RestartGame.png");
        restart = createButton(i2, 120);

        Image i3 = new Image("Exit.png");
        exit = createButton(i3, 50);

        VBox layout = new VBox();
        layout.setSpacing(100);
        layout.setStyle("-fx-background-color: #272727");
        layout.setPadding(new Insets(40, 35, 35, 35));
        layout.setAlignment(Pos.CENTER);
        layout.setMinHeight(720);
        layout.setMinWidth(1280);
        layout.getChildren().addAll(heading, resume, restart, exit);
        scene = new Scene(layout);
    }
}
