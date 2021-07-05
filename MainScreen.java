import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.scene.control.*;

class MainScreen{

    private Scene scene;
    private final Button button = new Button();

    public Scene getScene() {
        return scene;
    }

    public Button getPlayButton() {
        return button;
    }

    public void update(){
        int screenX=960,screenY=540,radius=140;

        Text heading=new Text();
        heading.setText("C   L   R  SWITCH");
        heading.setFont(Font.font("Blissful Thinking",120));
        heading.setFill(Color.WHITE);
        heading.setX(screenX/2-395);
        heading.setY(screenY/2-150);

        Group root=new Group();

        root.getChildren().add(heading);

        root=makeCircle((screenX/2-400)+125,(screenY/2-150)-40,37,10,-1,0,root);
        root=makeCircle((screenX/2-400)+260,(screenY/2-150)-40,37,10,1,0,root);

        root=makeCircle(screenX/2,screenY/2+50,radius,25,-1,0,root);
        root=makeCircle(screenX/2,screenY/2+50,radius-30,20,1,90,root);
        root=makeCircle(screenX/2,screenY/2+50,radius-55,15,-1,0,root);

        Circle playOut=new Circle();
        playOut.setCenterX(screenX/2);
        playOut.setCenterY(screenY/2+50);
        playOut.setRadius(70);
        playOut.setFill(Color.rgb(75,75,75));

        root.getChildren().add(playOut);

        Polygon playButton=new Polygon();
        playButton.getPoints().setAll((double)(screenX/2-20),(double)(screenY/2+50-40),(double)(screenX/2-20),(double)(screenY/2+50+40),(double)(screenX/2+40),(double)(screenY/2+50));
        playButton.setFill(Color.WHITE);

        button.setGraphic(playButton);
        button.setPickOnBounds(false);
        button.setLayoutX(screenX/2 - 28);
        button.setLayoutY(screenY/2 + 4);
        button.setStyle("-fx-background-color: #4B4B4B");
        root.getChildren().add(button);

        scene = new Scene(root,screenX,screenY,Color.rgb(27,27,27));
    }

    private Group makeCircle(int centerX, int centerY, int radius, int width, int rot, int angle, Group root){
        Arc arcYe = new Arc();
        createArc(arcYe, centerX, centerY, radius, 0, width, 250, 225, 0);

        Arc arcPu = new Arc();
        createArc(arcPu, centerX, centerY, radius, 90, width, 140, 13, 255);


        Arc arcPi = new Arc();
        createArc(arcPi, centerX, centerY, radius, 180, width, 255, 1, 129);

        Arc arcBl=new Arc();
        createArc(arcBl, centerX, centerY, radius, 270, width, 50, 219, 240);

        Group subGroup=new Group();
        subGroup.getChildren().addAll(arcYe,arcPu,arcPi,arcBl);

        Rotate c=new Rotate();
        c.setAngle(angle);
        c.setPivotX(centerX);
        c.setPivotY(centerY);

        subGroup.getTransforms().add(c);

        RotateTransition rotate;
        rotate=new RotateTransition();
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setByAngle(360*rot);
        rotate.setCycleCount(Animation.INDEFINITE);
        rotate.setDuration(Duration.millis(5000));
        rotate.setAutoReverse(false);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setNode(subGroup);

        rotate.play();

        root.getChildren().add(subGroup);
        return root;
    }

    private void createArc(Arc arc, int centerX, int centerY, int radius, int startAngle, int width, int fillR, int fillG, int fillB){
        arc.setCenterX(centerX);
        arc.setCenterY(centerY);
        arc.setRadiusX(radius);
        arc.setRadiusY(radius);
        arc.setStartAngle(startAngle);
        arc.setLength(90);
        arc.setType(ArcType.ROUND);
        arc.setStroke(Color.rgb(fillR,fillG,fillB));
        arc.setStrokeWidth(width);
        arc.setType(ArcType.OPEN);
        arc.setFill(Color.rgb(27,27,27));
    }
}