import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

class MainMenu {

    private Scene scene;
    private Button newGame;
    private Button loadGame;
    private Button exit;

    public Scene getScene() {
        return scene;
    }

    public Button getNewGameButton() {
        return newGame;
    }

    public Button getLoadGameButton() {
        return loadGame;
    }

    public Button getExitButton() {
        return exit;
    }

    public void update(){
        Image i1 = new Image("NewGame.png");
        newGame = createButton(i1, 170);

        Image i2 = new Image("LoadGame.png");
        loadGame = createButton(i2, 170);

        Image i3 = new Image("Exit.png");
        exit = createButton(i3, 50);

        VBox layout = new VBox();
        layout.setSpacing(100);
        layout.setStyle("-fx-background-color: #272727");
        layout.setPadding(new Insets(35, 35, 35, 35));
        layout.setAlignment(Pos.CENTER);
        layout.setMinHeight(720);
        layout.setMinWidth(1280);
        layout.getChildren().addAll(newGame, loadGame, exit);
        scene = new Scene(layout);
    }

    private Button createButton(Image i, int fh) {
        Button button = new Button();
        button.setPrefSize(500, fh);
        button.setStyle("-fx-background-color: #272727");
        ImageView x = new ImageView(i);
        x.setFitHeight(fh);
        x.setPreserveRatio(true);
        button.setGraphic(x);
        return button;
    }
}
